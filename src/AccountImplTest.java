import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author aji.pramono
 * @created 06/01/2022 - 21.00
 */
public class AccountImplTest {
    AccountImpl account;

    @BeforeEach
    void setup(){
        this.account = new AccountImpl(1,1);
    }

    @Test
    void login() {
        // Make sure auth works
        assertTrue(account.login(1, 1));
        // Make sure auth fails
        assertFalse(account.login(1, 2));
        assertFalse(account.login(2, 2));
        assertFalse(account.login(2, 1));
    }

    @Test
    void withdraw() {
        // Withdrawal works even when balance is not enough, even though it'll be overdrawn
        assertEquals(account.withdraw(100), -105);

        // Deposit 105
        assertEquals(account.deposit(205), 100);

        // Withdraw 20
        assertEquals(account.withdraw(20), 80);
    }

    @Test
    void deposit() {
        // Make sure deposits work
        assertEquals(account.deposit(100), 100);
    }
}
