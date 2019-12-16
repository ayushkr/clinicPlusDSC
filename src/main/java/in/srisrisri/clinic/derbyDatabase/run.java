/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.derbyDatabase;

/**
 *
 * @author akr2
 */



import java.io.IOException;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.iapi.tools.i18n.LocalizedResource;

import org.apache.derby.tools.SignatureChecker;
import org.apache.derby.tools.dblook;
import org.apache.derby.tools.ij;
import org.apache.derby.tools.sysinfo;

public class run {
  public static void main(String[] paramArrayOfString) throws IOException {
    if (paramArrayOfString.length < 1) {
      printUsage();
    } else if (paramArrayOfString[0].equals("ij")) {
      ij.main(trimArgs(paramArrayOfString));
    } else if (paramArrayOfString[0].equals("sysinfo")) {
      sysinfo.main(trimArgs(paramArrayOfString));
    } else if (paramArrayOfString[0].equals("dblook")) {
      dblook.main(trimArgs(paramArrayOfString));
    } else if (paramArrayOfString[0].equals("server")) {
      NetworkServerControl.main(trimArgs(paramArrayOfString));
    } else if (paramArrayOfString[0].equals("SignatureChecker")) {
      SignatureChecker.main(trimArgs(paramArrayOfString));
    } else if (paramArrayOfString[0].equals("PlanExporter")) {
      
    } else {
      printUsage();
    } 
  }
  
  private static String[] trimArgs(String[] paramArrayOfString) {
    String[] arrayOfString = new String[paramArrayOfString.length - 1];
    System.arraycopy(paramArrayOfString, 1, arrayOfString, 0, paramArrayOfString.length - 1);
    return arrayOfString;
  }
  
  public static void printUsage() {
    LocalizedResource localizedResource = LocalizedResource.getInstance();
//    System.err.println(localizedResource.getTextMessage("RUN_Usage", new Object[0]));
  }
}
