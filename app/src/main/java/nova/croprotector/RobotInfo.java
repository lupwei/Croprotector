package nova.croprotector;


public class RobotInfo {
    private String location;                      //将来换成GPS位置，数据类型可能要变
    private String time;                          //将来换成时间，数据类型可能要变
    private int imageId;

    public RobotInfo(String location, String time, int imageId){
        this.location=location;
        this.time=time;
        this.imageId=imageId;

    }

    public String getLocation(){
        return location;
    }

    public String getTime(){
        return time;
    }

    public int getImageId(){
        return imageId;
    }


    public void setLocation(String location){
        this.location=location;
    }

    public void setTime(String time){
        this.time=time;
    }

    public void setImageId(int imageId){
        this.imageId=imageId;
    }

}
