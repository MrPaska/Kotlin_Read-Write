package com.example.myapplication22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.asmenys_list.*
import org.json.JSONObject
import java.io.FileInputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class Read : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val write = findViewById<Button>(R.id.button_write)

        write.setOnClickListener {
            val i = Intent(this, Write::class.java)
            startActivity(i)
        }
            val asmenys = ArrayList<Asmenys>()
            if (readFile() == "") {
                println("empty")
                Toast.makeText(this, "Failas neegzistuoja!", Toast.LENGTH_SHORT).show()
            } else {
                val obj = JSONObject(readFile())
                val jsonArray = obj.getJSONArray("asmenys")
                for (i in 0 until jsonArray.length()) {
                    val asmenys_dtl = jsonArray.getJSONObject(i)
                    val vardas = asmenys_dtl.getString("vardas")
                    println("vardas $vardas")
                    val amzius = asmenys_dtl.getInt("amzius")
                    val lytis = asmenys_dtl.getString("lytis")
                    val pomegiaiArray = ArrayList<String>()
                    val pomegiaiJSONArray = asmenys_dtl.getJSONArray("pomegiai")
                    for (j in 0 until pomegiaiJSONArray.length()) {
                        pomegiaiArray.add(pomegiaiJSONArray.getString(j))
                    }
                    val asmenys_list = Asmenys(vardas, amzius, lytis, pomegiaiArray)
                    asmenys.add(asmenys_list)
                }
                val rvAsmenys = findViewById<RecyclerView>(R.id.rvAsmenys)
                rvAsmenys.layoutManager = LinearLayoutManager(this)
                val itemAdapter = Adapter(this, asmenys)
                rvAsmenys.adapter = itemAdapter
            }
        }
    }

