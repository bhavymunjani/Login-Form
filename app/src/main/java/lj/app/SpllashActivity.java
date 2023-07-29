package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class SpllashActivity extends AppCompatActivity {
    ImageView imageView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spllash);
        imageView=findViewById(R.id.splash_iv);
        sp=getSharedPreferences(ConstantURl.PREF,MODE_PRIVATE);
        getSupportActionBar().hide();
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        //alphaAnimation.setRepeatCount(2);
        alphaAnimation.setDuration(2700);
        imageView.startAnimation(alphaAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               
                if(sp.getString(ConstantURl.ID,"").equalsIgnoreCase(""))
                {
                    new CommonMethod(SpllashActivity.this,JsonLoginActivity.class);
                    finish();
                }
                else
                {
                    new CommonMethod(SpllashActivity.this,JsonProfileActivity.class);
                }

            }
        },3000);

    }
}