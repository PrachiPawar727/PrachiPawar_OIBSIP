import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Player {
    String name;
    int rounds;

    public Player(String name, int rounds) {
        this.name = name;
        this.rounds = rounds;
    }
}

public class NUMBERGAME {
    public static void main(String[] args) {
        System.out.println("Welcome to the Number Guessing Game!");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> leaderboard = new ArrayList<>();

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Play a new game");
            System.out.println("2. Show leaderboard");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner = new Scanner(System.in);//this will reintialize the object and flush out value cached from keyboard

            switch (choice) {
                case 1:
                    playGame(scanner, leaderboard);
                    break;
                case 2:
                    showLeaderboard(leaderboard);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Thank you for playing. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void playGame(Scanner scanner, ArrayList<Player> leaderboard) {
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        scanner = new Scanner(System.in);//this will reintialize the object and flush javac out value cached from keyboard

        System.out.print("Enter the minimum value of the range: ");
        int minRange = scanner.nextInt();

        System.out.print("Enter the maximum value of the range: ");
        int maxRange = scanner.nextInt();

        if (minRange >= maxRange) {
            System.out.println("Invalid range. The minimum value should be less than the maximum value.");
            return;
        }

        int secretNumber = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
        System.out.println("I have picked a number between " + minRange + " and " + maxRange + ". Let's begin!");

        int roundsTaken = 0;
        for (int roundNumber = 1; roundNumber <= 10; roundNumber++) {
            System.out.print("Round " + roundNumber + ": Enter your guess: ");
            int guess = scanner.nextInt();
            roundsTaken++;

            if (guess == secretNumber) {
                System.out.println("Congratulations, " + playerName + "! You guessed the number " + secretNumber + " correctly in " + roundsTaken + " rounds.");

                leaderboard.add(new Player(playerName, roundsTaken));
                Collections.sort(leaderboard, (p1, p2) -> Integer.compare(p1.rounds, p2.rounds));

                break;
            } else if (guess < secretNumber) {
                System.out.println("Try again! Your guess is too low.");
            } else {
                System.out.println("Try again! Your guess is too high.");
            }
        }

        if (roundsTaken > 10) {
            System.out.println("Sorry, you've run out of rounds. The secret number was " + secretNumber + ".");
        }

    }

    private static void showLeaderboard(ArrayList<Player> leaderboard) {
        System.out.println("---- Leaderboard ----");
        for (int i = 0; i < Math.min(5, leaderboard.size()); i++) {
            Player player = leaderboard.get(i);
            System.out.println((i + 1) + ". " + player.name + " - " + player.rounds + " rounds");
        }
    }
}
