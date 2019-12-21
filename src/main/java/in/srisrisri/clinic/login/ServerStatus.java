package in.srisrisri.clinic.login;


import in.srisrisri.clinic.ayfilman.SearchResponse;
import in.srisrisri.clinic.ayfilman.FileService;
import in.srisrisri.clinic.user.UserRepo;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/clinicPlus")
public class ServerStatus {

    Logger logger=Logger.getLogger(ServerStatus.class);

    FileService fileService;
    UserRepo userRepo;

    @Autowired
    public ServerStatus(FileService fileService,UserRepo userRepo) {

        this.fileService=fileService;
        this.userRepo=userRepo;
    }

    
    @GetMapping("status")
    public String get1(){
     
        String body="i am alive";
        return body;
    }

    
}
