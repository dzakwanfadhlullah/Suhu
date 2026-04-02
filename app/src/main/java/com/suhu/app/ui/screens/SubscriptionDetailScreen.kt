package com.suhu.app.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhu.app.ui.theme.InterFontFamily
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

// ==========================================
// DATA CLASS SEMENTARA (Dummy riwayat pembayaran)
// Akan diganti dengan TransactionEntity dari Room Database di Fase 6
// ==========================================
private data class PaymentHistoryItem(
    val source: String,
    val date: String,
    val amount: String,
    val status: String = "Berhasil"
)

/**
 * LAYAR DETAIL LANGGANAN — Konten yang ditampilkan di dalam SuhuBottomSheet.
 *
 * Composable ini adalah KONTEN dari modal, bukan modal itu sendiri.
 * Pemanggil bertanggung jawab membungkusnya dengan `SuhuBottomSheet`.
 *
 * Contoh pemanggilan:
 * ```
 * SuhuBottomSheet(
 *     isVisible = showDetail,
 *     onDismiss = { showDetail = false }
 * ) {
 *     SubscriptionDetailContent(
 *         serviceName = "Netflix",
 *         ...
 *     )
 * }
 * ```
 *
 * @param serviceName Nama layanan langganan.
 * @param priceLabel Harga ditampilkan sebagai string (e.g. "Rp 186.000").
 * @param iconSymbol Emoji/simbol ikon layanan.
 * @param cycle Siklus langganan (e.g. "Bulanan").
 * @param category Kategori layanan (e.g. "Entertainment").
 * @param startDate Tanggal mulai langganan.
 * @param isActive Status aktif/nonaktif langganan.
 * @param onToggleActive Callback saat toggle status diklik.
 * @param onDelete Callback saat "Hapus Langganan" diklik.
 */
@Composable
fun SubscriptionDetailContent(
    serviceName: String = "Netflix",
    priceLabel: String = "Rp 186.000",
    iconSymbol: String = "🎬",
    cycle: String = "Bulanan",
    category: String = "Entertainment",
    startDate: String = "12 Januari 2024",
    isActive: Boolean = true,
    onToggleActive: (Boolean) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    // Dummy payment history
    val paymentHistory = remember {
        listOf(
            PaymentHistoryItem("BCA Mobile", "Auto-detected • 12 Feb", "-Rp 186.000"),
            PaymentHistoryItem("BCA Mobile", "Auto-detected • 12 Jan", "-Rp 186.000")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(bottom = spacing.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ==========================================
        // HEADER: Ikon layanan (80dp) + Nama + Harga/bulan
        // ==========================================
        DetailHeader(
            iconSymbol = iconSymbol,
            serviceName = serviceName,
            priceLabel = priceLabel
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ==========================================
        // TOGGLE STATUS LANGGANAN (Aktif/Nonaktif)
        // ==========================================
        StatusToggleRow(
            isActive = isActive,
            onToggle = onToggleActive
        )

        Spacer(modifier = Modifier.height(spacing.extraLarge))

        // ==========================================
        // BENTO INFO GRID: Siklus, Kategori, Tanggal Mulai
        // ==========================================
        BentoInfoGrid(
            cycle = cycle,
            category = category,
            startDate = startDate
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ==========================================
        // SECTION: Riwayat Pembayaran
        // ==========================================
        PaymentHistorySection(payments = paymentHistory)

        Spacer(modifier = Modifier.height(40.dp))

        // ==========================================
        // FOOTER: Tombol "Hapus Langganan" (merah/error)
        // ==========================================
        DeleteButton(onClick = onDelete)
    }
}

// ==========================================
// KOMPONEN: Detail Header — Ikon besar + Nama + Harga
// ==========================================
@Composable
private fun DetailHeader(
    iconSymbol: String,
    serviceName: String,
    priceLabel: String
) {
    val colors = SuhuTheme.colors

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ikon layanan (80dp, squircle rounded)
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = colors.onSurface.copy(alpha = 0.08f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(colors.surfaceContainerLowest),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = iconSymbol,
                style = TextStyle(fontSize = 40.sp)
            )
        }

        Spacer(modifier = Modifier.height(SuhuTheme.spacing.large))

        // Nama layanan
        BasicText(
            text = serviceName,
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                letterSpacing = (-0.5).sp,
                color = colors.onSurface,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Harga / bulan
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            BasicText(
                text = priceLabel,
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    letterSpacing = (-1).sp,
                    color = colors.onSurface
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            BasicText(
                text = "/ bln",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = colors.onSurfaceVariant
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

// ==========================================
// KOMPONEN: Toggle Status Langganan — Pill toggle custom
// ==========================================
@Composable
private fun StatusToggleRow(
    isActive: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing
    var toggled by remember { mutableStateOf(isActive) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(colors.surfaceContainerLowest)
            .padding(spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ikon status (hijau aktif)
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    if (toggled) colors.tertiary.copy(alpha = 0.1f)
                    else colors.outline.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = if (toggled) "✓" else "✕",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (toggled) colors.tertiary else colors.outline
                )
            )
        }

        Spacer(modifier = Modifier.width(spacing.medium))

        // Label teks
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = "Status Langganan",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = colors.onSurface
                )
            )
            BasicText(
                text = "Terakhir dicek hari ini",
                style = SuhuTheme.typography.labelSmall.copy(
                    color = colors.onSurfaceVariant
                )
            )
        }

        // Pill Toggle Custom
        PillToggle(
            isOn = toggled,
            onToggle = {
                toggled = !toggled
                onToggle(toggled)
            }
        )
    }
}

// ==========================================
// KOMPONEN: Pill Toggle (iOS-style Switch)
// ==========================================
@Composable
private fun PillToggle(
    isOn: Boolean,
    onToggle: () -> Unit
) {
    val colors = SuhuTheme.colors
    val thumbOffset by animateDpAsState(
        targetValue = if (isOn) 21.dp else 0.dp,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f),
        label = "toggle_thumb"
    )

    Box(
        modifier = Modifier
            .width(48.dp)
            .height(28.dp)
            .clip(CircleShape)
            .background(if (isOn) colors.primary else colors.outlineVariant)
            .bouncyClickable(onClick = onToggle)
            .padding(3.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = thumbOffset)
                .size(22.dp)
                .clip(CircleShape)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                    spotColor = colors.onSurface.copy(alpha = 0.15f)
                )
                .background(colors.onPrimary)
        )
    }
}

