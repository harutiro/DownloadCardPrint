package net.harutiro.downloadcardprint.core.entity

import net.harutiro.downloadcardprint.R

data class DownloadCard(
    val title : String,
    val thumbnail: Int,
    val password: String,
    val downloadUrl: String,
    val circle: String,
)

val DOWNLOAD_CARDS = listOf(
    DownloadCard(
        title = "Syskenの技術本",
        thumbnail = R.drawable.sysken_book,
        password = "kadai^yaritakunai#sysken",
        downloadUrl = "https://booth.pm/ja/items/5235288",
        circle = "システム工学研究会",
    ),
)