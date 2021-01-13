package ValidateCarDetails.testRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features= {"src\\test\\features\\verifyCarDetails.feature"},
        glue = {"ValidateCarDetails.stepDefinitions"},
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber"}
)
public class TestRunner {

}
