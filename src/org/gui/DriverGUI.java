package org.gui;

import org.gui.ui.MainFrame;

import javax.swing.*;

public class DriverGUI
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
