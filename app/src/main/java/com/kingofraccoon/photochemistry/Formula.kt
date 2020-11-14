package com.kingofraccoon.photochemistry

class Formula(var reagents: MutableMap<Compound, Int>, var products: MutableMap<Compound, Int>) {
    override fun toString(): String {
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
                string = " + "
        }
        return string
    }

    fun getElements(){
        val map = mutableMapOf<Element, Int>()
        reagents.forEach {
            for (i in 0 until it.value)
                map.putAll(it.key.elements)
        }
        println(map)
        val prodMap = mutableMapOf<Element, Int>()
        products.forEach {
            for (i in 0 until it.value)
                prodMap.putAll(it.key.elements)
        }
        println(prodMap)
    }
}
