package ua.it1.fbd.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Widget {

    public static void setPadding(JLabel label, int size, boolean left){
        if (left)
            label.setBorder(new EmptyBorder(0,size,0,0));
        else
            label.setBorder(new EmptyBorder(0,size,0,size));
    }

}
