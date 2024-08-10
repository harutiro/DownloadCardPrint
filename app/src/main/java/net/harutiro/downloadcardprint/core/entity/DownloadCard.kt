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
        title = "Sysken's technical book",
        thumbnail = R.drawable.sysken_book,
        password = "kadai^yaritakunai#sysken",
        downloadUrl = "https://sysken.booth.pm/items/5235288",
        circle = "System Engineering Team",
        id = "1"
    ),
    DownloadCard(
        title = "Computer hardware learned from standards",
        thumbnail = R.drawable.hardware_book,
        password = "sysken@peguin#hogehoge",
        downloadUrl = "https://sysken.booth.pm/items/5234962",
        circle = "Luciano Lab",
        id = "2"
    ),
    DownloadCard(
        title = "esa book",
        thumbnail = R.drawable.esa_book,
        password = "piyopiyo!sysken#choco",
        downloadUrl = "https://sysken.booth.pm/items/5235271",
        circle = "System Engineering Team",
        id = "3"
    ),
    DownloadCard(
        title = "A book on getting the number of terminals from ICMP and ARP using Golang",
        thumbnail = R.drawable.arp_book,
        password = "icmp@arp#ping\$www",
        downloadUrl = "https://sysken.booth.pm/items/5282358",
        circle = "System Engineering Team",
        id = "4"
    ),
    DownloadCard(
        title = "Super convenient home server recipes made with Proxmox",
        thumbnail = R.drawable.proxmox_book,
        password = "proxmox@benri#sugoi",
        downloadUrl = "https://sysken.booth.pm/items/5730612",
        circle = "Luciano Lab",
        id = "5"
    ),
    DownloadCard(
        title = "Google search and error resolution methods - Use generation AI too!",
        thumbnail = R.drawable.google_search_book,
        password = "google@chatgpt#thisbook",
        downloadUrl = "https://sysken.booth.pm/items/5730620",
        circle = "System Engineering Team",
        id = "6"
    ),
    DownloadCard(
        title = "A design escape route taken by a Nondezainer programmer.",
        thumbnail = R.drawable.design_book,
        password = "design\$is@kantan",
        downloadUrl = "https://sysken.booth.pm/items/5730626",
        circle = "System Engineering Team",
        id = "7"
    ),
)