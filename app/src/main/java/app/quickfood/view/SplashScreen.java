package app.quickfood.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import app.quickfood.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.ibrahimsn.particle.ParticleView;

public class SplashScreen extends AppCompatActivity {

    @BindView(R.id.img_splash)
    ImageView img_splash;

    Handler handler;

    @BindView(R.id.particleView)
    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        img_splash.animate().rotation(360f).setDuration(3000).start();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                particleView.pause();
                Intent intent=new Intent(SplashScreen.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }

    @Override
    protected void onResume() {
        super.onResume();
        particleView.resume();
    }
}