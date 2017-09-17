package nova.croprotector;

/**
 * Created by WT on 2017/7/28.
 */

public class User {                                             //现在不分姓和名，只有用户名一项，所以先暂时让firstname和lastname值都为username，只用到firstname
    private String phonenumber;
    private String password;
    private String firstname;
    private String lastname;

    public void User(String phonenumber,String password){
        this.phonenumber = phonenumber;
        this.password = password;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public String Get_phonenumber(){
        return phonenumber;
    }

    public String Get_password(){
        return password;
    }

    public String Get_firstname(){
        return firstname;
    }

    public String Get_lastname(){
        return lastname;
    }

    public void setPhoneNumber(String phonenumber){
        this.phonenumber = phonenumber;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }

}
