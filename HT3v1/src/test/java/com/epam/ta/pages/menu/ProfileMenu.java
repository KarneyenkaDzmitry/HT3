package com.epam.ta.pages.menu;

import com.epam.ta.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfileMenu extends AbstractPage implements MenuBars {
    private final String BASE_URL = "https://github.com/";

    @FindBy(xpath = "//summary[@class='HeaderNavlink name mt-1']")
    private WebElement buttonProfile;

    @FindBy(xpath = "//*[@id=\"user-links\"]/li[3]/details/ul/li[8]/a")
    private WebElement settings;

    @FindBy(linkText = "Your profile")
    private WebElement profile;

    @FindBy(xpath = "//button[contains(@class, \"dropdown-item dropdown-signout\")]")
    @CacheLookup
    private WebElement singOutRef;

    @Override
    public void openPage() {
        driver.get(BASE_URL);
    }

    public ProfileMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void chooseLogout() {
        buttonProfile.click();
        singOutRef.click();
    }

    public void chooseSettings() {
        buttonProfile.click();
        settings.click();
    }

    public void chooseProfile() {
        buttonProfile.click();
        profile.click();
    }
}
