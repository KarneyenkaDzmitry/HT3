package com.epam.ta.pages;

import com.epam.ta.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewRepositoryPage extends AbstractPage {
    private final String BASE_URL = "http://www.github.com/new";
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(id = "repository_name")
    @CacheLookup
    private WebElement inputRepositoryName;

    @FindBy(id = "repository_description")
    @CacheLookup
    private WebElement inputRepositoryDescription;

    @FindBy(xpath = "//body/div[4]/div[1]/div/form/div[3]/button")
    @CacheLookup
    private WebElement buttonCreate;

    @FindBy(linkText = "Import code")
    @CacheLookup
    private WebElement labelEmptyRepoSetupOption;

    @FindBy(xpath = "//a[@data-pjax=\"#js-repo-pjax-container\"]")
    @CacheLookup
    private WebElement linkCurrentRepository;

    public CreateNewRepositoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public boolean isCurrentRepositoryEmpty() {
        return labelEmptyRepoSetupOption.isDisplayed();
    }

    public String createNewRepository(String repositoryName, String repositoryDescription) {
        String repositoryFullName = repositoryName + Utils.getRandomString(6);
        inputRepositoryName.sendKeys(repositoryFullName);
        inputRepositoryDescription.sendKeys(repositoryDescription);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        while (!buttonCreate.isEnabled()) {
            try {
                Thread.sleep(500);
                //wait.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buttonCreate.click();
        return repositoryFullName;
    }

    public String getCurrentRepositoryName() {
        return linkCurrentRepository.getText();
    }

    @Override
    public void openPage() {
        driver.navigate().to(BASE_URL);
    }

}
