package pl.commit.connection.utils;

public class ValidationInputsLogin {

    public static final String VALIDATION_OK = "OK";
    public static final String LOGIN_EMPTY = "Login nie może być pusty.";
    public static final String PASSWORD_EMPTY = "Hasło nie może być puste.";

    public static String checkInputLoginPassword(String login, String password) {
        if (login == null || login.isEmpty()) return LOGIN_EMPTY;
        if (password == null || password.isEmpty()) return PASSWORD_EMPTY;

        return VALIDATION_OK;
    }
}
