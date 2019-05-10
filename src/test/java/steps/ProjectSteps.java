package steps;

import core.selenium.WebDriverManager;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pivotal.model.Project;
import pivotal.ui.*;

import java.util.List;
import java.util.Map;

public class ProjectSteps {

    private Project project;

    public ProjectSteps( Project project){
        this.project = project;
    }

    PageTransporter pageTransporter = PageTransporter.getInstance();

    //pages
    ProjectDashboardPage projectDashboardPage;
    CreateProjectPopup createProjectPopup;
    ProjectsPage projectsPage;
    TopBar topBar;
    TopBarProject topBarProject;
    ProjectsDropDownPanel projectsDropDownPanel;
    IntroductionPage introductionPage;

    @When("^I navigate to Project Dashboard page$")
    public void navigateToProjectDashboardPage() {
        projectDashboardPage = pageTransporter.navigateToProjectDashboardPage();
    }

    @And("^I create a new Project from Project Dashboard page with the following values$")
    public void createProject(DataTable dt) {
        List<Map<String, String>> data = dt.asMaps(String.class, String.class);
        createProjectPopup = projectDashboardPage.pressCreateProjectButton();
        this.project.setName(data.get(0).get("Project Name"));
        this.project.setAccount(data.get(0).get("Account"));
        this.project.setType(data.get(0).get("Project privacy"));
        createProjectPopup.createNewProject(data.get(0).get("Project Name"), data.get(0).get("Account"), data.get(0).get("Project privacy"));
    }

    @And("^I create a new Project from Projects page with the following values$")
    public void createProjects(DataTable dt) {
        List<Map<String, String>> data = dt.asMaps(String.class, String.class);
        createProjectPopup = projectsPage.pressCreateProjectLink();
        this.project.setName(data.get(0).get("Project Name"));
        this.project.setAccount(data.get(0).get("Account"));
        this.project.setType(data.get(0).get("Project privacy"));
        createProjectPopup.createNewProject(data.get(0).get("Project Name"), data.get(0).get("Account"), data.get(0).get("Project privacy"));
    }

    @And("^I create a new Project from Projects Dropdown page with the following values$")
    public void createProjectsDropdown(DataTable dt) {
        List<Map<String, String>> data = dt.asMaps(String.class, String.class);
        topBar = new TopBar();
        projectsDropDownPanel = topBar.PressProjectDropdownbutton();
        createProjectPopup = projectsDropDownPanel.pressCreateProjectLink();

        this.project.setName(data.get(0).get("Project Name"));
        this.project.setAccount(data.get(0).get("Account"));
        this.project.setType(data.get(0).get("Project privacy"));

        createProjectPopup.createNewProject(data.get(0).get("Project Name"), data.get(0).get("Account"), data.get(0).get("Project privacy"));
    }

    @Then("^the Project page should display the project name$")
    public void projectPageShouldBeDisplayed() {
        //Assert.assertTrue(WebDriverManager.getInstance().getWebDriver().getCurrentUrl().contains("/n/projects/"));
        topBarProject = new TopBarProject();
        Assert.assertEquals(topBarProject.GetCurrentProjectName(), project.getName());
    }

//    public void verifyProjectPageIsDisplayed(){
//        Assert.assertTrue(WebDriverManager.getInstance().getWebDriver().getCurrentUrl().contains("https://www.pivotaltracker.com/n/projects/"));
//    }


    @Then("^the Project name should be displayed in Project Dashboard page$")
    public void projectIsDisplayed() {
        Assert.assertTrue(projectDashboardPage.projectNameIsListed(project.getName()));
    }

    @When("^I navigate to Projects page$")
    public void navigateToProjectsPage() {
        projectsPage = pageTransporter.navigateToProjectsPage();
    }

    @Then("^the Project name should be displayed in Projects page$")
    public void verifyProjectIsDisplayed() {
        Assert.assertTrue(projectsPage.projectNameIsListed(project.getName()));
    }

    @When("^I display the Projects menu from the top bar$")
    public void displayToProjectsMenuFromTopBar() {
        topBar = new TopBar();
        projectsDropDownPanel = topBar.PressProjectDropdownbutton();
    }

    @Then("^the Project name should be displayed in the Projects menu$")
    public void verifyProjectIsDisplayedInProjectsPanel() {
        Assert.assertTrue(projectsDropDownPanel.projectNameIsListed(project.getName()));
    }

    @When("^I navigate to Introduction page$")
    public void navigateToIntroductionPage() {
        introductionPage = pageTransporter.navigateToIntroductionPage();
    }

    @And("^I create a new Project from Introduction page with the name \"([^\"]*)\"$")
    public void createFirstProject(String projectName) {
        introductionPage.firstProject(projectName);
        this.project.setName(projectName);
    }

    @When("^I navigate to Projects Drop Down Panel$")
    public void navigateToProjectsDropDownPanel() {
        projectsDropDownPanel = pageTransporter.navigateToProjectsDropDownPanel();
    }

    @And("^close the application$")
    public void closeTheApplication(){
        WebDriverManager.getInstance().getWebDriver().close();
    }
}
