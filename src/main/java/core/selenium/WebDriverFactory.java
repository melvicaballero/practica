package core.selenium;

import core.selenium.webdrivers.Chrome;
import core.selenium.webdrivers.Firefox;
import core.utils.Setup;

public class WebDriverFactory {

    public IDriverInit GetDriverInit(){
        if (Setup.getInstance().browser == "Chrome")
            return new Chrome();
        else
            return new Firefox();

    }
}