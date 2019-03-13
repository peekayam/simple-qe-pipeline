package xyz.aingaran.framework.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import xyz.aingaran.framework.core.Framework;

public class Context {

    @Before
    public void beforeScenario(Scenario scenario)   {
        //
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            byte[] screenshot = Framework.takeScreenShot(scenario.getName(), scenario.getId());
            scenario.embed(screenshot, "image/png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
