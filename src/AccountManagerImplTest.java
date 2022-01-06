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
}
