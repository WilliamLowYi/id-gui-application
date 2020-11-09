import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Application extends JFrame {
    // Private as it shouldn't be change by outsider
    // Initialise ArrayList
    private ArrayList<Integer> idStorage = new ArrayList<Integer>();

    // Initialise JTextField
    private JTextField userInputID = new JTextField(10);
    private JTextField redColor = new JTextField("0", 5);
    private JTextField greenColor = new JTextField("0" ,5);
    private JTextField blueColor = new JTextField("0", 5);

    // Initialise JTextArea
    private JTextArea displayArea = new JTextArea(50, 35);

    public Application(){
        // Frame Settings
        this.setTitle("CE203 Assignment 1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        // Initialise JPanel
        JPanel userInputPanel = new JPanel();
        JPanel displayOutputPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

    // User Input Panel (Input has moved outside of constructor)
        // Make display label to give instruction to text field input
        JLabel labelID = new JLabel("ID Input: ");
        JLabel labelRGB = new JLabel("       RGB For Font Color ->       ");
        JLabel labelRed = new JLabel("Red: ");
        JLabel labelGreen = new JLabel("Green: ");
        JLabel labelBlue = new JLabel("Blue: ");


        // Add Label & JTextField to userInputPanel
        userInputPanel.add(labelID);
        userInputPanel.add(userInputID);

        userInputPanel.add(labelRGB);

        userInputPanel.add(labelRed);
        userInputPanel.add(redColor);

        userInputPanel.add(labelGreen);
        userInputPanel.add(greenColor);

        userInputPanel.add(labelBlue);
        userInputPanel.add(blueColor);

    // Buttons Panel
        // Create Buttons
        JButton addBut = new JButton("Add ID");
        JButton displayBut = new JButton("Display All IDs");
        JButton removeBut = new JButton("Remove ID");
        JButton sortBut = new JButton("Sort All IDs");
        JButton clearBut = new JButton("Clear IDs");

        // Add Buttons to Panel
        buttonsPanel.add(addBut);
        buttonsPanel.add(displayBut);
        buttonsPanel.add(removeBut);
        buttonsPanel.add(sortBut);
        buttonsPanel.add(clearBut);

        // Add Action Listener to buttons
        addBut.addActionListener(new ButtonHandler(this, "add"));
        displayBut.addActionListener(new ButtonHandler(this, "display"));
        removeBut.addActionListener(new ButtonHandler(this, "remove"));
        sortBut.addActionListener(new ButtonHandler(this, "sort"));
        clearBut.addActionListener(new ButtonHandler(this, "clear"));

    // Display Panel
        // Display Text Area Settings
        displayArea.setEditable(false);
        displayArea.setWrapStyleWord(true);
        displayArea.setFont(new Font(displayArea.getFont().getName(), Font.PLAIN, 16));

        // Add Display Text Area to Display Panel
        displayOutputPanel.add(displayArea);


    // Set those JPanel Layout in application
        this.add(userInputPanel, BorderLayout.NORTH);
        this.add(displayOutputPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

// These method is not sort by order, try to Ctrl + F when you need to find them
    // Method to add ID into the ArrayList
    public void addIDToArray (Integer id){
        idStorage.add(id);
    }

    // Method to display message to the user
    public void setMessageToDisplay(String message){
        displayArea.setText(message);
    }

    // Return whether the Integer argument is in the array
    public boolean isTheIntegerInArray(Integer integer) {
        return idStorage.contains(integer);
    }


    // Get the user input as String
    public String getUserInputString() {
        return userInputID.getText().trim();
    }

    // Method to check whether the Array is empty and display message instantly
    public boolean isTheArrayEmpty(){
        if (idStorage.isEmpty()){
            setMessageToDisplay("The list is empty make sure to add new ID to the list.");
            return true;
        } else {
            return false;
        }
    }

    // (Transform) Make sure that every Integer in the list is in 6 digits form
    public String sixDigitsID(Integer id) {
        String stringID = id.toString();
        // number 100 will need 3 zero and run loop to add to the front of ID
        if (stringID.length() < 6){
            int numberOfZero = 6 - stringID.length();
            for (int i = 0; i < numberOfZero; i++) {
                stringID = "0" + stringID;
            }
        }
        return stringID;
    }

    // Clear(Empty) the Array List
    public void clearTheArray() {
        idStorage.clear();
    }

    // Sort (ASC) the Array List
    public void sortTheArray() {
        Collections.sort(idStorage);
    }

    // Make all Integer ID into a String to be display (Button 2 Display All IDs)
    public String concatIDsToString (){
        Iterator<Integer> it = idStorage.iterator();
        String outputToDisplay = "";
        int index = 0;
        // Turn those Integer to String and combine them together with some space / new line
        while (it.hasNext()) {
            String next = sixDigitsID(it.next());
            outputToDisplay += next + "  ";
            index++;
            if (index % 7 == 0) {
                outputToDisplay += "\n";
            }
        }
        return outputToDisplay;
    }

    // Search for the ID and remove every single copy of them (Button 3 Clear ALL IDs)
    public void removeID(Integer idToRemove) {
        idStorage.removeIf(integer -> integer.equals(idToRemove));
    }

    // Get the red input field as int
    public int getRedInputInt(){
        return Integer.parseInt(redColor.getText().trim());
    }

    // Get the green input field as int
    public int getGreenInputInt() {
        return Integer.parseInt(greenColor.getText().trim());
    }

    // Get the blue input field as int
    public int getBlueInputInt() {
        return Integer.parseInt(blueColor.getText().trim());
    }

    // Set the Red Green Blue text field to the arguments
    public void setRGBInputField(String red, String green, String blue){
        redColor.setText(red);
        greenColor.setText(green);
        blueColor.setText(blue);
    }

    // Change the text color based on the given color
    public void setTextColor(Color color) {
        displayArea.setForeground(color);
    }


    // MAIN METHOD TO RUN THE APPLICATION
    public static void main(String[] args) {
        Application app = new Application();
        app.setVisible(true);
    }

}



// Button Handler that waiting for event to happen
class ButtonHandler implements ActionListener {
    // Variable to store application and action name to perform
    Application app;
    String action;

    // ButtonHandler constructor to get those needed arguments
    ButtonHandler(Application app, String action) {
        this.app = app;
        this.action = action.toLowerCase();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check and change the font color
        changeFontColor();

        // These variables used a lot in most of the buttons
        String userInput = app.getUserInputString();
        // If the array is empty then some of the buttons will be waste to run everything
        boolean isArrayEmpty = app.isTheArrayEmpty();

        // Perform action based on the name of action
        // Using switch to check whether the action match any of these cases below
        switch (this.action) {
            // Action to ADD ID to ArrayList
            case "add" -> {
                // IF userInput is valid then add it to the array and display some success message
                if (isValidID(userInput)) {
                    app.addIDToArray(Integer.parseInt(userInput));
                    app.setMessageToDisplay("ID " + userInput + " has successfully added to the list.");
                }
                // ELSE display not valid message
                else {
                    userInputInvalidMessage();
                    app.setMessageToDisplay("ID \"" + userInput + "\" was not added to the list as it is not a valid " +
                            "ID.");
                }
            }
            // Action to DISPLAY all IDs
            case "display" -> {
                if (isArrayEmpty) {
                    break;
                }
                // Display with concatenate all the IDs
                app.setMessageToDisplay(app.concatIDsToString());
            }
            // Action to REMOVE ID
            case "remove" -> {
                if (isArrayEmpty) { break; }
                // Check the validation of input and whether the ID is in the array in order to be remove
                if (isValidID(userInput)) {
                    Integer integer = Integer.parseInt(userInput);
                    if (app.isTheIntegerInArray(integer)) {
                        app.removeID(integer);
                        app.setMessageToDisplay("ID " + userInput + " has been removed from the list.");
                    } else {
                        userInputInvalidMessage();
                        app.setMessageToDisplay("ID " + userInput + " is not on the list.");
                    }
                } else {
                    userInputInvalidMessage();
                    app.setMessageToDisplay("ID \"" + userInput + "\" was not able to remove from the list as it is " +
                            "not a valid ID.");
                }
            }
            // Action to SORT IDs and display the array too
            case "sort" -> {
                if (isArrayEmpty) { break; }
                app.sortTheArray();
                app.setMessageToDisplay(app.concatIDsToString());
            }

            // Action to CLEAR all IDs from the Array
            case "clear" -> {
                if (isArrayEmpty){ break; }
                app.clearTheArray();
                app.setMessageToDisplay("The list has been clear.");


            }
        }
    }

    // Change font color on display text area
    public void changeFontColor() {
        Color currentColor;
        // Try to make a color using the input of R, G and B
        try {
            currentColor = new Color(app.getRedInputInt(), app.getGreenInputInt(), app.getBlueInputInt());
        }
        // Exception for the new Color above
        // No need of NumberFormatException as it extends from IAE
        catch (IllegalArgumentException iae) {
            // Set the RGB to 0 if exception arise
            currentColor = returnBlack();
            JOptionPane.showMessageDialog(app, "Please insert Integer number between 0 to 255 for Red, Greeen" +
                    ", Blue color to display correct color.", "Wrong RGB Color", JOptionPane.WARNING_MESSAGE);
        }
        app.setTextColor(currentColor);
    }

    // Set all RGB input field to 0 and return a black color
    public Color returnBlack() {
        app.setRGBInputField("0", "0", "0");
        return new Color(app.getRedInputInt(), app.getGreenInputInt(), app.getBlueInputInt());
    }



    // Method to check valid ID input and make sure parseInt will not rise any exception
    public boolean isValidID (String input){
        // Condition to valid ID : length is equal to 6 and it's a positive value
        try {
            int verifiedID = Integer.parseInt(input);
            if (input.length() == 6) {
                return verifiedID > 0;
            } else {
                return false;
            }
        // Exception for String input to parse to Integer
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    // Invalid user input ID wake this method
    public void userInputInvalidMessage() {
        JOptionPane.showMessageDialog(app, "Please insert valid positive 6-digits Integer ID",
                "Alert for invalid ID", JOptionPane.WARNING_MESSAGE);
    }



}
