import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author aji.pramono
 * @created 06/01/2022 - 23.22
 */
public class Main {
    private static AccountManager manager = new AccountManagerImpl();

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        // Have to create an account before being able to access it
        System.out.println("Welcome! Enter credentials to open an account...");
        boolean accountCreated = false;
        while (!accountCreated) {
            try {
                System.out.print("AccountID: ");
                int accountId = Integer.parseInt(in.next());
                System.out.print("AccountPin: ");
                int accountPin = Integer.parseInt(in.next());
                if (accountId < 0 || accountPin < 0) {
                    throw new NumberFormatException("Inputs have to be positive");
                }
                manager.open(accountId, accountPin);
                accountCreated = true;
            } catch (Exception e) {
                System.out.println("Inputs have to be valid positive integers");
            }
        }

        // Timer for 2 min timeout
        Timer timer = new Timer();
        timer.schedule(createTask(), 120000);

        while (in.hasNextLine()) {
            timer.cancel(); // Cancel the timer once the input is registered
            String[] input = in.nextLine().split(" "); // Split by whitespace for command amount inputs
            String command = input[0]; // Get the command

            switch (command) {
                case "login":
                    // Validate the input
                    try {
                        int accountIdReauth = Integer.parseInt(input[1]);
                        int accountPinReauth = Integer.parseInt(input[2]);
                        if (accountIdReauth < 0 || accountPinReauth < 0) {
                            System.out.println("Inputs have to be positive");
                            throw new NumberFormatException("Inputs have to be positive");
                        }
                        // Call the login method
                        manager.login(accountIdReauth, accountPinReauth);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input! Please try again...\nFormat is: authorize <accountId> <accountPin>");
                    }
                    break;
                case "open":
                    // Validate the input
                    try {
                        int accountIdReauth = Integer.parseInt(input[1]);
                        int accountPinReauth = Integer.parseInt(input[2]);
                        if (accountIdReauth < 0 || accountPinReauth < 0) {
                            System.out.println("Inputs have to be positive");
                            throw new NumberFormatException("Inputs have to be positive");
                        }
                        // Call the openAccount method
                        manager.open(accountIdReauth, accountPinReauth);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input! Please try again...\nFormat is: open <accountId> <accountPin>");
                    }
                    break;
                case "withdraw":
                    // Validate the input
                    try {
                        int amount = Integer.parseInt(input[1]);
                        if (amount < 0) {
                            System.out.println("Inputs have to be positive");
                            throw new NumberFormatException("Inputs have to be positive");
                        }
                        // Call the withdraw method
                        manager.withdraw(amount);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input! Please try again...\nFormat is: withdraw <amount>");
                    }
                    break;
                case "deposit":
                    // Validate the input
                    try {
                        int amount = Integer.parseInt(input[1]);
                        if (amount < 0) {
                            System.out.println("Inputs have to be positive");
                            throw new NumberFormatException("Inputs have to be positive");
                        }
                        // Call the deposit method
                        manager.deposit(amount);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input! Please try again...\nFormat is: deposit <amount>");
                    }
                    break;
                case "balance":
                    // Call the balance method
                    manager.balance();
                    break;
                case "logout":
                    // Call the logout method
                    manager.logout();
                    break;
                case "":
                    break;
                default:
                    // If nothing matches the input default to this
                    System.out.println("Invalid input! Please try again... Available commands {login, open, withdraw, deposit, balance, logout}");
                    break;
            }
            // Reset the timer
            timer = new Timer();
            timer.schedule(createTask(), 120000);
            System.out.print("Please enter a command: ");
        }
    }

    // Timeout task to call logout after session expires
    public static TimerTask createTask() {
        return new TimerTask() {
            @Override
            public void run() {
                manager.logout();
            }
        };
    }
}
