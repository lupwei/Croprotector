package nova.croprotector;

/**
 * Created by ZTH on 2017/9/13.
 */

public class SetLocation {
    public static double setLongitude() {
        double longitude=78+Math.random()*99%(99-78+1);
        return longitude;
    }

    public static double setLatitude() {
        double latitude=27+Math.random()*37%(37-27+1);
        return latitude;
    }
}
