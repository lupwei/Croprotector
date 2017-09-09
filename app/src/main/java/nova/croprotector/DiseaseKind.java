package nova.croprotector;

/**
 * Created by ZTH on 2017/9/3.
 */

public class DiseaseKind {

    private String diseaseNo;
    private String diseaseName;

    public DiseaseKind(){
        diseaseNo="未检测";
        diseaseName="未检测";
    }

    public void setDiseaseNo(String diseaseNo){
        this.diseaseNo=diseaseNo;
    }

    public void setDiseaseName(String diseaseName){
        this.diseaseName=diseaseName;
    }

    public String getDiseaseNo(){
        return diseaseNo;
    }

    public String getDiseaseName(){
        return diseaseName;
    }
}
