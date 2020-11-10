fun main(){
    var h2 = Compound().apply {
        elements = mutableMapOf(
            Element.H to 2
        )
    }
    var o = Compound().apply {
        elements = mutableMapOf(
            Element.O to 1
        )
    }
    var sum = h2 + o
    sum.show()
    var o1 = Element.O
    println(sum.searchElement(o1))
    println(o1.molecule.ox)
//    2H2 + 02 = 2H2O
//    H2 + O2 = H2O
//
}
