package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import model.ComputerPlayer;
import model.RandomAI;
import model.StopperAI;
import model.TicTacToeGame;
import model.testingAI;
import model.IGotNowhereToGoException;

import org.junit.Test;

public class TestStrategies {

    @Test(expected = IGotNowhereToGoException.class)
    public void showWhatHappensWhenAnExcpetionMustBeThrown() {
	TicTacToeGame theGame = new TicTacToeGame();

	ComputerPlayer playerWithRandomStrategy = new ComputerPlayer();
	playerWithRandomStrategy.setStrategy(new RandomAI());

	// Make one move
	Point aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);

	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and another
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and an eight
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);
	// and the ninth
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
	theGame.choose(aRandomSquare.x, aRandomSquare.y);

	/*
	 * TODO - added
	 */
	System.out.println(theGame.toString());
	System.out.println(theGame.movesAvailable());
	System.out.println("=====================\n");
	/*
	 * NOTE - end of added
	 */

	// This should throw an exception since the board is filled
	aRandomSquare = playerWithRandomStrategy.desiredMove(theGame);
    }
    
    @Test
    public void testingAI() {
	ComputerPlayer randomBot = new ComputerPlayer();
	randomBot.setStrategy(new RandomAI());
	ComputerPlayer stopperBotPlaysFirst = new ComputerPlayer();
	ComputerPlayer stopperBotPlaysSecond = new ComputerPlayer();
	stopperBotPlaysFirst.setStrategy(new testingAI(1));
	stopperBotPlaysSecond.setStrategy(new testingAI(2));

	int randomPlayerWins = 0;
	int stopperPlayerWins = 0;
	int ties = 0;

	for (int game = 1; game <= 5; game++) {
	    char winner = playOneGame(randomBot, stopperBotPlaysSecond);
	    if (winner == 'X')
		randomPlayerWins++;
	    if (winner == 'O')
		stopperPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	/*
	 * WORKS :
	 */
	for (int game = 1; game <= 5; game++) {
	    char winner = playOneGame(stopperBotPlaysFirst, randomBot);
	    if (winner == 'X')
		stopperPlayerWins++;
	    if (winner == 'O')
		randomPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	System.out.println("StopperAI strategy shoud have more wins than");
	System.out.println("than RandomAI strategy when they both go first");
	System.out.println("the same number of times. And ties do happen");
	System.out.println("===========================================");
	System.out.println("Stopper wins: " + stopperPlayerWins);
	System.out.println("Random wins: " + randomPlayerWins);
	System.out.println("Ties: " + ties);
    }

    @Test
    public void run1000000TicTacToeGames() {
	ComputerPlayer randomBot = new ComputerPlayer();
	randomBot.setStrategy(new RandomAI());
	ComputerPlayer stopperBotPlaysFirst = new ComputerPlayer();
	ComputerPlayer stopperBotPlaysSecond = new ComputerPlayer();
	stopperBotPlaysFirst.setStrategy(new StopperAI(1));
	stopperBotPlaysSecond.setStrategy(new StopperAI(2));

	int randomPlayerWins = 0;
	int stopperPlayerWins = 0;
	int ties = 0;

	for (int game = 1; game <= 500000; game++) {
	    char winner = playOneGame(randomBot, stopperBotPlaysSecond);
	    if (winner == 'X')
		randomPlayerWins++;
	    if (winner == 'O')
		stopperPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	/*
	 * WORKS :
	 */
	for (int game = 1; game <= 500000; game++) {
	    char winner = playOneGame(stopperBotPlaysFirst, randomBot);
	    if (winner == 'X')
		stopperPlayerWins++;
	    if (winner == 'O')
		randomPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	System.out.println("StopperAI strategy shoud have more wins than");
	System.out.println("than RandomAI strategy when they both go first");
	System.out.println("the same number of times. And ties do happen");
	System.out.println("===========================================");
	System.out.println("Stopper wins: " + stopperPlayerWins);
	System.out.println("Random wins: " + randomPlayerWins);
	System.out.println("Ties: " + ties);
    }

    private char playOneGame(ComputerPlayer first, ComputerPlayer second) {
	TicTacToeGame theGame = new TicTacToeGame();

	while (true) {
	    Point firstsMove = first.desiredMove(theGame);
	    assertTrue(theGame.choose(firstsMove.x, firstsMove.y));

	    if (theGame.tied())
		return 'T';

	    if (theGame.didWin('X')) {
		System.out.println("Printing the game: ");
		System.out.println(theGame);
		return 'X';
	    }

	    if (theGame.didWin('O')) {
		System.out.println("Printing the game: ");
		System.out.println(theGame);
		return 'O';
	    }

	    Point secondsMove = second.desiredMove(theGame);
	    assertTrue(theGame.choose(secondsMove.x, secondsMove.y));

	    if (theGame.tied())
		return 'T';

	    if (theGame.didWin('X')) {
		System.out.println("Printing the game: ");
		System.out.println(theGame);
		return 'X';
	    }

	    if (theGame.didWin('O')) {
		System.out.println("Printing the game: ");
		System.out.println(theGame);
		return 'O';
	    }
	}
    }

    @Test
    public void testStopper() {
	/*
	 * "X" is "minimizer" 2nd player
	 */
	TicTacToeGame theGame = new TicTacToeGame();

	ComputerPlayer playerWithStopperStrategy = new ComputerPlayer();
	playerWithStopperStrategy.setStrategy(new StopperAI(2));
	// X
	theGame.choose(0, 0);
	System.out.println(theGame);

	// O
	theGame.choose(2, 0);
	System.out.println(theGame);

	// X
	theGame.choose(0, 1);
	System.out.println(theGame);
	System.out.println("========================");
	
	Point computerMove = playerWithStopperStrategy.desiredMove(theGame);
	System.out.println(computerMove.getX() + computerMove.getY());

	System.out.println("Before the move has been made");
	System.out.println(theGame);

	theGame.choose(computerMove.x, computerMove.y);

	System.out.println("After the move has been made");
	System.out.println(theGame);

	assertEquals(0, computerMove.x);
	assertEquals(2, computerMove.y);
    }

    @Test
    public void testStopper2() {
	/*
	 * "X" is "maximizer"
	 */
	TicTacToeGame theGame = new TicTacToeGame();

	ComputerPlayer playerWithStopperStrategy = new ComputerPlayer();
	playerWithStopperStrategy.setStrategy(new StopperAI());
	// X
	theGame.choose(0, 0);
	System.out.println(theGame);

	// O
	theGame.choose(2, 0);
	System.out.println(theGame);

	// X
	theGame.choose(0, 2);
	System.out.println(theGame);

	System.out.println("===========================");

	Point computerMove = playerWithStopperStrategy.desiredMove(theGame);
	theGame.choose(computerMove.x, computerMove.y);

	System.out.println(theGame);

	assertEquals(0, computerMove.x);
	assertEquals(1, computerMove.y);
    }

    @Test
    public void testStopper3() {
	/*
	 * "X" is "maximizer"
	 */
	TicTacToeGame theGame = new TicTacToeGame();
	System.out.println(theGame);

	ComputerPlayer playerWithStopperStrategy = new ComputerPlayer();
	playerWithStopperStrategy.setStrategy(new StopperAI());
	// X
	theGame.choose(0, 0);
	System.out.println(theGame);
	// O
	theGame.choose(0, 2);
	System.out.println(theGame);
	// X
	theGame.choose(1, 0);
	System.out.println(theGame);

	Point computerMove = playerWithStopperStrategy.desiredMove(theGame);
	System.out.println(theGame);
	System.out.println("\tDEBUGGING: " + computerMove.x + " " + computerMove.y);
	System.out.println("\tSHOULD BE: 2 0");

	theGame.choose(computerMove.x, computerMove.y);
	System.out.println(theGame);

	assertEquals(2, computerMove.x);
	assertEquals(0, computerMove.y);
    }

    @Test
    public void testStopperTakesWin() {
	/*
	 * "X" is "minimizer"
	 */
	TicTacToeGame theGame = new TicTacToeGame();

	ComputerPlayer playerWithStopperStrategy = new ComputerPlayer();
	playerWithStopperStrategy.setStrategy(new StopperAI(2));
	// X
	theGame.choose(0, 0);
	
	// O
	theGame.choose(2, 0);
	
	// X
	theGame.choose(1, 0);
	
	// O
	theGame.choose(2, 1);
	
	// X
	theGame.choose(0, 2);
	
	System.out.println(theGame);

	Point computerMove = playerWithStopperStrategy.desiredMove(theGame);
	theGame.choose(computerMove.x, computerMove.y);
	
	System.out.println(theGame);
	
	assertEquals(2, computerMove.x);
	assertEquals(2, computerMove.y);
    }
}