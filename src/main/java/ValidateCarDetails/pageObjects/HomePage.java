package ValidateCarDetails.pageObjects;

import ValidateCarDetails.baseTest.BaseTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends BaseTest {

    @FindBy(id= "vrm-input")
    WebElement RegistrationField;

    @FindBy(xpath = "//button[text()='Free Car Check']")
    WebElement freeCarCheckButton;

    @FindBy(xpath = "//dt[text()='Registration']/ancestor::dl/dd")
    WebElement registration;

    @FindBy(xpath = "//dt[text()='Make']/ancestor::dl/dd")
    WebElement make;

    @FindBy(xpath = "//dt[text()='Model']/ancestor::dl/dd")
    WebElement model;

    @FindBy(xpath = "//dt[text()='Colour']/ancestor::dl/dd")
    WebElement colour;

    @FindBy(xpath = "//dt[text()='Year']/ancestor::dl/dd")
    WebElement year;

    @FindBy(css = "a[aria-label='new search']")
    public static WebElement searchLink;

    public HomePage() throws FileNotFoundException {
        PageFactory.initElements(driver, this);
    }

    Vehicle[] vehicles = vehicleInfo();
    Boolean searchComplete = false;

    public void searchVehicleDetails() throws IOException, InterruptedException {
        ArrayList<String> nums = getRegistrationNumbers();
        for (String s : nums) {
            runTest(s, vehicles);
        }

    }

    public boolean validateVehicleDetails() {
        return searchComplete;

    }

    public void runTest(String regNum, Vehicle[] vehicles) throws InterruptedException {

        PageFactory.initElements(driver, this);
        RegistrationField.sendKeys(regNum);
        freeCarCheckButton.click();

        String regFromWeb = registration.getText().trim();
        String makeFromWeb = make.getText().trim();
        String modelFromWeb = model.getText().trim();
        String colourFromWeb = colour.getText().trim();
        String yearFromWeb = year.getText().trim();


        for (Vehicle vehicle : vehicles) {
            if (regFromWeb.contentEquals(vehicle.registrationNumber)) {
                Assert.assertEquals(vehicle.registrationNumber, vehicle.registrationNumber.trim(), regFromWeb);
                Assert.assertEquals(vehicle.make, vehicle.make.trim(), makeFromWeb);
                Assert.assertEquals(vehicle.model, vehicle.model.trim(), modelFromWeb);
                Assert.assertEquals(vehicle.colour, vehicle.colour.trim(), colourFromWeb);
                Assert.assertEquals(vehicle.year, vehicle.year.trim(), yearFromWeb);
                searchComplete = true;
                break;
            }
        }
        searchLink.click();
    }

    public static ArrayList<String> getRegistrationNumbers() throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        File folder = new File("src\\main\\java\\ValidateCarDetails\\inputFiles");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String content = FileUtils.readFileToString(file);
                list = getRegNums(content);
            }
        }
        return list;
    }

    static ArrayList<String> getRegNums(String content) {
        ArrayList<String> regNumbers = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?=.{1,7})(([a-zA-Z]){1,3}(\\d){1,3}(\\s?)([a-zA-Z]){1,3})");

        Matcher matcher = pattern.matcher(content);
        System.out.println(matcher.toString());
        while (matcher.find()) {
            String s = matcher.group(1);
            System.out.println(s);
            regNumbers.add(s);
        }
        return regNumbers;
    }

    static Vehicle[] vehicleInfo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src\\main\\java\\ValidateCarDetails\\outputFiles\\car_output.txt"));
        input.useDelimiter(",|\n");

        Vehicle[] vehicles = new Vehicle[0];
        while (input.hasNext()) {
            String registration = input.next();
            String make = input.next();
            String model = input.next();
            String colour = input.next();
            String year = input.next();

            Vehicle newVehicle = new Vehicle(registration, make, model, colour, year);
            vehicles = addProduct(vehicles, newVehicle);
        }

        return vehicles;
    }

    private static Vehicle[] addProduct(Vehicle[] vehicles, Vehicle vehicleToAdd) {
        Vehicle[] newVehicles = new Vehicle[vehicles.length + 1];
        System.arraycopy(vehicles, 0, newVehicles, 0, vehicles.length);
        newVehicles[newVehicles.length - 1] = vehicleToAdd;

        return newVehicles;
    }

    public static class Vehicle {
        protected String registrationNumber;
        protected String make;
        protected String model;
        protected String colour;
        protected String year;

        public Vehicle(String rgs_num, String mke, String mdl, String clr, String yr) {
            registrationNumber = rgs_num;
            make = mke;
            model = mdl;
            colour = clr;
            year = yr;
        }

        @Override
        public String toString() {
            return String.format("registrationNumber: %s\r\nmake: %s\r\nmodel: %s\r\ncolour: %s\r\nyear: %s\r\n",
                    registrationNumber, make, model, colour, year);
        }

    }
}
