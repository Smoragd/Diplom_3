package stellarburgers.usefuldata;

public class UserPOJO {

    private String email;
    private String password;
    private String name;

    //для регистрации
    public UserPOJO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // для авторизации
    public UserPOJO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserPOJO() { }

    // Геттеры и сеттеры - с помощью Lombok
}
