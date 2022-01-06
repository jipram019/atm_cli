/**
 * @author aji.pramono
 * @created 06/01/2022 - 21.36
 */
public interface AccountManager {
    Account open(int accountId, int accountPin);
    Account login(int accountId, int accountPin);
    boolean logout();
    int withdraw(int amount);
    int deposit(int amount);
    int balance();
}
