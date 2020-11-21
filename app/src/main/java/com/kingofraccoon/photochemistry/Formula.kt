package com.kingofraccoon.photochemistry

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
// класс формулы
class Formula(var reagents: MutableMap<Compound, Int>, var products: MutableMap<Compound, Int>) {
    override fun toString(): String { // возврат текста без изменения размера индексов
        var string = ""
        reagents.forEach {
            if (it.value > 1)
                string += it.value.toString()
            string += it.key.toString()
            if (it.key != reagents.toList().last().first)
                string += " + "
            else
                string += " = "
        }
        products.forEach {
            if (it.value > 1)
                string += it.value.toString()
            string += it.key.toString()
            if (it.key != products.toList().last().first)
                string += " + "
        }
        return string
    }

    fun getSpan(): SpannableString{ // возвращет текст с изменением размера индексов
        var string = ""
        val arrayNumbers = arrayOf("2", "3", "4", "5", "6", "7", "8", "9")
        val listNumbers = mutableListOf<Int>()
        val list = reagents.toList()
        for (i in 0 until list.size) {
            if (list[i].second > 1)
                string += list[i].second.toString()
            list[i].first.toString().forEach {
                if (it.toString() in arrayNumbers)
                    listNumbers.add(string.length)
                string += it
            }
            if (list[i] != list.last())
                string += " + "
            else
                string += " = "
        }
        val listProducts = products.toList()
        for (i in 0 until listProducts.size){
            if (listProducts[i].second > 1)
                string += list[i].second.toString()
            listProducts[i].first.toString().forEach {
                if (it.toString() in arrayNumbers)
                    listNumbers.add(string.length)
                string += it
            }
            if (listProducts[i] != listProducts.last())
                string += " + "
        }
        val stringSpan = SpannableString(string)
        listNumbers.forEach {
            stringSpan.setSpan(RelativeSizeSpan(0.5f), it, it+1, 0)
        }
        return stringSpan
    }
}
