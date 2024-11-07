package nagyhazi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionDialog extends JDialog {
    private JButton phonebookButton;
    private JButton testButton;
    private JButton cancelButton;
    private boolean runPhonebook = false;
    private boolean runTest = false;

    public SelectionDialog(JFrame parent) {
        super(parent, "Select Action", true);
        setLayout(new GridLayout(3, 1));

        JLabel promptLabel = new JLabel("What would you like to do?");
        promptLabel.setHorizontalAlignment(JLabel.CENTER);
        add(promptLabel);

        phonebookButton = new JButton("Run Phonebook");
        testButton = new JButton("Run Tests");
        cancelButton = new JButton("Cancel");

        add(phonebookButton);
        add(testButton);
        add(cancelButton);

        phonebookButton.addActionListener(new PhonebookActionListener());
        testButton.addActionListener(new TestActionListener());
        cancelButton.addActionListener(e -> System.exit(0));

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    public boolean isRunPhonebook() {
        return runPhonebook;
    }

    public boolean isRunTest() {
        return runTest;
    }

    private class PhonebookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runPhonebook = true;
            setVisible(false); // Close the dialog
        }
    }

    private class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runTest = true;
            setVisible(false); // Close the dialog
        }
    }
}
