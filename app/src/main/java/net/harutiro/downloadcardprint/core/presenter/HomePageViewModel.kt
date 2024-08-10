package net.harutiro.downloadcardprint.core.presenter

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import net.harutiro.downloadcardprint.core.entity.DownloadCard
import net.harutiro.downloadcardprint.features.db.DownloadCardDBRepository
import net.harutiro.downloadcardprint.features.printer.Repository.PrinterRepository

class HomePageViewModel: ViewModel(){
    var printerRepository: PrinterRepository? = null
    var downloadCardDbRepository: DownloadCardDBRepository? = null
    var printerDevice: MutableState<BluetoothDevice?> = mutableStateOf(null)
    var printData: DownloadCard? = null

    fun setup(context: Context){
        printerRepository = PrinterRepository(context)
        downloadCardDbRepository = DownloadCardDBRepository(context)
    }

    fun getParingDeviceList(): List<BluetoothDevice> {
        return printerRepository?.getParingDeviceList() ?: listOf()
    }

    fun connectPrinter(){
        printerRepository?.printerConnect(printerDevice.value)
    }

    fun disconnectPrinter(){
        printerRepository?.closeConnection(printerDevice.value)
    }

    fun printDownloadCard(downloadCard: DownloadCard){
        printerRepository?.printDownloadCard(downloadCard)
    }

    fun getCounter(key: String): Int{
        return downloadCardDbRepository?.getData(key) ?: 0
    }

    fun setCounter(key:String , count: Int){
        downloadCardDbRepository?.saveData(key, count)
    }

}