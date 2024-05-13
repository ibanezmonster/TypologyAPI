package com.typology;

import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
    "com.typology.service"
})
public class ServiceLayerUnitTestSuite{

}