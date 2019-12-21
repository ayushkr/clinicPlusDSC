/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.ayushLogger;

import in.srisrisri.clinic.Frame0;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author akr2
 */
public class LoggerFactory {

    public static JTextArea jta;

    static String classname;

    static Map mapUI = new HashMap<String, JTextArea>();
    static Map mapLogger = new HashMap<String, Logger>();
    static Logger logger;

    public static void setJta(JTextArea jta) {
        LoggerFactory.jta = jta;
    }

    public static Logger getLogger(Class aClass) {
        return getLogger(aClass, Frame0.jTextAreaLogger);
    }

    public static Logger getLogger(Class aClass, JTextArea jta_arg) {
        classname = aClass.getName();

        logger = (Logger) mapLogger.get(classname);

        if (logger == null) {

            if (jta_arg == null) {

                jta = Frame0.jTextAreaLogger;
            } else {
                jta = jta_arg;
                 System.out.println("jta_arg="+jta_arg.getName());
            }
           
            logger = new Logger(classname, jta);
            mapLogger.put(classname, logger);
        }

        return logger;
    }

}
