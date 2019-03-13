package xyz.aingaran.framework.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features"
        ,glue= {""}
)
public class ConnectionTestRunner {
    @Test
    public void test()  {
        // for running tests..
    }
}
