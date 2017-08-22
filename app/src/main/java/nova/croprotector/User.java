package nova.croprotector;

/**
 * Created by WT on 2017/7/28.
 */

public class User {
    private String phonenumber;
    private String password;

    public void User(String phonenumber,String password){
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String Get_phonenumber(){
        return phonenumber;
    }

    public String Get_password(){
        return password;
    }

    public void setPhoneNumber(String phonenumber){
        this.phonenumber = phonenumber;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
