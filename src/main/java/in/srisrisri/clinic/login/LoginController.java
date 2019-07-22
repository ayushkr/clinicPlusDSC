package in.srisrisri.clinic.login;


import in.srisrisri.clinic.ayfilman.SearchResponse;
import in.srisrisri.clinic.ayfilman.FileService;
import in.srisrisri.clinic.user.UserService;

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
@RequestMapping("")
public class LoginController {

    Logger logger=Logger.getLogger(LoginController.class);

    FileService fileService;
    UserService userService;

    @Autowired
    public LoginController(FileService fileService,UserService userService) {

        this.fileService=fileService;
        this.userService=userService;
    }
    
//     @GetMapping("/error")
//    public String getError(){
//        System.out.println("test");
//        String body="error";
//        return body;
//    }
    
    
    @GetMapping("")
    public String get1(){
        System.out.println("test");
        String body="<script> window.location.href='clinicPlus/index.html'</script>";
        return body;
    }

    @PostMapping("login")
    public String login(final HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
     return "ok";
    }



    @PostMapping("checkThisCredentials")
    public @ResponseBody
    SearchResponse l(final HttpServletRequest request, final HttpServletResponse response){
        SearchResponse  searchResponse=new SearchResponse();
        //String keyLine=request.getParameter("keyLine");
        //logger.info("keyLine="+keyLine);
        //fileService.doNeedful(keyLine,searchResponse);

        return searchResponse;

    }
}
