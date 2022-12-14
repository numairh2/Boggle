import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BoggleSolver {

	Set<String> dictionary;

	/**
	 * Constructor that puts all the file content in HashSet which makes searching
	 * easy and fast.
	 * 
	 * @param dictionaryName name of the dictionary file
	 * @throws FileNotFoundException
	 */
	public BoggleSolver(String dictionaryName) throws FileNotFoundException {
		dictionary = new HashSet<String>();
		Scanner sc = new Scanner(new File(dictionaryName));
		while (sc.hasNextLine()) {
			dictionary.add(sc.nextLine());
		}
	}

	/**
	 * Returns the set of all valid words in the given Boggle board, as an Iterable
	 * object by using the Iterable interface.
	 * 
	 * @param board the BoggleBoard Object from BoggleBoard class which creates the
	 *              actual board
	 * @return Returns the set of all valid words
	 */
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		Set<String> validWords = new HashSet<String>();
		for (int r = 0; r < board.rows(); r++) {
			for (int c = 0; c < board.cols(); c++) {
				boolean[][] trackBoard = new boolean[board.rows()][board.cols()];
				getAllValidWords(validWords, trackBoard, board, "", r, c);
			}
		}
		return validWords;

	}

	/**
	 * Helps find all valid possibility by looking at one point in the 2D array.
	 * 
	 * @param validWords The Set which finds
	 * @param tracker    2D array which keeps track of repeated letters. It helps
	 *                   avoid errors like StackOverload Errors
	 * @param board      the BoggleBoard Object from BoggleBoard class which creates
	 *                   the actual board
	 * @param phrase     keeps track of the word
	 * @param x          the row # in the 2D array
	 * @param y          the col # in the 2D array
	 */
	private void getAllValidWords(Set<String> validWords, boolean[][] tracker, BoggleBoard board, String phrase, int x,
			int y) {
		if (tracker[x][y]) {
			return;
		}

		char let = board.getLetter(x, y);
		String word = phrase;

		if (let == 'Q')
			word += "QU";
		else
			word += let;

		if (word.length() > 2 && dictionary.contains(word))
			validWords.add(word);

		tracker[x][y] = true;
		
				if (isInBounds(board, x + 1, y )) 
					getAllValidWords(validWords, tracker, board, word, x + 1, y );
				if (isInBounds(board, x, y+1 )) 
					getAllValidWords(validWords, tracker, board, word, x, y + 1 );
				if (isInBounds(board, x + 1, y + 1 )) 
					getAllValidWords(validWords, tracker, board, word, x + 1, y + 1 );
				if (isInBounds(board, x - 1, y )) 
					getAllValidWords(validWords, tracker, board, word, x - 1, y );
				if (isInBounds(board, x, y-1 )) 
					getAllValidWords(validWords, tracker, board, word, x, y - 1 );
				if (isInBounds(board, x-1, y-1 )) 
					getAllValidWords(validWords, tracker, board, word, x -1 , y-1 );
				if (isInBounds(board, x+1, y-1 )) 
					getAllValidWords(validWords, tracker, board, word, x +1 , y-1 );
				if (isInBounds(board, x - 1, y + 1 )) 
					getAllValidWords(validWords, tracker, board, word, x -1 , y+1 );

		tracker[x][y] = false;

	}

	private boolean isInBounds(BoggleBoard board, int x, int y) {
		return (x >= 0) && (x < board.rows()) && (y >= 0) && (y < board.cols());
	}

	/**
	 * Returns the score of the given word if it is in the dictionary, zero
	 * otherwise. Points are determined by length
	 * 	   Length * Points
	 * 		*************
	 * 		* 3-4 * 1	*
	 * 		*	5 * 2	*
	 * 		*	6 *	3	*
	 * 		*	7 * 5	*
	 * 		*  +8 * 11	*
	 * 		*************
	 * @param word String from the set of AllValidWords
	 * @return the number of point for each Valid Word
	 */
	public int scoreOf(String word) {

		if (word.length() >= 8)
			return 11;
		else if (word.length() == 7)
			return 5;
		else if (word.length() == 6)
			return 3;
		else if (word.length() == 5)
			return 2;
		else if (word.length() == 3 || word.length() == 4)
			return 1;

		return 0;
	}

	// Runner
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("WORKING");

		final String PATH = "./data/";
		BoggleBoard board = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); // should print 84

		// new BoggleGame(4, 4);
	}

}
