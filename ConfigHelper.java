package PageObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class ConfigHelper {
    FileInputStream fis;
    InputStream in;
    Properties properties;
    String prop;
    String url;
    String result;
    String newStr;

    public String getConfig(String propName)  {
        try{
            properties = new Properties();
            fis = new FileInputStream("src/resources/config.properties");
            properties.load(fis);
            byte bytes[] = properties.getProperty(propName).getBytes("ISO-8859-1");
//            prop = new String(properties.getProperty(propName).getBytes("ISO-8859-1"));
//            prop = properties.getProperty(propName);
            prop = new String(bytes, "UTF-8");

        }catch (IOException e){
            System.out.println("нет файла");
        }

        return prop;
    }

    public String getCustomerUrl(String customer){
        url = getConfig(customer + ".url");
        return url;
    }

    public void writeVersionInReport(String browserVersion, String browser){
        try{
            File file  = new File("allure-results/","environment.properties");
            FileWriter writer = new FileWriter(file, false);
            writer.write("Browser.Version=" + browserVersion);
            writer.write('\n');
            writer.write("Browser=" + browser);
            writer.write('\n');
            writer.write("Stand=" + "Dev");
            writer.flush();
        }catch (IOException e){
            System.out.println("нет файла");
        }
    }
}
