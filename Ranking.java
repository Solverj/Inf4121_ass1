import java.util.Scanner;

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

		bubbleSortHighScoreAscendingOrder();
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

	private void bubbleSortHighScoreAscendingOrder() {
		if (ammountOfPeopleInHighScoreList < 2) {
			return;
		}
		boolean unsorted = true;
		while (unsorted) {
			unsorted = false;
			for (int i = 0; i < (ammountOfPeopleInHighScoreList - 1); i++) {
				if (highScoreList[i + 1] > highScoreList[i]) {
					int swapR = highScoreList[i];
					highScoreList[i] = highScoreList[i + 1];
					highScoreList[i + 1] = swapR;
					String swapN = nameAccordingToScoreList[i];
					nameAccordingToScoreList[i] = nameAccordingToScoreList[i + 1];
					nameAccordingToScoreList[i + 1] = swapN;
					unsorted = true;
				}//if
			}//for
		}//while
	}//sort
}//class
