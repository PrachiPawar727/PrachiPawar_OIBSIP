import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    
    String name;
    public int pin;
    double balance;
    StringBuilder transactionHistory;

    public User(String name, int pin, double balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new StringBuilder();
    }
    
}

public class ATMINTERFACE {
    private static Map<Integer, User> users = new HashMap<>();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    static int userId = 1001;

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM Interface!");
        int choice;
        do {
            System.out.println("\n---- Main Menu ----");
            System.out.println("1. Register");
            System.out.println("2. Log In");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }

    private static void registerUser() {
        System.out.print("Enter your name: ");
        String name = scanner.next();
        scanner = new Scanner(System.in);
        System.out.print("Enter a 4-digit PIN: ");
        int pin = scanner.nextInt();

        // Check if PIN is 4-digit
        if (pin < 1000 || pin > 9999) {
            System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
            return;
        }

        User newUser = new User(name, pin, 0.0);
        users.put(userId, newUser);

        System.out.println("Registration successful. Your user ID is: " + userId++);
    }

    private static void loginUser() {
        System.out.print("Enter your user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        User user = users.get(userId);
        if (user != null&& user.pin == pin) {
            currentUser = user;
            showMenu();
        } else {
            System.out.println("Invalid user ID or PIN. Please try again.");
        }
    }

    private static void showMenu() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\n---- ATM Menu ----");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Transactions History");
            System.out.println("6. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    quit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void viewBalance() {
        System.out.println("Your current balance: $" + currentUser.balance);
    }

    private static void withdraw() {
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (amount <= currentUser.balance) {
            currentUser.balance -= amount;
            currentUser.transactionHistory.append("- $" + amount + " (Withdraw)\n");
            System.out.println("Withdrawal successful. Your current balance: $" + currentUser.balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private static void deposit() {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        currentUser.balance += amount;
        currentUser.transactionHistory.append("+ $" + amount + " (Deposit)\n");
        System.out.println("Deposit successful. Your current balance: $" + currentUser.balance);
    }

    private static void transfer() {
        System.out.print("Enter the user ID to transfer: ");
        int userId = scanner.nextInt();
        User recipient = users.get(userId);
        if (recipient != null) {
            System.out.print("Enter the amount to transfer: $");
            double amount = scanner.nextDouble();
            if (amount <= currentUser.balance) {
                currentUser.balance -= amount;
                recipient.balance += amount;
                currentUser.transactionHistory.append("- $" + amount + " (Transfer to " + recipient.name + ")\n");
                recipient.transactionHistory.append("+ $" + amount + " (Transfer from " + currentUser.name + ")\n");
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n---- Transactions History ----");
        System.out.println(currentUser.transactionHistory.toString());
    }
}