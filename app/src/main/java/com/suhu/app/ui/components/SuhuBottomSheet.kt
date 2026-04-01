package com.suhu.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.springSmooth
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Bottom Sheet kustom yang meniru perilaku iOS Modal.
 * Menggantikan `ModalBottomSheet` Material dengan Foundation murni dan logika Draggable.
 */
@Composable
fun SuhuBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    // Animatable offset untuk fitur Draggable secara manual
    val offsetY = remember { Animatable(0f) }
    // Mematikan ripple klik
    val interactionSource = remember { MutableInteractionSource() }

    // Kembalikan offset ke asalnya bila state diatur menyembunyikan modal
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            offsetY.snapTo(0f)
        }
    }

    // 1. OVERLAY BACKDROP
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Scrim gelap. Pengurangan visibilitas murni lewat Alpha
                .background(SuhuTheme.colors.scrim.copy(alpha = 0.4f))
                // Glassmorphism Blur (Efek blur standar API ini berfungsi jika latar memiliki tekstur sendiri)
                .blur(radius = 16.dp) 
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onDismiss
                )
        )
    }

    // 2. KONTEN MODAL SHEET 
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it }, // Meluncur dari bawah layar penuh
            animationSpec = springSmooth()
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = springSmooth()
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    // Reaksi posisi saat modal ditarik dengan gesture Drag
                    .offset { IntOffset(0, offsetY.value.roundToInt()) }
                    .clip(
                        RoundedCornerShape(
                            topStart = 40.dp, // Radius ekstra melengkung
                            topEnd = 40.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .background(SuhuTheme.colors.surface)
                    // Mengintersep klik agar tak ditembus ke backdrop
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {} 
                    )
                    // Input pointer gesture (Menangani interaksi tarik ke bawah mereduksi offset)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                change.consume()
                                // Hanya mengizinkan tarikan ke bawah, tak melampaui posisi nol
                                val newOffset = (offsetY.value + dragAmount).coerceAtLeast(0f)
                                coroutineScope.launch {
                                    offsetY.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                // Apabila ditarik lebih dari 300px, dismiss
                                if (offsetY.value > 300f) {
                                    onDismiss()
                                } else {
                                    // Membal ke posisi awalnya (offset 0) dengan pegas mulus
                                    coroutineScope.launch {
                                        offsetY.animateTo(0f, springSmooth())
                                    }
                                }
                            }
                        )
                    }
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(bottom = SuhuTheme.spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(SuhuTheme.spacing.small))
                
                // Draggable Grabber Indikator visual (Pil) di atas
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(5.dp)
                        .clip(CircleShape)
                        .background(SuhuTheme.colors.outlineVariant)
                )
                
                Spacer(modifier = Modifier.height(SuhuTheme.spacing.large))

                // Slot Konten Transmisi Composable
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SuhuTheme.spacing.large)
                ) {
                    content()
                }
            }
        }
    }
}
