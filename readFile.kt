package com.example.myapplication22

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresPermission
import org.json.JSONException
import java.io.FileInputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.io.File

    fun readFile(): String {
        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/data.json"
        var jsonString = ""
        val file = File(path)
        if (file.exists()){
            val stream = FileInputStream(file)
            stream.use {
                val fileChannel = it.channel
                val mappedByteBuffer = fileChannel.map(
                    FileChannel.MapMode.READ_ONLY,
                    0,
                    fileChannel.size()
                )
                jsonString = Charset.defaultCharset().decode(mappedByteBuffer).toString()
            }
        }else{
            println("Not exists")}
        return jsonString
    }


