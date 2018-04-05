package com.epam.ta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RepositoryPage extends AbstractPage {
    private String name;

    @Override
    public void openPage() {

    }

    public RepositoryPage(WebDriver driver, String name) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RepoSettingsPage getSettings() {
        By xpath = By.xpath("/html/body/div[4]/div/div/div[1]/nav/a[4]");
        driver.findElement(xpath).click();
        return new RepoSettingsPage(driver, name);
    }
}
