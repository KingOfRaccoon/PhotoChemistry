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
}
