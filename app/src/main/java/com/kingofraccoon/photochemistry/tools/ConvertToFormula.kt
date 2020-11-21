package com.kingofraccoon.photochemistry.tools

import com.kingofraccoon.photochemistry.Compound
import com.kingofraccoon.photochemistry.emun_class.Element
import com.kingofraccoon.photochemistry.Formula

// класс для превращения строки в формулу
class ConvertToFormula(var string: String) {
    fun getFormula(): Formula { // возвращает формулу
        val array = string.split("=")
        val listReagents = array.first().split("+")
        val listAnswer = array.last().split("+")
        val mutMapReagents = mutableListOf<MutableMap<String, Int>>()
        listReagents.forEach {
            mutMapReagents.add(getCompound(it))
        }
        val mutMapAnswer = mutableListOf<MutableMap<String, Int>>()
        listAnswer.forEach {
            mutMapAnswer.add(getCompound(it))
        }
        val mutableMapReagents = mutableListOf<MutableMap<Element, Int>>()
        mutMapReagents.forEach { it1 ->
            mutableMapReagents.add(mutableMapOf())
            it1.forEach {
                if (getElement(it.toPair()) != null)
                    mutableMapReagents.last().put(getElement(it.toPair())!!.first, getElement(it.toPair())!!.second)
            }
        }
        val answers = mutableListOf<MutableMap<Element, Int>>()
        mutMapAnswer.forEach { it1 ->
            answers.add(mutableMapOf())
            it1.forEach {
                if (getElement(it.toPair()) != null)
                    answers.last().put(getElement(it.toPair())!!.first, getElement(it.toPair())!!.second)
            }
        }
        val mutableMapCompound = mutableMapOf<Compound, Int>()
        mutableMapReagents.forEach {
            mutableMapCompound.put(Compound().apply { elements = it }, 1)
        }
        val answersMapCompound = mutableMapOf<Compound, Int>()
        answers.forEach {
            answersMapCompound.put(Compound().apply { elements = it }, 1)
        }
        return Formula(mutableMapCompound, answersMapCompound)
    }

    fun getCompound(string: String): MutableMap<String, Int> { // возвращет моллекулы в виде строк
        val arrayNumbers = arrayOf("2", "3", "4", "5", "6", "7", "8", "9")
        val list = mutableMapOf<String, Int>()
        val stringList = mutableListOf<Pair<Char, Int>>()
        for (i in string.indices){
            stringList.add(string[i] to i)
        }
        var str = ""
        stringList.forEach {
            if (it.second != 0 && it.first.isUpperCase()) {
                list.put(str.trim(), 1)
                str = ""
            }
            if (arrayNumbers.contains(it.first.toString())) {
                list.put(str.trim(), it.first.toString().toInt())
                str = ""
            } else {
                str += it.first.toString()
            }
        }
        if (str.isNotBlank())
            list.put(str.trim(), 1)
        return list
    }

    fun getElement(pair: Pair<String, Int>): Pair<Element, Int>? { // возвращет элемент по названию
        return when (pair.first.trim()) {
            Element.H.molecule.symbol -> Element.H to pair.second
            Element.O.molecule.symbol -> Element.O to pair.second
            Element.Al.molecule.symbol -> Element.Al to pair.second
            Element.Zn.molecule.symbol -> Element.Zn to pair.second
            Element.Cl.molecule.symbol -> Element.Cl to pair.second
            Element.Mg.molecule.symbol -> Element.Mg to pair.second
            Element.Cu.molecule.symbol -> Element.Cu to pair.second
            Element.Na.molecule.symbol -> Element.Na to pair.second
            Element.Ca.molecule.symbol -> Element.Ca to pair.second
            else -> null
        }
    }
}