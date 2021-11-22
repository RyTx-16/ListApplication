import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CE203_2019_Ass1 extends JFrame {
    // Information for various variables.
    GridLayout layout = new GridLayout(2,3);
    ArrayList<String> list = new ArrayList<String>();
    JLabel strings;
    JTextField word, r, g, b;

    public CE203_2019_Ass1(){
        // Adding panels.
        JPanel upperPanel = new JPanel();
        JPanel centrePanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        // Add to frame panels and labels.
        add(upperPanel, BorderLayout.NORTH);
        upperPanel.setLayout(layout);
        add(centrePanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
        strings = new JLabel("Please add a word into the text field below.");
        strings.setForeground(new Color(0,0,0));

        // Defining text boxes.
        word = new JTextField("", 20);
        r = new JTextField("R", 3);
        g = new JTextField("G", 3);
        b = new JTextField("B", 3);

        // Create buttons and assigning button actions to them.
        JButton buttonAdd = new JButton("Add to List");
        JButton buttonDisplay = new JButton("Display (ending in)");
        JButton buttonRemove = new JButton("Remove from List");
        JButton buttonClear = new JButton("Clear List");
        JButton buttonEnter = new JButton("Enter Colour Value");
        JButton buttonReset = new JButton("Reset Colour Value");
        buttonAdd.addActionListener(new ButtonHandlerAdd(this));
        buttonDisplay.addActionListener(new ButtonHandlerDisplay(this));
        buttonRemove.addActionListener(new ButtonHandlerRemove(this));
        buttonClear.addActionListener(new ButtonHandlerClear(this));
        buttonEnter.addActionListener(new ButtonHandlerEnter(this));
        buttonReset.addActionListener(new ButtonHandlerReset(this));

        // Adding to the three panels the defined buttons, labels and text boxes.
        upperPanel.add(buttonAdd);
        upperPanel.add(buttonDisplay);
        upperPanel.add(buttonRemove);
        upperPanel.add(buttonClear);
        upperPanel.add(buttonEnter);
        upperPanel.add(buttonReset);

        centrePanel.add(strings);

        lowerPanel.add(word);
        lowerPanel.add(r);
        lowerPanel.add(g);
        lowerPanel.add(b);
    }

    /*
        Method to get the current line of text in the program to be able to then be able to use it in all of
        our functions.
     */
    public JLabel getLabel() {
        return strings;
    }

    /*
        Class that takes the input from the JTextField and adds it into an ArrayList only if it begins
        with an alphanumeric value, by checking the first character of the word. Also makes sure the
        textfield contains text.
     */
    class ButtonHandlerAdd implements ActionListener{
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerAdd(CE203_2019_Ass1 ass1){
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String words = String.valueOf(ass1.word.getText());
            boolean checkSpecial = false;
            try{
                if (Character.isLetter(words.charAt(0))){
                    if (words.matches(".*[`¬!£$%^&*()_=+{}#~'@;:/?.>,<|].*")){
                        checkSpecial = true;
                        strings.setText("String '" + words +"' is not a valid string.");
                        word.setText("");
                    }
                    if (!checkSpecial){
                        list.add(words);
                        strings.setText("'"+ words + "' added to List.");
                        word.setText("");
                    }
                }
                else {
                    strings.setText("String '" + words +"' is not a valid string.");
                }
            }
            catch (StringIndexOutOfBoundsException er){
                strings.setText("No input detected.");
            }
        }
    }

    /*
        Class that outputs the contents of the list that ends with a specified input from the user,
        checks that both case sensitive occurrences of the word is allowed and then displayed; regardless of
        if the user inputs an 'n' all words ending in 'n' or 'N' are displayed. Also outputs if no matches are found.
     */
    class ButtonHandlerDisplay implements ActionListener{
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerDisplay(CE203_2019_Ass1 ass1){
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> displayList = new ArrayList<>();
            boolean check = false;
            String words = String.valueOf(ass1.word.getText());
            String words1 = words.toUpperCase();
            String words2 = words.toLowerCase();
            if (words.length()>1){
                strings.setText("Invalid input, only one character allowed.");
                word.setText("");
            }
            else{
                for (String i : list){
                    if (i.endsWith(words1)){
                        displayList.add(i);
                        check = true;
                    }
                    else if (i.endsWith(words2)){
                        displayList.add(i);
                        check = true;
                    }
                }
                if (!check){
                    strings.setText("No occurrences of strings ending in '"+ words1 + "' or '" + words2 + "' found." );
                }
                if (check){
                    String display = Arrays.toString(displayList.toArray()).replace("[","").replace("]","");
                    strings.setText("Occurrences: " + display);
                }
                if (words.equals("")){
                    strings.setText("No input detected.");
                }
            }
            word.setText("");
        }
    }

    /*
        Class that removes the occurrences of all matching strings from the ArrayList that the user has specified.
        Also takes into account case-sensitive versions of the same word, so that all versions of the word are removed
        from the list and not just the single version.
     */
    class ButtonHandlerRemove implements ActionListener {
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerRemove(CE203_2019_Ass1 ass1) {
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> removeList = new ArrayList<>();
            String words = String.valueOf(ass1.word.getText());
            if (words.equals("")){
                strings.setText("No input detected.");
            }
            else {
                for (String i : list){
                    if (i.equalsIgnoreCase(words)){
                        removeList.add(i);
                    }
                }
                list.removeAll(removeList);
                String words2 = words.toLowerCase();
                strings.setText("All instances of '" + words2 + "' removed.");
                word.setText("");
            }
        }
    }

    /*
        Class that clears the list and outputs an appropriate message to the centre message. Checks whether
        the list is already empty also.
     */
    class ButtonHandlerClear implements ActionListener {
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerClear(CE203_2019_Ass1 ass1) {
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.isEmpty()){
                strings.setText("List is already empty.");
            }
            else {
                list.clear();
                strings.setText("List cleared.");
                word.setText("");
            }
        }
    }

    /*
        Class that sets the RGB values to equal the input provided by the user in each of the
        three text fields. Also checks if the values given are in the range of 0-255, and if not outputs
        an error message in the form of a dialog box and reverts the colour to black.
     */
    class ButtonHandlerEnter implements ActionListener {
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerEnter(CE203_2019_Ass1 ass1) {
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int r2 = 0;
            int g2 = 0;
            int b2 = 0;
            try {
                int r1 = Integer.parseInt(ass1.r.getText());
                int g1 = Integer.parseInt(ass1.g.getText());
                int b1 = Integer.parseInt(ass1.b.getText());
                if ((r1 < 0) || (g1 < 0) || (b1 < 0)) {
                    ass1.getLabel().setForeground(new Color(r2, g2, b2));
                    r.setText("0");
                    g.setText("0");
                    b.setText("0");
                    JOptionPane.showMessageDialog(null, "Error: Input under 0.");
                }
                if ((r1 > 255) || (g1 > 255) || (b1 > 255)) {
                    r.setText("0");
                    g.setText("0");
                    b.setText("0");
                    ass1.getLabel().setForeground(new Color(r2, g2, b2));
                    JOptionPane.showMessageDialog(null, "Error: Input over 255.");
                }
                else {
                    ass1.getLabel().setForeground(new Color(r1, g1, b1));
                }
            }
            catch (NumberFormatException ex1){
                r.setText("0");
                g.setText("0");
                b.setText("0");
                ass1.getLabel().setForeground(new Color(r2, g2, b2));
                JOptionPane.showMessageDialog(null, "Error: Input is not an integer.");
            }
            catch (IllegalArgumentException ex2){
            }
        }
    }

    /*
        Class that sets the RGB values to equal 0, resetting the colour of the value from the
        input from the previous class.
     */
    class ButtonHandlerReset implements ActionListener {
        private CE203_2019_Ass1 ass1;
        public ButtonHandlerReset(CE203_2019_Ass1 ass1){
            this.ass1 = ass1;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int r1 = 0;
            int g1 = 0;
            int b1 = 0;
            r.setText("0");
            g.setText("0");
            b.setText("0");
            ass1.getLabel().setForeground(new Color(r1, g1, b1));
        }
    }

    /*
        Main method to display JFrame and other values of the frame. Including making it so it appears
        in the middle of the screen.
     */
    public static void main(String[] args) {
        JFrame frame = new CE203_2019_Ass1();
        frame.setSize(480, 150);
        frame.setDefaultCloseOperation(CE203_2019_Ass1.EXIT_ON_CLOSE);
        frame.setTitle("CE203 Assignment 1: 1605364");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
