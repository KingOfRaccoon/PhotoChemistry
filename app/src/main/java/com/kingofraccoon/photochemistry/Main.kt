fun main(){
    val h2 = Compound().apply {
        elements = mutableMapOf(
            Element.H to 2
        )
    }
    val o = Compound().apply {
        elements = mutableMapOf(
            Element.O to 1
        )
    }
    val sum = h2 + o
    sum.show()
    println(sum.toString())
    val o1 = Element.O
    val formula = Formula(
            mutableListOf(h2, o),
            mutableListOf(sum)
    )
    println(formula.toString())
    formula.getElements()

//    println(sum.searchElement(o1))
//    println(h2)
//    2H2 + 02 = 2H2O
//    H2 + O2 = H2O
//    2H0 - 2H+1 = 2e
//    2O0 - 20-2 = -4e
}
