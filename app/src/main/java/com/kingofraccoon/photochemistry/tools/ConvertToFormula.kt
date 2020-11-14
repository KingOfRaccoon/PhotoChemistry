package com.kingofraccoon.photochemistry.tools

import com.kingofraccoon.photochemistry.Compound
import com.kingofraccoon.photochemistry.Element
import com.kingofraccoon.photochemistry.Formula

// "H2+O2=H2O"
// Н2О ->
class ConvertToFormula(var string: String){
    fun getFormula(): Formula {
        val array = string.split("=")
        val listReagents = array.first().split("+")
        val listAnswer = array.last().split("+")
//        println(array)
//        println(listReagents)
//        println(listAnswer)
        val mutMapReagents = mutableListOf<MutableMap<String, Int>>()
        listReagents.forEach {
            mutMapReagents.add(getCompound(it))
        }
        val mutMapAnswer = mutableListOf<MutableMap<String, Int>>()
        listAnswer.forEach {
            mutMapAnswer.add(getCompound(it))
        }
//        println(mutMapReagents)
//        println(mutMapAnswer)
        val mutableMapReagents = mutableListOf<MutableMap<Element, Int>>()
        mutMapReagents.forEach { it1 ->
            mutableMapReagents.add(mutableMapOf())
            it1.forEach {
                if (getElement(it.toPair()) != null)
                    mutableMapReagents.last().put(getElement(it.toPair())!!.first, getElement(it.toPair())!!.second)
            }
        }
        val answers = mutableListOf<MutableMap<Element, Int>>()
        mutMapAnswer.forEach {it1 ->
            answers.add(mutableMapOf())
            it1.forEach {
                if (getElement(it.toPair()) != null)
                    answers.last().put(getElement(it.toPair())!!.first, getElement(it.toPair())!!.second)
            }
        }
//        println(mutableMapReagents)
//        println(answers)
        val mutableMapCompound = mutableMapOf<Compound, Int>()
        mutableMapReagents.forEach {
            mutableMapCompound.put(Compound().apply { elements = it }, 1)
        }
        val answersMapCompound = mutableMapOf<Compound, Int>()
        answers.forEach {
            answersMapCompound.put(Compound().apply { elements = it }, 1)
        }
//        println(mutableMapCompound)
//        println(answersMapCompound)

        return Formula(mutableMapCompound, answersMapCompound)
    }
    fun getCompound(string: String): MutableMap<String, Int> {
        val arrayNumbers = arrayOf("2","3","4","5","6","7","8","9")
        val list = mutableMapOf<String, Int>()
        var str = ""
        string.forEach {
            if (arrayNumbers.contains(it.toString())){
                list.put(str ,it.toString().toInt())
                str = ""
            }
            else{
                str += it
            }
        }
        if (str.isNotBlank())
            list.put(str, 1)
        return list
    }
//    fun getLists(string: String, number: Int): MutableList<String>{
//        val list = mutableListOf<Pair<String, Int>>()
//        var str = ""
//        var int = 0
//        for (i in 0 until string.length){
//            when (i){
//                in (0 until number) -> str += string[i]
//                number -> int = string[number].toInt()
//                else ->
//            }
//        }
//
//        return list
//    }
    fun find(list: MutableList<Pair<String, Int>>){
        val compound = Compound()
        list.forEach {

        }
    }
    fun getElement(pair: Pair<String, Int>): Pair<Element, Int>? {
        when(pair.first){
            Element.H.molecule.symbol -> return Element.H to pair.second
            "Н" -> return Element.H to pair.second
            Element.O.molecule.symbol -> return Element.O to pair.second
            else -> return null
        }
    }
}