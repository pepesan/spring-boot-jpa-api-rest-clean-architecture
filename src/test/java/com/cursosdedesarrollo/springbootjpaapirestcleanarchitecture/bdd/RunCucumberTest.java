package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Descubre y ejecuta los .feature bajo classpath: features
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,
        value = "com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.bdd.steps")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, summary, junit:target/cucumber-junit-report.xml, html:target/cucumber-report.html")
public class RunCucumberTest {
}

