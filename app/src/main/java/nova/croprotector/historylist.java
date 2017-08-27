package nova.croprotector;

/**
 * Created by ZTH on 2017/7/23.
 */

public class historylist {
    private String diseasename;
    private String num;
    private int imageid;

    public historylist(String diseasename, String num, int imageid){
        this.diseasename=diseasename;
        this.num=num;
        this.imageid=imageid;
    }

    public String getDiseasename(){
        return diseasename;
    }

    public String getNum(){
        return num;
    }

    public int getImageid(){
        return imageid;
    }

    public void setDiseasename(String newname){
        this.diseasename=newname;
    }

    public void setNum(String newnum){
        this.num = newnum;
    }

    public void setImageid(int newimageid){
        this.imageid=newimageid;
    }
}
