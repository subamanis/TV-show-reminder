package org.gui.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener
{
    public JButton listBtn = new JButton("Show list");
    public JButton addBtn = new JButton("Add show");
    public JButton prefBtn = new JButton("Preferences");


    public MenuPanel()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT,60,10));
        setBackground(new Color(128, 155,180));
        setPreferredSize(new Dimension(0,45));

        listBtn.setFocusPainted(false);
        listBtn.addActionListener(this);
        add(listBtn);

        addBtn.setFocusPainted(false);
        addBtn.addActionListener(this);
        add(addBtn);

        prefBtn.setFocusPainted(false);
        prefBtn.addActionListener(this);
        add(prefBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Show list")){
            MainFrame.screenPanels[2].setVisible(false);
            MainFrame.screenPanels[1].setVisible(false);
            MainFrame.screenPanels[0].setVisible(true);
        }else if(e.getActionCommand().equals("Add show")){
            MainFrame.screenPanels[0].setVisible(false);
            MainFrame.screenPanels[2].setVisible(false);
            MainFrame.screenPanels[1].setVisible(true);
        }else{
            MainFrame.screenPanels[0].setVisible(false);
            MainFrame.screenPanels[1].setVisible(false);
            MainFrame.screenPanels[2].setVisible(true);
        }
    }
}
