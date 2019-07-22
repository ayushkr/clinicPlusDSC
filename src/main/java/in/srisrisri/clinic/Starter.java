package in.srisrisri.clinic;

import in.srisrisri.clinic.FileStorage.FileStorageProperties;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import java.awt.Desktop;
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

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class Starter {

    private static org.apache.log4j.Logger logger = Logger.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("----------- before SpringApplication.run----------------->");

//            DerbyServerUtil derbyServerUtil = new DerbyServerUtil(1527);
//           derbyServerUtil.start();
        SpringApplication.run(Starter.class, args);
       
        logger.info("-------------started--------------- -");
        try {
            Runtime.getRuntime().exec("clear");
            Runtime.getRuntime().exec("aplay /usr/share/sounds/alsa/serverStarted.wav");
        } catch (IOException ex) {
             logger.info("------------aplay issue- --------------- -"+ex.toString());
        }

    }

    public static void test(){
        FileStorageService fileStorageService=new FileStorageService(new FileStorageProperties());
        fileStorageService.listFilesInThisFolder("patient");
    
    }
    
    
    
    
      @Bean
    File filesystemRoot() {
        try {
            return new File("/home/akr2/NetBeansProjects/persisted/fileRepo/clinicPlus");
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

}
