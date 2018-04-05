package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.CreateNewRepositoryPage;
import com.epam.ta.pages.EditProfilePage;
import com.epam.ta.pages.LoginPage;
import com.epam.ta.pages.MainPage;
import com.epam.ta.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Steps {
    private WebDriver driver;

    private final Logger logger = LogManager.getRootLogger();

    public void initBrowser() {
        driver = DriverSingleton.getDriver();
    }

    public void closeDriver() {
        DriverSingleton.closeDriver();
    }

    public void loginGithub(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        loginPage.login(username, password);
    }

    public boolean isLoggedIn(String username) {
        LoginPage loginPage = new LoginPage(driver);
        String actualUsername = loginPage.getLoggedInUserName().trim();
        logger.info("Actual username: " + actualUsername);
        return actualUsername.equals(username);
    }

    public boolean createNewRepository(String repositoryName, String repositoryDescription) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnCreateNewRepositoryButton();
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        String expectedRepoName = createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
        return expectedRepoName.equals(createNewRepositoryPage.getCurrentRepositoryName());
    }

    public boolean currentRepositoryIsEmpty() {
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        return createNewRepositoryPage.isCurrentRepositoryEmpty();
    }

    public boolean logoutGitHub() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogoutReference();
        return driver.getCurrentUrl().equals("https://github.com/");
    }

    public boolean deleteRepositories(int count) {
        boolean marker = true;
        for (int i = 0; i < count; i++) {
            marker = deleteFirstRepository();
            if (!marker) {
                return marker;
            }
        }
        return marker;
    }

    public boolean deleteFirstRepository() {
        MainPage mainPage = new MainPage(driver);
        String nameRepo = mainPage.deleteRepository();
        logger.info("[" + nameRepo + "] has been deleted.");
        return !repoExists(nameRepo);
    }

    public boolean repoExists(String name) {
        MainPage mainPage = new MainPage(driver);
        return mainPage.repoExists(name);
    }

    public boolean setProfileName(String name) {
        MainPage mainPage = new MainPage(driver);
        EditProfilePage editProfilePage = mainPage.chooseEditProfile();
        String randomName = name + Utils.getRandomString(10);
        editProfilePage.setUserName(randomName);
        return mainPage.getUserName().equals(randomName);
    }

    public boolean setBio(String bioText) {
        MainPage mainPage = new MainPage(driver);
        EditProfilePage editProfilePage = mainPage.chooseEditProfile();
        String randomBio = bioText + Utils.getRandomString(50);
        editProfilePage.setBio(randomBio);
        String actualbioOnMainPage  = mainPage.getBio();
        logger.info("Expected bio[" + randomBio + "] but actual bio[" + actualbioOnMainPage + "]");
        return actualbioOnMainPage.equals(randomBio);
    }

    public boolean setLocation(String location) {
        MainPage mainPage = new MainPage(driver);
        EditProfilePage editProfilePage = mainPage.chooseEditProfile();
        String randomLoc = location + Utils.getRandomString(5);
        editProfilePage.setLocation(randomLoc);
        logger.info("Expected location: " + randomLoc);
        return mainPage.getLocation().equals(randomLoc);
    }
}
