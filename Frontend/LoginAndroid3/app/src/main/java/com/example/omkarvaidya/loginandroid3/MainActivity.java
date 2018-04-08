package com.example.omkarvaidya.loginandroid3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

//        cardView = (CardView) findViewById(R.id.cardView);
        final TextView etUname = (TextView) findViewById(R.id.textView);
        final EditText etPass = (EditText) findViewById(R.id.editText);
        final CardView btnReg = (CardView) findViewById(R.id.cardView);
        final TextView register = (TextView) findViewById(R.id.textView3);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request a string response from the provided URL.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                final String uname=etUname.getText().toString();
                String pass=etPass.getText().toString();
                String url = "http://1bcfb5ad.ngrok.io/login?username="+uname+"&pass="+pass;
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
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("username", uname);
                                    editor.commit();

                                    sharedpreferences = getSharedPreferences(mypreference,
                                            Context.MODE_PRIVATE);

                                    if (sharedpreferences.contains("username")) {
                                        Toast.makeText(getBaseContext(),(sharedpreferences.getString ("username", "##")),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(),"User not available in SP",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    Intent shareCont = new Intent(getApplicationContext(), SharingPage.class);
                                    startActivity(shareCont);

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                Intent registerPg = new Intent(getApplicationContext(),
                RegisterActivity.class);
                startActivity(registerPg);
            }
        });
    }

}
