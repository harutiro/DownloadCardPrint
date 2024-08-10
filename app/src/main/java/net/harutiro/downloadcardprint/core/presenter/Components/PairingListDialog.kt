package net.harutiro.downloadcardprint.core.presenter.Components

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairingListDialog(
    bluetoothDeviceList: List<BluetoothDevice>,
    onSelectedDevice: (BluetoothDevice) -> Unit,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ){
        Card{
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("ペアリングするデバイスを選択してください")
                for (device in bluetoothDeviceList) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            // デバイスを選択したときの処理
                            onSelectedDevice(device)
                        }
                    ) {
                        Text(device.name.toString())
                    }
                }
            }
        }
    }
}