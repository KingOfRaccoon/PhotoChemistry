import com.kingofraccoon.photochemistry.Compound
import com.kingofraccoon.photochemistry.CompoundGenerator
import com.kingofraccoon.photochemistry.Element
import com.kingofraccoon.photochemistry.Formula
import com.kingofraccoon.photochemistry.tools.ConvertToFormula

fun main(){
    val h2 = Compound().apply {
        elements = mutableMapOf(
            Element.H to 2
        )
    }
    val o = Compound().apply {
        elements = mutableMapOf(
            Element.O to 2
        )
    }
    val o1 = Compound().apply {
        elements = mutableMapOf(
                Element.O to 1
        )
    }
    val sum = h2 + o
    sum.show()
    println(sum.toString())
    val formula = Formula(
            mutableMapOf(h2 to 1, o to 1),
            mutableMapOf(sum to 1)
    )
    println(formula.toString())
    formula.getElements()
    val map = mutableMapOf(Element.O to 3, Element.H to 5)
    val map1 = mutableMapOf(Element.O to 3, Element.H to 5)
//    map1.forEach {
//        if (map.containsKey(it.key))
//            map.plusAssign(it.key to map[it.key]!! - it.value)
//        else
//            map.plusAssign(it.key to -it.value)
//    }
//    println(map)
//    println(plusMap(map, map1))
    println(NOD(126, 70))
    println(NOK(126, 70))
//    val muMap = mutableMapOf<com.kingofraccoon.photochemistry.Compound, Int>()
//    formula.reagents.forEach {
//        muMap.put(it, 1)
//    }
    val compoundGenerator = CompoundGenerator(formula.reagents, h2 + o1 to 1)
//    println(compoundGenerator.getElementsReagents())
//    println(compoundGenerator.getElementsAnswers())
//    val v = minusMap(compoundGenerator.getElementsReagents(), compoundGenerator.getElementsAnswers())
//    v.forEach {
//        println(compoundGenerator.find(it.key to it.value))
//    }
    println(compoundGenerator.reagents)
    println(compoundGenerator.answer)
    compoundGenerator.check()
    println(compoundGenerator.reagents)
    println(compoundGenerator.answer)

//    println(sum.searchElement(o1))
//    println(h2)
//    2H2 + 02 = 2H2O
//    H2 + O2 = H2O
//    2H0 - 2H+1 = 2e
//    2O0 - 20-2 = -4e
    val convertToFormula = ConvertToFormula("H2 + O2 = H2O")
    println(convertToFormula.getElement("H" to 2))
    println(convertToFormula.getCompound("H2O"))
    println(convertToFormula.getFormula().toString())
    CompoundGenerator(convertToFormula.getFormula()).check()
}

fun minusMap(startMutableMap: MutableMap<Element, Int>, mutableMap: MutableMap<Element, Int>): MutableMap<Element, Int> {
    mutableMap.forEach {
        if (startMutableMap.containsKey(it.key))
            if (startMutableMap[it.key] == it.value)
                startMutableMap.remove(it.key)
            else
                startMutableMap.plusAssign(it.key to startMutableMap[it.key]!! - it.value)
        else
            startMutableMap.plusAssign(it.key to -it.value)
    }
    return startMutableMap
}
fun plusMap(startMap: MutableMap<Element, Int>, map: MutableMap<Element, Int>): MutableMap<Element, Int>{
    map.forEach {
        if (startMap.containsKey(it.key))
            startMap.plusAssign(it.key to it.value + startMap[it.key]!!)
        else
            startMap.plusAssign(it.key to it.value)
    }
    return startMap
}

fun NOD(a: Int, b: Int): Int {
    var a1 = a
    var b1 = b
    while (b1 != 0){
        val temp = a1 % b1
        a1 = b1
        b1 = temp
    }
    return a1
}

fun NOK(a: Int, b: Int): Int{
    return a*b/NOD(a,b)
}