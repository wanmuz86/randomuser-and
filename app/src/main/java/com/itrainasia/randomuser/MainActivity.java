package com.itrainasia.randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView nameTextView, addressTextView, emailTextView, dobTextView, phoneTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTextView = findViewById(R.id.textView);
        emailTextView = findViewById(R.id.textView2);
        addressTextView = findViewById(R.id.textView3);
        dobTextView = findViewById(R.id.textView4);
        phoneTextView = findViewById(R.id.textView5);
        imageView = findViewById(R.id.imageView);



    }

    public void buttonPressed(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://randomuser.me/api/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject results = new JSONObject(response);
                            JSONObject personInfo =
                                    results.getJSONArray("results").getJSONObject(0);
                            String email = personInfo.getString("email");

                            String firstName = personInfo.getJSONObject("name").getString("first");
                            String lastName = personInfo.getJSONObject("name").getString("last");
                            String title = personInfo.getJSONObject("name").getString("title");

                            String street = personInfo.getJSONObject("location").getString("street");
                            String city = personInfo.getJSONObject("location").getString("city");
                            String state = personInfo.getJSONObject("location").getString("state");
                            String postcode = personInfo.getJSONObject("location").getString("postcode");

                            String phone = personInfo.getString("phone");
                            String dob = personInfo.getJSONObject("dob").getString("date");
                            nameTextView.setText(title+ " "+firstName+" "+lastName);
                            emailTextView.setText(email);
                            addressTextView.setText(street + " "+city+" "+state+" "+postcode);
                            phoneTextView.setText(phone);
                            dobTextView.setText(dob);

                            String pictureUrl = personInfo.getJSONObject("picture").getString("large");
                            Glide.with(MainActivity.this).load(pictureUrl).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("debug","That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
