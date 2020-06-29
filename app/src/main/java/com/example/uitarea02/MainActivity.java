package com.example.uitarea02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uitarea02.io.Bancos;
import com.example.uitarea02.io.JsonPlaceHolderApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewResult;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    private String username="";
    private String password="";
    public static String LOGIN_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////LOGIN
        editTextUsername = (EditText) findViewById(R.id.txtNombre);
        editTextPassword = (EditText) findViewById(R.id.txtClave);
        buttonLogin = (Button) findViewById(R.id.btnAcceder);

        buttonLogin.setOnClickListener(this);

        textViewResult = findViewById(R.id.txtBancos);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-uat.kushkipagos.com/transfer-subscriptions/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Bancos>> call = jsonPlaceHolderApi.getBancos();
        call.enqueue(new Callback<List<Bancos>>() {
            @Override
            public void onResponse(Call<List<Bancos>> call, Response<List<Bancos>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Bancos> posts = response.body();
                for (Bancos post : posts) {
                    String content = "";
                    content += post.getName() + "\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Bancos>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void btnEnviar(){

        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        LOGIN_URL="http://uealecpeterson.net/ws/login.php?usr="+username+"&pass="+password;
        //Toast toast = Toast.makeText(this, LOGIN_URL, Toast.LENGTH_SHORT);
        //toast.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Login Correcto!")){
                            openProfile();
                        }else{
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, validarLogin.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        btnEnviar();
    }
}