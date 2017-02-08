package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * This TTT strategy tries to prevent the opponent from winning by checking for
 * a space where the opponent is about to win. If none found, it randomly picks
 * a place to win, which an sometimes be a win even if not really trying.
 * 
 * @author mercer
 */
public class StopperAI implements TicTacToeStrategy {

    /*
     * In order to prevent the stack from growing I will be using an instance
     * variable of the game state.
     * 
     * Rather than treat the game state as being immutable and creating a new
     * one on each recursive call to minimax() I will just be altering the state
     * and then undoing the creation of points as to reset the board to the
     * original state before the minimax(). This too is motivated by the desire
     * to minimize stack size an dmaximize speed
     */
    private TicTacToeGame theGame;

    private char maximizer;
    private char minimizer;

    /*
     * Keep track of the best possible move for out maximizer form the call to
     * the the minimax()
     */
    private Point bestMove;

    /*
     * defaults with the user going first because "X" is always the first char
     * to be places
     */
    public StopperAI() {
	this.minimizer = 'X';
	this.maximizer = 'O';
    }

    /*
     * When "X" goes first it is the "maximizer"
     */
    public StopperAI(int startPosition) {
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

    @Override
    public Point desiredMove(TicTacToeGame theGame) {

	// First look to block an opponent win

	// If the AI can not block, look for a win

	// If no block or win is possible, pick a move from those still
	// available

	/*
	 * I am just going to play a perfect game instead
	 */

	/*
	 * if there is no more positions on the board I will throw an error
	 */
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

    /*
     * As it stands the minimax() will traverse all possibilities of the game
     * state tree until it finds a win. The definition of a win is a series of
     * moves that result in a win, there is no differentiation between the
     * number of moves that are required to win. A win in 5 turns is equal to a
     * move in 3 turns. The winning move that is selected is the once that
     * occurs first during the traversal of the game state tree. As mentioned
     * earlier the minimax() will continue searching the tree and it will save
     * the best move during the traversal. There is a possibility that the best
     * move is a tie. There is also a possibility that there is only a
     * possibility to loose. In that case we will have to traverse the whole
     * tree. If we do find a win however, we will return, this causes the before
     * mentioned trait to not have preference over win's, and it will also
     * significantly reduce the required search time.
     * 
     * minimax() does not differentiate between losses either. All losses are
     * equal, but as mentioned earlier we save the best moves, and to begin the
     * best move has a value of Integr.MIN_VALUE, and a loss has a value of -1.
     * Because of this we will replace the best value with the 1st loss we find.
     * We will continue searching for possible wins, but if none are found it
     * will maintain the first loosing move as the best move.
     * 
     * There is a way to alter the minimax() by factoring the depth of the
     * search into the value of the bestMove. For example if a win is found in 1
     * move that would be the best possible move. This would also mean that
     * loosing in 5 steps would be better than loosing in 1 step. We could do
     * this through the use of a fraction being multiplied by the depth as well
     * as the 1 / -1 value for a win or a loss. The problem with doing this is
     * that we will then eliminate all of our ability to prune the tree after
     * finding a win. This will ultimately slow down the program significantly,
     * and is not worth it because the only time it would be of use is if we had
     * a rigged board, because I do not care if the minimax() wins in 5 moves or
     * 3 moves. I would otherwise care if it were to loose in 3 moves or 1
     * moves, but as mentioned minimax() will at worst tie the game if minimax()
     * is allowed to play the game without interference or a rigged board.
     * 
     * - In order to make these changes I would need to substantially alter the
     * existing code
     */
    public int minimax(int depth, char turn) {
	if (this.theGame.didWin(this.maximizer)) {
	    return +1;
	} else if (this.theGame.didWin(this.minimizer)) {
	    return -1;
	}

	/*
	 * There is no need to know how many availablePositions exist if there
	 * is already a win, and we have to check for a win before we recurse,
	 * so we might as well do it first and optimize the code.
	 */
	ArrayList<Point> pointsAvailable = this.theGame.getAvailablePositions();
	// trade off between speed and heap storage
	int numberOfPoints = pointsAvailable.size();
	if (numberOfPoints == 0) {
	    // the board is full, (TIE)
	    return 0;
	}

	/*
	 * Start of major optimization : it went from 25 minutes to 12 seconds.
	 * 
	 * It is known that if you are able to make the first move you should
	 * always go with a corner, so I select the top left corner. By doing
	 * this we eliminate the need to traverse an entire game graph of depth
	 * 9, this is a HUGE savings alone.
	 * 
	 * The next optimization is for the second move. If you are given the
	 * second move there are known patterns to prevent the opponent from
	 * winning if they play a perfect game. minimax() assumes that the
	 * opponent will play a perfect game, so we take the counter measures to
	 * block the opponent. By performing simple checks we eliminate the
	 * traversal of an an entire game tree of height 8, which is again a
	 * HUGE reduction.
	 */
	if (numberOfPoints == 9) {
	    this.bestMove = new Point(0, 0);
	    return 0;
	}

	if (numberOfPoints == 8) {
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
	 * End of major optimization.
	 */

	/*
	 * for the maximizer, we assume that the initial "MAX" value is the
	 * lowest possible that way we can later replace it with any higher
	 * value (-1, 0, 1), same concept but in reverse applies for the "MIN"
	 * value
	 */
	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;

	for (int index = 0; index < numberOfPoints; index++) {
	    /*
	     * Both the minimizer and the maximizer we are going to need to know
	     * the next point to recurse
	     */
	    Point point = pointsAvailable.get(index);

	    if (turn == this.maximizer) {
		// make a move with the point
		this.theGame.minimaxChoose(point.x, point.y);
		/*
		 * get the minimax score, and recurse in a depth first fashion,
		 * alternating the player
		 */
		int currentScore = minimax(depth + 1, this.minimizer);
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
		    if (depth == 0) {
			/*
			 * we have walked back up from the tree and are now back
			 * at the root of the move
			 */
			bestMove = point;
		    }
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

		if (index == numberOfPoints - 1 && max < 0) {
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
	    } else if (turn == this.minimizer) {
		this.theGame.minimaxChoose(point.x, point.y);
		int currentScore = minimax(depth + 1, this.maximizer);
		min = Math.min(currentScore, min);
		if (min == -1) {
		    /*
		     * The current move is a loosing move, we don't even need to
		     * check the remainder of that portion of the game tree, we
		     * go back up to the top of the game tree and consider the
		     * next move that can be made.
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
	return turn == this.maximizer ? max : min;
    }
}