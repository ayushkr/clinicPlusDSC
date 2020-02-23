
package in.srisrisri.clinic.controllers;

import in.srisrisri.clinic.Frame0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Controller
public class Controller0 extends AbstractFrameController {

    
//    @Autowired
    private  final Frame0 frame0;
    
    
    
    
    public Controller0(Frame0 frame0) {
        this.frame0 = frame0;
    }
    
    @Override
    public void prepareAndOpenFrame() {
        frame0.setVisible(true);
    }

}