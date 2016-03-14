import java.util.Scanner;

public class Ranking {

	private final int MAX_PEOPLE_LIMIT = 5;
	private String[] names;
	private int[] scores;
	private int last;

	final private Scanner in = new Scanner(System.in);

	Ranking() {
		names = new String[MAX_PEOPLE_LIMIT];
		scores = new int[MAX_PEOPLE_LIMIT];

		last = 0;
	}

	public void recordScore(int score) {
		System.out.print("\nPlease enter your name: ");
		String newName = in.nextLine();
		if((last == MAX_PEOPLE_LIMIT) && scores[MAX_PEOPLE_LIMIT - 1] > score) {
			System.out.println("\nSorry you cannot enter top " + (MAX_PEOPLE_LIMIT) + " players");
			return;
		} else if(last == MAX_PEOPLE_LIMIT) {
			names[MAX_PEOPLE_LIMIT - 1] = newName;
			scores[MAX_PEOPLE_LIMIT - 1] = score;
		} else {
			names[last] = newName;
			scores[last] = score;
			last++;
		}

		sort();
		show();
	}

	public void show() {
		if(last == 0) {
			System.out.println("Still no results");
			return;
		}
		System.out.println("N Name\t\tresult");
		for(int i = 0; i < last; i++) {
			System.out.println((i + 1) + " " + names[i] + "\t" + scores[i]);
		}
	}

	private void sort() {
		if(last < 2)
			return;
		boolean unsorted = true;
		while(unsorted) {
			unsorted = false;
			for(int i = 0; i < (last - 1); i++) {
				if(scores[i + 1] > scores[i]) {
					int swapR = scores[i];
					{
						scores[i] = scores[i + 1];
						scores[i + 1] = swapR;
						String swapN = names[i];
						names[i] = names[i + 1];
						names[i + 1] = swapN;
						unsorted = true;
					}
				}
			}
		}
	}
}
