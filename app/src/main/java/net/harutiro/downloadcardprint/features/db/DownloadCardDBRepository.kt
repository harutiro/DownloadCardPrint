package net.harutiro.downloadcardprint.features.db

import android.content.Context
import android.content.SharedPreferences



class DownloadCardDBRepository(
    private val context: Context
) {

    var data: SharedPreferences = context.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

    fun saveData(key: String, value: Int) {
        val editor = data.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getData(key: String): Int {
        return data.getInt(key, 0)
    }


}