// ==========================================
// KOMPONEN: Bento Info Grid — Siklus, Kategori, Tanggal Mulai
// ==========================================
@Composable
private fun BentoInfoGrid(
    cycle: String,
    category: String,
    startDate: String
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        // Baris 1: 2 kolom (Siklus + Kategori)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            InfoCard(
                icon = "📅",
                label = "Siklus",
                value = cycle,
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                icon = "📂",
                label = "Kategori",
                value = category,
                modifier = Modifier.weight(1f)
            )
        }

        // Baris 2: Full width (Tanggal Mulai)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(colors.surfaceContainerLow)
                .padding(spacing.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = "🔄",
                style = TextStyle(fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.width(spacing.medium))

            Column(modifier = Modifier.weight(1f)) {
                BasicText(
                    text = "Tanggal Mulai",
                    style = SuhuTheme.typography.labelSmall.copy(
                        color = colors.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                )
                BasicText(
                    text = startDate,
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colors.onSurface
                    )
                )
            }

            BasicText(
                text = "⟩",
                style = SuhuTheme.typography.bodyLarge.copy(
                    color = colors.outlineVariant
                )
            )
        }
    }
}

// ==========================================
// KOMPONEN: Info Card (untuk Bento Grid item)
// ==========================================
@Composable
private fun InfoCard(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(colors.surfaceContainerLow)
            .padding(spacing.large)
    ) {
        BasicText(
            text = icon,
            style = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        BasicText(
            text = label,
            style = SuhuTheme.typography.labelSmall.copy(
                color = colors.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        BasicText(
            text = value,
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = colors.onSurface
            )
        )
    }
}

// ==========================================
// SECTION: Riwayat Pembayaran
// ==========================================
@Composable
private fun PaymentHistorySection(payments: List<PaymentHistoryItem>) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = "Riwayat Pembayaran",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = (-0.3).sp,
                    color = colors.onSurface
                )
            )
            BasicText(
                text = "Lihat Semua",
                style = SuhuTheme.typography.bodySmall.copy(
                    color = colors.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(spacing.small))

        // Container kartu riwayat
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(colors.surfaceContainerLowest)
        ) {
            payments.forEachIndexed { index, payment ->
                PaymentRow(
                    payment = payment,
                    showDivider = index < payments.lastIndex
                )
            }
        }
    }
}

// ==========================================
// KOMPONEN: Payment History Row
// ==========================================
@Composable
private fun PaymentRow(
    payment: PaymentHistoryItem,
    showDivider: Boolean
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon bank/sumber
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = "🏦",
                    style = TextStyle(fontSize = 24.sp)
                )
            }

            Spacer(modifier = Modifier.width(spacing.medium))

            // Detail sumber + tanggal
            Column(modifier = Modifier.weight(1f)) {
                BasicText(
                    text = payment.source,
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colors.onSurface
                    )
                )
                BasicText(
                    text = payment.date,
                    style = SuhuTheme.typography.labelSmall.copy(
                        color = colors.onSurfaceVariant
                    )
                )
            }

            // Amount + Status
            Column(horizontalAlignment = Alignment.End) {
                BasicText(
                    text = payment.amount,
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colors.onSurface
                    )
                )
                BasicText(
                    text = payment.status.uppercase(),
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp,
                        color = colors.tertiary
                    )
                )
            }
        }

        // Divider
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.large)
                    .height(0.5.dp)
                    .background(colors.surfaceContainerLow)
            )
        }
    }
}

// ==========================================
// KOMPONEN: Tombol "Hapus Langganan" (merah/error)
// ==========================================
@Composable
private fun DeleteButton(onClick: () -> Unit) {
    val colors = SuhuTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = colors.onSurface.copy(alpha = 0.04f)
            )
            .background(colors.surfaceContainerLowest)
            .bouncyClickable(onClick = onClick)
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = "Hapus Langganan",
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = colors.error
            )
        )
    }
}
