package Api.ApiData;

public class UserDataAuth {

    public String email;
    public String password;

    public UserDataAuth() {

    }

    public String getLogin() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserDataAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDataAuth loginInfo() {
        return new UserDataAuth(this.email, this.password);
    }

}