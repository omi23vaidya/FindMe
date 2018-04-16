package com.example.omkarvaidya.loginandroid3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.omkarvaidya.loginandroid3.R.id.startShariId;

public class SharingPage extends AppCompatActivity {

    private String myUname="";
    String[] unames=null;
    String resp="";

    public void givePermission(String contact){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = getString(R.string.address)+"givePermission?username="+myUname+"&permittedUser="+contact;    //whose=getAddedUser or getSharingUser
        Log.d("URL", url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response",response);
                        resp=response;
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
        return;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_page);

        final CardView addCont = (CardView) findViewById(R.id.addContact);
        final CardView strtSharing = (CardView) findViewById(R.id.shareContact);
        //final CardView viewCont = (CardView) findViewById(R.id.viewContact);
        final CardView viewJour = (CardView) findViewById(R.id.viewJourId);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        myUname = prefs.getString("username","mohit");

        addCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SharingPage.this, AddContact.class);
                startActivity(myIntent);
            }
        });

        strtSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = getString(R.string.address)+"getAddedUser?username="+myUname;
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response",response);
                                if(response.length()>0) {
                                    response = response.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
                                    unames = response.split(",");
                                }
                                else
                                    unames[0]="";
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SharingPage.this);
                                LayoutInflater inflater = getLayoutInflater();
                                View convertView = (View) inflater.inflate(R.layout.activity_contacts, null);
                                alertDialog.setView(convertView);
                                alertDialog.setTitle("Contacts");
                                ListView lv = (ListView) convertView.findViewById(R.id.contact_lv);
                                ArrayAdapter<String> adapter;
                                if(unames[0]!="") {
                                    adapter = new ArrayAdapter<String>(SharingPage.this, android.R.layout.simple_list_item_1, unames);
                                    lv.setAdapter(adapter);
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                                long id) {
                                            //Intent intent = new Intent(SharingPage.this, ViewMap.class);
                                            String contact = (String) parent.getItemAtPosition(position);
                                            //intent.putExtra("selectedContact", contact);
                                            //startActivity(intent);
                                            givePermission(contact);
                                            Log.d("Response123",resp);
                                            if(resp.equals("true"))
                                            {
                                                Toast.makeText(SharingPage.this, "Location successfully shared with "+contact, Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(SharingPage.this, "Unable to share location", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    alertDialog.show();
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"No contacts added yet!",
                                            Toast.LENGTH_SHORT).show();
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

//                Intent myIntent = new Intent(SharingPage.this, ViewMap.class);
//                startActivity(myIntent);
            }
        });

//        viewCont.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(SharingPage.this, ViewMap.class);
//                startActivity(myIntent);
//            }
//        });

        viewJour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names[] ={"Karan","Mohit","Prateek","Omkar", "Karan","Mohit","Prateek","Omkar", "Karan","Mohit","Prateek","Omkar"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SharingPage.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.activity_contacts, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Contacts");
                ListView lv = (ListView) convertView.findViewById(R.id.contact_lv);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SharingPage.this,android.R.layout.simple_list_item_1,names);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(SharingPage.this, ViewMap.class);
                        String contact = (String) parent.getItemAtPosition(position);
                        intent.putExtra("selectedContact", contact);
                        startActivity(intent);
                    }
                });
                alertDialog.show();


            }
        });

        /*
        * String names[] ={"Karan","Mohit","Prateek","Omkar", "Karan","Mohit","Prateek","Omkar", "Karan","Mohit","Prateek","Omkar"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SharingPage.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.activity_contacts, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Contacts");
                ListView lv = (ListView) convertView.findViewById(R.id.contact_lv);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SharingPage.this,android.R.layout.simple_list_item_1,names);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(SharingPage.this, ViewMap.class);
                        String contact = (String) parent.getItemAtPosition(position);
                        intent.putExtra("selectedContact", contact);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
        * */

    }
}
