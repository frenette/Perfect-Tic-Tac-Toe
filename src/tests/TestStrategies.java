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

	for (int game = 1; game <= 500; game++) {
	    char winner = playOneGame(randomBot, stopperBotPlaysSecond);
	    if (winner == 'X')
		randomPlayerWins++;
	    if (winner == 'O')
		stopperPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	for (int game = 1; game <= 500; game++) {
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
    public void run1000TicTacToeGames() {
	ComputerPlayer randomBot = new ComputerPlayer();
	randomBot.setStrategy(new RandomAI());
	ComputerPlayer stopperBotPlaysFirst = new ComputerPlayer();
	ComputerPlayer stopperBotPlaysSecond = new ComputerPlayer();
	stopperBotPlaysFirst.setStrategy(new StopperAI(1));
	stopperBotPlaysSecond.setStrategy(new StopperAI(2));

	int randomPlayerWins = 0;
	int stopperPlayerWins = 0;
	int ties = 0;

	for (int game = 1; game <= 500; game++) {
	    char winner = playOneGame(randomBot, stopperBotPlaysSecond);
	    if (winner == 'X')
		randomPlayerWins++;
	    if (winner == 'O')
		stopperPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	for (int game = 1; game <= 500; game++) {
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
	
	assertEquals(randomPlayerWins, 0);
    }
    
    @Test
    public void stopperVsStopper() {
	ComputerPlayer stopperBotPlaysFirst = new ComputerPlayer();
	ComputerPlayer stopperBotPlaysSecond = new ComputerPlayer();
	stopperBotPlaysFirst.setStrategy(new StopperAI(1));
	stopperBotPlaysSecond.setStrategy(new StopperAI(2));

	int randomPlayerWins = 0;
	int stopperPlayerWins = 0;
	int ties = 0;

	for (int game = 1; game <= 500; game++) {
	    char winner = playOneGame(stopperBotPlaysFirst, stopperBotPlaysSecond);
	    if (winner == 'X')
		randomPlayerWins++;
	    if (winner == 'O')
		stopperPlayerWins++;
	    if (winner == 'T')
		ties++;
	}

	for (int game = 1; game <= 500; game++) {
	    char winner = playOneGame(stopperBotPlaysFirst, stopperBotPlaysSecond);
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
	
	assertEquals(ties, 1000);
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
}