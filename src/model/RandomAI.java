package model;

import java.awt.Point;
import java.util.Random;

/**
 * This strategy selects the first available move at random. It is easy to beat
 * 
 * @throws IGotNowhereToGoException
 *             whenever asked for a move that is impossible to deliver
 * 
 * @author mercer
 */

// There is an intentional compile time error. Implement this interface
public class RandomAI implements TicTacToeStrategy {

    // Randomly find an open spot while ignoring possible wins and stops.
    // This should be easy to beat as a human.
    Random random = new Random();

    @Override
    public Point desiredMove(TicTacToeGame theGame) {
	if (theGame.movesAvailable()) {
	    // there are moves available
	    int row;
	    int column;

	    do {
		row = random.nextInt(3);
		column = random.nextInt(3);
	    } while (!theGame.available(row, column));

	    return new Point(row, column);

	} else {
	    throw new IGotNowhereToGoException("All positions on the board are occupied.");
	}
    }

}