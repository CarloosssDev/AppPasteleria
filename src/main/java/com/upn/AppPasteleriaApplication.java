package com.upn;

import com.upn.frame.Login_Pasteleria;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppPasteleriaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AppPasteleriaApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        java.awt.EventQueue.invokeLater(() -> {
            Login_Pasteleria login = context.getBean(Login_Pasteleria.class);
            login.setVisible(true);
        });
    }
}