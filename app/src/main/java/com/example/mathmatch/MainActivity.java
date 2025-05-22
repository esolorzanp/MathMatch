package com.example.mathmatch;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageView cuadro1, cuadro2, cuadro3, cuadro4, cuadro5, cuadro6, cuadroTocadoAnterior;
    private TextView txvPuntaje;
    private int[] imagenesPorCuadro;
    private int contadorToques = 0, primeraImagenDestapada;
    private String etiquetaTocadaAnterior = "";
    private String etiquetaTocada;

    // Variables para controlar los puntos para el jugador si acierta o falla en el intento
    private int puntosAcierto = 10;
    private int puntosEquivocacion = -5;

    // Variable con el puntaje del jugador
    private int puntajeJuego = 0;

    // Variable que hará la gestión del sonido
    SoundPool gestorSonido;

    // Variable que contendrá la pista de audio a reproducir
    int pistaAudio, pistaAudio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtPuntaje), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Se muestra el ícono de la APP dentro de la pantalla principal
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // Se enlaza la variable del modelo con el componente de la vista
        cuadro1 = findViewById(R.id.imv1);
        cuadro2 = findViewById(R.id.imv2);
        cuadro3 = findViewById(R.id.imv3);
        cuadro4 = findViewById(R.id.imv4);
        cuadro5 = findViewById(R.id.imv5);
        cuadro6 = findViewById(R.id.imv6);

        // Se inicializa el text view donde se muestra el puntaje
        txvPuntaje = findViewById(R.id.txvPuntaje);

        // Se inicializan el nombre de las imágenes q mostrará cada cuadro
        imagenesPorCuadro = new int[6];
        imagenesPorCuadro[0] = R.drawable.i2;
        imagenesPorCuadro[1] = R.drawable.i3;
        imagenesPorCuadro[2] = R.drawable.i1;
        imagenesPorCuadro[3] = R.drawable.i1;
        imagenesPorCuadro[4] = R.drawable.i2;
        imagenesPorCuadro[5] = R.drawable.i3;

        // Se inicializa el contador de toques (clics)
        contadorToques = 0;
        primeraImagenDestapada = 0;

        // Se inicializa el gestor de sonido
        gestorSonido = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);

        // Se carga la pista de audio
        pistaAudio = gestorSonido.load(this, R.drawable.sonido_acierto, 1);
        pistaAudio2 = gestorSonido.load(this, R.drawable.sonido_error, 1);
    }

    public void ClicEnCuadro(View v) {
        etiquetaTocada = "";
//        int idImagen = imagenesPorCuadro[2]; //[(idCuadro - 1)];
        boolean coincidio = false;
        ImageView cuadroTocado;

        cuadroTocado = (ImageView) v;
        etiquetaTocada = cuadroTocado.getTag().toString();

        // No permite que una etiqueta seleccionada se pueda seleccionar nuevamente
        if (!etiquetaTocada.equals(etiquetaTocadaAnterior)) {
            // Se destapa la imágen respectiva
            cuadroTocado.setImageResource(imagenesPorCuadro[Integer.parseInt(etiquetaTocada)]);

            // Se incrementa el contador de toques (¿cuántos cuadros se han destapado?)
            contadorToques++;

            System.out.println("Contador de Toques->" + contadorToques);

            if (contadorToques == 1) {
                etiquetaTocadaAnterior = etiquetaTocada;
                cuadroTocadoAnterior = (ImageView) v;
            } else if (contadorToques == 2) {
                if (imagenesPorCuadro[Integer.parseInt(etiquetaTocada)] == imagenesPorCuadro[Integer.parseInt(etiquetaTocadaAnterior)]) {
                    // El usuario si encontró la pareja

                    // Se actualiza el puntaje
                    puntajeJuego += puntosAcierto;

                    // Se reproduce el audio
                    gestorSonido.play(pistaAudio, 1, 1, 1, 0, 0);

                    Toast.makeText(getApplicationContext(), "Resultado-> MATCH, Pareja Completada!" + "\nPuntaje->" + puntajeJuego, Toast.LENGTH_LONG).show();
                } else {
                    // El usuario se equivocó en este intento

                    // Se actualiza el puntaje
                    puntajeJuego += puntosEquivocacion;

                    // Se reproduce el audio
                    gestorSonido.play(pistaAudio2, 1, 1, 1, 0, 0);

                    cuadroTocado.setImageResource(imagenesPorCuadro[Integer.parseInt(etiquetaTocada)]);
                    Toast.makeText(getApplicationContext(), "Resultado-> INCORRECTO, Pareja Fallida!" + "\nPuntaje->" + puntajeJuego, Toast.LENGTH_LONG).show();
                    etiquetaTocadaAnterior = "";

                    // Esperar 2 segundos (2000 milisegundos) y luego tapar las imagenes seleccionadas
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Se vuelven a ocultar  las imágenes
                            cuadroTocado.setImageResource(R.drawable.tapado);
                            cuadroTocadoAnterior.setImageResource(R.drawable.tapado);
                        }
                    }, 2000);
                }

                // Se actualiza el puntaje en la pantalla
                txvPuntaje.setText(String.valueOf(puntajeJuego));

                // Se reinicia el contador de toques
                contadorToques = 0;
            }
        }
    }
}