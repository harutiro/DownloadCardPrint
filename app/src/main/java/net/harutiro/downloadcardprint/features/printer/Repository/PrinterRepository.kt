package net.harutiro.downloadcardprint.features.printer.Repository

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AndroidRuntimeException
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.android.print.sdk.CanvasPrint
import com.android.print.sdk.PrinterConstants
import com.android.print.sdk.PrinterInstance
import com.android.print.sdk.PrinterType
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import net.harutiro.downloadcardprint.R
import net.harutiro.downloadcardprint.core.entity.DownloadCard
import net.harutiro.downloadcardprint.features.printer.utils.PrintUtils

class PrinterRepository(
    private val context: Context
) {

    val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter = bluetoothManager.adapter
    val bluetoothDeviceList = mutableStateListOf<BluetoothDevice>()
    val printUtils = PrintUtils()

    val TAG = "PosViewModel"

    var printer:PrinterInstance? = null

    @SuppressLint("MissingPermission")
    fun getParingDeviceList(): List<BluetoothDevice> {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        bluetoothDeviceList.clear()
        pairedDevices?.forEach { device ->
            Log.d(TAG, device.name ?:"null")
            bluetoothDeviceList.add(device)
        }
        return bluetoothDeviceList
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PrinterConstants.Connect.SUCCESS -> {
                    // 接続成功
                    Toast.makeText(context, "接続成功", Toast.LENGTH_SHORT).show()
                }
                PrinterConstants.Connect.FAILED -> {
                    // 接続失敗
                    Toast.makeText(context, "接続失敗", Toast.LENGTH_SHORT).show()
                }
                PrinterConstants.Connect.CLOSED -> {
                    // 接続終了
                    Toast.makeText(context, "接続終了", Toast.LENGTH_SHORT).show()
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun printerConnect(device: BluetoothDevice?){
        Log.d(TAG, "Printing with device: ${device?.name}")

        if (device == null) {
            Log.e(TAG, "BluetoothDevice is null")
            return
        }

        Thread {
            try {
                printer = PrinterInstance(context, device, handler)
                printer?.openConnection()
                printer?.init()
            } catch (e: Exception) {
                Log.e(TAG, "Error during printing", e)
            }
        }.start()
    }




    fun printDownloadCard(
        downloadCard: DownloadCard
    ){
        Thread {
            try {
                //写真を載せる
                val cpImage = CanvasPrint()
                cpImage.init(PrinterType.T9)

                val bitmapImage = BitmapFactory.decodeResource(context.resources, downloadCard.thumbnail)
                val resizedBitmapImage = printUtils.convertToBlackWhite(bitmapImage) // 適切なサイズに変換
                cpImage.drawImage(resizedBitmapImage)

                printer?.printImage(cpImage.canvasImage)
                printer?.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2)

                val sb = StringBuffer()
                // printer?.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);
                printer?.setPrinter(
                    PrinterConstants.Command.ALIGN,
                    PrinterConstants.Command.ALIGN_CENTER
                )
                // サークル名
                printer?.printText("Circle Name \n")

                val cpCircleImage = CanvasPrint()
                cpCircleImage.init(PrinterType.T9)

                val autoLineBreakCircleText = printUtils.autoLineBreak(downloadCard.circle,12)

                val bitmapCircleImage = printUtils.textToBitmap(autoLineBreakCircleText, 30f, Color.BLACK)
                cpCircleImage.drawImage(bitmapCircleImage)

                printer?.printImage(cpCircleImage.canvasImage)


                printer?.printText("\n==============================\n")

                // 本のタイトル
                printer?.printText("Book Title \n")

                val cpTitleImage = CanvasPrint()
                cpTitleImage.init(PrinterType.T9)

                val autoLineBreakTitleText = printUtils.autoLineBreak(downloadCard.title,12)

                val bitmapTitleImage = printUtils.textToBitmap(autoLineBreakTitleText, 30f, Color.BLACK)
                cpTitleImage.drawImage(bitmapTitleImage)

                printer?.printImage(cpTitleImage.canvasImage)

                printer?.printText("\n==============================\n")

                // パスワード
                printer?.setPrinter(
                    PrinterConstants.Command.ALIGN,
                    PrinterConstants.Command.ALIGN_LEFT
                )
                printer?.setCharacterMultiple(0, 0)
                sb.append("Password: ${downloadCard.password}\n")
                printer?.printText(sb.toString())
                printer?.printText("\n==============================\n")

                // ダウンロードようのLink
                printer?.setPrinter(
                    PrinterConstants.Command.ALIGN,
                    PrinterConstants.Command.ALIGN_CENTER
                )
                //QRコード化する文字列
                val data = downloadCard.downloadUrl

                //QRコード画像の大きさを指定(pixel)
                val size = 200

                val barcodeEncoder = BarcodeEncoder()
                //QRコードをBitmapで作成
                val bitmap =
                    barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, size, size)

                val cp = CanvasPrint()
                cp.init(PrinterType.T9)

                val resizedBitmap = printUtils.convertToBlackWhite(bitmap) // 適切なサイズに変換
                Log.d(
                    "PrintUtils",
                    "bitmap: $bitmap width: ${bitmap.width} height: ${bitmap.height}"
                )
                Log.d(
                    "PrintUtils",
                    "resizedBitmap: $resizedBitmap width: ${resizedBitmap.width} height: ${resizedBitmap.height}"
                )
                cp.drawImage(resizedBitmap)
                printer?.printText("url: ${downloadCard.downloadUrl}\n")
                printer?.printImage(cp.canvasImage)
                printer?.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2)
            } catch (e: Exception) {
                Log.e(TAG, "Error during printing", e)
            }
        }.start()
    }



    @SuppressLint("MissingPermission")
    fun printTest() {
        Thread {
            try {
//                printer?.printText("Hello World")

                val cpImage = CanvasPrint()
                cpImage.init(PrinterType.T9)

                val autoLineBreakTitle = printUtils.autoLineBreak("こんにちはこんにちはこんにちはこんにちは",12)
                Log.d(TAG, "autoLineBreakTitle: $autoLineBreakTitle")

                val bitmapImage = printUtils.textToBitmap(autoLineBreakTitle, 30f, Color.BLACK)
                cpImage.drawImage(bitmapImage)

                printer?.printImage(cpImage.canvasImage)

                printer?.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2)
            } catch (e: Exception) {
                Log.e(TAG, "Error during printing", e)
            }
        }.start()
    }

    @SuppressLint("MissingPermission")
    fun closeConnection(device: BluetoothDevice?) {
        Log.d(TAG, "Printing with device: ${device?.name}")

        if (device == null) {
            Log.e(TAG, "BluetoothDevice is null")
            return
        }

        Thread {
            try {
                printer = PrinterInstance(context, device, handler)
                printer?.closeConnection()
                printer = null
            } catch (e: Exception) {
                Log.e(TAG, "Error during printing", e)
            }
        }.start()
    }
}