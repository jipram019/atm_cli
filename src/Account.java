/**
 * @author aji.pramono
 * @created 06/01/2022 - 20.28
 */
public interface Account {
    boolean login(int accountId, int accountPin);
    boolean logout();
    int balance();
    int withdraw(int amount);
    int deposit(int amount);
    int transfer(int target, int amount);
}
