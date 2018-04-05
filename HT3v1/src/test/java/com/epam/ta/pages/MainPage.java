package com.epam.ta.pages;

import com.epam.ta.pages.menu.CreateMenu;
import com.epam.ta.pages.menu.ProfileMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage extends AbstractPage {
    private final String BASE_URL = "https://github.com/";

    private CreateMenu createMenu;
    private ProfileMenu profileMenu;

    @FindBy(xpath = "//a[@class=\"UnderlineNav-item \"][@title=\"Repositories\"]")
    private WebElement repositories;

    @FindBy(xpath = "//*[@id='user-repositories-list']/ul/li[1]/div[1]/h3/a")
    private WebElement repository;

    @FindBy(xpath = "//span[@class='p-name vcard-fullname d-block overflow-hidden'][@itemprop='name']")
    private WebElement userName;

    @FindBy(xpath = "//*[@id='js-pjax-container']/div/div[1]/div[4]/div")
    private WebElement bio;

    @FindBy(xpath = "//*[@id='js-pjax-container']/div/div[1]/ul/li[1]/span")
    private WebElement location;

    @FindBy(xpath = "//*[@id='your-repos-filter']")
    private WebElement filter;

    @FindBy(xpath = "//*[@id='user-repositories-list']/div[2]/h4")
    private WebElement noMatchText;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
        createMenu = new CreateMenu(driver);
        profileMenu = new ProfileMenu(driver);
    }

    public void clickOnCreateNewRepositoryButton() {
        createMenu.chooseNewRepository();
    }

    public void clickLogoutReference() {
        openPage();
        profileMenu.chooseLogout();
    }

    private RepositoryPage chooseRepo(WebElement element) {
        String el = element.getText();
        element.click();
        return new RepositoryPage(driver, el);
    }

    public String deleteRepository() {
        chooseListOfRepositories();
        RepositoryPage repositoryPage = chooseRepo(driver.findElement(By.xpath("//*[@id='user-repositories-list']/ul/li[1]/div[1]/h3/a")));
        String nameRepo = repositoryPage.getName();
        RepoSettingsPage repoSettingsPage = repositoryPage.getSettings();
        repoSettingsPage.deleteRepo();
        return nameRepo;
    }

    public void chooseListOfRepositories() {
        openPage();
        profileMenu.chooseProfile();
        repositories.click();
    }

    public boolean repoExists(String name) {
        chooseListOfRepositories();
        filter.sendKeys(name);
        try {
            noMatchText.getText();
        } catch (NoSuchElementException e) {
            return true;
        }
        if (noMatchText.getText().contains(" doesn’t have any repositories that match.")) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

    public EditProfilePage chooseEditProfile() {
        openPage();
        profileMenu.chooseSettings();
        return new EditProfilePage(driver);
    }

    public String getUserName() {
        openPage();
        profileMenu.chooseProfile();
        return userName.getText();
    }

    public String getBio() {
        openPage();
        profileMenu.chooseProfile();
        return bio.getText();
    }

    public String getLocation() {
        openPage();
        profileMenu.chooseProfile();
        return location.getText();
    }
}
