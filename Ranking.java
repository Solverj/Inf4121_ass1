import java.util.Scanner;
/**
 * The Ranking class has responsibility of containing the top 5 players of this mine sweeper session - and for displaying result to the player.
 * @author unknown
 * @version 1.1
 * @since 2016-02-29
 */
public class Ranking {

	private final int MAX_PEOPLE_LIMIT = 5;
	private String[] nameAccordingToScoreList;
	private int[] highScoreList;
	private int ammountOfPeopleInHighScoreList;
	private Scanner in;

	Ranking() {
		nameAccordingToScoreList = new String[MAX_PEOPLE_LIMIT];
		highScoreList = new int[MAX_PEOPLE_LIMIT];

		ammountOfPeopleInHighScoreList = 0;
	}

	private boolean scoreListFull() {
		return ammountOfPeopleInHighScoreList == MAX_PEOPLE_LIMIT;
	}

	private boolean resultLowerThenLowestScore(int result) {
		return highScoreList[MAX_PEOPLE_LIMIT - 1] > result;
	}
/**
 * Method takes in a name, checks if highscore is full and result is higher then lowest score, if so puts player in list.
 * else just puts in list, then method sorts the score from lowest to highest and displays it for the player.
 * @return nothing.
 */
	public void recordName(int result) {
		System.out.print("\n Please enter your name -");
		in = new Scanner(System.in);
		String newName = in.nextLine();

		if (scoreListFull() && resultLowerThenLowestScore(result)) {
			System.out.println("\nSorry you cannot enter top " + (MAX_PEOPLE_LIMIT) + " players");
			return;
		}
		if (scoreListFull()) {
			nameAccordingToScoreList[MAX_PEOPLE_LIMIT - 1] = newName;
			highScoreList[MAX_PEOPLE_LIMIT - 1] = result;
		} else {
			nameAccordingToScoreList[ammountOfPeopleInHighScoreList] = newName;
			highScoreList[ammountOfPeopleInHighScoreList] = result;
			ammountOfPeopleInHighScoreList++;
		}

		bubbleSortHighScoreDescendingOrder();
		displayHighScore();
	}

	public void displayHighScore() {
		if (ammountOfPeopleInHighScoreList == 0) {
			System.out.println("Still no results");
			return;
		}
		System.out.println("N Name\t\tresult");
		for (int i = 0; i < ammountOfPeopleInHighScoreList; i++) {
			System.out.println((i + 1) + " " + nameAccordingToScoreList[i] + "\t" + highScoreList[i]);
		}
	}

	private void bubbleSortHighScoreDescendingOrder() {
		if (ammountOfPeopleInHighScoreList < 2) {
			return;
		}
		boolean unsorted = true;
		while (unsorted) {
			unsorted = false;
			for (int i = 0; i < (ammountOfPeopleInHighScoreList - 1); i++) {
				if (highScoreList[i + 1] > highScoreList[i]) {
					int lowerRank = highScoreList[i];
					highScoreList[i] = highScoreList[i + 1];
					highScoreList[i + 1] = lowerRank;
					String nameOfLowerRank = nameAccordingToScoreList[i];
					nameAccordingToScoreList[i] = nameAccordingToScoreList[i + 1];
					nameAccordingToScoreList[i + 1] = nameOfLowerRank;
					unsorted = true;
				}//if
			}//for
		}//while
	}//sort
}//class
