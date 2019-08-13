package br.com.lucasts.boookado.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import br.com.lucasts.boookado.R;

public class MainActivity extends AppCompatActivity {


    ViewFlipper viewFlipper;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = findViewById(R.id.btn_entrar);

        int images[] = {R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3, R.drawable.slide_4};

        viewFlipper = findViewById(R.id.flipper_slides);

        for (int i = 0; i <images.length; i++){
            slideImages(images[i]);
        }
    }

    public void goEntrar(View view){

        Intent intent = new Intent(this, ListarBooksActivity.class);
        startActivity(intent);
    }

    public void slideImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000); //5 segundos
        viewFlipper.setAutoStart(true);

        //animação
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}
