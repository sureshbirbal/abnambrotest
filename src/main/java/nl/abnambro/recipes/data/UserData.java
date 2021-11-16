package nl.abnambro.recipes.data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserData {

    @NotNull
    @NotBlank(message = "Username can not be empty")
    private String username;

    @NotNull
    @NotBlank(message = "Password can not be empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}