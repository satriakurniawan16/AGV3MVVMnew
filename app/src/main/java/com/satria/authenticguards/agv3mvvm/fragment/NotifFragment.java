package com.satria.authenticguards.agv3mvvm.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Adapter.NotifAdapter;
import com.satria.authenticguards.agv3mvvm.model.Notif;
import com.satria.authenticguards.agv3mvvm.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Notif> notifList;
    private RecyclerView.Adapter adapter;
    private View emptyview,emptyviewLogin;
    FirebaseUser firebaseUser;


    public NotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_notif, container, false);
        setupView(view);
        generateView(view);
        return view;
    }

    private void setupView(View view){
        mList = view.findViewById(R.id.recyler_notif);
        emptyview = view.findViewById(R.id.empty_view_notif);

        notifList = new ArrayList<>();
        adapter = new NotifAdapter(getApplicationContext(), notifList, emptyview);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        emptyviewLogin = (LinearLayout) view.findViewById(R.id.empty_view_login);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimary));
        }
    }
    private void generateView(View view){
        changeStatusBarColor();
        JsonUtil jsonUtil=new JsonUtil();
        if(firebaseUser != null ){
            jsonUtil.getDataNotif(getContext(),adapter,notifList);
            emptyviewLogin.setVisibility(View.GONE);
        }
        else{
            emptyviewLogin.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE);
        }
    }

}
