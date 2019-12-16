/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.controllers;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbstractFrameController {

    public abstract void prepareAndOpenFrame();

    public void registerAction(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }

}
