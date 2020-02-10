package com.marcosprata.sas.simuladao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.marcosprata.sas.test.AlunosControllerTest;
import com.marcosprata.sas.test.ProvasControllerTest;
import com.marcosprata.sas.test.QuestoesControllerTest;
import com.marcosprata.sas.test.SimuladosControllerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   AlunosControllerTest.class,
   QuestoesControllerTest.class,
   ProvasControllerTest.class,
   SimuladosControllerTest.class
})

public class SimuladaoSuitTests {   
}  