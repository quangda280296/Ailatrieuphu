package ailatrieuphu.trieuphu2019.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import ailatrieuphu.trieuphu2019.R;
import ailatrieuphu.trieuphu2019.listener.OnTouchClickListener;
import ailatrieuphu.trieuphu2019.utils.PlayMusic;
import ailatrieuphu.trieuphu2019.utils.Utils;

import static ailatrieuphu.trieuphu2019.activity.MainActivity.mainActivity;

public class RuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        new PlayMusic().playRule(getApplicationContext());
        FrameLayout layout_ads = findViewById(R.id.layout_ads);
        RelativeLayout adView = findViewById(R.id.adView);
        Utils.showAd(getApplicationContext(), adView, layout_ads);

        findViewById(R.id.img_board).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .duration(1000)
                .playOn(findViewById(R.id.img_board));

        playAnimation();
    }

    public void playAnimation() {
        Handler handler = new Handler();

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.img_50).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(500)
                        .playOn(findViewById(R.id.img_50));
            }
        }, 8000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.img_call).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(500)
                        .playOn(findViewById(R.id.img_call));
            }
        }, 9000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.img_audience).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(500)
                        .playOn(findViewById(R.id.img_audience));
            }
        }, 10000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.img_switch).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(500)
                        .playOn(findViewById(R.id.img_switch));
            }
        }, 11000);*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.rule).setVisibility(View.GONE);
                findViewById(R.id.ready).setVisibility(View.VISIBLE);

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RuleActivity.this, PlayActivity.class));
                        finish();
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        mainActivity.setSound();
                    }
                }, 20, getApplicationContext()));
                //end
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set fullscreen
        findViewById(R.id.root).setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        PlayMusic.mp.start();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayMusic.mp.pause();
    }
}
