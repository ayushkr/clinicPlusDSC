/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic;

import in.srisrisri.clinic.controllers.Controller0;
import in.srisrisri.clinic.FileStorage.FileStorageProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author akr2
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class Application {
    
    public static void main(String[] args) {
        LookAndFeelUtils.setOSLookAndFeel();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).headless(false).run(args);
        Controller0 controller0 = context.getBean(Controller0.class);
        controller0.prepareAndOpenFrame();
    }
    
}
