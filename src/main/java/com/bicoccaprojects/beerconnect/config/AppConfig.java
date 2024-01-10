package com.bicoccaprojects.beerconnect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * AppConfig is a configuration class responsible for encapsulating configuration properties related to the data source.
 * It is annotated with @Component to be automatically scanned and registered as a Spring bean. The @PropertySource
 * annotation specifies the location of the external configuration file "config.properties" from which values will be loaded.
 * The class contains private fields annotated with @Value, used to inject values from the configuration file into the corresponding fields.
 * These values represent the URL, username, and password for the data source.
 */

@Component
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
