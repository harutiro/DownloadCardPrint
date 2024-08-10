package net.harutiro.downloadcardprint.features.printer.utils

import android.graphics.Bitmap
import android.media.ThumbnailUtils

class PrintUtils {
    /**
     * 将彩色图转换为黑白图
     *
     * @param 位图
     * @return 返回转换好的位图
     */
    fun convertToBlackWhite(bmp: Bitmap): Bitmap {
        val width = bmp.width // 获取位图的宽
        val height = bmp.height // 获取位图的高
        val pixels = IntArray(width * height) // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                var grey = pixels[width * i + j]

                val red = ((grey and 0x00FF0000) shr 16)
                val green = ((grey and 0x0000FF00) shr 8)
                val blue = (grey and 0x000000FF)

                grey = ((red * 0.3) + (green * 0.59) + (blue * 0.11)).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        newBmp.setPixels(pixels, 0, width, 0, 0, width, height)

        val resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460)
        return resizeBmp
    }
}
