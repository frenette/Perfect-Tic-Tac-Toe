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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;

public class TextAreaView extends JPanel implements OurObserver {
    ////

    private JTextField rowTextField;
    private JTextField colTextField;

    private JLabel rowLabel;
    private JLabel colLabel;

    private JButton moveButton;

    // TODO
    private JTextArea displayGame;

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
	this.add(createDisplay());
    }

    private JPanel createDisplay() {
	JPanel returnPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel rowColPanel = new JPanel();
	JPanel rowPanel = new JPanel();
	JPanel colPanel = new JPanel();

	this.rowTextField = new JTextField();
	this.rowTextField.setPreferredSize(new Dimension(20, 30));
	this.colTextField = new JTextField();
	this.colTextField.setPreferredSize(new Dimension(20, 30));

	this.rowLabel = new JLabel("Row");
	this.colLabel = new JLabel("Column");

	this.moveButton = new JButton("Make the move");
	// TODO
	moveButton.addActionListener(new ButtonListener());

	this.displayGame = new JTextArea();
	this.displayGame.setAlignmentX(CENTER_ALIGNMENT);
	this.displayGame.setAlignmentY(CENTER_ALIGNMENT);
	this.displayGame.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
	this.displayGame.setMinimumSize(new Dimension(300, 300));
	this.displayGame.setText(this.theGame.toString());

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

	returnPanel.setLayout(new GridLayout(2, 1));
	returnPanel.add(inputPanel);
	returnPanel.add(this.displayGame);

	return returnPanel;
    }

    // private void u

    @Override
    public void update() {
	// TODO Auto-generated method stub
	this.displayGame.setText(this.theGame.toString());

    }

    private class ButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    // TODO Auto-generated method stub
	    System.out.println("I was clicked");

	    // make player's move
	    System.out.println(rowTextField.getText());
	    System.out.println(colTextField.getText());
	    int row = Integer.parseInt(rowTextField.getText());
	    int col = Integer.parseInt(colTextField.getText());
	    theGame.choose(row, col);

	    Point play = computerPlayer.desiredMove(theGame);
	    theGame.choose(play.x, play.y);
	}

    }

}