package com.teemodev.commentfacebookui.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String?): String? {
        val jsonString: String
        jsonString = try {
            val `is`: InputStream = context.assets.open(fileName.toString())
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}