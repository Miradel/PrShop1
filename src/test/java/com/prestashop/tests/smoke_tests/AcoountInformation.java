package com.prestashop.tests.smoke_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AcoountInformation {
    WebDriver driver;


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");

        //1.Go to http://automationpractice.com/index.php

        //  2.Click Signinlink
        driver.findElement(By.xpath("//a[@class='login']")).click();
        // 3.Login using valid username and password
        driver.findElement(By.id("email")).sendKeys("miradel1234@yahoo.com");
        driver.findElement(By.id("passwd")).sendKeys("miradel1234");
        driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();

    }

    @Test
    public void verfyTitleAndUserName(){
        // 4.Verify that title contains My account
        Assert.assertTrue(driver.getTitle().contains("My account"));

        // 5.Verify that account holder full name is displayed next to the Sign out link
        String expectedName = "alim sattaer";
        String actualName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        Assert.assertEquals(expectedName, actualName);
    }

    @Test
    public void My_personal_information() {


        // 6.Click on My personal informationbutton
        driver.findElement(By.xpath("//a[@title='Information']")).click();
        // 7.Verify title contains Identity
        Assert.assertTrue(driver.getTitle().contains("Identity"));

        // 8.Verify that first name and last name matches the full name on top
        String fullName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        String fristName =
      driver.findElement(By.xpath("//input[@class='is_required validate form-control']")).getAttribute("value");
        String lastName = driver.findElement(By.id("lastname")).getAttribute("value");
        Assert.assertEquals(fullName,fristName+" "+lastName);

        // 9.Click on Save button
        driver.findElement(By.xpath("//button[@class='btn btn-default button button-medium']")).click();

        // 10.Verify error message “The password you entered is incorrect.”
        String actualErrorMessage = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/div/ol/li")).getText();
        String expectedErrorMessage = "The password you entered is incorrect.";
        Assert.assertEquals(expectedErrorMessage,actualErrorMessage);

        // 11.Click onBack to your account
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/a/span")).click();

        // 12.Verify that titlecontains My account
        Assert.assertTrue(driver.getTitle().contains("My account"));

    }
    @Test
    public void My_addresses(){
        //Login:
        // 13.Click on My addresses
        driver.findElement(By.xpath("//i[@class='icon-building']")).click();

        // 14.Click on Adda new address
        driver.findElement(By.xpath("//a[@title='Add an address']")).click();

        // 15.Verify that first name and last name matches the full name on top
        String fullName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        String fristName =
                driver.findElement(By.xpath("//input[@class='is_required validate form-control']")).getAttribute("value");
        String lastName = driver.findElement(By.id("lastname")).getAttribute("value");
        Assert.assertEquals(fullName,fristName+" "+lastName);

        // 16.Delete the first name
        driver.findElement(By.xpath("//input[@class='is_required validate form-control']")).clear();

        // 17.Click save
        driver.findElement(By.id("submitAddress")).click();

        // 18.Verify error message “firstname is required.”
        String expectedErrorMessage = "firstname is required.";
        String actualErrorMessage = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li")).getText();
        Assert.assertEquals(expectedErrorMessage,actualErrorMessage);

    }

}
