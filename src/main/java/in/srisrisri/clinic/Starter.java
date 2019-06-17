package in.srisrisri.clinic;

import in.srisrisri.clinic.FileStorage.FileStorageProperties;
import in.srisrisri.clinic.FileStorage.FileStorageService;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
       
        logger.info("-------------started- ----------------");

    }

    public static void test(){
        FileStorageService fileStorageService=new FileStorageService(new FileStorageProperties());
        fileStorageService.listFilesInThisFolder("patient");
    
    }
    
    
    
    
    
    
    
    
    
    @Bean
    public CommandLineRunner demo() {
        logger.info("started CommandLineRunner");
        return null;

    }

}
