/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic;

/**
 *
 * @author akr2
 */

import in.srisrisri.clinic.Constants.ConstMessagesEN;
import javax.swing.*;

public class LookAndFeelUtils {

    public static void setOSLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    ConstMessagesEN.Messages.WINDOWS_STYLE_LOADING_ERROR_MESSAGE + e,
                    ConstMessagesEN.Messages.ALERT_TILE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
