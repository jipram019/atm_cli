import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author aji.pramono
 * @created 06/01/2022 - 21.54
 */
public class AccountManagerImplTest {
    AccountManagerImpl accountManager;

    @BeforeEach
    void setup(){
        accountManager = new AccountManagerImpl();
    }

    @Test
    void login() {
        assertNull(this.accountManager.authorizedAccount);
        // Account doesn't exist, should be failed
        this.accountManager.login(1, 1);

        // Open an account, try to log in with wrong pin, should be failed
        Account account = accountManager.open(1, 1);
        this.accountManager.login(1, 2);
        assertNull(this.accountManager.authorizedAccount);

        // Try to log in with correct password, should be success
        this.accountManager.login(1, 1);
        assertEquals(this.accountManager.authorizedAccount, account);

        // Try to log in with different account wat the same time, should be failed
        Account account2 = accountManager.open(2, 2);
        Account authorizedAccount = this.accountManager.login(2, 2);
        assertEquals(authorizedAccount, account);
        assertNotEquals(authorizedAccount, account2);
    }

    @Test
    void logout() {
        // Can't log out without being logged in.
        Account account = accountManager.open(1, 1);
        assertFalse(this.accountManager.logout());

        // Login
        assertEquals(this.accountManager.login(1, 1), this.accountManager.authorizedAccount);

        // Logout
        assertTrue(this.accountManager.logout());
        assertNull(this.accountManager.authorizedAccount);
    }

    @Test
    void deposit() {
        // Must login first before deposit
        Account atmAccount = accountManager.open(1, 1);
        assertEquals(accountManager.deposit(100), 0);

        // Login to an account then deposit, and make sure total available amount for withdrawal is updated
        this.accountManager.login(1, 1);
        assertEquals(this.accountManager.deposit(100), 100);
        assertEquals(this.accountManager.totalAmount, 10100);
    }

    @Test
    void balance() {
        // Must login first before checking balance
        Account atmAccount = accountManager.open(1, 1);
        atmAccount.deposit(100);
        assertEquals(accountManager.balance(), 0);

        // Login to an account and then deposit, and make sure total available amount for withdrawal is updated
        this.accountManager.login(1, 1);
        assertEquals(this.accountManager.deposit(100), this.accountManager.balance());
        assertEquals(this.accountManager.totalAmount, 10100);
    }

    @Test
    void withdraw() {
        // Make sure can't withdraw without logged in
        Account atmAccount = accountManager.open(1, 1);
        assertEquals(accountManager.withdraw(100), 0);

        // Withdraw $100, will over draw the account and withdrawal will fail
        accountManager.login(1,1);
        assertEquals(accountManager.withdraw(100), 0);
        assertEquals(accountManager.totalAmount, 1000000);

        // Deposit money so we can test for more withdrawal logic
        accountManager.deposit(100);
        assertEquals(accountManager.totalAmount, 1000100);

        // Normal case
        assertEquals(accountManager.withdraw(100), 0);
        assertEquals(accountManager.totalAmount, 1000000);

        // Can't withdraw more because account is overdrawn
        assertEquals(accountManager.withdraw(100), 0);
        assertEquals(accountManager.totalAmount, 1000000);
    }

}
