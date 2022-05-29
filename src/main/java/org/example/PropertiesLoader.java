package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private Properties configuration;

    public int getNumberOfConfigurations() {
        return numberOfConfigurations;
    }

    public void setNumberOfConfigurations(int numberOfConfigurations) {
        this.numberOfConfigurations = numberOfConfigurations;
    }

    private int numberOfConfigurations;
    public PropertiesLoader(String resourceFileName) throws IOException {
        this.configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        this.numberOfConfigurations = configuration.size() / 3;
    }


    public boolean configurationValidation(){
        return this.configuration.size() % 2 == 0;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }
}
