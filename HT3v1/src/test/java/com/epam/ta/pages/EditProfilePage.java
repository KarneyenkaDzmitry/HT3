package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditProfilePage extends AbstractPage {
    private final String BASE_URL = "https://github.com/settings/profile";

    @FindBy(xpath = "//*[@id='user_profile_name']")
    private WebElement addUserNameField;

    @FindBy(xpath = "//*[@id='user_profile_bio']")
    private WebElement addBioField;

    @FindBy(xpath = "//*[@id='user_profile_location']")
    private WebElement addLocation;

    @FindBy(xpath = "//button[text()= 'Update profile']")
    private WebElement updateProfile;

    public EditProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void openPage() {
        driver.get(BASE_URL);
    }

    public void setUserName(String name) {
        openPage();
        addUserNameField.clear();
        addUserNameField.sendKeys(name);
        pushUpdateProfileButton();
    }

    public void setBio(String text) {
        openPage();
        addBioField.clear();
        addBioField.sendKeys(text);
        pushUpdateProfileButton();
    }

    public void addToBio(String text) {
        openPage();
        addBioField.sendKeys(text);
        pushUpdateProfileButton();
    }

    public void setLocation(String text) {
        openPage();
        addLocation.clear();
        addLocation.sendKeys(text);
        pushUpdateProfileButton();
    }

    public void pushUpdateProfileButton() {
        updateProfile.click();
    }
}
