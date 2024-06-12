package com.example.demo.poms;

import com.example.demo.UICommonActions;
import com.example.demo.configuration.Config;
import com.example.demo.model.ScheduleEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class LiveProgramPage {
    @Autowired
    Config config;
    @Autowired
    UICommonActions uiCommonActions;

    private WebDriver webDriver;

    private By modalCloseBtn= By.xpath("//div[@data-cy='closeBtn']");
    private By registerOrLoginModal= By.xpath("//div[@data-cy='registerOrLogin']");
    private By filter= By.xpath("//cm-floating-container//span[text()='Όλοι οι αγώνες']");
    private String filterItem="//div[@class='cdk-overlay-pane']//span[text()='%s']";

    private By kumulosPrompt=By.className("kumulos-prompt");
    private By kumulosPromptNo=By.xpath(".//button[text()='Όχι, ευχαριστώ']");

    private String eventItem="//cm-card-body//sb-event-default-list-item//a[@href='%s']";

    private By eventList=By.xpath("//cm-card-body//sb-event-default-list-item");


    public LiveProgramPage(  Config config,UICommonActions uiCommonActions){

        this.config=config;
        this.uiCommonActions=uiCommonActions;

    }
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
        webDriver.get(config.getUrlProgram());
        webDriver.manage().addCookie(new Cookie("tutorial_live_calendar", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("acceptCookiesV1", "acceptCookies"));
        webDriver.manage().addCookie(new Cookie("registerOrLogin", "true"));

    }

    public void quitDriver(){
        webDriver.quit();
    }

    public void filterMarkets(String market){
      WebElement filterElem= uiCommonActions.waitAndGetElement(webDriver,filter,30);
      filterElem.click();
      uiCommonActions.retryingFindClick(webDriver,By.xpath(String.format(filterItem,market)),1);
    }

    @Retryable(maxAttempts = 3, value = { StaleElementReferenceException.class})
    public List<ScheduleEvent> getAllEvents(){
        List<WebElement> events= uiCommonActions.waitAndGetElements(webDriver,eventList,30);
        List<ScheduleEvent> scheduleEventList=new ArrayList<>();
        events.forEach(webElement->{
            scheduleEventList.add(
                    ScheduleEvent.builder().market(webElement.findElement(By.xpath(".//img")).getAttribute("alt")).url(webElement.findElement(By.xpath(".//a")).getAttribute("href").replace("https://www.novibet.gr","")).build());
        });
        return scheduleEventList;
    }

    public  boolean checkIFElementGoingLive(ScheduleEvent event){
       return uiCommonActions.waitAndGetElement(webDriver,By.xpath(String.format(eventItem,event.getUrl())),5)==null;
    }


}
