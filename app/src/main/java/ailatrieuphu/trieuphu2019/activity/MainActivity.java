package com.quangda280296.ailatrieuphu.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.quangda280296.ailatrieuphu.Config;
import com.quangda280296.ailatrieuphu.R;
import com.quangda280296.ailatrieuphu.database.HighscoreHelper;
import com.quangda280296.ailatrieuphu.listener.OnTouchClickListener;
import com.quangda280296.ailatrieuphu.utils.Utils;

//import com.noname.quangcaoads.QuangCaoSetup;

public class MainActivity extends AppCompatActivity {

    int countBack = 0;
    MediaPlayer mp;
    public static MainActivity mainActivity;

    private ImageView img_sound;
    HighscoreHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //QuangCaoSetup.initiate(MainActivity.this);

        /*FrameLayout layout_ads = findViewById(R.id.layout_ads);
        RelativeLayout adView = findViewById(R.id.adView);
        Utils.showAd(getApplicationContext(), adView, layout_ads);*/

        mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music);
        mp.setLooping(true);
        mainActivity = MainActivity.this;

        helper = new HighscoreHelper(getApplicationContext());

        findViewById(R.id.btn_play).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RuleActivity.class));
                //startActivity(new Intent(MainActivity.this, PlayActivity.class));
                mp.pause();
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_high_score).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBack = 0;

                ListView list = (ListView) findViewById(R.id.list);
                Cursor model = helper.getAll();
                startManagingCursor(model);
                HighscoreAdapter adapter = new HighscoreAdapter(model);
                list.setAdapter(adapter);

                findViewById(R.id.main_layout).setVisibility(View.GONE);
                findViewById(R.id.extra_view).setVisibility(View.VISIBLE);
                findViewById(R.id.secondary_layout).setVisibility(View.VISIBLE);
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_rate).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.quangda280296.ailatrieuphu"));
                startActivity(intent);
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_other).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Game+Offline"));
                startActivity(intent);
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.img_info).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, 20, getApplicationContext()));

        img_sound = findViewById(R.id.img_sound);
        img_sound.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.isPlayMusic) {
                    img_sound.setImageResource(R.mipmap.ic_sound_off);
                    Config.isPlayMusic = false;
                    mp.pause();
                } else {
                    img_sound.setImageResource(R.mipmap.ic_sound);
                    Config.isPlayMusic = true;
                    mp.start();
                }
            }
        }, 20, getApplicationContext()));

        mp.start();
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

        if (!mp.isPlaying())
            if (Config.isPlayMusic)
                mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.main_layout).getVisibility() == View.GONE) {
            findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.extra_view).setVisibility(View.GONE);
            findViewById(R.id.secondary_layout).setVisibility(View.GONE);
            return;
        }

        this.countBack++;
        if (this.countBack == 2)
            finish();
        else
            Utils.longToast(getApplicationContext(), getString(R.string.press_back_again));
    }

    public void setSound() {
        if (Config.isPlayMusic)
            img_sound.setImageResource(R.mipmap.ic_sound);
        else {
            img_sound.setImageResource(R.mipmap.ic_sound_off);
            mp.pause();
        }
    }

    class HighscoreAdapter extends CursorAdapter {
        public HighscoreAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row_item_highscore, parent, false);
            HighscoreHolder holder = new HighscoreHolder(row);
            row.setTag(holder);

            return (row);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            HighscoreHolder holder = (HighscoreHolder) view.getTag();
            holder.populateFrom(cursor, helper);
        }
    }

    static class HighscoreHolder {
        private TextView lbl_stt;
        private TextView lbl_reward;
        private TextView lbl_time;

        public HighscoreHolder(View row) {
            lbl_stt = (TextView) row.findViewById(R.id.lbl_stt);
            lbl_reward = (TextView) row.findViewById(R.id.lbl_reward);
            lbl_time = (TextView) row.findViewById(R.id.lbl_time);
        }

        public void populateFrom(Cursor c, HighscoreHelper helper) {
            lbl_stt.setText((helper.getSTT(c) + 1) + "");

            if (helper.getReward(c) > 0)
                lbl_reward.setText(helper.getReward(c) + "000 đ");
            else
                lbl_reward.setText(helper.getReward(c) + " đ");

            lbl_time.setText(helper.getTime(c) + " giây");
        }
    }
}