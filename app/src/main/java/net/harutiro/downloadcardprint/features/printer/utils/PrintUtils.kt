package net.harutiro.downloadcardprint.features.printer.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
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

    fun textToBitmap(text: String, textSize: Float, textColor: Int): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT

        val lines = text.split("\n") // テキストを改行で分割
        val maxWidth = lines.maxOf { line -> paint.measureText(line).toInt() } // 最も長い行の幅を取得
        val lineHeight = (-paint.ascent() + paint.descent()).toInt() // 1行の高さを取得
        val height = (lineHeight * lines.size) // 全行の高さを合計

        val bitmap = Bitmap.createBitmap(maxWidth, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        for ((index, line) in lines.withIndex()) {
            val y = (lineHeight * index) - paint.ascent() // 各行のY座標を計算
            canvas.drawText(line, 0f, y, paint) // 各行をCanvasに描画
        }

        return bitmap
    }

    fun autoLineBreak(text: String, lineLength: Int): String {
        val sb = StringBuilder(text.length + text.length / lineLength)

        var i = 0
        while (i < text.length) {
            if (i > 0 && i % lineLength == 0) {
                sb.append("\n") // 改行を挿入
            }
            sb.append(text[i])
            i++
        }
        return sb.toString()
    }
}
