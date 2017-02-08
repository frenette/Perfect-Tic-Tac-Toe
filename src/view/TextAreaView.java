package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;

public class TextAreaView extends JPanel implements OurObserver {
    ////
    private JPanel displayPanel;

    private JTextField rowTextField;
    private JTextField colTextField;

    private JLabel rowLabel;
    private JLabel colLabel;

    private JButton moveButton;

    // TODO
    private JTextArea displayGame;

    private JLabel bottomText = new JLabel("");

    /////
    private TicTacToeGame theGame;
    private JButton stateButton = new JButton("Click your move");
    private JButton[][] buttons = null;
    private ComputerPlayer computerPlayer;
    private int height, width;

    public TextAreaView(TicTacToeGame TicTacToeGame, int width, int height) {
	this.theGame = TicTacToeGame;
	this.height = height;
	this.width = width;
	computerPlayer = theGame.getComputerPlayer();
	createDisplay();
	this.add(displayPanel);
    }

    private void createDisplay() {
	displayPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel rowColPanel = new JPanel();
	JPanel rowPanel = new JPanel();
	JPanel colPanel = new JPanel();

	this.rowTextField = new JTextField();
	this.rowTextField.setPreferredSize(new Dimension(45, 45));
	this.rowTextField.setFont(new Font("Courier", Font.BOLD, 36));
	this.colTextField = new JTextField();
	this.colTextField.setPreferredSize(new Dimension(45, 45));
	this.colTextField.setFont(new Font("Courier", Font.BOLD, 36));

	this.rowLabel = new JLabel("Row");
	this.colLabel = new JLabel("Column");

	this.moveButton = new JButton("Make the move");
	// TODO
	moveButton.addActionListener(new ButtonListener());

	this.displayGame = new JTextArea();
	this.displayGame.setAlignmentX(CENTER_ALIGNMENT);
	this.displayGame.setAlignmentY(CENTER_ALIGNMENT);
	this.displayGame.setFont(new Font("Courier", Font.BOLD, 36));
	// this.displayGame.setMinimumSize(new Dimension(300, 300));
	this.displayGame.setText(this.theGame.toString());

	this.bottomText.setFont(new Font("Courier", Font.BOLD, 36));

	rowPanel.add(rowTextField);
	rowPanel.add(rowLabel);

	colPanel.add(colTextField);
	colPanel.add(colLabel);

	// set the rowColPanel
	rowColPanel.setLayout(new GridLayout(2, 1));
	rowColPanel.add(rowPanel);
	rowColPanel.add(colPanel);

	// set the inputPanel
	inputPanel.setLayout(new GridLayout(1, 2));
	inputPanel.add(rowColPanel);
	inputPanel.add(this.moveButton);

	displayPanel.setLayout(new GridLayout(3, 1));
	displayPanel.add(inputPanel);
	displayPanel.add(this.displayGame);
	displayPanel.add(this.bottomText);
    }

    @Override
    public void update() {
	// TODO Auto-generated method stub
	this.displayGame.setText(this.theGame.toString());
	// check to see if the board is full, or is there is a win and disable
	// the button
	if (this.theGame.didWin('X')) {
	    this.moveButton.setText("X wins");
	    this.moveButton.setEnabled(false);

	    this.bottomText.setText("X wins");
	} else if (this.theGame.didWin('O')) {
	    this.moveButton.setText("O wins");
	    this.moveButton.setEnabled(false);
	    this.bottomText.setText("O wins");
	} else if (!theGame.movesAvailable()) {
	    this.moveButton.setText("Tie");
	    this.moveButton.setEnabled(false);
	    this.bottomText.setText("Tie");
	} else {
	    this.moveButton.setText("Make the move");
	    this.moveButton.setEnabled(true);
	    this.bottomText.setText("");
	}

    }

    private class ButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    // TODO Auto-generated method stub
	    System.out.println("I was clicked");

	    if (validInput()) {
		int row = Integer.parseInt(rowTextField.getText());
		int col = Integer.parseInt(colTextField.getText());

		if (theGame.available(row, col)) {
		    theGame.choose(row, col);

		    Point play = computerPlayer.desiredMove(theGame);
		    theGame.choose(play.x, play.y);
		} else {
		    // alert the player the move is taken
		    JOptionPane.showMessageDialog(displayPanel, "Invalid move : already occupied");
		}

	    } else {
		// not a valid input for a move, alert the player
		JOptionPane.showMessageDialog(displayPanel, "Inputs must be 0, 1, or 2");
	    }

	}

	private boolean validInput() {
	    String rowText = rowTextField.getText();
	    String colText = colTextField.getText();

	    try {

		int row = Integer.parseInt(rowText);
		int col = Integer.parseInt(colText);

		// check if row is valid
		if (row >= 0 && row <= 2) {
		    // check if col is valid
		    if (col >= 0 && col <= 2) {
			return true;
		    } else {
			return false;
		    }
		} else {
		    return false;
		}

	    } catch (Exception e) {
		return false;
	    }
	}

    }

}