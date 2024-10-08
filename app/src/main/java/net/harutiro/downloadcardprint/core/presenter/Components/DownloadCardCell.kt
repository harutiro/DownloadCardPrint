package net.harutiro.downloadcardprint.core.presenter.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.harutiro.downloadcardprint.R
import net.harutiro.downloadcardprint.core.entity.DownloadCard

@Composable
fun DownloadCardCell(
    downloadCard: DownloadCard,
    onCountUpButton: () -> Unit = {},
    onCountDownButton: () -> Unit = {},
    counter: Int,
    onPrintButton: () -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = downloadCard.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis  // 長い場合に省略記号を表示
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically  // 垂直方向に中央揃え
            ){
                Image(
                    painter = painterResource(downloadCard.thumbnail),
                    contentDescription = downloadCard.title,
                    modifier = Modifier
                        .height(76.dp)
                )
                
                IconButton(onClick = onPrintButton) {
                    Icon(imageVector = Icons.Filled.Print, contentDescription = "Settings")
                }

                Spacer(modifier = Modifier.weight(1f))

                Counter(
                    count = counter,
                    countUp = {
                        onCountUpButton()
                    },
                    countDown = {
                        onCountDownButton()
                    }
                )
            }
        }

    }
}

@Composable
fun Counter(
    count: Int,
    countUp: () -> Unit = {},
    countDown: () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,  // 垂直方向に中央揃え
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Button(
            onClick = {
                countDown()
            }
        ) {
            Text("-")
        }
        Text(
            text = count.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                countUp()
            }
        ) {
            Text("+")
        }
    }
}

@Preview
@Composable
fun DownloadCardCellPreview(){
    DownloadCardCell(
        downloadCard = DownloadCard(
            title = "Syskenの技術本Syskenの技術本Syskenの技術本Syskenの技術本",
            thumbnail = R.drawable.sysken_book,
            password = "test_password",
            downloadUrl = "https://booth.pm/ja/items/5235288",
            circle = "システム工学研究会",
            id = "1"
        ),
        counter = 3
    )
}