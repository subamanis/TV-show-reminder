package org.gui.ui;

import org.gui.logic.TVShow;
import org.gui.logic.Utilities;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditShowPanel extends JPanel
{
    public static List<JTextField> textAreas = new ArrayList<>();

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        pane.add(Box.createRigidArea(new Dimension(0, 25)));
        addLabelAndTextField(pane, "Name");
        pane.add(Box.createRigidArea(new Dimension(0, 22)));
        addLabelAndTextField(pane, "Season number");
        pane.add(Box.createRigidArea(new Dimension(0, 22)));
        addLabelAndTextField(pane, "Release");
        pane.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(210, 210, 210));
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        btnPanel.setPreferredSize(new Dimension(140,160));

        JButton updateButton = new JButton("Update!");
        updateButton.addActionListener( (e) -> {
            var table = ShowsPanel.getShowsTable();
            var model = table.getModel();

            var name = textAreas.get(0).getText().trim();
            var season = textAreas.get(1).getText().trim();
            var release = textAreas.get(2).getText().trim();

            if(name.isEmpty() || season.isEmpty() || release.isEmpty()){
                return;
            }

            if(Utilities.checkIfShowNameExistsExcludingIndex(ShowButtonsPanel.selectedRow, name, MainFrame.showList)){
                textAreas.get(0).setText("Name already exists");
                return;
            }

            int seasonNum;
            try{
                seasonNum = Integer.parseInt(season);
            }catch (NumberFormatException ex){
                textAreas.get(1).setText("Not a number");
                return;
            }

            LocalDate releaseDate = Utilities.getDateFromString(release);
            TVShow newShow = new TVShow(name);
            if(releaseDate != null){
                newShow.addNextSeason(seasonNum, releaseDate);
                release = Utilities.getDateAndRemainingDays(releaseDate);
            }else{
                newShow.addNextSeason(seasonNum, release);
            }

            model.setValueAt("     "+name, ShowButtonsPanel.selectedRow, 0);
            model.setValueAt("     "+seasonNum, ShowButtonsPanel.selectedRow, 1);
            model.setValueAt("     "+release, ShowButtonsPanel.selectedRow, 2);

            MainFrame.showList.set(ShowButtonsPanel.selectedRow, newShow);
            Utilities.saveToFile(MainFrame.showList);
        } );
        updateButton.setPreferredSize(new Dimension(90,45));
        updateButton.setFocusPainted(false);
        btnPanel.add(updateButton);

        pane.add(btnPanel);
        pane.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private static void addLabelAndTextField(Container pane, String s)
    {
        JLabel label = new JLabel(s);
        label.setFont(new Font("", Font.PLAIN, 18));
        label.setPreferredSize(new Dimension(0,30));
        pane.add(label);

        JTextField nameArea = new JTextField();
        nameArea.setFont(new Font("", Font.PLAIN, 16));
        nameArea.setHorizontalAlignment(SwingConstants.CENTER);
        nameArea.setBorder(BorderFactory.createLineBorder(Color.black));
        textAreas.add(nameArea);
        pane.add(nameArea);
    }

}
