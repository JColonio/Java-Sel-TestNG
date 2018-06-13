# Java-Sel-TestNG

Simple project using Java, Selenium Webdriver, TestNG, and aeonbits Owner. The project initializes the webdriver and uses the desired properties file (based on TestNG parameters) to navigate to either Google, Bing, or Yahoo and search for SauceLabs. You can also determine whether to run the test Locally or in SauceLabs in parallel (using Chrome, Firefox, and IE 11). 

## Getting Started

Everyone is welcomed to utilize this project as a starting point. When possible, please reference this repository.

To utilize the project, you must use Eclipse and have the following extensions:
```
1. Maven Integration for Eclipse (Luna and newer)
2. TestNG for Eclipse
```

Update the drivers based on your current setup browser version and desired SeleniumVersion if you would like to run your tests locally. At the time, I am currently using Chrome 67 and Firefox 60

### Maven - Structure

Two folders were added to divide the Page and Test classes. The TestBase utilizes @BeforeMethod (TestNG) to initialize the Driver. Depending on your desired structure you can create a separate class specifically to initalize the Driver, lets call the file "Core", where TestBase and PageBase inherit from "Core".

The Environments class utilizes Owner and TestNG parameters to identify which properties file to use. 

The AnnnotationTransformer is used to override TestNG's setRetryAnalyzer by using the Retry class.

QuickTest only contains the Test Method and initalizes the specified Page class (in this case, its Search)

All the local drivers are located within the "driver" folder. This can be moved to the resources folder.

POM file contains all your necessary dependencies and plugins. Maven surefire was added to integrate this project any CI (Jenkins or VSTS). With Maven Surefire, the CI will run the pom.xml files and instantly know that it must utilize TestSuite.xml

TestSuite.xml is the TestNG file that drives your test. It is set to run each test in parallel (parellel="tests"). The thread-count is set to 3 for all 3 browsers in use (Chrome, FF, IE 11). The listener tag is where AnnotationTransformer is initialized. There are also 2 parameters: environment and setup. 

The environments parameter identifies which properties file to utilize in your test. Currently, the Properties file contains the URL and Search Field ID. This can be changed to switch between environments such as Test, Dev, or Staging.

The setup parameter identifies how the driver is initialized within the TestBase. Either for SauceLab or Locally (using the "driver" folder).

To add more test, add a new class tag within each test tag and use the correct file name (case sensitive). Do not forget to use the @Test annotation within your Test classes.


If you have any questions, please feel free to message me. I am more than happy to guide new users through the setup. 

### Support or Contact

Having trouble understanding how to use the project or setting up locally? [contact support](https://github.com/contact) and I'll be  help.
