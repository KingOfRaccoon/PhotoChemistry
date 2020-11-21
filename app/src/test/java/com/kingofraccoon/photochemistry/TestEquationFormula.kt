package com.kingofraccoon.photochemistry

import com.kingofraccoon.photochemistry.tools.ConvertToFormula
import org.junit.Assert
import org.junit.Test

class TestEquationFormula {
    @Test
    fun checkCuO2(){
        val convertToFormula = ConvertToFormula("Cu + O2 = CuO")
        Assert.assertEquals("2Cu + O2 = 2CuO", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkAlO2(){
        val convertToFormula = ConvertToFormula("Al + O2 = Al2O3")
        Assert.assertEquals("4Al + 3O2 = 2Al2O3", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkNa2OH2O(){
        val convertToFormula = ConvertToFormula("Na2O + H2O = NaOH")
        Assert.assertEquals("Na2O + H2O = 2NaOH", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkZnCl(){
        val convertToFormula = ConvertToFormula("Zn + Cl = ZnCl2")
        Assert.assertEquals("Zn + 2Cl = ZnCl2", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkZnO2(){
        val convertToFormula = ConvertToFormula("Zn + O2 = ZnO")
        Assert.assertEquals("2Zn + O2 = 2ZnO", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkMgO2(){
        val convertToFormula = ConvertToFormula("Mg + O2 = MgO")
        Assert.assertEquals("2Mg + O2 = 2MgO", EquationFormula(convertToFormula.getFormula()).check())
    }
    @Test
    fun checkCuOHCl(){
        val convertToFormula = ConvertToFormula("CuO + HCl = CuCl2 + H2O")
        Assert.assertEquals("CuO + 2HCl = CuCl2 + H2O", EquationFormula(convertToFormula.getFormula()).check())
    }
}