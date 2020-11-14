package com.kingofraccoon.photochemistry

import NOK
import minusMap
import plusMap

class CompoundGenerator(var reagents: MutableMap<Compound, Int>, var answer: Pair<Compound, Int>) {
    constructor(formula: Formula): this(formula.reagents, formula.products.toList().first()){
//        reagents = formula.reagents
//        answer = formula.products.toList().first()
    }
    fun check(): String {
        var reagentsMap = getElementsReagents()
        var answerMap = getElementsAnswers()
        var checkMap: MutableMap<Element, Int>
        var temp = mutableMapOf<Compound, Int>()
        while (reagentsMap != answerMap) {
            checkMap = minusMap(reagentsMap, answerMap)
            checkMap.forEach {
                temp = find(it.toPair())
            }
            reagents.plusAssign(temp.toList().first().first to NOK(getKey(reagents, temp.toList().first().first)?.searchElement(checkMap.toList().first().first)?.second!!, temp.toList().last().second) / temp.toList().first().second)
            answer = multiply(answer, NOK(answer.second, temp.toList().first().second) / answer.second)
            reagentsMap = getElementsReagents()
            answerMap = getElementsAnswers()
            println(reagentsMap)
            println(answerMap)
        }
        val formula = Formula(reagents, mutableMapOf(answer))
        return formula.toString()
    }

    fun find(pair: Pair<Element, Int>): MutableMap<Compound, Int> { // поиск элемента, который надо домножить
        val mutableMap = mutableMapOf<Compound, Int>()
        for (it in reagents.keys) {
            if (it.searchElement(pair.first) != null) {
                mutableMap.put(it, it.searchElement(pair.first)!!.second)
                break
            }
        }
        if (answer.first.searchElement(pair.first) != null)
            mutableMap.put(answer.first, answer.first.searchElement(pair.first)!!.second * answer.second)
        return mutableMap
    }

    fun getElementsReagents(): MutableMap<Element, Int> {
        var map = mutableMapOf<Element, Int>()
        reagents.forEach {
            for (i in 0 until it.value)
                map = plusMap(map, it.key.elements)
        }
        return map
    }

    fun getElementsAnswers(): MutableMap<Element, Int> {
        var map = mutableMapOf<Element, Int>()
        for (i in 0 until answer.second)
            map = plusMap(map, answer.first.elements)
        return map
    }
    fun getKey(mutableMap: MutableMap<Compound, Int>, compound: Compound): Compound? {
        mutableMap.forEach {
            if (it.key == compound)
                return it.key
        }
        return null
    }
    fun multiply(pair: Pair<Compound, Int>, int: Int): Pair<Compound, Int>{
        return pair.first to pair.second * int
    }
}