package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account login(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public Account register(String username, String password) {
        if (password.length() >= 4 && !username.isEmpty() && accountDAO.getAccountByUsername(username) == null) {
            Account account = new Account(username, password);
            return accountDAO.insertAccount(account);
        }
        return null;
    }
}
