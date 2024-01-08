package com.bicoccaprojects.beerconnect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * AppConfig è una classe di configurazione che si occupa incapsulare le proprietà di
 * configurazione relative al data source. È annotata con @Component per essere automaticamente
 * scansita e registrata come un bean Spring. L'annotazione @PropertySource specifica la posizione
 * del file di configurazione esterno "config.properties" da cui verranno caricate le valori.
 * La classe contiene campi privati annotati con @Value, utilizzati per iniettare i valori dal file
 * di configurazione nei campi corrispondenti. Questi valori rappresentano l'URL, il nome utente e
 * la password per il data source.
 *
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
