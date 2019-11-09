package org.gui.ui;

import org.gui.logic.TVShow;
import org.gui.logic.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ScreenPanelOne extends JPanel implements ActionListener
{
    private final Color SUCCESS_COLOR = new Color(7,117,47);
    private final Color WARNING_COLOR = new Color(207, 113, 31);
    private final Color ERROR_COLOR = new Color(209, 56, 31);

    private JLabel userMessage;
    private JTextField nameField;
    private JTextField seasonNumField;
    private JTextField releaseField;

    private static JLabel dateFormatLabel;

    public ScreenPanelOne()
    {
        this.setBackground(new Color(215, 240,255));
        this.setBounds(0,45, MainFrame.SCREEN_SIZE.width/2, 3*MainFrame.SCREEN_SIZE.height/5);
        this.setVisible(false);

        this.setBorder(BorderFactory.createTitledBorder("Add a show!"));
        ((javax.swing.border.TitledBorder)this.getBorder()).setTitleFont(new Font("",Font.ITALIC,23));
        this.setLayout(new GridLayout(16, 2, 0, 0));

        JLabel nameLabel = new JLabel("Name : ", JLabel.LEFT);
        nameLabel.setFont(new Font("",Font.BOLD,17));
        nameField = new JTextField();
        nameField.setFont(new Font("",Font.PLAIN,19));

        JLabel seasonNumLabel = new JLabel("Season number : ", JLabel.LEFT);
        seasonNumLabel.setFont(new Font("",Font.BOLD,17));
        seasonNumField = new JTextField();
        seasonNumField.setFont(new Font("",Font.PLAIN,19));

        JLabel releaseLabel = new JLabel("Release date/period: ", JLabel.LEFT);
        releaseLabel.setFont(new Font("",Font.BOLD,17));
        releaseField = new JTextField();
        releaseField.setFont(new Font("",Font.PLAIN,19));

        dateFormatLabel = new JLabel("<html>The selected date format is: '<b>"+
                Utilities.getCurrentFormatterPattern()+"</b>'<br>" +
                "(You can change your preference in the preferences tab)</html>", JLabel.LEFT);
        dateFormatLabel.setFont(new Font("",Font.ITALIC,13));
        userMessage = new JLabel();
        userMessage.setFont(new Font("",Font.PLAIN,16));

        JPanel validatePanel = new JPanel();
        validatePanel.setBackground(new Color(215, 240,255));
        validatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton validateDateBtn = new JButton("Validate date");
        validateDateBtn.addActionListener(this);
        validatePanel.add(validateDateBtn);

        JPanel bottomButtonsPanel = new JPanel();
        bottomButtonsPanel.setBackground(new Color(215, 240,255));
        bottomButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        bottomButtonsPanel.add(submitButton);
        bottomButtonsPanel.add(clearButton);

        this.add(new Container());
        this.add(nameLabel);
        this.add(nameField);
        this.add(new Container());
        this.add(seasonNumLabel);
        this.add(seasonNumField);
        this.add(new Container());
        this.add(releaseLabel);
        this.add(releaseField);
        this.add(dateFormatLabel);
        this.add(validatePanel);
        this.add(userMessage);
        this.add(new Container());
        this.add(bottomButtonsPanel);
    }

    private void updateMessageText(String message, Color color)
    {
        userMessage.setForeground(color);
        userMessage.setText(message);
    }

    private void clearInputAreas()
    {
        userMessage.setText("");
        nameField.setText("");
        seasonNumField.setText("");
        releaseField.setText("");
    }

    public static void updateDateFormatText()
    {
        dateFormatLabel.setText("<html>The selected date format is: '<b>"+
                Utilities.getCurrentFormatterPattern()+"</b>'<br>" +
                "(You can change your preference in the preferences tab)</html>");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand()) {
            case "Validate date":
                if (!Utilities.isDate(releaseField.getText())) {
                    updateMessageText("Not a valid date.", WARNING_COLOR);
                } else {
                    updateMessageText("It is a valid date!", SUCCESS_COLOR);
                }
                break;
            case "Submit":
                userMessage.setText("");
                String nameInput = nameField.getText().trim();

                if(nameInput.equals("")){
                    updateMessageText("Please fill in the name of the show", ERROR_COLOR);
                    return;
                }
                TVShow newShow = new TVShow(nameInput);
                if(MainFrame.showList.contains(newShow)){
                    updateMessageText("A show with the name \""+nameInput+"\" already exists", ERROR_COLOR);
                    return;
                }

                int seasonNumInput;
                try {
                    seasonNumInput = Integer.parseInt(seasonNumField.getText().trim());
                } catch (NumberFormatException ex) {
                    updateMessageText("Next season number is not a number.", ERROR_COLOR);
                    return;
                }
                String releaseInput = releaseField.getText().trim();
                if(Utilities.isDate(releaseInput)){
                    newShow.addNextSeason(seasonNumInput, LocalDate.parse(releaseInput, Utilities.getCurrentFormatter()));
                }else{
                    newShow.addNextSeason(seasonNumInput, releaseInput);
                }

                MainFrame.showList.add(newShow);
                Utilities.saveToFile(MainFrame.showList);
                MainFrame.updateShowTable();
                clearInputAreas();
                updateMessageText(
                        "Show added successfully! Name: " + newShow.getShowName() + " , next season: " +
                                newShow.getNextSeason().number + " , release date: " + newShow.getNextSeason().releaseDate +
                                " , release period: " + newShow.getNextSeason().releasePeriod, SUCCESS_COLOR
                );
                break;
            case "Clear":
                clearInputAreas();
                break;
        }
    }
}
