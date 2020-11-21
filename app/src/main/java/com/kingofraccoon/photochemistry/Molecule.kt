package com.kingofraccoon.photochemistry

import com.kingofraccoon.photochemistry.emun_class.Oxidation

data class Molecule(
        val name: String,
        val ox: Oxidation,
        val symbol: String
)