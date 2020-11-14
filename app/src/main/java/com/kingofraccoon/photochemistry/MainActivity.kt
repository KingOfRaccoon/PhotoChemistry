package com.kingofraccoon.photochemistry

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.text.MLTextAnalyzer
import com.kingofraccoon.photochemistry.tools.ConvertToFormula

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var formulaTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textFormula)
        formulaTextView = findViewById(R.id.formula)
        val fab : FloatingActionButton = findViewById(R.id.photo)
        val button : Button = findViewById(R.id.check)
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 101)
        fab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 101)
        }
        button.setOnClickListener {
//            formulaTextView.text = deleteSpace(help(textView.text.toString()))
            val convertToFormula = ConvertToFormula(deleteSpace(help(textView.text.toString())))
            val compoundGenerator = CompoundGenerator(convertToFormula.getFormula())
            formulaTextView.text = compoundGenerator.check()
//            Toast.makeText(this, formulaTextView.text == )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = data?.extras
        val bitmap = bundle?.get("data") as Bitmap
        val analyzer = MLAnalyzerFactory.getInstance().localTextAnalyzer
        val frame = MLFrame.fromBitmap(bitmap)
        val task = analyzer.asyncAnalyseFrame(frame)
        task.addOnSuccessListener {
            textView.text = it.stringValue
        }
        task.addOnFailureListener {
            textView.text = it.message
        }
    }
    private fun help(string: String):String{
        var str = ""
        for (i in string){
            when(i){
                '-' -> str += "="
                '0' -> str += "O"
                else -> str += i
            }
        }
        return str
    }
    private fun deleteSpace(string: String): String{
        var str = ""
        string.forEach {
            if (it.toString() != " ")
                str += it
        }
        return str
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
    }
}