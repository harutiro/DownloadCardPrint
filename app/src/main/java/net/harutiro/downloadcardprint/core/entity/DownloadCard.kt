package net.harutiro.downloadcardprint.core.entity

import net.harutiro.downloadcardprint.R

data class DownloadCard(
    val title : String,
    val thumbnail: Int,
    val password: String,
    val downloadUrl: String,
    val circle: String,
    val id: String,
)

val DOWNLOAD_CARDS = listOf(
    DownloadCard(
        title = "シス研の技術本",
        thumbnail = R.drawable.sysken_book,
        password = "kadai^yaritakunai#sysken",
        downloadUrl = "https://sysken.booth.pm/items/5235288",
        circle = "システム工学研究会",
        id = "1"
    ),
    DownloadCard(
        title = "企画から知るコンピュータハードウェア",
        thumbnail = R.drawable.hardware_book,
        password = "sysken@peguin#hogehoge",
        downloadUrl = "https://sysken.booth.pm/items/5234962",
        circle = "Luciano研究所",
        id = "2"
    ),
    DownloadCard(
        title = "esaをCMSにしてGitHubActionsとhugoを用いて自動でホームページを更新する方法の考案",
        thumbnail = R.drawable.esa_book,
        password = "piyopiyo!sysken#choco",
        downloadUrl = "https://sysken.booth.pm/items/5235271",
        circle = "システム工学研究会",
        id = "3"
    ),
    DownloadCard(
        title = "ICMPとARPを使って端末数を取得する本 with Golang",
        thumbnail = R.drawable.arp_book,
        password = "icmp@arp#ping\$www",
        downloadUrl = "https://sysken.booth.pm/items/5282358",
        circle = "システム工学研究会",
        id = "4"
    ),
    DownloadCard(
        title = "Proxmoxで作る超便利な自宅サーバーレシピ",
        thumbnail = R.drawable.proxmox_book,
        password = "proxmox@benri#sugoi",
        downloadUrl = "https://sysken.booth.pm/items/5730612",
        circle = "Luciano研究所",
        id = "5"
    ),
    DownloadCard(
        title = "Google検索及びエラーの解決方法　生成AIも活用使用！　これは本と言い張る版",
        thumbnail = R.drawable.google_search_book,
        password = "google@chatgpt#thisbook",
        downloadUrl = "https://sysken.booth.pm/items/5730620",
        circle = "システム工学研究会",
        id = "6"
    ),
    DownloadCard(
        title = "のんでざいなーのプログラマーによる、デザインのにげかた。これは本と言い張る版",
        thumbnail = R.drawable.design_book,
        password = "design\$is@kantan",
        downloadUrl = "https://sysken.booth.pm/items/5730626",
        circle = "システム工学研究会",
        id = "7"
    ),
)