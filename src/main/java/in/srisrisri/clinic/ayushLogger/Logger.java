/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.ayushLogger;

import javax.swing.JTextArea;

/**
 *
 * @author akr2
 */
public class Logger {

    String classname;
    JTextArea jta;

    public Logger(String classname, JTextArea jta) {
        this.classname = classname;
        this.jta = jta;
    }

    public void info(String message, Object[] objects) {
        any(message, objects);
    }

    public void info(String message, Object o) {
        any(message, o);
    }

    public void warn(String message, Object[] objects) {
        any(message, objects);
    }

    public void warn(String message, Object s) {
        any(message, s);
    }

    public void any(String message, Object s) {
        Object[] or = new Object[]{s};
        any(message, or);
    }

    public void any(String message, Object[] objects) {
        String objectStringFull = "";
        for (Object object : objects) {
            objectStringFull += object + "\t";
        }
        String finalMessage;
        finalMessage = classname + "\t" + message + "--> \t" + objectStringFull + "\n";
        if (jta == null) {
            System.out.println(" jta is null");
        } else {
            jta.append(finalMessage);
        }
//        jTextPane.setT
//System.out.println(""+finalMessage);
    }
}
