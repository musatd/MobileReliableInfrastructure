package infrastructure.reliable.org.reliableinfrastructure.optionFrag;

import java.util.List;

public class User {
    private String token;
    private String phone;
    private List<String> cities;

    public User() {}

    public User(String token, String phone, List<String> cities) {
        this.token = token;
        this.phone = phone;
        this.cities = cities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "User [token=" + token + ", phone=" + phone + ", cities=" + cities + "]";
    }

}

