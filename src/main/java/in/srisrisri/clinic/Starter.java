package in.srisrisri.clinic;

import in.srisrisri.clinic.FileStorage.FileStorageProperties;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EnableScheduling
public class Starter {

    private static org.apache.log4j.Logger logger = Logger.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("----------- before SpringApplication.run----------------->");
        try {
            spring_start(args);
        } catch (Exception ex) {
            logger.info("-------->"+ex.toString());
        }

    }

    public static void test() {
        FileStorageService fileStorageService = new FileStorageService(new FileStorageProperties());
        fileStorageService.listFilesInThisFolder("patient");

    }

    public static void spring_start(String[] args) {
        SpringApplication.run(Starter.class, args);
        server2_start();
        logger.info("--------spring_start()-----started--------------- -");
//        try {
//            Runtime.getRuntime().exec("clear");
//            Runtime.getRuntime().exec("aplay /usr/share/sounds/alsa/serverStarted.wav");
//        } catch (Exception ex) {
//            logger.info("------------aplay issue- --------------- -" + ex.toString());
//        }
    }

    public static void speak(String words) {
        try {
            Runtime.getRuntime().exec("espeak " + words);
        } catch (Exception ex1) {
            java.util.logging.Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
    }

    @Bean
    File filesystemRoot() {
        try {
            return new File("/common/common/NetBeansProjects/persisted/fileRepo/clinicPlus");
        } catch (Exception ioe) {

        }
        return null;
    }

    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }

    @Bean
    public CommandLineRunner demo() {
        logger.info("started CommandLineRunner");
        return null;

    }
    
    public static String server2_start(){
    try {
        
           logger.info("started server2_start trying");
            Runtime.getRuntime().exec("sh /common/common/dsc/test.sh") ;
             logger.info("started server2_start done");
            return "/clinicPlus/startOldVersion";
        } catch (IOException ex) {
            logger.info("server2_start failed");
            java.util.logging.Logger.getLogger(unsortedController.class.getName()).log(Level.SEVERE, null, ex);
          return ex.toString();
        }
    }

}
