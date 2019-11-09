package org.gui.ui;

import org.gui.logic.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import static org.gui.ui.MainFrame.SCREEN_SIZE;

public class ScreenPanelTwo extends JPanel implements ActionListener
{
    private ButtonGroup G1;
    private ButtonGroup G2;

    public ScreenPanelTwo()
    {
        this.setBounds(0,45,SCREEN_SIZE.width/2, 3*SCREEN_SIZE.height/5);
        this.setBackground(new Color(159, 172, 151));
        this.setBorder(BorderFactory.createTitledBorder("Preferences"));
        ((javax.swing.border.TitledBorder)this.getBorder()).setTitleFont(new Font("",Font.ITALIC,24));
        this.setLayout(null);
        this.setVisible(false);

        JRadioButton dateFormatRadio1 = new JRadioButton();
        dateFormatRadio1.setText("dd-MM-uuuu");
        dateFormatRadio1.setBounds(160, 75, 100, 50);
        this.add(dateFormatRadio1);

        JRadioButton dateFormatRadio2 = new JRadioButton();
        dateFormatRadio2.setText("MM-dd-uuuu");
        dateFormatRadio2.setBounds(290, 75, 100, 50);
        this.add(dateFormatRadio2);

        JRadioButton dateFormatRadio3 = new JRadioButton();
        dateFormatRadio3.setText("uuuu-MM-dd");
        dateFormatRadio3.setBounds(420, 75, 100, 50);
        this.add(dateFormatRadio3);


        JRadioButton listOrderRadio1 = new JRadioButton();
        listOrderRadio1.setText("Ascending");
        listOrderRadio1.setBounds(160, 270, 90, 50);
        this.add(listOrderRadio1);

        JRadioButton listOrderRadio2 = new JRadioButton();
        listOrderRadio2.setText("Descending");
        listOrderRadio2.setBounds(290, 270, 95, 50);
        this.add(listOrderRadio2);


        JButton saveBtn1 = new JButton("Save Format");
        saveBtn1.addActionListener(this);
        saveBtn1.setBounds(160, 145, 110, 30);
        JButton saveBtn2 = new JButton("Save Order");
        saveBtn2.addActionListener(this);
        saveBtn2.setBounds(160, 340, 110, 30);
        this.add(saveBtn1);
        this.add(saveBtn2);

        JLabel dateLabel = new JLabel("Date format");
        dateLabel.setBounds(54, 75, 150, 50);
        dateLabel.setFont(new Font("",Font.BOLD, 17));
        JLabel listLabel = new JLabel("Table order");
        listLabel.setBounds(54, 270, 150, 50);
        listLabel.setFont(new Font("",Font.BOLD, 17));
        this.add(dateLabel);
        this.add(listLabel);

        G1 = new ButtonGroup();
        G2 = new ButtonGroup();
        G1.add(dateFormatRadio1);
        G1.add(dateFormatRadio2);
        G1.add(dateFormatRadio3);
        G2.add(listOrderRadio1);
        G2.add(listOrderRadio2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Save Format")){
            JRadioButton selectedRadioButton = null;
            for(Enumeration<AbstractButton> buttons = G1.getElements(); buttons.hasMoreElements();){
                AbstractButton button = buttons.nextElement();

                if(button.isSelected()){
                    selectedRadioButton = (JRadioButton) button;
                }
            }
            if(selectedRadioButton == null){
                return;
            }

            String selectedPatternFormat = selectedRadioButton.getText();
            Utilities.setFormatterPattern(selectedPatternFormat);
            ScreenPanelOne.updateDateFormatText();
            Utilities.savePrefs();
            MainFrame.updateShowTable();
            EditShowPanel.textAreas.get(0).setText("");
            EditShowPanel.textAreas.get(1).setText("");
            EditShowPanel.textAreas.get(2).setText("");
        }else if(e.getActionCommand().equals("Save Order")){
            JRadioButton selectedRadioButton = null;
            for(Enumeration<AbstractButton> buttons = G2.getElements(); buttons.hasMoreElements();){
                AbstractButton button = buttons.nextElement();

                if(button.isSelected()){
                    selectedRadioButton = (JRadioButton) button;
                }
            }
            if(selectedRadioButton == null){
                return;
            }

            String order = selectedRadioButton.getText();

            Utilities.setSortingOrder(order);
            Utilities.savePrefs();
            MainFrame.updateShowTable();
        }
    }
}
