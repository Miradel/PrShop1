package com.prestashop.tests.smoke_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Product_information {

    WebDriver driver;
    WebElement product;
    String productName;
    WebElement price;
    String productPrice;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");

        // product = driver.findElement(By.partialLinkText("Faded Short"));
        product = driver.findElement(By.xpath("//a[@class='product-name']"));
        productName = product.getText();
        System.out.println(productName);

        price = driver.findElement(By.xpath("(//div[@class='content_price'])[2]/span"));
        // price = driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]/div/div[2]/div[1]/span"));
        productPrice = price.getText();


    }

    @Test
    public void Product_information_price() {

        System.out.println(productPrice);
        //1.Go to http://automationpractice.com/index.php
        //2.Click on any product
        product.click();

        //3.Verify that same name and price displayed as on the home page

        String actualProductName = driver.findElement(By.xpath("//h1[.='Faded Short Sleeve T-shirts']")).getText();
        String actualPrice = driver.findElement(By.id("our_price_display")).getText();

        System.out.println(actualPrice);

        Assert.assertEquals(productName, actualProductName);
        Assert.assertEquals(productPrice, actualPrice);

    }

    @Test
    public void Product_information_details() {
        /**
         * 6.Verify that size options are S, M, LProduct information–Add to cart:
         */
        //4.that default quantity is 1
        product.click();
        String defaultValue = "1";
        String actualValue = driver.findElement(By.xpath("//input[@id='quantity_wanted']")).getAttribute("value");

        Assert.assertEquals(defaultValue, actualValue);



        /*
        / 5.Verify that DefaultSize is S
        *********
        ** Because select size is in dropDown manu, so we need Select class to get all the selectable options
         ********/
        WebElement actualDefaultSize = driver.findElement(By.xpath("//select[@id='group_1']"));
        Select sel = new Select(actualDefaultSize);

        String defaultSelectedSize = sel.getFirstSelectedOption().getAttribute("title");
        String ExpectedDefaultSize = "S";
        Assert.assertEquals(ExpectedDefaultSize, defaultSelectedSize);

        //6.Verify that size options are S, M, L Product information–Add to cart:
        List<String> expexctedSizeOptions = new ArrayList<>();
        expexctedSizeOptions.add("S");
        expexctedSizeOptions.add("M");
        expexctedSizeOptions.add("L");

        List<WebElement> actualSizeOptions = sel.getOptions();

        for (int i = 0; i < actualSizeOptions.size(); i++) {
            Assert.assertEquals(expexctedSizeOptions.get(i), actualSizeOptions.get(i).getText());
        }

    }

    @Test
    public void Add_to_cart() {
        product.click();

        //7.Click on Add to cart
        driver.findElement(By.xpath("//p[@id='add_to_cart']/button/span")).click();

//        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='fancybox-iframe']"));
//        driver.switchTo().frame(iframe);

        //8.Verify confirmation message “Product successfully added to your shopping cart”
        String expectedConMessage = "Product successfully added to your shopping cart";

       WebElement actualMes = driver.findElement(By.xpath("//div[@class='layer_cart_product col-xs-12 col-md-6']//h2"));


        //actualMes.click();
       WebDriverWait wait = new WebDriverWait(driver,2);
       wait.until(ExpectedConditions.visibilityOf(actualMes));



//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String actualConMessage = actualMes.getText();
        System.out.println("--------------------------");


        System.out.println(actualConMessage);
         Assert.assertEquals(expectedConMessage,actualConMessage);


        // 9.that default quantity is1


        String expectedQuan = "1";
        WebElement defaultQuan = driver.findElement(By.xpath("//span[@id='layer_cart_product_quantity']"));
        String dq = defaultQuan.getText();
        System.out.println(dq);
         Assert.assertEquals(expectedQuan,dq);

        // 10.Verify that defaultsize is S
        String expectedSize = "S";
        String actualSizeeee = driver.findElement(By.xpath("//span[@id=\"layer_cart_product_attributes\"]")).getText();
        System.out.println(actualSizeeee);
         String act = actualSizeeee.substring(actualSizeeee.length()-1);
         System.out.println(act);
         Assert.assertEquals(expectedSize,act);
         Assert.assertTrue(actualSizeeee.contains(expectedSize));

        //11.Verify that same name and price displayed as on the home page
        String expectedName = driver.findElement(By.id("layer_cart_product_title")).getText();
        System.out.println(expectedName);

        driver.close();

    }




}
