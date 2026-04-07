package com.suhu.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhu.app.ui.components.SuhuFormGroup
import com.suhu.app.ui.components.SuhuPickerItem
import com.suhu.app.ui.components.SuhuPrimaryButton
import com.suhu.app.ui.components.SuhuTextField
import com.suhu.app.ui.theme.InterFontFamily
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

// ==========================================
// OPSI SIKLUS PENAGIHAN
// ==========================================
private val billingCycleOptions = listOf("Bulanan", "Mingguan", "Tahunan")

// ==========================================
// OPSI KATEGORI LAYANAN
// ==========================================
private val categoryOptions = listOf(
    "🎬 Streaming", "🎵 Musik", "☁️ Cloud Storage",
    "🎮 Gaming", "📰 Berita", "💪 Fitness",
    "📚 Edukasi", "🛒 Belanja", "📱 Lainnya"
)

/**
 * LAYAR FORM MANUAL ENTRY — Input data langganan baru secara manual.
 *
 * Menampilkan GlassNavBar ("Batal" + "Suhu"), header editorial, 3 grup form
 * (Identitas, Biaya & Siklus, Penjadwalan), MascotInsightBox tips Si Kancil,
 * dan fixed footer CTA "Simpan Langganan".
 *
 * @param onCancel Callback saat "Batal" diklik (kembali ke layar sebelumnya).
 * @param onSave Callback saat "Simpan Langganan" diklik (kirim data form).
 * @param onPickCategory Callback trigger pemilihan kategori (bisa buka BottomSheet).
 * @param onPickDate Callback trigger pemilihan tanggal (bisa buka DatePicker).
 * @param onPickCycle Callback trigger pemilihan siklus penagihan.
 */
