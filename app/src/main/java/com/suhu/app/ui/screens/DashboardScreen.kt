package com.suhu.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhu.app.ui.components.SuhuBottomNavBar
import com.suhu.app.ui.components.SuhuNavRoute
import com.suhu.app.ui.components.SuhuPrimaryButton
import com.suhu.app.ui.components.SuhuTopAppBar
import com.suhu.app.ui.theme.InterFontFamily
import com.suhu.app.ui.theme.SuhuTheme

/**
 * LAYAR DASHBOARD KOSONG — State awal saat belum ada data langganan.
 *
 * Menampilkan skeleton/ghost state untuk ringkasan, ilustrasi Si Kancil
 * dengan pesan "Suhu Sedang Berjaga", tombol CTA "Tambah Manual Pertama",
 * dan placeholder transaksi terakhir.
 *
 * TopAppBar dan BottomNavBar AKTIF di layar ini.
 *
 * @param onAddManualClick Callback saat tombol "Tambah Manual Pertama" diklik.
 * @param onAddClick Callback saat ikon (+) di TopAppBar diklik.
 * @param onProfileClick Callback saat ikon profil di TopAppBar diklik.
 * @param onNavigate Callback navigasi BottomNavBar.
 */
@Composable
fun DashboardEmptyScreen(
    onAddManualClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onNavigate: (SuhuNavRoute) -> Unit = {}
) {
    val colors = SuhuTheme.colors
    val typography = SuhuTheme.typography
    val spacing = SuhuTheme.spacing

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
                // Ruang untuk TopAppBar (fixed di atas) dan BottomNavBar (fixed di bawah)
                .padding(top = 100.dp, bottom = 120.dp)
                .padding(horizontal = spacing.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // SECTION 1: "Ringkasan" Header + Bento Ghost Grid
            // ==========================================
            RingkasanSection()

            Spacer(modifier = Modifier.height(48.dp))

            // ==========================================
            // SECTION 2: Ilustrasi Si Kancil + Pesan Kosong
            // ==========================================
            EmptyStateSection()

            Spacer(modifier = Modifier.height(40.dp))

            // ==========================================
            // SECTION 3: Tombol CTA "Tambah Manual Pertama"
            // ==========================================
            SuhuPrimaryButton(
                text = "Tambah Manual Pertama",
                onClick = onAddManualClick,
                iconSymbol = "＋",
                modifier = Modifier.padding(horizontal = spacing.medium)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // ==========================================
            // SECTION 4: "Transaksi Terakhir" Ghost Rows
            // ==========================================
            TransaksiTerakhirSection()
        }

        // ==========================================
        // FIXED: TopAppBar (di atas)
        // ==========================================
        SuhuTopAppBar(
            onAddClick = onAddClick,
            onProfileClick = onProfileClick
        )

        // ==========================================
        // FIXED: BottomNavBar (di bawah)
        // ==========================================
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SuhuBottomNavBar(
                currentRoute = SuhuNavRoute.HOME,
                onNavigate = onNavigate
            )
        }
    }
}

// ==========================================
// SECTION: Ringkasan (Header + Bento Ghost Grid)
// ==========================================
@Composable
private fun RingkasanSection() {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header row: "Ringkasan" + "Hari ini"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            BasicText(
                text = "Ringkasan",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    lineHeight = 36.sp,
                    letterSpacing = (-0.5).sp,
                    color = colors.onSurface
                )
            )
            BasicText(
                text = "Hari ini",
                style = SuhuTheme.typography.labelMedium.copy(
                    color = colors.outline
                )
            )
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        // Bento Grid Ghost State: 2 kolom
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            GhostBentoCard(
                icon = "💳",
                modifier = Modifier.weight(1f),
                // Ukuran skeleton bars berbeda per kartu untuk variasi visual
                labelBarWidth = 48.dp,
                valueBarWidth = 80.dp
            )
            GhostBentoCard(
                icon = "📈",
                modifier = Modifier.weight(1f),
                labelBarWidth = 64.dp,
                valueBarWidth = 48.dp
            )
        }
    }
}

