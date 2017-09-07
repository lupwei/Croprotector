package nova.croprotector;

/**
 * Created by ZTH on 2017/9/6.
 */

public class Password {

    private String new_password;
    private String phonenumber;


    public void setNewPassword(String new_password) {
        this.new_password=new_password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber=phonenumber;
    }


    public String getNewPassword() {
        return new_password;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }
}
