package com.kingofraccoon.photochemistry

import NOK
import android.text.SpannableString
import com.kingofraccoon.photochemistry.emun_class.Element
import minusMap
import plusMap
// класс для уравнивания формулы
class EquationFormula(var reagents: MutableMap<Compound, Int>, var products: MutableMap<Compound, Int>) {
    constructor(formula: Formula) : this(formula.reagents, formula.products)

    fun check(): SpannableString { // функция для уравнивания ОВР
        var quantityElementsInReagents = getElementsReagents()
        var quantityElementsInProduct = getElementsProducts()
        var checkMap: MutableMap<Element, Int>
        val needToBalance = mutableListOf<MutableMap<Compound, Int>>()
        while (quantityElementsInReagents != quantityElementsInProduct) {
            checkMap = minusMap(getElementsReagents(), quantityElementsInProduct)
            checkMap.forEach {
                needToBalance.add(findCompoundToBalance(it.toPair()))
            }
            for (it in needToBalance) {
                val firstReagent = it.toList().first()
                val secondReagent = it.toList().last()
                val compoundToBalance = if (reagents.containsKey(firstReagent.first)) firstReagent.first to reagents[firstReagent.first] else null
                val quantityInReagents = compoundToBalance?.first?.getQuantityElements(checkMap.toList().first().first)!! * compoundToBalance.second!!
                val currentQuantityInReagent = it.toList().first().second
                val compoundProductToBalance = if (products.containsKey(secondReagent.first)) secondReagent.first to products[secondReagent.first] else null
                val quantityInProduct = compoundProductToBalance?.first?.getQuantityElements(checkMap.toList().first().first)!! * compoundProductToBalance.second!!
                val quantityFirstReagent = NOK(quantityInReagents, quantityInProduct) / currentQuantityInReagent
                reagents.plusAssign(multiply(firstReagent.first to reagents[firstReagent.first]!!, quantityFirstReagent))
                val currentQuantityInProduct = it.toList().last().second
                val quantity = NOK(quantityInProduct, quantityInReagents) / currentQuantityInProduct
                products.plusAssign(multiply(secondReagent.first to products[secondReagent.first]!!, quantity))
                break
            }
            quantityElementsInReagents = getElementsReagents()
            quantityElementsInProduct = getElementsProducts()
            needToBalance.clear()
        }
        val formula = Formula(reagents, products)
        return formula.getSpan()
    }

    private fun findCompoundToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> { // поиск элементов, которых не хватает
        val mutableList = mutableMapOf<Compound, Int>()
        mutableList.putAll(getCompoundReagentsToBalance(pair))
        mutableList.putAll(getCompoundAnswersToBalance(pair))
        return mutableList
    }

    private fun getCompoundReagentsToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> { // поиск элемнтов в реагентах, которых не хвататет
        val mutableMap = mutableMapOf<Compound, Int>()
        for (it in reagents.keys) {
            if (it.getQuantityElements(pair.first) != null) {
                mutableMap.put(it, it.getQuantityElements(pair.first)!! * reagents[it]!!)
                break
            }
        }
        return mutableMap
    }

    private fun getCompoundAnswersToBalance(pair: Pair<Element, Int>): MutableMap<Compound, Int> { // поиск элементов в продуктах, которых не хватает
        val mutableMap = mutableMapOf<Compound, Int>()
        for (it in products.keys) {
            if (it.getQuantityElements(pair.first) != null) {
                mutableMap.put(it, it.getQuantityElements(pair.first)!! * products[it]!!)
                break
            }
        }
        return mutableMap
    }

    private fun getElementsReagents(): MutableMap<Element, Int> { // возврат всех элементов в реагентах
        var map = mutableMapOf<Element, Int>()
        reagents.forEach {
            for (i in 0 until it.value)
                map = plusMap(map, it.key.elements)
        }
        return map
    }

    private fun getElementsProducts(): MutableMap<Element, Int> { // возврат всех элементов в продуктах
        var map = mutableMapOf<Element, Int>()
        products.forEach {
            for (i in 0 until it.value)
                map = plusMap(map, it.key.elements)
        }
        return map
    }

    private fun multiply(pair: Pair<Compound, Int>, int: Int): Pair<Compound, Int> { // домножение моллекулы
        return pair.first to pair.second * int
    }
}