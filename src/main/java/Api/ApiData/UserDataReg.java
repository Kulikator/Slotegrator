package Api.ApiData;

public class UserDataReg {
    private String currency_code;
    private String name;
    private String email;
    private String password_change;
    private String password_repeat;
    private String surname;
    private String username;


    public UserDataReg(String currency_code, String password_change, String password_repeat, String username, String surname, String name, String email) {
        this.currency_code = currency_code;
        this.password_change = password_change;
        this.password_repeat = password_repeat;
        this.username = username;
        this.surname = surname;
        this.name = name;
        this.email = email;
    }

    public UserDataReg() {
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getCountry() {
        return password_change;
    }

    public String getPassword_repeat() {
        return password_repeat;
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
