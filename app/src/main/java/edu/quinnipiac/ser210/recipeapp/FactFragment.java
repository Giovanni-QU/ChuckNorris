package edu.quinnipiac.ser210.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class FactFragment extends Fragment {

    public FactFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Fact Frag", " running on create view");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fact, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Fact Frag", " running on create view");
            //View view = getView();
        // fact = (TextView)view.findViewById(R.id.factText);
    }

    @Override
    public void onStart() {
        super.onStart();



    }

}
