package com.example.omkarvaidya.loginandroid3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView back = (TextView) findViewById(R.id.textView7);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        final TextView regUname = (TextView) findViewById(R.id.textView4);
        final EditText regPass = (EditText) findViewById(R.id.editText2);
        final CardView btnReg = (CardView) findViewById(R.id.registerCardView);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request a string response from the provided URL.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String uname=regUname.getText().toString();
                String pass=regPass.getText().toString();
                String url = "http://232e6d07.ngrok.io/register?username="+uname+"&pass="+pass;
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                if(response.equals("true"))
                                {
                                    Toast.makeText(getBaseContext(),"Congrats! You can login",
                                            Toast.LENGTH_SHORT).show();
                                    //Intent myIntent = new Intent(MainActivity.this,
                                    //      sharingPage.class);
                                    //startActivity(myIntent);
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"Sorry! Username is incorrect.",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                            }
                        }
                );
                queue.add(postRequest);
            }
        });
    }
}
