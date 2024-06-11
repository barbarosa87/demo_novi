package com.example.demo.steps;


import com.example.demo.Actions;
import com.example.demo.context;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@CucumberContextConfiguration
@SpringBootTest(classes ={context.class})
public class StepDefinition  {

    @Autowired
    Actions actions;

    @Given("User opens live-program page")
    public void user_opens_live_program_page() {
        actions.openLiveProgramPage();
    }

    @Given("User closes the live-program browser")
    public void user_closes_live_program_browser() {
        actions.quitLiveProgramBrowser();
    }


    @And("User filter with {string} filter")
    public void user_uses_market_filter(String market) {
        actions.filterMarketList(market);
    }

    @And("User opens stoixima-live page")
    public void userOpensStoiximaLivePage() {
        actions.openLiveStoiximaPage();
    }

    @Given("User closes stoixima-live the browser")
    public void user_closes_stoixima_live_browser() {
        actions.quitLiveStoiximaBrowser();
    }

    @And("User waits {int} minutes and checking if events are delayed or dropped")
    public void userWaitsMinutesAndCheckingIfEventsAreDelayedOrDropped(int time) {
        actions.waitAndCheckEvents(time);
    }
}
