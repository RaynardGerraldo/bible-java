package com.bible.app;

import com.bible.ui.UserInterface;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application {
    public void run() {
        UserInterface ui = new UserInterface();
        ui.createUI();
        //ui.getbutton().addActionListener(new ActionListener() {
            //@Override
            //public void actionPerformed(ActionEvent e){
                //System.out.println("Hello world");
            //}
        //});
    }
}
