package com.epam.ta.pages.menu;

import com.epam.ta.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateMenu extends AbstractPage implements MenuBars {
    private final String BASE_URL = "https://github.com/";

    @FindBy(xpath = "//summary[contains(@aria-label, 'Create new…')]")
    @CacheLookup
    private WebElement buttonCreateNew;

    @FindBy(xpath = "//a[contains(text(), 'New repository')]")
    @CacheLookup

    private WebElement linkNewRepository;
    @Override
    public void openPage() {
        driver.get(BASE_URL);
    }

    public CreateMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void chooseNewRepository () {
        buttonCreateNew.click();
        linkNewRepository.click();
    }

}
