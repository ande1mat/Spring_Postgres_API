package com.springjpa;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({


        /****** MOCK CONTROLLER TESTS **********/
        MockControllerUnitTests.class,


        /****** MOCK SERVICE TESTS **********/
        MockServiceUnitTests.class



        /****** INTEGRATION TESTS **********/
        /****** THESE INTERACT WITH POSTGRES *********/
        //IntegrationTests.class,

})

public class TestSuite {

}
