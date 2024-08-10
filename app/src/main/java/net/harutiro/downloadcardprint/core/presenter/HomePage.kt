package net.harutiro.downloadcardprint.core.presenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.harutiro.downloadcardprint.core.entity.DOWNLOAD_CARDS
import net.harutiro.downloadcardprint.core.presenter.Components.AlertDialogExample
import net.harutiro.downloadcardprint.core.presenter.Components.DownloadCardCell
import net.harutiro.downloadcardprint.core.presenter.Components.PairingListDialog

@SuppressLint("MissingPermission")
@Composable
fun HomePage(
    homepageViewmodel : HomePageViewModel = HomePageViewModel()
){

    val openAlertDialog = remember { mutableStateOf(false) }
    val openBluetoothDeviceDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    homepageViewmodel.setup(context)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column {

//            Button(onClick = {
//                homepageViewmodel.printTest()
//            }){
//                Text("テスト")
//            }

            PairingSetting(
                onPairing = {
                    if(homepageViewmodel.printerDevice.value != null){
                        homepageViewmodel.disconnectPrinter()
                        homepageViewmodel.printerDevice.value = null
                    }else{
                        openBluetoothDeviceDialog.value = true
                    }

                },
                settingDeviceName = homepageViewmodel.printerDevice.value?.name ?: "設定なし",
                isPairing = homepageViewmodel.printerDevice.value != null,
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                items(DOWNLOAD_CARDS) { section ->

                    var count by remember { mutableIntStateOf( homepageViewmodel.getCounter(section.id)) }

                    DownloadCardCell(
                        downloadCard = section,
                        counter = count,
                        onCountUpButton = {
                            openAlertDialog.value = true
                            homepageViewmodel.printData = section
                            count++
                            homepageViewmodel.setCounter(section.id, count)
                        },
                        onCountDownButton = {
                            count--
                            homepageViewmodel.setCounter(section.id, count)
                        },
                        onPrintButton = {
                            homepageViewmodel.printDownloadCard(section)
                        }
                    )
                }
            }
        }


        when {
            openAlertDialog.value -> {
                AlertDialogExample(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        homepageViewmodel.printDownloadCard(homepageViewmodel.printData ?: return@AlertDialogExample)
                        Log.d("HomePage","ダウンロードカードをプリントします") // Add logic here to handle confirmation.
                    },
                    dialogTitle = "ダウンロードカードをプリントしますか？",
                    dialogText = "数を増やすためだけで、プリントしない場合はプリントしないを押してください。",
                    icon = Icons.Default.Info
                )
            }
            openBluetoothDeviceDialog.value -> {
                PairingListDialog(
                    bluetoothDeviceList = homepageViewmodel.getParingDeviceList(),
                    onSelectedDevice = {
                        openBluetoothDeviceDialog.value = false
                        homepageViewmodel.printerDevice.value = it
                        homepageViewmodel.connectPrinter()
                        Log.d("HomePage","ペアリングしたデバイス: ${it.name}")
                    },
                    onDismissRequest = {
                        openBluetoothDeviceDialog.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun PairingSetting(
    isPairing: Boolean,
    onPairing: () -> Unit = {},
    settingDeviceName: String = "設定なし",
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
    ){
        Button(onClick = onPairing) {
            Text(if(isPairing){"ペアリング解除"}else{"ペアリングを行う"} )
        }
        Text(
            text = settingDeviceName,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun HomePagePreview(){
    HomePage()
}
