package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme

/**
 * Custom Input TextField yang murni dikonstruksi tanpa Material Design (termasuk OutlinedTextField).
 * Memiliki indikator *hairline divider* di bawahnya (bisa dimatikan untuk form item terakhir).
 */
@Composable
fun SuhuTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    iconSymbol: String? = null,
    showDivider: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SuhuTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = SuhuTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon pada sisi kiri (Opsional)
            if (iconSymbol != null) {
                BasicText(
                    text = iconSymbol,
                    style = SuhuTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.width(SuhuTheme.spacing.medium))
            }

            // Area Tekstual Inti
            Box(
                modifier = Modifier.weight(1f), 
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    BasicText(
                        text = placeholder,
                        style = SuhuTheme.typography.bodyLarge.copy(color = SuhuTheme.colors.outlineVariant)
                    )
                }
                
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = SuhuTheme.typography.bodyLarge.copy(color = SuhuTheme.colors.onSurface),
                    cursorBrush = SolidColor(SuhuTheme.colors.primary), // Kursor warna brand
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Garis batas (Divider) setipis sehelai rambut, posisinya menyesuaikan adanya Ikon
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = if (iconSymbol != null) 48.dp else SuhuTheme.spacing.medium)
                    .height(0.5.dp)
                    .background(SuhuTheme.colors.surfaceVariant)
            )
        }
    }
}
