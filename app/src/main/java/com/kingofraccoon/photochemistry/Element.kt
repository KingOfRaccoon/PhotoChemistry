package com.kingofraccoon.photochemistry

enum class Element(var molecule: Molecule) {
    H(Molecule("hydrogenium", Oxidation.I1, "H")),
    O(Molecule("oxygen", Oxidation._I2, "O"))
}