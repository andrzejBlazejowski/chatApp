package server;

public class User {
    private String login;
    private String password;

    public User(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public User(String[] user) {
        setLogin(user[0]);
        setPassword(user[1]);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
