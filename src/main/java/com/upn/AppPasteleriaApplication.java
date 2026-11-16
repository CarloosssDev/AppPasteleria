package com.upn;

import com.upn.frame.Login;
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
            Login login = context.getBean(Login.class);
            login.setVisible(true);
        });
    }
}
