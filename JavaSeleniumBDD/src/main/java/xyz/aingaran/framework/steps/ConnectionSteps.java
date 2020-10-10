package xyz.aingaran.framework.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import xyz.aingaran.framework.core.Framework;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;

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
        try{
            Framework.getWebDriver().findElement(By.xpath("/html/body/app-root/h1"));
        }catch(Exception e){
            Assert.assertTrue(false);
        }
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
        try{
            Framework.getWebDriver().findElement(By.xpath("//*[contains(text(),'The Homelander')]"));
        }catch(Exception e){
            Assert.assertTrue(false);
        }
    }

    @Then("user takes a screenshot of 'The Homelander' in hero list")
    public void user_takes_a_screenshot_1() {
        Framework.takeScreenShot("pwa", "page2");
    }

    @Given("loads browser to check link2")
    public void user_loads_browser_to_check_link3() {
        Framework.init();
    }

    @Then("user clicks on 'The Homelander'")
    public void user_sees_element_link3() {
        try{
            WebDriver driver = Framework.getWebDriver();
            Actions ac = new Actions(driver);
            WebElement el = driver.findElement(By.xpath("//*[contains(text(),'The Homelander')]"));
            ac.moveToElement(el).click();
            Thread.sleep(1000);
        }catch(Exception e){
            Assert.assertTrue(false);
        }
    }
    @Then("user sees 'The Homelander' value in Textbox")
    public void user_sees_element_textbox() {
        try{
            WebDriver driver = Framework.getWebDriver();
            WebElement tBox = driver.findElement(By.xpath("//input"));
            String tBoxVal = tBox.getAttribute("value");
            Assert.assertEquals(tBoxVal,"The Homelander");
        }catch(Exception e){
            Assert.assertTrue(false);
        }
    }
    @Then("user takes a screenshot of link2")
    public void user_takes_a_screenshot_3() {
        Framework.takeScreenShot("pwa", "page3");
    }
}
