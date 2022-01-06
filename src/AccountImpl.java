/**
 * @author aji.pramono
 * @created 06/01/2022 - 20.37
 */
public class AccountImpl implements Account{
    int accountId;
    int accountPin;
    int balance;
    AccountManagerImpl accountManager;

    public AccountImpl(int accountId, int accountPin){
        this.accountId = accountId;
        this.accountPin = accountPin;
        this.balance = 0;
        this.accountManager = new AccountManagerImpl();
    }

    /**
     * Login into an account
     * @param accountId
     * @param accountPin
     * @return whether or not the login was successful
     */
    @Override
    public boolean login(int accountId, int accountPin) {
        if (this.accountId == accountId && this.accountPin == accountPin) {
            System.out.println(String.format("Hello, " + "%s!", accountId));
            System.out.println(String.format("Your balance is " + "%d", this.balance));
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }

    /**
     * Logout of account
     * @return true
     */
    @Override
    public boolean logout() {
        System.out.println(String.format("Goodbye," + " %s!", this.accountId));
        return true;
    }

    /**
     * Check balance of account
     * @return balance
     */
    @Override
    public int balance() {
        System.out.println(String.format("Current balance: $%s", this.balance));
        return this.balance;
    }

    /**
     * Withdraw money from account
     * @param amount
     * @return balance after withdrawal
     */
    @Override
    public int withdraw(int amount) {
        if(this.balance - amount >= 0){
            this.balance -= amount;
            System.out.println(String.format("Amount withdrawn: $%s", amount));
            System.out.println(String.format("Current balance: $%s", this.balance));
        } else if(this.balance - amount < 0){
            System.out.println(String.format("Amount to withdraw: $%s", amount));
            System.out.println(String.format("Amount to withdraw exceeded available amount. Current balance: $%s", this.balance));
        }
        return balance;
    }

    /**
     * Deposit money into the account
     * @param amount
     * @return balance after deposit
     */
    @Override
    public int deposit(int amount) {
        this.balance += amount;
        System.out.println(String.format("Your balance is $%s", this.balance));
        return this.balance;
    }

    @Override
    public int transfer(int target, int amount) {
        return 0;
    }
}
