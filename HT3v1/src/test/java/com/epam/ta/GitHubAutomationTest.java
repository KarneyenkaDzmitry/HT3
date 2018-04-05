package com.epam.ta;

import com.epam.ta.steps.Steps;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

public class GitHubAutomationTest {

    private Steps steps;
    private final String message = "See more in log - file.";
    private final String USERNAME = "UserForTests";
    private final String PASSWORD = "GitTest_1";
    private final String FULL_NAME = "LastName FirstName Patronymic - random text: ";
    private final String BIO = "BIO - this is a special information about the user - random text: ";
    private final String LOCATION = "Location - random text: ";

    @BeforeClass(description = "Init browser")
    public void beforeClass() {
        steps = new Steps();
        steps.initBrowser();
    }

    @BeforeMethod(description = "Log in to GitHub")
    public void setUp() throws Exception {
        steps.loginGithub(USERNAME, PASSWORD);

    }

    @AfterMethod(description = "Log out")
    public void tearDown() throws Exception {
        steps.logoutGitHub();
    }

    @Test(description = "Create new Repository")
    public void oneCanCreateProject() {
        assertTrue(steps.createNewRepository("testRepo", "auto-generated test repo"), message);
        assertTrue(steps.currentRepositoryIsEmpty(), message);
    }

    @Test(description = "Do we Login to Github")
    public void oneCanLoginGithub() {
        assertTrue(steps.isLoggedIn(USERNAME), message);
    }

    @Test(description = "Do [logout] works correctly")
    public void testLogout() throws Exception {
        assertTrue(steps.logoutGitHub(), message);
        setUp();
    }

    @Test(description = "Try delete the first three repository(the last created) from list of repositories.")
    public void testDeleteFirstThreeRepositories() throws Exception {
        assertTrue(steps.deleteRepositories(3), message);
    }

    @Test(description = "Try delete the first repository(the last created) from list of repositories.")
    public void testDeleteFirstRepository() throws Exception {
        assertTrue(steps.deleteFirstRepository(), message);
    }

    @Test(description = "Edit profile's name and check the name on the main page.")
    public void testAddProfileName() throws Exception {
        assertTrue(steps.setProfileName(FULL_NAME), message);
    }

    @Test(description = "Edit profile's bio and check the bio on the main page.")
    public void testAddBio() throws Exception {
        assertTrue(steps.setBio(BIO), message);
    }

    @Test(description = "Edit profile's location and check the location on the main page.")
    public void testLocation() throws Exception {
        assertTrue(steps.setLocation(LOCATION), message);
    }

    @AfterClass(description = "Stop Browser")
    public void stopBrowser() {
        steps.closeDriver();
    }
}
