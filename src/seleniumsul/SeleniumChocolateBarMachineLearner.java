package seleniumsul;

import com.google.common.collect.ImmutableSet;
import de.learnlib.api.SUL;
import basiclearner.BasicLearner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by ramon on 13-12-16.
 */
//@SuppressWarnings("unused")
public class SeleniumChocolateBarMachineLearner {
    public static void main(String[] args) throws IOException {
    	
        /* Ensure that the following parameters are correctly set! */

    	/*
    	// file URI to Chocolat Bar website in this project 
        String sulURI = "file://path/to/chocolatebarwebsite/website.html";

       
        // for Chrome/Chromium : chromedriver
        String chromeDriverLocation = "path/to/webdriver"; //path of the chromedriver-file
        System.setProperty("webdriver.chrome.driver",chromeDriverLocation);
        WebDriver driver = new ChromeDriver();
        */
    	
    	/*
        // for Firefox: geckodriver 
        String geckoDriverLocation = "path/to/webdriver"; //path of the geckodriver-file
        System.setProperty("webdriver.gecko.driver", geckoDriverLocation);
        WebDriver driver = new FirefoxDriver();
        */
        
    	Path project_path=Paths.get(new File("").getAbsolutePath());
    	String sulURI = "file://" + project_path.toString() + "/sul/chocolate_bar_machine/website.html";
        
    	// install chromedriver in the webdriver/ folder in your project 
    	String chromeDriverLocation = project_path.toString() + "/webdriver/chrome-webdriver/chromedriver";
        System.setProperty("webdriver.chrome.driver",chromeDriverLocation);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(project_path.toString() + "/webdriver/google-chrome/chrome");
        WebDriver driver = new ChromeDriver(options);
        

        /* If all is set, we can start learning */
        Collection<String> inputAlphabet = ImmutableSet.of("5ct", "10ct", "mars", "snickers", "twix");
        SUL<String, String> sul = new SeleniumSUL(sulURI, driver);
        BasicLearner.runControlledExperiment(
                sul,
                BasicLearner.LearningMethod.TTT,
                BasicLearner.TestingMethod.UserQueries,
                inputAlphabet);

        driver.close();

    }

}
