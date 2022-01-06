/**
 * @author aji.pramono
 * @created 06/01/2022 - 21.38
 */

import java.util.HashMap;
import java.util.Map;

/** AccountManager represents module to manage accounts **/
public class AccountManagerImpl implements AccountManager{
    Account authorizedAccount; // Current authorized account, only 1 account at a time could login
    int totalAmount; // Total amount that can be withdrawn
    Map<Integer, Account> accounts; // List of all accounts, stored in memory for now

    public AccountManagerImpl(){
        this.authorizedAccount = null;
        this.totalAmount = 1000000; // hardcoded for now
        this.accounts = new HashMap<>();
    }

    /**
     * Open an account
     * @param accountId
     * @param accountPin
     * @return Account instance
     */
    @Override
    public Account open(int accountId, int accountPin) {
        if (getAccount(accountId) == null) {
            Account account = new AccountImpl(accountId, accountPin);
            this.accounts.put(accountId, account);
            System.out.println(String.format("Account %s created.", accountId));
            return account;
        } else {
            System.out.println("Account already exists.");
            return getAccount(accountId);
        }
    }

    /**
     * Login into an account
     * @param accountId
     * @param accountPin
     * @return
     */
    @Override
    public Account login(int accountId, int accountPin) {
        // Check currently logged in account
        if (this.authorizedAccount == null) {
            // Check if account is exist
            if (getAccount(accountId) != null) {
                Account account = getAccount(accountId);
                if (account.login(accountId, accountPin)) {
                    this.authorizedAccount = account; // Set this as the current authorized account
                    return account;
                } else {
                    return null;
                }
            } else {
                System.out.println(String.format("Account %s does not exist. Open account first", accountId));
                return null;
            }
        } else {
            System.out.println("Another account is already logged in, please logout first.");
            return this.authorizedAccount;
        }
    }

    /**
     * Get Account
     * @param accountId
     * @return Account
     */
    private Account getAccount(int accountId) {
        return this.accounts.get(accountId);
    }

    /**
     * Log out of account
     * @return
     */
    @Override
    public boolean logout() {
        if (this.authorizedAccount != null) {
            this.authorizedAccount.logout();
            this.authorizedAccount = null;
            return true;
        } else {
            System.out.println("No account is currently logged in.");
            return false;
        }
    }

    @Override
    public int withdraw(int amount) {
        return 0;
    }

    @Override
    public int deposit(int amount) {
        return 0;
    }

    @Override
    public int balance() {
        return 0;
    }
}
