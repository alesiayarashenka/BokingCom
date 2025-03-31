package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        tags = "@all",
        glue = "steps",
        snippets = CucumberOptions.SnippetType.UNDERSCORE,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }

)

public class BookingTest extends AbstractTestNGCucumberTests {
}
