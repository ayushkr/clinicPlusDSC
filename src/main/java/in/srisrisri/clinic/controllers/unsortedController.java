package in.srisrisri.clinic.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("")
public class unsortedController {

    Logger logger = Logger.getLogger(unsortedController.class);

    @GetMapping("/clinicPlus/api/report_incomeFromDoctor/pageable")
    public String get12() {
        return "{ }";
    }

    @GetMapping("/clinicPlus/api/dateExpiry")
    public String get1() {
        return "{}";
    }

    @GetMapping("/clinicPlus/api/expiryDate")
    public String get2() {
        return "{}";
    }

   

}
