package model;

import java.awt.Point;
import java.util.ArrayList;

public class testingAI implements TicTacToeStrategy {
    private TicTacToeGame theGame = new TicTacToeGame();
    private char maximizer;
    private char minimizer;
    private Point bestMove;

    public testingAI() {
	this.minimizer = 'X';
	this.maximizer = 'O';
    }

    /*
     * When "X" goes first it is the "maximizer"
     */
    public testingAI(int startPosition) {
	if (startPosition == 2) {
	    this.minimizer = 'X';
	    this.maximizer = 'O';
	} else {
	    /*
	     * if invalid value is provided default to the where "X" goes 1st
	     */
	    this.minimizer = 'O';
	    this.maximizer = 'X';
	}

    }

    private int minimax(int depth, char currentChar) {
	if (this.theGame.didWin(this.maximizer)) {
	    return +1;
	} else if (this.theGame.didWin(this.minimizer)) {
	    return -1;
	}

	ArrayList<Point> availablePositions = new ArrayList<>();
	availablePositions = this.theGame.getAvailablePositions();
	int availablePositionsSize = availablePositions.size();

	if (availablePositionsSize == 0) {
	    // the board is full, (TIE)
	    return 0;
	}

	/*
	 * optimization
	 */
	if (availablePositionsSize == 9) {
	    this.bestMove = new Point(0, 0);
	    return 0;
	}

	if (availablePositionsSize == 8) {
	    /*
	     * The opponent placed on the corners
	     */
	    for (int row = 0; row < 2; row++) {
		for (int col = 0; col < 2; col++) {
		    if (!this.theGame.available((row * 2), (col * 2))) {
			// if any of the four corners are taken, take center
			this.bestMove = new Point(1, 1);
			return 0;
		    }
		}
	    }

	    for (int column = 0; column < 3; column++) {
		if (!this.theGame.available((column % 2 + 1), column)) {
		    // pick row 0, column column
		    this.bestMove = new Point(0, column);
		    return 0;
		}
	    }

	    if (!this.theGame.available(0, 1)) {
		this.bestMove = new Point(0, 0);
		return 0;
	    }
	}
	/*
	 * End of Optimization
	 */

	/*
	 * for the maximizer, we assume that the initial "MAX" value is the
	 * lowest possible that way we can later replace it with any higher
	 * value (-1, 0, 1), same concept but in reverse applies for the "MIN"
	 * value
	 */
	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;

	for (int index = 0; index < availablePositionsSize; index++) {
	    /*
	     * Both the minimizer and the maximizer we are going to need to know
	     * the next point to recurse
	     */
	    Point point = availablePositions.get(index);

	    if (currentChar == this.maximizer) {
		// make a move with the point
		this.theGame.minimaxChoose(point.x, point.y);
		/*
		 * get the minimax score, and recurse in a depth first fashion,
		 * alternating the player
		 */
		int currentScore = this.minimax(depth + 1, this.minimizer);
		max = Math.max(currentScore, max);

		/*
		 * display the "minimax" score. This will only display after
		 * traversing all possible moves for the initial tree and then
		 * returning back up the tree. (This will print at max, as many
		 * times as there are initial moves for the starting game tree,
		 * it will not print if it has already found a win)
		 */
		if (depth == 0) {
		    System.out.println("Score for position " + (index + 1) + " = " + currentScore);
		}

		if (currentScore >= 0) {
		    // the move is a win, or at least a tie
		    if (depth == 0)
			/*
			 * we have walked back up from the tree and are now back
			 * at the root of the move
			 */
			bestMove = point;
		}

		if (currentScore == 1) {
		    /*
		     * We found a win. We are not going to continue checking for
		     * any other possible wins. The point that lead to the win
		     * has been stored, now we just have to remove the point for
		     * the board for it to be put back later
		     */
		    this.theGame.minimaxRemove(point.x, point.y);
		    break;
		}

		if (index == availablePositionsSize - 1 && max < 0) {
		    /*
		     * We have checked all possible moves and are in the last
		     * move node, and we haven't found a win or a tie yet. We
		     * are just going to save this node as we exit the minimax
		     * cycle. are going to
		     */
		    if (depth == 0) {
			bestMove = point;
		    }
		}
	    } else if (currentChar == this.minimizer) {
		// make a move with the point
		this.theGame.minimaxChoose(point.x, point.y);
		// get the minimax score
		int currentScore = this.minimax(depth + 1, this.minimizer);

		min = Math.min(currentScore, min);
		if (min == -1) {
		    /*
		     * We found the worst move for the opponent, this will be
		     * the move we will select. There is no need to continue
		     * looking for ways to loose.
		     */
		    this.theGame.minimaxRemove(point.x, point.y);
		    break;
		}
	    }
	    /*
	     * Reset this point that we added, we only reset this point as we
	     * traverse back up the game tree.
	     */
	    this.theGame.minimaxRemove(point.x, point.y);
	}
	return currentChar == this.maximizer ? max : min;
    }

    @Override
    public Point desiredMove(TicTacToeGame theGame) {
	if (theGame.movesAvailable()) {
	    // store the game state in an instance variable
	    this.theGame = theGame;
	    // call the minimax()
	    this.minimax(0, this.maximizer);
	    // return the point found by minimax()
	    // return this.bestMove;
	    return this.bestMove;
	} else {
	    throw new IGotNowhereToGoException("All positions on the board are occupied.");
	}
    }
}
