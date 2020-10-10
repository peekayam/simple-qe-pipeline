package xyz.aingaran.framework.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import xyz.aingaran.framework.core.Framework;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConnectionSteps {

    @Given("user loads browser")
    public void user_loads_browser() {
        Framework.init();
    }

    @When("user navigates to http:\\/\\/52.150.14.46:3000\\/")
    public void user_navigates_to_google_com() {
        Framework.getWebDriver().navigate().to("http:\\/\\/52.150.14.46:3000\\/");
    }

    @Then("user sees element h1")
    public void user_sees_element_hplogo() {
        Framework.getWebDriver().findElement(By.xpath("/html/body/app-root/h1"));
    }
    
    @Then("user takes a screenshot")
    public void user_takes_a_screenshot() {
        Framework.takeScreenShot("pwa", "page1");
    }   
    
    @Given("loads browser to check link1")
    public void user_loads_browser_to_check_link() {
        Framework.init();
    }

    @Then("user sees 'The Homelander' in hero list")
    public void user_sees_element_link1() {
        Framework.getWebDriver().findElement(By.xpath("//*[contains(text(),'The Homelander')]"));
    } 
    
    @Then("user takes a screenshot of 'The Homelander' in hero list")
    public void user_takes_a_screenshot_1() {
        Framework.takeScreenShot("pwa", "page2");
    } 
    
    @Given("loads browser to check link2")
    public void user_loads_browser_to_check_link3() {
        Framework.init();
    }

    @Then("user sees element link2")
    public void user_sees_element_link3() {
        try{
            Framework.getWebDriver().findElement(By.id("link3"));
        }catch(Exception e){
            Assert.assertFalse(true);
        } 
    } 
    
    @Then("user takes a screenshot of link2")
    public void user_takes_a_screenshot_3() {
        Framework.takeScreenShot("pwa", "page3");
    } 
}
