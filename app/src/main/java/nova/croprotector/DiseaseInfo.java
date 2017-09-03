package nova.croprotector;

import java.util.Date;

/**
 * Created by ZTH on 2017/7/25.
 */

public class DiseaseInfo {
    private String infoNo;                          //手机号加时间
    private String diseaseNo;                     //还未检测出结果时默认为"未检测"
    private DiseaseKind diseasekind;
    private String picture;                        //Base64格式的字符串
    private Date infoTime;
    private double longitude;                      //不存在默认为-1
    private double latitude;                       //不存在默认为-1
    private String phonenumber;

    public void setInfoNo(String infoNo){
        this.infoNo=infoNo;
    }

    public void setDiseaseNo(String diseaseName){
        this.diseaseNo=diseaseName;
    }

    public void setDiseaseKind(DiseaseKind diseasekind) {
        this.diseasekind=diseasekind;
    }

    public void setPicture(String picture){
        this.picture=picture;
    }

    public void setTime(Date time){
        this.infoTime=time;
    }

    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }

    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }

    public void setPhonenumber(String phonenumber){
        this.phonenumber=phonenumber;
    }

    public String getInfoNo(){
        return infoNo;
    }

    public String getDiseaseNo(){
        return diseaseNo;
    }

    public DiseaseKind getDiseaseKind() {
        return diseasekind;
    }

    public String getPicture(){
        return picture;
    }

    public Date getInfoTime(){
        return infoTime;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getPhonenumber(){
        return phonenumber;
    }
}
