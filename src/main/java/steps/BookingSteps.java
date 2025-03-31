package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import waiters.Waiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class BookingSteps {
    Waiter waiter = new Waiter();

    String city;

    @BeforeMethod
    public void initTest() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        options.addArguments("--disable-popup-blocking");
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);
        setWebDriver(driver);
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.headless = false;
        Configuration.browserSize = "1024x768";
    }

    @Given("User is looking for hotels in {string} city")
    public void userIsLookingForHotelsInCity(String city) {
        this.city = city;
    }

    @When("User does search")
    public void userDoesSearch() {
        //open page
        //set value in city field
        //click on dates
        //select dates
        //click search
        open("https://www.booking.com/");
        $(By.name("ss")).setValue(city);
        $(By.cssSelector("[data-testid='searchbox-dates-container']")).click();
        $(By.cssSelector("[data-date='2025-03-29']")).click();
        $(By.cssSelector("[data-date='2025-04-27']")).click();
        $(By.cssSelector("[type='submit']")).click();
    }

    @Then("Hotel {string} should be on the search results page")
    public void hotelShouldBeOnTheSearchResultsPage(String hotel) {
        ElementsCollection titleList = $$(By.cssSelector("[data-testid='title']"));
        ArrayList<String> hotelNames = new ArrayList<>();
        for (SelenideElement element : titleList) {
            hotelNames.add(element.getText());
        }
        Assert.assertTrue(hotelNames.contains(hotel));
    }

    @Then("Hotel {string} rating is {string}")
    public void hotelKahanaFallsResortOpensRatingIs(String hotel, String rate) {
        String hotelRate = $x(String.format("//*[contains(text(),'%s')]/ancestor::div[@data-testid='property-card-container']//*[@data-testid='review-score']/div/div", hotel)).getText();
        Assert.assertEquals(hotelRate.split(" ")[1], rate);
    }

    @AfterMethod
    public void endTest() {
        getWebDriver().quit();
    }

    @When("User chooses city and dates")
    public void userStartsSearch() {
        open("https://www.booking.com/");
        $(By.name("ss")).setValue(city);
        waiter.waitForPageLoaded();
        $(By.cssSelector("[data-testid='searchbox-dates-container']")).click();
        $(By.cssSelector("[data-date='2025-03-29']")).click();
        $(By.cssSelector("[data-date='2025-04-29']")).click();
    }

    @And("User submits your options")
    public void userSubmitsYourOptions() {
        $(By.cssSelector("[type='submit']")).click();
        waiter.waitForPageLoaded();
    }

    @And("User chooses room for three adults")
    public void userChoosesRoomForAdults() {
        $(By.cssSelector("[data-testid='occupancy-config']")).click();
        $(By.xpath("//*[contains(text(),'Взрослых')]/following::div[1]/child::button[2]")).click();
    }

    @And("User chooses room for one child")
    public void userChoosesRoomForChild() {

    }

    @And("User chooses room with two bedrooms")
    public void userChoosesRoomWithBedrooms() {
        $(By.xpath("//*[contains(text(),'Номера')]/following::div[1]/child::button[2]")).click();
    }

    @And("User chooses opportunity to stay with pets")
    public void userChoosesOpportunityToStayWithPets() {
        $(By.xpath("//input[@type='checkbox']/following::label/child::span[1]")).click();
        $(By.xpath("//*[contains(text(),'Готово')]/ancestor::button")).click();
    }

    @And("User chooses room for one child {string} years")
    public void userChoosesRoomForOneChildYears(String age) {
        $(By.xpath("//*[contains(text(),'Детей')]/following::div[1]/child::button[2]")).click();
        $(By.xpath("//select[@name='age']")).click();
        $x(String.format("//select[@name='age']/child::*[@data-key='%s']", age)).click();
    }

    @And("User chooses filter villas")
    public void userChoosesFilterVillas() {
        $(By.xpath("//div[contains(@id,'filter_group_popular_:rp:')]/child::div[contains(@data-filters-item,'id=213')]")).click();
        waiter.waitForPageLoaded();
    }

/*As example
    @Given("User provide information:")
    public void userProvideInformation(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String name = data.get(0).get("name");
        String email = data.get(0).get("email");
        String phone = data.get(0).get("phone");
        System.out.println("Name: " + name);
    }

 */
}
