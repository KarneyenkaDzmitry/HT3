package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RepoSettingsPage extends AbstractPage {
    private String name;

    @FindBy(xpath = "/html/body/div[4]/div/div/div[2]/div[1]/div/div[2]/div/div[10]/ul/li[4]/button")
    private WebElement deleteButton;

    @FindBy(xpath = "/html/body/div[4]/div/div/div[2]/div[1]/div/div[2]/div/div[10]/ul/li[4]/div/div/div/div/div[2]/form/p/input")
    private WebElement fieldForType;

    @FindBy(xpath = "//button[text()='I understand the consequences, delete this repository']")
    private WebElement confirmDelete;

    public RepoSettingsPage(WebDriver driver, String name) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.name = name;
    }

    public void openPage() {

    }

    public void deleteRepo() {
        deleteButton.click();
        WebDriverWait wait = new WebDriverWait(this.driver, 2);
        while (!fieldForType.isEnabled()) {
            try {
                Thread.sleep(500);
                //wait.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fieldForType.sendKeys(name);
        confirmDelete.click();
    }
}
