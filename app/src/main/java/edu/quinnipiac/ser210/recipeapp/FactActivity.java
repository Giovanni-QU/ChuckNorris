package edu.quinnipiac.ser210.recipeapp;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FactActivity extends AppCompatActivity {
    //instance variables
    private TextView fact; //will display fun fact
    private ShareActionProvider provider; //used for sharing feature
    private boolean background = false; //toggling background image change


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);
        FactFragment frag = (FactFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_fact);

        //creates toolbar-repetitive code?
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fact = (TextView)findViewById(R.id.factText);
        String foodFact = (String)getIntent().getExtras().get("food fact");
        fact.setText(foodFact);
        frag.setText(foodFact);
    }
    //toolbar methods, ultimately the same as in MainActivity, see comments in MainActivity for details
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem helpItem = menu.findItem(R.id.help);


        MenuItem settingsItem =  menu.findItem(R.id.changeColor);
        MenuItem shareItem =  menu.findItem(R.id.share);
        // Get the ActionProvider for later usage
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.changeColor:
                //true means new background, false means original background
                if(background) background = false;
                else background = true;
                switchActivityBackground(background);
                return  true;
            case R.id.help:
                Toast.makeText(this,"This app retrieves chuck norris fun facts from the Chuck Norris Jokes API on RapidAPI.com",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.share:
                // populate the share intent with data
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
                provider.setShareIntent(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void switchActivityBackground(boolean background) {
        LinearLayout factLayout = (LinearLayout) findViewById(R.id.facts);
        if(background) factLayout.setBackgroundResource(R.drawable.chuck2);

        else factLayout.setBackgroundResource(R.drawable.chuck3);



        Log.v("background image", "should change");
    }
}
