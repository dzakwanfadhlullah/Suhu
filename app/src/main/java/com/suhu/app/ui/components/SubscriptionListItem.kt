package com.suhu.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
importত্তির androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Komponen Baris Langganan bergaya iOS Settings.
 * Mengusung gestur Swipe-to-Delete yang ditulis secara manual menggunakan PointerInput, 
 * bebas ketergantungan API SwipeToDismiss milik Material Design.
 */
@Composable
fun SubscriptionListItem(
    serviceName: String,
    priceLabel: String,
    dateLabel: String,
    iconSymbol: String = "🎵",
    showDivider: Boolean = true,
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val density = LocalDensity.current
    val deleteActionWidth = with(density) { 80.dp.toPx() } // Threshold & dimensi tombol hapus (merah)

    // Container Interaksi Global
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(SuhuTheme.colors.surface)
            .clip(SuhuTheme.shapes.extraSmall) // Antisipasi melesat ke luar pojokan
    ) {
        // --- 1. LAYER BELAKANG: ACTION DELETE (Merah) ---
        // Jika list digeser ke kiri, warna merah dengan tulisan Hapus terlihat.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SuhuTheme.colors.error)
                // Implementasi efek klik iOS standar pada tombol list
                .bouncyClickable {
                    onDelete()
                    coroutineScope.launch { offsetX.animateTo(0f, tween(300)) }
                },
            contentAlignment = Alignment.CenterEnd
        ) {
            BasicText(
                text = "Hapus", 
                style = SuhuTheme.typography.labelLarge.copy(color = SuhuTheme.colors.onError),
                modifier = Modifier.padding(end = 24.dp)
            )
        }

        // --- 2. LAYER DEPAN: KONTEN KARTU LIST ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .background(SuhuTheme.colors.surface)
                .pointerInput(Unit) {
                    val velocityTracker = VelocityTracker()
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            velocityTracker.addPosition(change.uptimeMillis, change.position)
                            
                            // Gestur usap/sweep geser hanya mengizinkan tarikan ke kiri (nilai negatif) sejauh tombol Hapus.
                            val newOffset = (offsetX.value + dragAmount)
                                .coerceAtMost(0f) 
                                .coerceAtLeast(-deleteActionWidth - 100f) // Resistensi elastis simulasi apple di ujung swipe
                            
                            coroutineScope.launch {
                                offsetX.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            val velocityX = velocityTracker.calculateVelocity().x
                            
                            // Determinasi snap akhir: Tampilkan Hapus ATAU Tutup Hapus
                            val targetOffset = if (offsetX.value < -deleteActionWidth * 0.5f || velocityX < -500f) {
                                -deleteActionWidth // Terbuka
                            } else {
                                0f // Tertutup
                            }
                            
                            coroutineScope.launch {
                                // Mengembalikan offset dengan sedikit tween agar mulus
                                offsetX.animateTo(targetOffset, tween(250)) 
                            }
                        }
                    )
                }
                .clickable { onClick() } 
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Push ke ujung row agar padding terdistribusi penuh di tinggi Box
                        .padding(horizontal = SuhuTheme.spacing.large),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Ikon Layanan iOS-style (rounded kotak)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(SuhuTheme.shapes.medium)
                            .background(SuhuTheme.colors.surfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicText(
                            text = iconSymbol,
                            style = SuhuTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.width(SuhuTheme.spacing.medium))

                    // Detail Teks: Nama Layanan & Tenggat (Ditekan flexibilitasnya ke tengah)
                    Column(modifier = Modifier.weight(1f)) {
                        BasicText(
                            text = serviceName,
                            style = SuhuTheme.typography.bodyLarge.copy(
                                color = SuhuTheme.colors.onSurface
                            ),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        BasicText(
                            text = dateLabel,
                            style = SuhuTheme.typography.bodySmall.copy(
                                color = SuhuTheme.colors.secondary
                            ),
                            maxLines = 1
                        )
                    }

                    // Teks Harga di posisi ujung
                    BasicText(
                        text = priceLabel,
                        style = SuhuTheme.typography.bodyLarge.copy(
                            color = SuhuTheme.colors.secondary
                        )
                    )

                    Spacer(modifier = Modifier.width(SuhuTheme.spacing.small))

                    // Indikator panah ke menu detail layaknya Settings.app (Chevron Right)
                    BasicText(
                        text = "⟩",
                        style = SuhuTheme.typography.bodyLarge.copy(
                            color = SuhuTheme.colors.outlineVariant
                        )
                    )
                }

                // Divider Tipis iOS-style (1px ketebalan) - Hanya digambar garis lurus seukuran alignment label
                if (showDivider) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            // Padding 16dp.large + 40dp ikon + 16dp.medium -> offset 72.dp
                            .padding(start = 72.dp)
                            .height(0.5.dp) // Hair-line ketat 
                            .background(SuhuTheme.colors.surfaceVariant)
                    )
                }
            }
        }
    }
}
