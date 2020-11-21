import com.kingofraccoon.photochemistry.Compound
import com.kingofraccoon.photochemistry.EquationFormula
import com.kingofraccoon.photochemistry.emun_class.Element
import com.kingofraccoon.photochemistry.tools.ConvertToFormula

fun main(){
    val h2 = Compound().apply {
        elements = mutableMapOf(
            Element.H to 2
        )
    }

    val o1 = Compound().apply {
        elements = mutableMapOf(
                Element.O to 1
        )
    }

    val sum = h2 + o1
    sum.show()

    val convertToFormula = ConvertToFormula("Cu + O2 = CuO")
    val cTF = ConvertToFormula("Al + O2 = Al2O3")
    val ctf = ConvertToFormula("Na2O + H2O = NaOH")
    val c = ConvertToFormula("Zn + Cl = ZnCl2")
    val c1 = ConvertToFormula("Zn + O2 = ZnO")
    val c2 = ConvertToFormula("Mg + O2 = MgO")
    val c3 = ConvertToFormula("CuO + HCl = CuCl2 + H2O")
    val text = EquationFormula(cTF.getFormula()).check()
//    println(EquationFormula(convertToFormula.getFormula()).check())
    text.forEachIndexed { index, c ->
        println("$index : $c")
    }
//    println(EquationFormula(ctf.getFormula()).check())
//    println(EquationFormula(c.getFormula()).check())
//    println(EquationFormula(c1.getFormula()).check())
//    println(EquationFormula(c2.getFormula()).check())
//    println(EquationFormula(c3.getFormula()).check())
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