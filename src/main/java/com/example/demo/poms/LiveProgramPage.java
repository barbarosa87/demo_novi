package com.example.demo.poms;

import com.example.demo.UICommonActions;
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
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class LiveProgramPage {


    @Value( "${live-programma.url}" )
    private String url;
    @Value( "${waittimeoutSeconds}" )
    private int waittimeoutSeconds;

    @Autowired
    UICommonActions UICommonActions;

    private WebDriver webDriver;

    private By modalCloseBtn= By.xpath("//div[@data-cy='closeBtn']");
    private By registerOrLoginModal= By.xpath("//div[@data-cy='registerOrLogin']");
    private By filter= By.xpath("//cm-floating-container//span[text()='Όλοι οι αγώνες']");
    private String filterItem="//div[@class='cdk-overlay-pane']//span[text()='%s']";

    private By kumulosPrompt=By.className("kumulos-prompt");
    private By kumulosPromptNo=By.xpath(".//button[text()='Όχι, ευχαριστώ']");



    private By eventList=By.xpath("//cm-card-body//sb-event-default-list-item");

    //private By eventList=By.xpath(".//sb-event-default-list-item");



    public LiveProgramPage(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver=new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waittimeoutSeconds));
        this.webDriver=driver;
    }
    public void openPage(){
        webDriver.get(url);
        webDriver.manage().addCookie(new Cookie("tutorial_live_calendar", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("acceptCookiesV1", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("registerOrLogin", "true"));

    }

    public void quitDriver(){
        webDriver.quit();
    }

    public void filterMarkets(String market){
      WebElement filterElem= UICommonActions.waitAndGetElement(webDriver,filter,30);
      filterElem.click();
      UICommonActions.retryingFindClick(webDriver,By.xpath(String.format(filterItem,market)));
    }

    public List<WebElement> getAllEvents(){
       // WebElement cardElem=UICommonActions.waitAndGetElement(webDriver,eventCard,30);
        List<WebElement> events=UICommonActions.waitAndGetElements(webDriver,eventList,30);
       // List<ScheduleEvent> scheduleEventList=new ArrayList<>();

        return events;
    }



}
