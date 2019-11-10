package org.gui.ui;

import org.gui.logic.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainFrame extends JFrame
{
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static JPanel[] screenPanels = new JPanel[3];

    public static List<TVShow> showList = new ArrayList<>();

    public MainFrame()
    {
        super("ShowReminder");

        initializeShowList();

        setBounds(SCREEN_SIZE.width/4,SCREEN_SIZE.height/8,SCREEN_SIZE.width/2 + 17, 2*SCREEN_SIZE.height/3);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);

        add(new MenuPanel(), BorderLayout.NORTH);

        createScreenPanelZero();
        add(screenPanels[0], BorderLayout.CENTER);

        createScreenPanelOne();
        add(screenPanels[1], BorderLayout.CENTER);

        createScreenPanelTwo();
        add(screenPanels[2], BorderLayout.CENTER);
    }

    private void initializeShowList()
    {
        showList = Utilities.readFromFile();
    }

    private void createScreenPanelZero()
    {
        screenPanels[0] = new JPanel();
        screenPanels[0].setBounds(0,45,SCREEN_SIZE.width/2, 3*SCREEN_SIZE.height/5);
        screenPanels[0].setLayout(new BorderLayout());
        screenPanels[0].add(new ShowsPanel(), BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        detailsPanel.setBackground(new Color(210, 210, 210));
        EditShowPanel.addComponentsToPane(detailsPanel);

        ShowButtonsPanel showButtonsPanel = new ShowButtonsPanel();
        showButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        rightPanel.add(showButtonsPanel, BorderLayout.NORTH);
        rightPanel.add(detailsPanel, BorderLayout.CENTER);
        screenPanels[0].add(rightPanel, BorderLayout.CENTER);
    }

    private void createScreenPanelOne()
    {
        screenPanels[1] = new ScreenPanelOne();
    }

    private void createScreenPanelTwo()
    {
        screenPanels[2] = new ScreenPanelTwo();
    }

    public static void updateShowTable()
    {
        ShowsPanel.clearTable();
        ShowsPanel.addContents();
    }
}
