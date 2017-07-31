package org.tianjyan.luban.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.tianjyan.luban.R;
import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.model.Const;

import java.util.ArrayList;
import java.util.List;

public class OutParaFragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_out_para, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.para_rv);
        List<OutPara> outParas = new ArrayList<>();
        OutPara outPara = new OutPara();
        outPara.setKey(Const.Floating_Area_Title);
        outParas.add(outPara);
        OutPara outPara1 = new OutPara();
        outPara1.setKey("Test1");
        outParas.add(outPara1);
//        outPara.setKey("Test");
//        outParas.add(outPara);
//        outPara.setKey(Const.Normal_Area_Title);
//        outParas.add(outPara);
//        outPara.setKey("Test2");
//        outParas.add(outPara);

        OutParaDataAdapter outParaDataAdapter = new OutParaDataAdapter(getActivity(), outParas);
        recyclerView.setAdapter(outParaDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }


}