@Composable
fun ManualEntryFormScreen(
    onCancel: () -> Unit = {},
    onSave: (name: String, price: String, category: String, cycle: String, date: String) -> Unit = { _, _, _, _, _ -> },
    onPickCategory: () -> Unit = {},
    onPickDate: () -> Unit = {},
    onPickCycle: () -> Unit = {}
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    // ==========================================
    // STATE FORM — Akan dimigrasi ke ViewModel di Fase 5
    // ==========================================
    var serviceName by remember { mutableStateOf("") }
    var priceAmount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Pilih Kategori") }
    var selectedCycle by remember { mutableStateOf("Bulanan") }
    var selectedDate by remember { mutableStateOf("12 Oktober 2023") }

    // ==========================================
    // VALIDASI INPUT — Menggunakan derivedStateOf agar hanya
    // memicu recomposition saat hasil boolean berubah,
    // bukan setiap kali karakter diketik.
    // ==========================================
    val isFormValid by remember {
        derivedStateOf {
            val isNameValid = serviceName.isNotBlank()
            val isPriceValid = priceAmount.isNotBlank() && priceAmount.toDoubleOrNull() != null && (priceAmount.toDoubleOrNull() ?: 0.0) > 0
            val isCategorySelected = selectedCategory != "Pilih Kategori"
            isNameValid && isPriceValid && isCategorySelected
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // ==========================================
        // KONTEN UTAMA (Scrollable)
        // ==========================================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                // Ruang untuk GlassNavBar (fixed di atas) dan Footer CTA (fixed di bawah)
                .padding(top = 100.dp, bottom = 140.dp)
                .padding(horizontal = spacing.extraLarge)
        ) {
            // ==========================================
            // HEADER EDITORIAL
            // ==========================================
            BasicText(
                text = "Tambah Langganan",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 40.sp,
                    letterSpacing = (-0.5).sp,
                    color = colors.onSurface
                )
            )

            Spacer(modifier = Modifier.height(spacing.small))

            BasicText(
                text = "Catat detail pengeluaran rutin Anda secara manual.",
                style = SuhuTheme.typography.bodyLarge.copy(
                    color = colors.onSurfaceVariant,
                    lineHeight = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ==========================================
            // GRUP 1: IDENTITAS LAYANAN
            // ==========================================
            FormSectionHeader(label = "IDENTITAS LAYANAN")
            Spacer(modifier = Modifier.height(spacing.small))

            SuhuFormGroup {
                SuhuTextField(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    placeholder = "Nama Layanan (Contoh: Netflix)",
                    iconSymbol = "🏷️",
                    showDivider = true
                )
                SuhuPickerItem(
                    label = selectedCategory,
                    value = "",
                    iconSymbol = "📂",
                    showDivider = false,
                    onClick = onPickCategory
                )
            }

            // Validasi error hint dihitung via derivedStateOf di isFormValid

            Spacer(modifier = Modifier.height(40.dp))

            // ==========================================
            // GRUP 2: BIAYA & SIKLUS
            // ==========================================
            FormSectionHeader(label = "BIAYA & SIKLUS")
            Spacer(modifier = Modifier.height(spacing.small))

            SuhuFormGroup {
                // Input nominal dengan prefix "Rp"
                SuhuTextField(
                    value = priceAmount,
                    onValueChange = { newValue ->
                        // Hanya terima digit dan titik desimal
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            priceAmount = newValue
                        }
                    },
                    placeholder = "0",
                    iconSymbol = "💰",
                    showDivider = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                SuhuPickerItem(
                    label = "Siklus Penagihan",
                    value = selectedCycle,
                    iconSymbol = "🔄",
                    showDivider = false,
                    onClick = onPickCycle
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ==========================================
            // GRUP 3: PENJADWALAN
            // ==========================================
            FormSectionHeader(label = "PENJADWALAN")
            Spacer(modifier = Modifier.height(spacing.small))

            SuhuFormGroup {
                DatePickerRow(
                    dateLabel = selectedDate,
                    onClick = onPickDate
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraLarge))

            // ==========================================
            // MASCOT INSIGHT BOX — Tips Si Kancil
            // ==========================================
            MascotInsightBox()
        }

        // ==========================================
        // FIXED: GlassNavBar (di atas)
        // ==========================================
        GlassNavBar(onCancel = onCancel)

        // ==========================================
        // FIXED: Footer CTA "Simpan Langganan" (di bawah)
        // ==========================================
        FooterCTA(
            isEnabled = isFormValid,
            onClick = {
                if (isFormValid) {
                    onSave(serviceName, priceAmount, selectedCategory, selectedCycle, selectedDate)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ==========================================
// KOMPONEN: GlassNavBar — "Batal" (kiri) + "Suhu" (tengah)
// Glassmorphism effect, tanpa TopAppBar/BottomNavBar
// ==========================================
@Composable
private fun GlassNavBar(onCancel: () -> Unit) {
    val colors = SuhuTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface.copy(alpha = 0.8f))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = SuhuTheme.spacing.large, vertical = SuhuTheme.spacing.medium)
    ) {
        // "< Batal" di kiri
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .bouncyClickable(onClick = onCancel),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = "‹",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontSize = 22.sp,
                    color = colors.primary,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            BasicText(
                text = "Batal",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontSize = 17.sp,
                    color = colors.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // "Suhu" di tengah
        BasicText(
            text = "Suhu",
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                letterSpacing = (-0.3).sp,
                color = colors.onSurface
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// ==========================================
// KOMPONEN: Form Section Header (uppercase label abu-abu)
// ==========================================
@Composable
private fun FormSectionHeader(label: String) {
    BasicText(
        text = label,
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            color = SuhuTheme.colors.onSurfaceVariant
        ),
        modifier = Modifier.padding(start = 4.dp)
    )
}

// ==========================================
// KOMPONEN: Date Picker Row (untuk Grup 3)
// Menampilkan label "Mulai Langganan" + tanggal terpilih + ikon kalender
// ==========================================
@Composable
private fun DatePickerRow(
    dateLabel: String,
    onClick: () -> Unit
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface)
            .bouncyClickable(onClick = onClick)
            .padding(horizontal = spacing.medium, vertical = spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ikon kalender
        BasicText(
            text = "📅",
            style = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.width(spacing.medium))

        // Label + Tanggal
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = "Mulai Langganan",
                style = SuhuTheme.typography.labelSmall.copy(
                    color = colors.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            BasicText(
                text = dateLabel,
                style = SuhuTheme.typography.bodyLarge.copy(
                    color = colors.onSurface,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // Ikon edit kalender
        BasicText(
            text = "📝",
            style = TextStyle(
                fontSize = 20.sp,
                color = colors.primary
            )
        )
    }
}

// ==========================================
// KOMPONEN: Mascot Insight Box — Tips Si Kancil
// ==========================================
@Composable
private fun MascotInsightBox() {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SuhuTheme.shapes.large)
            .background(colors.primaryContainer.copy(alpha = 0.3f))
            .padding(spacing.large),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar maskot Si Kancil
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape,
                    spotColor = colors.primary.copy(alpha = 0.1f)
                )
                .clip(CircleShape)
                .background(colors.surfaceContainerLowest),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = "🦌",
                style = TextStyle(fontSize = 28.sp)
            )
        }

        // Tips text
        BasicText(
            text = "Tips Si Kancil: Pastikan tanggal mulai benar agar aku bisa mengingatkanmu tepat 3 hari sebelum saldo terpotong!",
            style = SuhuTheme.typography.bodySmall.copy(
                color = colors.onPrimaryContainer,
                lineHeight = 20.sp
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

// ==========================================
// KOMPONEN: Footer CTA Fixed — "Simpan Langganan"
// Gradient button dengan glassmorphism background
// ==========================================
@Composable
private fun FooterCTA(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface.copy(alpha = 0.8f))
            .padding(horizontal = spacing.extraLarge, vertical = spacing.large)
    ) {
        // Gradient button — primary -> primaryContainer
        val buttonAlpha = if (isEnabled) 1f else 0.5f

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = if (isEnabled) 12.dp else 0.dp,
                    shape = SuhuTheme.shapes.squircle,
                    spotColor = colors.primary.copy(alpha = 0.3f)
                )
                .clip(SuhuTheme.shapes.squircle)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = buttonAlpha),
                            colors.primaryContainer.copy(alpha = buttonAlpha)
                        )
                    )
                )
                .then(
                    if (isEnabled) {
                        Modifier.bouncyClickable(onClick = onClick)
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                BasicText(
                    text = "Simpan Langganan",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = colors.onPrimary
                    )
                )
                BasicText(
                    text = "✓",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onPrimary
                    )
                )
            }
        }
    }
}
