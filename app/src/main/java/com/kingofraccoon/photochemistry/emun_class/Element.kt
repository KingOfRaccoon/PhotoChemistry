package com.kingofraccoon.photochemistry.emun_class

import com.kingofraccoon.photochemistry.Molecule
// класс всех элементов
enum class Element(var molecule: Molecule) {
    H(Molecule("Hydrogenium", Oxidation.I1, "H")),
    O(Molecule("Oxygen", Oxidation._I2, "O")),
    Al(Molecule("Aluminium", Oxidation.I3, "Al")),
    Zn(Molecule("Zinc", Oxidation.I2, "Zn")),
    Cl(Molecule("Chlorine", Oxidation._I1, "Cl")),
    Mg(Molecule("Magnesium", Oxidation.I2, "Mg")),
    Cu(Molecule("Cuprum", Oxidation.I2, "Cu")),
    Na(Molecule("Natrium", Oxidation.I1, "Na")),
    Ca(Molecule("Calcium", Oxidation.I2, "Ca"))
}