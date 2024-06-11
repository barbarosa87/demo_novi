package com.example.demo;


import com.example.demo.poms.LiveProgramPage;
import com.example.demo.poms.LiveStoiximaPage;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

@Component
public class Actions {

    @Autowired
    LiveProgramPage liveProgramPage;
    @Autowired
    LiveStoiximaPage liveStoiximaPage;

    public void openLiveProgramPage() {
        liveProgramPage.openPage();
    }

    public void quitLiveProgramBrowser() {
        liveProgramPage.quitDriver();
    }


    public void filterMarketList(String market) {
        liveProgramPage.filterMarkets(market);
    }


    public void openLiveStoiximaPage() {
        liveStoiximaPage.openPage();
    }

    public void quitLiveStoiximaBrowser() {
        liveStoiximaPage.quitDriver();
    }

    public void waitAndCheckEvents(int timeout) {
        int interval = 1000;
        long startTime = System.currentTimeMillis();
        LongSupplier timeSpent = () -> System.currentTimeMillis() - startTime;

        List<WebElement> allEventsElements=liveProgramPage.getAllEvents();
        try {

            while (timeSpent.getAsLong() < timeout*60000) {
               // List<WebElement> goingLive=getGoingLiveElements(allEventsElements);
                //allEventsElements.stream().
                allEventsElements.stalen4
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private List<WebElement> getGoingLiveElements(List<WebElement> allEventsElements) {
        List<WebElement> allEventsElementsSnapshot=liveProgramPage.getAllEvents();
        List<WebElement> goingLiveEvents=new ArrayList<>();
        allEventsElements.forEach(webElement ->{

           /* String href =webElement.getAttribute("href");
           if (allEventsElementsSnapshot.stream().noneMatch(origElement->origElement.findElement(By.xpath(".//a")).getAttribute("href").equalsIgnoreCase(href))){
               goingLiveEvents.add(webElement);
           }*/
        });
        System.out.println("Going live events = "+goingLiveEvents.size());
        return goingLiveEvents;
    }
}
