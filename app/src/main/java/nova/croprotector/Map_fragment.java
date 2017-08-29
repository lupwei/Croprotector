package nova.croprotector;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;


public class Map_fragment extends MapFragmentBase {

    public Map_fragment() {}

    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    protected static CameraPosition cameraPosition;

    @Override
    LatLng getTarget() {
        return BEIJING;
    }

    @Override
    CameraPosition getCameraPosition() {
        return cameraPosition;
    }

    @Override
    void setCameraPosition(CameraPosition cameraPosition) {
        Map_fragment.cameraPosition = cameraPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        //TODO

        return view;
    }
}