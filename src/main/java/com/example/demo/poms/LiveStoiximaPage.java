package com.example.demo.poms;

import com.example.demo.UICommonActions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
public class LiveStoiximaPage {

    @Value( "${stoixima-live.url}" )
    private String url;
    @Value( "${waittimeoutSeconds}" )
    private int waittimeoutSeconds;

    @Autowired
    UICommonActions UICommonActions;

    private WebDriver webDriver;


    public LiveStoiximaPage(){
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




}
