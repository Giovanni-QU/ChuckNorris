package edu.quinnipiac.ser210.recipeapp;

import android.content.Intent;

import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.ShareActionProvider;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//created By Giovanni Greco
public class MainActivity extends AppCompatActivity implements MainFragment.FactListener {
    //instance variables
    private String url = "https://matchilling-chuck-norris-jokes-v1.p.rapidapi.com/jokes/random";//full url string
    private ShareActionProvider provider;
    private boolean background = false; //boolean instance variable used for toggling background image change through button on toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("msg", " running on create MainActivity");



        //instantiates toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflates menu using toolbar.xml
        getMenuInflater().inflate(R.menu.toolbar,menu);

        //creates instances of menu items on toolbar
        MenuItem helpItem = menu.findItem(R.id.help);
        MenuItem settingsItem =  menu.findItem(R.id.changeColor);
        MenuItem shareItem =  menu.findItem(R.id.share);

        // Get the ActionProvider for later usage
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return super.onCreateOptionsMenu(menu);
    }
    public void onClick(View view) {
        new getFoodFact().execute(url);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.changeColor:
                //true means new background, false means original background
                if(background) background = false; //toggles background image
                else background = true;
                //calls method to handle actual UI background change
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
    //creates instsnce of UI layout and changes background image based on boolean param
    public void switchActivityBackground(boolean background) {
        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.MainFrag);
        if(background) mainLayout.setBackgroundResource(R.drawable.chuck2);

        else mainLayout.setBackgroundResource(R.drawable.chuck);



        Log.v("background image", "should change");
    }



    @Override
    public void buttonClickedMoveToDetail() {
       /* View fragmentContainer = findViewById(R.id.MainFrag);
        if (fragmentContainer != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FactFragment factFragment = new FactFragment();

            ft.replace(R.id.MainFrag,factFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();


        }
        */
    }


    private class getFoodFact extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = getFoodFact.class.getSimpleName();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            HttpURLConnection urlConnection =null;
            BufferedReader reader =null;
            String foodFact = null;

            try {
                //creates URL, establishes connection and method call as GET
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","172233e7bcmshfd503dd9975861fp1f4661jsn0cfef73a4dca");;
                //connects to URL
                urlConnection.connect();
                Log.v("msg","url connection open");

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                //debug: code is reaching here, so in != null
                reader  = new BufferedReader(new InputStreamReader(in));
                //debug:something going wrong here, reader is set to null

                // call getBufferString to get the string from the buffer.
                String foodFactJsonString = getBufferStringFromBuffer(reader).toString();

                // call a method to parse the json data and return a string.
                foodFact=  getFoodFact(foodFactJsonString);


            }catch(Exception e){
               Log.e(LOG_TAG,"Error " + e.getMessage());
                return null;
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG,"IOError" + e.getMessage());
                        return null;
                    }
                }
            }

            return foodFact;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
                //creates intent to switch activities and sends API response to new activity
                Intent intent = new Intent(MainActivity.this,FactFragment.class);
                intent.putExtra("food fact",result);
                startActivity(intent);


        }



        //formatting
        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            StringBuffer buffer = new StringBuffer();
            String line;

            //this code is not running because br.readLine() is null, how to fix?
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
                Log.v("buffer.append()", "is running");
            }

            if (buffer.length() == 0) {
                Log.v("buffer.length == 0", " so return null ");
                //this code is running
                return null;
            }

            return buffer;
        }
        //formatting
        public String getFoodFact(String yearFactJsonStr) throws JSONException {
            JSONObject yearFactJSONObj = new JSONObject(yearFactJsonStr);
            return yearFactJSONObj.getString("value"); // specified retreiving value
        }

    }
}
