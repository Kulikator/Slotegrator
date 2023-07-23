package Api.ApiData;

public class UserDataGetOne {

    private String email;

    public UserDataGetOne(String email) {
        this.email = email;
    }

    public UserDataGetOne() {
    }

    public String getEmail() {
        return email;
    }
}

