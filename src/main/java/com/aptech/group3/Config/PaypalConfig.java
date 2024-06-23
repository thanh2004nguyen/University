package com.aptech.group3.Config;



import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    
    private String clientId="AU06lIPNQMm0onL8NczeNhNE3OhQgm4R31VSqnzbRIQIWYeZSAC1dwvAqTEfNzU_c68it7yVvkzSM99H";
    
    private String clientSecret="ECdPA1hUYLd2ZqOGCgEDkqjskLNad2qM3499U1O0jtS0tWfUL92bghI03Zkd6ZMU-rOv9r9d1t4YQ9UN";
   
    private String mode="sandbox";

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }
}
