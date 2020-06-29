package com.example.uitarea02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import retrofit2.Response;

public class validarLogin extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_login);
        textView = (TextView) findViewById(R.id.txtPerfil);

        Intent intent = getIntent();

        textView.setText("BIENVENIDO " + intent.getStringExtra(MainActivity.KEY_USERNAME).toUpperCase());

    }

}