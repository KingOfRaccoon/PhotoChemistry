data class Molecule(
    val name: String,
    val ox: Oxidation,
    val symbol: String
)

//enum class StartElement(val molecule: Molecule){
//    H(Molecule())
//}
enum class Element(var molecule: Molecule) {
    H(Molecule("hydrogenium", Oxidation.I1, "H")),
    O(Molecule("oxygen", Oxidation._I2, "O"))
}

class Compound{
    var elements = mutableMapOf<Element, Int>()

    operator fun plus(other: Compound): Compound {
        var mySum = 0
        for (i in 0..elements.size-1) {
            mySum += this.elements.keys.toList()[i].molecule.ox.k * this.elements.values.toList()[i]
        }
        var otherSum = 0
        for (i in 0..elements.size-1) {
            otherSum += other.elements.keys.toList()[i].molecule.ox.k * other.elements.values.toList()[i]
        }

        println(mySum)
        println(otherSum)
        if (mySum + otherSum == 0){
            println("nice")
            var ret = Compound()
            ret.elements.putAll(this.elements)
            ret.elements.putAll(other.elements)
            return ret
        }
        else{
            
        }
        return Compound()
    }
    fun show(){
        println(this.elements)
    }
//    fun getFormule(){
//        var string = ""
//        elements.fo
//    }

    override fun toString(): String {
        var string = ""
        elements.forEach {
            string += it.key.molecule.symbol
            if (it.value != 1)
                string += it.value.toString()
        }
        return string
    }

    fun searchElement(element: Element): Element? {
        if(elements.containsKey(element)){
           return element
        }
        else
//        this.elements.keys.toMutableList().forEach {
//            if (it.name == element.name){
//                return element
//            }
//        }
        return null
    }
}
class Formula(var reagents: MutableList<Compound>, var products: MutableList<Compound>) {

    override fun toString(): String {
        var string = ""
        reagents.forEach {
            string += it.toString()
            if (it != reagents.last())
                string += " + "
            else
                string += " = "
        }
        products.forEach {
            string += it.toString()
            if (it != products.last())
                string = " + "
        }
        return string
    }

    fun getElements(){
        val map = mutableMapOf<Element, Int>()
        reagents.forEach {
            map.putAll(it.elements)
        }
        println(map)
        val prodMap = mutableMapOf<Element, Int>()
        products.forEach {
            prodMap.putAll(it.elements)
        }
        println(prodMap)
    }
}

//степени окисления
enum class Oxidation(val k:Int){
    I0(0),
    I1(1),
    _I1(-1),
    I2(2),
    _I2(-2),
    I3(3),
    _I3(-3),
    I4(4),
    _I4(-4),
    I5(5),
    _I5(-5),
    I6(6),
    _I6(-6),
    I7(7),
    _I7(-7),
    I8(8),
    _I8(-8)
}
