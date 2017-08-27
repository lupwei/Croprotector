package nova.croprotector;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EditPassword_fragment extends Fragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_password,container,false);
        Button buttonSubmit=(Button)view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
                                    //TODO 还要添加提交逻辑
    }
}
