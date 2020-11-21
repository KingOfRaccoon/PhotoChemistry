package com.kingofraccoon.photochemistry

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLFrame
import com.kingofraccoon.photochemistry.tools.ConvertToFormula
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView // поле с выводом приведенной ОВР
    lateinit var formulaTextView: TextView // поле для ввода формулы
    lateinit var imageUri : Uri // картинка для считывания
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textFormula)
        formulaTextView = findViewById(R.id.formula)
        val fab: FloatingActionButton = findViewById(R.id.photo) // кнопка для вызыва камера
        val button: Button = findViewById(R.id.check) // кнопка для начала решения
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) // проверка на наличие разрешений
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 101)
        fab.setOnClickListener {
            CropImage.activity().start(this) // выбор поля считывания с фото
        }
        button.setOnClickListener {
            val convertToFormula = ConvertToFormula(deleteSpace(help(textView.text.toString())))
            val equationFormula = EquationFormula(convertToFormula.getFormula())
            formulaTextView.text = equationFormula.check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = CropImage.getActivityResult(data)
        imageUri = result.uri
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val analyzer = MLAnalyzerFactory.getInstance().localTextAnalyzer // анализатор текста
        val frame = MLFrame.fromBitmap(bitmap)
        val task = analyzer.asyncAnalyseFrame(frame)
        task.addOnSuccessListener {
            textView.text = deleteSpace(help(it.stringValue)) // вывод текста
        }
        task.addOnFailureListener {
            textView.text = it.message
        }
    }

    private fun help(string: String): String { // функция для исправления ошибок считывания теска
        var str = ""
        for (i in string.indices) {
            when (string[i]) {
                '-' -> str += "="
                '0' -> str += "O"
                ',' -> str += "2"
                'I' -> { // костыль, надо что-то придумать с I и l, ибо api их путает
                    if (string[i-1].isUpperCase())
                        str += "l"
                    else
                        str += "I"
                }
                else -> str += string[i]
            }
        }
        return str
    }

    private fun deleteSpace(string: String): String { // функция для удаления лишних пробелов
        var str = ""
        string.forEach {
            if (it.toString() != " ")
                str += it
        }
        return str
    }

    private fun setFragment(fragment: Fragment) { // функция для установки фрагмента
        supportFragmentManager.beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
    }
}