// ==========================================
// KOMPONEN: Ghost Bento Card (Skeleton state)
// ==========================================
@Composable
private fun GhostBentoCard(
    icon: String,
    modifier: Modifier = Modifier,
    labelBarWidth: androidx.compose.ui.unit.Dp = 48.dp,
    valueBarWidth: androidx.compose.ui.unit.Dp = 80.dp
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(colors.surfaceContainerLowest)
            .padding(spacing.large),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Ikon di pojok atas
        BasicText(
            text = icon,
            style = TextStyle(
                fontSize = 24.sp,
                color = colors.outline
            )
        )

        Spacer(modifier = Modifier.height(spacing.xxl))

        // Skeleton bars (ghost effect)
        Column(verticalArrangement = Arrangement.spacedBy(spacing.extraSmall)) {
            // Label bar (pendek, tipis)
            Box(
                modifier = Modifier
                    .width(labelBarWidth)
                    .height(8.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.surfaceContainerHighest)
            )
            // Value bar (lebih lebar, lebih tinggi)
            Box(
                modifier = Modifier
                    .width(valueBarWidth)
                    .height(24.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.surfaceContainerLow)
            )
        }
    }
}

// ==========================================
// SECTION: Empty State (Si Kancil + Pesan)
// ==========================================
@Composable
private fun EmptyStateSection() {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ilustrasi Si Kancil - 256x256dp
        // Dengan background blur dekoratif di belakangnya
        Box(
            modifier = Modifier.size(256.dp),
            contentAlignment = Alignment.Center
        ) {
            // Decorative background blob (primary/5% blur effect)
            Box(
                modifier = Modifier
                    .size(192.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.primary.copy(alpha = 0.05f))
            )

            // Placeholder maskot Si Kancil
            // Akan diganti dengan gambar asli saat resource tersedia
            BasicText(
                text = "🦌",
                style = TextStyle(fontSize = 120.sp)
            )
        }

        Spacer(modifier = Modifier.height(spacing.extraLarge))

        // Judul: "Suhu Sedang Berjaga"
        BasicText(
            text = "Suhu Sedang Berjaga",
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 25.sp,
                color = colors.onSurface,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(spacing.small))

        // Pesan ajakan
        BasicText(
            text = "Lakukan transaksi atau tambah manual untuk mulai melacak.",
            style = SuhuTheme.typography.bodyMedium.copy(
                color = colors.secondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            ),
            modifier = Modifier.padding(horizontal = spacing.extraLarge)
        )
    }
}

// ==========================================
// SECTION: Transaksi Terakhir (Ghost Rows)
// ==========================================
@Composable
private fun TransaksiTerakhirSection() {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header row: "Transaksi Terakhir" + "Lihat Semua"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = "Transaksi Terakhir",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    color = colors.onSurface
                )
            )
            BasicText(
                text = "Lihat Semua",
                style = SuhuTheme.typography.bodySmall.copy(
                    color = colors.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // Container kartu dengan border tipis
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(colors.surfaceContainerLowest)
                .padding(8.dp)
        ) {
            // Ghost Row 1
            GhostTransactionRow(
                nameLabelWidth = 0.33f,
                dateLabelWidth = 0.25f,
                priceWidth = 48.dp
            )

            // Ghost Row 2
            GhostTransactionRow(
                nameLabelWidth = 0.5f,
                dateLabelWidth = 0.2f,
                priceWidth = 64.dp
            )
        }
    }
}

// ==========================================
// KOMPONEN: Ghost Transaction Row (Skeleton)
// ==========================================
@Composable
private fun GhostTransactionRow(
    nameLabelWidth: Float,
    dateLabelWidth: Float,
    priceWidth: androidx.compose.ui.unit.Dp
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder ikon (kotak abu-abu rounded)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.surfaceContainerLow)
        )

        Spacer(modifier = Modifier.width(spacing.medium))

        // Placeholder teks (nama + tanggal bars)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            // Nama layanan skeleton
            Box(
                modifier = Modifier
                    .fillMaxWidth(nameLabelWidth)
                    .height(12.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.surfaceContainerHigh)
            )
            // Tanggal skeleton
            Box(
                modifier = Modifier
                    .fillMaxWidth(dateLabelWidth)
                    .height(8.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.surfaceContainerHighest)
            )
        }

        Spacer(modifier = Modifier.width(spacing.medium))

        // Placeholder harga
        Box(
            modifier = Modifier
                .width(priceWidth)
                .height(16.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(colors.surfaceContainerLow)
        )
    }
}
