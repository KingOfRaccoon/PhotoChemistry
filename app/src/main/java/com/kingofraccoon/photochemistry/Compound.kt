package com.kingofraccoon.photochemistry

import com.kingofraccoon.photochemistry.emun_class.Element
// класс моллекулы
class Compound {
    var elements = mutableMapOf<Element, Int>()

    operator fun plus(other: Compound): Compound {
        var mySum = 0
        for (i in 0..elements.size - 1) {
            mySum += this.elements.keys.toList()[i].molecule.ox.k * this.elements.values.toList()[i]
        }
        var otherSum = 0
        for (i in 0..elements.size - 1) {
            otherSum += other.elements.keys.toList()[i].molecule.ox.k * other.elements.values.toList()[i]
        }

        println(mySum)
        println(otherSum)
        if (mySum + otherSum == 0) {
            println("nice")
            var ret = Compound()
            ret.elements.putAll(this.elements)
            ret.elements.putAll(other.elements)
            return ret
        } else {

        }
        return Compound()
    }

    fun show() {
        println(this.elements)
    }

    override fun toString(): String { // вывод названия молекулы
        var string = ""
        elements.forEach {
            string += it.key.molecule.symbol
            if (it.value != 1)
                string += it.value.toString()
        }
        return string
    }

    fun getQuantityElements(element: Element): Int? { // возвращение количества в данной моллекуле данных элементов
        if (elements.containsKey(element))
            return elements[element]!!
        else
            return null
    }
}
