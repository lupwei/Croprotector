package nova.croprotector;

/**
 * Created by WT on 2017/7/28.
 */

public class User {
    private String username;
    private String password;

    public void User(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String Get_username(){
        return username;
    }

    public String Get_password(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
