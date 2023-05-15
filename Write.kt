package com.example.myapplication22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.Writer

class Write : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        val read = findViewById<Button>(R.id.read_btn)
        val send = findViewById<Button>(R.id.send_btn)

        read.setOnClickListener{
            val i = Intent(this, Read::class.java)
            startActivity(i)
        }
        send.setOnClickListener{
            val vardas = findViewById<EditText>(R.id.vardasw_)
            val amzius = findViewById<EditText>(R.id.amzius_w)
            val lytis = findViewById<EditText>(R.id.lytis_w)
            val pomegiai1 = findViewById<EditText>(R.id.pomegiai_w)
            val pomegiai2 = findViewById<EditText>(R.id.pomegiai_w2)
            val pomegiai3 = findViewById<EditText>(R.id.pomegiai_w3)
            if(vardas.length()>0 && amzius.length()>0 && lytis.length()>0 && pomegiai1.length()>0 && pomegiai2.length()>0 && pomegiai3.length()>0){
            createJSONdata(vardas, amzius, lytis, pomegiai1, pomegiai2, pomegiai3)}
            else{
                Toast.makeText(this, "Užpildykite visus laukus!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun createJSONdata(vardas: EditText, amzius: EditText, lytis: EditText, pomegiai1: EditText, pomegiai2: EditText, pomegiai3: EditText){
        val vardas_ = vardas.text.toString()
        val amzius_ = amzius.text.toString().toInt()
        val lytis_ = lytis.text.toString()
        val pomegiai1_ = pomegiai1.text.toString()
        val pomegiai2_ = pomegiai2.text.toString()
        val pomegiai3_ = pomegiai3.text.toString()

            val asmenys =
                Asmenys_write(vardas_, amzius_, lytis_, pomegiai1_, pomegiai2_, pomegiai3_)
            if (readFile() == "") {
                val jsonObject = JSONObject()
                jsonObject.put("asmenys", JSONArray().put(addAsmenys(asmenys)))
                saveJSON(jsonObject.toString())
                clearFields(vardas, amzius, lytis, pomegiai1, pomegiai2, pomegiai3)
                println("emptyFILE")
            } else {
                val jsonObject = JSONObject(readFile())
                val jsonArray = jsonObject.getJSONArray("asmenys")
                jsonArray.put(addAsmenys(asmenys))
                saveJSON(jsonObject.toString())
            }
        }

    private fun addAsmenys(asmenys: Asmenys_write): JSONObject {
        return JSONObject()
            .put("vardas", asmenys.vardas)
            .put("amzius", asmenys.amzius)
            .put("lytis", asmenys.lytis)
            .put("pomegiai", JSONArray()
                .put(asmenys.pomegiai1)
                .put(asmenys.pomegiai2)
                .put(asmenys.pomegiai3))
    }
     fun saveJSON(s:String) {
        println("saveJSON")
        val output: Writer
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val file = File(path, "data.json")
         //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            output = FileWriter(file, true)
            output.write(s) // needs string to write
            output.close()
            Toast.makeText(this, "ATLIKTA", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "NEPAVYKO", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clearFields(vardas: EditText, amzius: EditText, lytis: EditText, pomegiai1: EditText, pomegiai2: EditText, pomegiai3: EditText){
        vardas.setText("")
        amzius.setText("")
        lytis.setText("")
        pomegiai1.setText("")
        pomegiai2.setText("")
        pomegiai3.setText("")
    }
}
