package ValidateCarDetails.stepDefinitions;

import ValidateCarDetails.baseTest.BaseTest;
import ValidateCarDetails.pageObjects.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StepDefinitions extends BaseTest{
    HomePage homePage = new HomePage();

    public StepDefinitions() throws FileNotFoundException {
        super();
    }

    @Before
    public void openBrowser(){
        BaseTest.setUp();
    }
    @Given("I navigated to cartaxcheck website")
    public void i_navigated_to_cartaxcheck_website() {
        BaseTest.navigateToURL();
    }

    @When("I search extracted registration number on car tax check website home page")
    public void i_search_extracted_registration_number_on_car_tax_check_website_home_page() throws IOException, InterruptedException {
        homePage.searchVehicleDetails();
    }

    @Then("The details on the website should match with the details in output text file")
    public void the_details_on_the_website_should_match_with_the_details_in_output_text_file() {
        Assert.assertTrue(homePage.validateVehicleDetails());
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
