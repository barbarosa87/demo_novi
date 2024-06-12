package com.example.demo.poms;

import com.example.demo.UICommonActions;
import com.example.demo.configuration.Config;
import com.example.demo.model.ScheduleEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
public class LiveStoiximaPage {

    @Autowired
    Config config;

    @Autowired
    UICommonActions uiCommonActions;

    private String filter="//sb-carousel//cm-svg-image/img[@alt='%s']";
    private String eventSelector="//cm-card-body/sb-event-default-list-item-live/div/a[contains(@href, '%s')]";
    private WebDriver webDriver;



    public void openPage(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver=new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getWaittimeoutSeconds()));
        this.webDriver=driver;
        webDriver.get(config.getUrlLive());
        webDriver.manage().addCookie(new Cookie("tutorial_live_calendar", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("acceptCookiesV1", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("registerOrLogin", "true"));


    }

    public void quitDriver(){
        webDriver.quit();
    }


    public boolean elemApears(ScheduleEvent event) {
       try {
        WebElement filterElem= uiCommonActions.waitAndGetElement(webDriver, By.xpath(String.format(filter,event.getMarket())),1);
        filterElem.click();
        String[] urlSplit=event.getUrl().split("/");
        WebElement eventElem= uiCommonActions.waitAndGetElement(webDriver, By.xpath(String.format(eventSelector,urlSplit[urlSplit.length-1].replace("e",""))),1);
        return eventElem!=null;
       }catch (NullPointerException e){
           return false;
       }

    }
}
