package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.suhu.app.ui.theme.SuhuTheme

/**
 * Kontainer grup input form yang meniru seksi (section) Settings di iOS.
 * Membungkus sekumpulan TextField atau Picker, memberikan radius pinggir bundar (Medium/Large)
 * dengan background surface yang terpisah dari warna dasar aplikasi (background).
 */
@Composable
fun SuhuFormGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(SuhuTheme.shapes.large) // iOS-like container curve
            .background(SuhuTheme.colors.surface)
            .padding(vertical = SuhuTheme.spacing.extraSmall)
    ) {
        content()
    }
}
