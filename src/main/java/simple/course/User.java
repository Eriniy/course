package simple.course;


public class User {

    public static String actualLogin;
    public static int actualId;

    private int id;
    private String login;
    private String password;
    private String name;
    private String law;

    public User(int id, String name, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaw() {
        return name;
    }

    public void setLaw(String law) {
        this.law = law;
    }
}

