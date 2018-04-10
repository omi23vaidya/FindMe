package com.example.omkarvaidya.loginandroid3;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView lvAdded;
    private CardView btnAdd;
    private EditText etUname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        if (lvAdded == null) {
            lvAdded = (ListView) findViewById(R.id.addContList);
        }
        if (btnAdd == null) {
            btnAdd = (CardView) findViewById(R.id.addContButn);
        }
        if (etUname == null) {
            etUname = (EditText) findViewById(R.id.editText3);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String myUname = prefs.getString("username","mohit");
        adapter = new ArrayAdapter<String>(this,
                R.layout.my_custom_layout,
                listItems);
        setListAdapter(adapter);
        populateList(myUname);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://4d161b3a.ngrok.io/addUser?username="+myUname+"&addContact="+etUname.getText().toString();
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                if(response.equals("true"))
                                {
                                    Toast.makeText(getBaseContext(),"User Added.",
                                            Toast.LENGTH_SHORT).show();
                                    addItems();
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"Sorry! Username does not exist.",
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

    public void populateList(String myUname) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://4d161b3a.ngrok.io/getAddedUser?username="+myUname;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(response.length()>0) {
                            response = response.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");
                            String[] addedUnames = response.split(",");
                            for (String un : addedUnames)
                                addItems(un);
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

    public void addItems() {
        listItems.add(etUname.getText().toString());
        adapter.notifyDataSetChanged();
    }

    public void addItems(String uName) {
        listItems.add(uName);
        adapter.notifyDataSetChanged();
    }

    protected ListView getListView() {
        if (lvAdded == null) {
            lvAdded = (ListView) findViewById(R.id.addContList);
        }
        return lvAdded;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }
}
