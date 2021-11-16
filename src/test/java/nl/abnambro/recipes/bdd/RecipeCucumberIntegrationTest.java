package nl.abnambro.recipes.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"pretty", "html:target/cucumber/recipes"},
        glue= "nl.abnambro.recipes")
public class RecipeCucumberIntegrationTest {
}
