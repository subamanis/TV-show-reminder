package org.gui.ui;

import org.gui.logic.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowButtonsPanel extends JPanel
{
    public static int selectedRow;

    public ShowButtonsPanel()
    {
        setLayout(new GridLayout(3,2,30,0));
        setPreferredSize(new Dimension(100,122));
        setBackground(new Color(210,210,210));
        setSize(new Dimension(100,200));

        add(new Container());
        add(new Container());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((e) -> {
            var table = ShowsPanel.getShowsTable();
            if(table.getSelectedRow() != -1) {
                Utilities.removeByName(
                        ((String) ShowsPanel.getShowsTable().getValueAt(table.getSelectedRow(), 0)).trim(), MainFrame.showList);
                Utilities.saveToFile(MainFrame.showList);
                ((DefaultTableModel) ShowsPanel.getShowsTable().getModel()).removeRow(table.getSelectedRow());
            }
        });
        deleteButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(200,70,80));
        deleteButton.setFont(new Font("",Font.BOLD, 16));
        add(deleteButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener((e) -> {
            JTable table = ShowsPanel.getShowsTable();
            selectedRow = table.getSelectedRow();
            EditShowPanel.textAreas.get(0).setText((String) table.getValueAt(selectedRow, 0));
            EditShowPanel.textAreas.get(1).setText((String) table.getValueAt(selectedRow, 1));
            EditShowPanel.textAreas.get(2).setText(Utilities.getByName(((String) table.getValueAt(selectedRow, 0)).trim(), MainFrame.showList));
        });
        editButton.setFocusPainted(false);
        editButton.setBackground(new Color(250,150,80));
        editButton.setFont(new Font("",Font.BOLD, 16));
        add(editButton);
    }
}
