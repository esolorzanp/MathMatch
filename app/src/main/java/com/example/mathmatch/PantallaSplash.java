package com.example.mathmatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PantallaSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Se agrega el código para causar el efecto de espera para la pantalla Splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Se invoca la Activity en memoria
                // 1er parámetro el nombre de la clase para la Actitivy del Splash
                // 2do parámetro la siguiente Activity que se mostrará después del Splash
                Intent ActivitySplash = new Intent(PantallaSplash.this, MainActivity.class);
                startActivity(ActivitySplash);
                finish();
            }
        }, 5000); // La pantalla Splash se mostrará por 5 segundos antes de que desaparezca
    }
}