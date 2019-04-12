package edu.quinnipiac.ser210.recipeapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainFragment extends Fragment {

    FactListener myActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("msg", "MainFrag OnAttatch");
        myActivity = (FactListener) activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("msg", "MainFrag onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.v("msg", "MainFrag onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.v("msg", "MainFrag onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("msg", "MainFrag onStart");



    }
    static interface FactListener{
        void buttonClickedMoveToDetail();
    }

}
