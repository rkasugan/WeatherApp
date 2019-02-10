//http://api.openweathermap.org/data/2.5/weather?zip=08852&appid=7e83d84a36b07633e296d51b421f7b7c -- curent weather at 08852
//http://api.openweathermap.org/data/2.5/forecast?zip=08852&appid=7e83d84a36b07633e296d51b421f7b7c -- 3 hour/5 day forecase at 08852

package com.example.a10010246.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    TextView textViewHigh;
    TextView textViewLow;
    EditText editText;
    Button button;
    ImageView imageViewCenter;
    TextView textViewQuote;
    TextView textViewZipcode;

    ImageView imageView1;
    TextView textView1High;
    TextView textView1Low;
    TextView textView1Date;
    ImageView imageView2;
    TextView textView2High;
    TextView textView2Low;
    TextView textView2Date;
    ImageView imageView3;
    TextView textView3High;
    TextView textView3Low;
    TextView textView3Date;
    ImageView imageView4;
    TextView textView4High;
    TextView textView4Low;
    TextView textView4Date;
    ImageView imageView5;
    TextView textView5High;
    TextView textView5Low;
    TextView textView5Date;

    JSONObject weatherJSON;
    String zipcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHigh = (TextView)findViewById(R.id.textViewHigh);
        textViewLow = (TextView)findViewById(R.id.textViewLow);
        editText = (EditText)findViewById(R.id.editText);
        imageViewCenter = (ImageView)findViewById(R.id.imageViewCenter);
        textViewQuote = (TextView)findViewById(R.id.textViewQuote);
        textViewZipcode = (TextView)findViewById(R.id.textViewZipcode);

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        textView1High = (TextView)findViewById(R.id.textView1High);
        textView1Low = (TextView)findViewById(R.id.textView1Low);
        textView1Date = (TextView)findViewById(R.id.textView1Date);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        textView2High = (TextView)findViewById(R.id.textView2High);
        textView2Low = (TextView)findViewById(R.id.textView2Low);
        textView2Date = (TextView)findViewById(R.id.textView2Date);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        textView3High = (TextView)findViewById(R.id.textView3High);
        textView3Low = (TextView)findViewById(R.id.textView3Low);
        textView3Date = (TextView)findViewById(R.id.textView3Date);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        textView4High = (TextView)findViewById(R.id.textView4High);
        textView4Low = (TextView)findViewById(R.id.textView4Low);
        textView4Date = (TextView)findViewById(R.id.textView4Date);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        textView5High = (TextView)findViewById(R.id.textView5High);
        textView5Low = (TextView)findViewById(R.id.textView5Low);
        textView5Date = (TextView)findViewById(R.id.textView5Date);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipcode = editText.getText().toString();
                AsyncThread thread = new AsyncThread();
                thread.execute(zipcode);
            }
        });
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... str) {
            try {
                String stringJSON = "";
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=" + str[0] + "&appid=7e83d84a36b07633e296d51b421f7b7c");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringJSON += line;
                }
                weatherJSON = new JSONObject(stringJSON);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                textViewLow.setText("Low: " + round(KelvinToF(weatherJSON.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min")), 1));
                textViewHigh.setText("High: " + round(KelvinToF(weatherJSON.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max")), 1));

                switch (weatherJSON.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main")) {
                    case "Clear":
                        imageViewCenter.setImageResource(R.drawable.sunbig);
                        textViewQuote.setText("It looks like Flareon used Sunny Day!");
                        break;
                    case "Clouds":
                        imageViewCenter.setImageResource(R.drawable.cloudbig);
                        textViewQuote.setText("It looks like Psyduck's Cloud Nine is kicking up some clouds!");
                        break;
                    case "Snow":
                        imageViewCenter.setImageResource(R.drawable.snowflakebig);
                        textViewQuote.setText("Watch out for Glaceon's Blizzard, it's pretty cold!");
                        break;
                    case "Rain":
                        imageViewCenter.setImageResource(R.drawable.rainbig);
                        textViewQuote.setText("Vaporeon used Rain Dance, so you better bring an umbrella!");
                        break;
                    case "Thunderstorm":
                        imageViewCenter.setImageResource(R.drawable.thunderstormbig);
                        textViewQuote.setText("Try not to get shocked by Jolteon's Thunder!");
                        break;
                    default:
                        imageViewCenter.setImageResource(R.drawable.sunbig);
                }

                TextView[] forecastHighs = {textView1High, textView2High, textView3High, textView4High, textView5High};
                TextView[] forecastLows = {textView1Low, textView2Low, textView3Low, textView4Low, textView5Low};
                TextView[] forecastDates = {textView1Date, textView2Date, textView3Date, textView4Date, textView5Date};
                ImageView[] forecastImages = {imageView1, imageView2, imageView3, imageView4, imageView5};

                for (int i = 0; i < 5; i ++) { //forecast
                    forecastHighs[i].setText("High: " + round(KelvinToF(weatherJSON.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp_max")), 1));
                    forecastLows[i].setText("Low: " + round(KelvinToF(weatherJSON.getJSONArray("list").getJSONObject(i).getJSONObject("main").getDouble("temp_min")), 1));
                    forecastDates[i].setText(convertTime(weatherJSON.getJSONArray("list").getJSONObject(i).getInt("dt")));
                    forecastImages[i].setImageResource(weatherPic(weatherJSON.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public double KelvinToF(double kelvin) {
            return kelvin*(9.0/5)-459.67;
        }
        public double round(double x, int decimalPlaces) {
            return Math.round(x * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        }
        public String convertTime(int x) {
            long milliseconds = (long)x*1000;
            Date date = new Date(milliseconds);
            DateFormat format = new SimpleDateFormat("hh:mm a");
            format.setTimeZone(TimeZone.getTimeZone("EST"));
            String result = format.format(date);
            return result;
        }
        public int weatherPic(String str) {
            switch (str) {
                case "Clear":
                    return R.drawable.sunsmall;
                case "Clouds":
                    return R.drawable.cloudsmall;
                case "Snow":
                    return R.drawable.snowflakesmall;
                case "Rain":
                    return R.drawable.rainsmall;
                case "Thunderstorm":
                    return R.drawable.thunderstormsmall;
                default:
                    return R.drawable.sunsmall;
            }
        }
    }
}
