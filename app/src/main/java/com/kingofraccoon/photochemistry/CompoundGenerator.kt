package com.kingofraccoon.photochemistry

import NOK
import minusMap
import plusMap

class CompoundGenerator(var reagents: MutableMap<Compound, Int>, var product: Pair<Compound, Int>) {
    constructor(formula: Formula) : this(formula.reagents, formula.products.toList().first())

    fun check(): String {
        var quantityElementsInReagents = getElementsReagents()
        var quantityElementsInProduct = getElementsProducts()
        var checkMap: MutableMap<Element, Int>
        val needToBalance = mutableListOf<MutableMap<Compound, Int>>()
        while (quantityElementsInReagents != quantityElementsInProduct) {
            checkMap = minusMap(getElementsReagents(), quantityElementsInProduct)
            checkMap.forEach {
                needToBalance.add(findCompoundToBalance(it.toPair()))
            }
            needToBalance.forEach {
                val firstReagent = it.toList().first()
                val compoundToBalance = if (reagents.containsKey(firstReagent.first)) firstReagent.first else null
                val quantityInReagents = compoundToBalance?.getQuantityElements(checkMap.toList().first().first)!!
                val quantityInProduct = it.toList().last().second
                val currentQuantityInReagent = it.toList().first().second
                val quantityFirstReagent = NOK(quantityInReagents, quantityInProduct) / currentQuantityInReagent
                reagents.plusAssign(multiply(firstReagent.first to reagents[firstReagent.first]!!, quantityFirstReagent))
                val currentQuantityInProduct = it.toList().first().second
                product = multiply(product, NOK(product.second, currentQuantityInProduct) / product.second)
                checkMap.minusAssign(checkMap.keys.first())
            }
            quantityElementsInReagents = getElementsReagents()
            quantityElementsInProduct = getElementsProducts()
            needToBalance.clear()
        }
        val formula = Formula(reagents, mutableMapOf(product))
        return formula.toString()
    }

    fun printList(): MutableList<MutableMap<Compound, Int>> {
        val needToBalance = mutableListOf<MutableMap<Compound, Int>>()
        minusMap(getElementsReagents(), getElementsProducts()).forEach {
            needToBalance.add(findCompoundToBalance(it.toPair()))
        }
        return needToBalance
    }

    private fun findCompoundToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> { // поиск элемента, который надо домножить
        val mutableList = mutableMapOf<Compound, Int>()
        mutableList.putAll(getCompoundReagentsToBalance(pair))
        mutableList.putAll(getCompoundAnswersToBalance(pair))
        return mutableList
    }

    private fun getCompoundReagentsToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> {
        val mutableMap = mutableMapOf<Compound, Int>()
        for (it in reagents.keys) {
            if (it.getQuantityElements(pair.first) != null) {
                mutableMap.put(it, it.getQuantityElements(pair.first)!! * reagents[it]!!)
                break
            }
        }
        return mutableMap
    }

    private fun getCompoundAnswersToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> {
        val mutableMap = mutableMapOf<Compound, Int>()
        if (product.first.getQuantityElements(pair.first) != null)
            mutableMap.put(product.first, product.first.getQuantityElements(pair.first)!! * product.second)
        return mutableMap
    }

    private fun getElementsReagents(): MutableMap<Element, Int> {
        var map = mutableMapOf<Element, Int>()
        reagents.forEach {
            for (i in 0 until it.value)
                map = plusMap(map, it.key.elements)
        }
        return map
    }

    private fun getElementsProducts(): MutableMap<Element, Int> {
        var map = mutableMapOf<Element, Int>()
        for (i in 0 until product.second)
            map = plusMap(map, product.first.elements)
        return map
    }

    private fun multiply(pair: Pair<Compound, Int>, int: Int): Pair<Compound, Int> {
        return pair.first to pair.second * int
    }
}