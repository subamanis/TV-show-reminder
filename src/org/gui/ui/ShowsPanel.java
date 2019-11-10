package org.gui.ui;
import org.gui.logic.TVShow;
import org.gui.logic.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ShowsPanel extends JPanel
{
    private static JTable showsTable;
    private static DefaultTableModel model;

    public ShowsPanel()
    {
        setPreferredSize(new Dimension(560,250));
        setLayout(new BorderLayout());

        String[] header = {"Show name", "Next season", "Release"};
        model = new DefaultTableModel(header, 0);

        addContents();

        showsTable = new JTable(model);
        showsTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        showsTable.setFillsViewportHeight(true);
        showsTable.getColumnModel().getColumn(0).setPreferredWidth(MainFrame.SCREEN_SIZE.width/15);
        showsTable.getColumnModel().getColumn(1).setPreferredWidth(MainFrame.SCREEN_SIZE.width/45);
        showsTable.getColumnModel().getColumn(2).setPreferredWidth(MainFrame.SCREEN_SIZE.width/18);
        showsTable.setRowHeight(70);
        showsTable.setFont(new Font("", Font.PLAIN, 15));

        JScrollPane js=new JScrollPane(showsTable);
        showsTable.getTableHeader().setPreferredSize(new Dimension(0,50));
        showsTable.getTableHeader().setFont(new Font("", Font.BOLD,18));
        showsTable.getTableHeader().setResizingAllowed(false);
        js.setVisible(true);
        add(js);
    }

    public static JTable getShowsTable()
    {
        return showsTable;
    }

    public static void clearTable()
    {
        model.setRowCount(0);
    }

    public static void addContents()
    {
        if(Utilities.checkForDuplicates(MainFrame.showList)){
            Vector<String> duplicate = new Vector<>(3);
            duplicate.add("Duplicate entry detected");
            duplicate.add("Duplicate entry detected");
            duplicate.add("Duplicate entry detected");
            model.addRow(duplicate);

            return;
        }

        Vector[] tableData = Utilities.getVectorArrayFromList(MainFrame.showList);
        for(int i=0; i<tableData.length; i++) {
            model.addRow(tableData[i]);
        }
    }

}
