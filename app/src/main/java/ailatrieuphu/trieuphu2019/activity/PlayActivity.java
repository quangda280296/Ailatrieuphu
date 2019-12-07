package com.quangda280296.ailatrieuphu.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.quangda280296.ailatrieuphu.Config;
import com.quangda280296.ailatrieuphu.R;
import com.quangda280296.ailatrieuphu.database.ALTPHelper;
import com.quangda280296.ailatrieuphu.database.HighscoreHelper;
import com.quangda280296.ailatrieuphu.listener.OnTouchClickListener;
import com.quangda280296.ailatrieuphu.utils.PlayMusic;
import com.quangda280296.ailatrieuphu.utils.Utils;

public class PlayActivity extends AppCompatActivity {

    private ImageView img_50;
    private ImageView img_call;
    private ImageView img_audience_help;
    private ImageView img_switch;

    private FrameLayout btn_a;
    private FrameLayout btn_b;
    private FrameLayout btn_c;
    private FrameLayout btn_d;

    private TextView lbl_time;
    private TextView lbl_confirm;

    private TextView lbl_a;
    private TextView lbl_b;
    private TextView lbl_c;
    private TextView lbl_d;

    int time_left = 30;
    int dapan;
    MediaPlayer mp;
    boolean deadLock = true;
    boolean time_run = true;
    Handler h = new Handler();

    Runnable wait = new Runnable() {
        @Override
        public void run() {
            if (time_left <= 0) {
                mp.stop();
                new PlayMusic().playTimesUp(getApplicationContext());
                endHere();
                return;
            }

            if (!time_run)
                return;

            time_left--;
            lbl_time.setText(time_left + "");

            h.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        /*FrameLayout layout_ads = findViewById(R.id.layout_ads);
        RelativeLayout adView = findViewById(R.id.adView);
        Utils.showAd(getApplicationContext(), adView, layout_ads);*/

        img_50 = findViewById(R.id.img_50);
        img_call = findViewById(R.id.img_call);
        img_audience_help = findViewById(R.id.img_audience_help);
        img_switch = findViewById(R.id.img_switch);

        btn_a = findViewById(R.id.btn_a);
        btn_b = findViewById(R.id.btn_b);
        btn_c = findViewById(R.id.btn_c);
        btn_d = findViewById(R.id.btn_d);

        initHelp();
        initAnswer();
        initSetting();

        if (!Config.help_1)
            img_50.setImageResource(R.mipmap.ic_50_off);

        if (!Config.help_2)
            img_call.setImageResource(R.mipmap.ic_call_off);

        if (!Config.help_3)
            img_audience_help.setImageResource(R.mipmap.ic_audience_help_off);

        if (!Config.help_4)
            img_switch.setImageResource(R.mipmap.ic_switch_question_off);

        ALTPHelper helper = new ALTPHelper(getApplicationContext(), Config.serial);
        Cursor cursor = helper.getCursor();

        int random = Utils.rand(0, cursor.getCount() - 1);
        cursor.moveToPosition(random);

        String question = helper.getQuestion(cursor);
        String answer_one = helper.getAnswerOne(cursor);
        String answer_two = helper.getAnswerTwo(cursor);
        String answer_three = helper.getAnswerThree(cursor);
        String answer_four = helper.getAnswerFour(cursor);

        String temp;
        dapan = Utils.rand(1, 4);
        String dap_an = "A";

        if (dapan != 1) {
            switch (dapan) {
                case 2:
                    temp = answer_one;
                    answer_one = answer_two;
                    answer_two = temp;
                    dap_an = "B";
                    break;

                case 3:
                    temp = answer_one;
                    answer_one = answer_three;
                    answer_three = temp;
                    dap_an = "C";
                    break;

                case 4:
                    temp = answer_one;
                    answer_one = answer_four;
                    answer_four = temp;
                    dap_an = "D";
                    break;
            }
        }

        TextView lbl_question = findViewById(R.id.lbl_question);
        lbl_question.setText(question);

        lbl_a = findViewById(R.id.lbl_a);
        lbl_a.setText("A. " + answer_one);

        lbl_b = findViewById(R.id.lbl_b);
        lbl_b.setText("B. " + answer_two);

        lbl_c = findViewById(R.id.lbl_c);
        lbl_c.setText("C. " + answer_three);

        lbl_d = findViewById(R.id.lbl_d);
        lbl_d.setText("D. " + answer_four);

        // setup other
        TextView lbl_serial = findViewById(R.id.lbl_serial);
        lbl_serial.setText("Câu " + Config.serial);

        TextView lbl_money = findViewById(R.id.lbl_money);
        lbl_money.setText(Utils.getMoney(Config.serial));

        lbl_time = findViewById(R.id.lbl_time);
        lbl_confirm = findViewById(R.id.lbl_confirm);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.level2);
        mp.setLooping(true);

        playMusic();
        //Utils.longToast(getApplicationContext(), "Đáp án: " + dap_an);
    }

    public void initSetting() {
        ImageView img_setting = findViewById(R.id.img_setting);
        if (Config.isPlayMusic)
            img_setting.setImageResource(R.mipmap.ic_sound);
        else
            img_setting.setImageResource(R.mipmap.ic_sound_off);

        img_setting.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                if (Config.isPlayMusic) {
                    img_setting.setImageResource(R.mipmap.ic_sound_off);
                    Config.isPlayMusic = false;
                    mp.pause();
                } else {
                    img_setting.setImageResource(R.mipmap.ic_sound);
                    Config.isPlayMusic = true;
                    mp.start();
                }
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.img_back).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                time_run = false;
                lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + "dừng cuộc chơi ?");

                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        time_run = true;
                        h.post(wait);
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.stop();

                        if (Config.serial != 1)
                            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + Utils.getMoney(Config.serial) + ".000 Đ");
                        else
                            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + "0 Đ");

                        findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.resetData();
                                finish();
                            }
                        }, 20, getApplicationContext()));

                        findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.resetData();
                                finish();
                            }
                        }, 20, getApplicationContext()));

                        findViewById(R.id.img_no).setVisibility(View.INVISIBLE);
                        new PlayMusic().playThankYou(getApplicationContext());
                    }
                }, 20, getApplicationContext()));
            }
        }, 20, getApplicationContext()));
    }

    public void playMusic() {
        Handler handler = new Handler();
        Utils.PlayMusic(getApplicationContext(), Config.serial);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_a.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(1000)
                        .playOn(findViewById(R.id.btn_a));
                new PlayMusic().playA(getApplicationContext());
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_b.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(1000)
                        .playOn(findViewById(R.id.btn_b));
                new PlayMusic().playB(getApplicationContext());
            }
        }, 4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_c.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(1000)
                        .playOn(findViewById(R.id.btn_c));
                new PlayMusic().playC(getApplicationContext());
            }
        }, 5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_d.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(1000)
                        .playOn(findViewById(R.id.btn_d));
                new PlayMusic().playD(getApplicationContext());
            }
        }, 6000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                deadLock = false;
                h.post(wait);

                if (Config.isPlayMusic)
                    mp.start();
            }
        }, 7000);
    }

    // initHelp
    public void initHelp() {
        img_50.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                if (Config.help_1) {
                    time_run = false;
                    lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + " sử dụng sự trợ giúp 50/50 ?");

                    findViewById(R.id.play).setVisibility(View.GONE);
                    findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                    findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            img_50.setImageResource(R.mipmap.ic_50_off);
                            Config.help_1 = false;
                            new PlayMusic().playHelp50(getApplicationContext());
                            deadLock = true;

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    int ran[] = new int[2];
                                    int exist[] = new int[2];
                                    exist[0] = dapan;
                                    exist[1] = -1;

                                    for (int i = 0; i < 2; i++) {
                                        ran[i] = Utils.rand(1, 4);
                                        while (ran[i] == exist[0] || ran[i] == exist[1]) {
                                            ran[i] = Utils.rand(1, 4);
                                        }

                                        exist[1] = ran[i];
                                        switch (ran[i]) {
                                            case 1:
                                                lbl_a.setVisibility(View.INVISIBLE);
                                                findViewById(R.id.btn_a).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }, 20, getApplicationContext()));
                                                break;

                                            case 2:
                                                lbl_b.setVisibility(View.INVISIBLE);
                                                findViewById(R.id.btn_b).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }, 20, getApplicationContext()));
                                                break;

                                            case 3:
                                                lbl_c.setVisibility(View.INVISIBLE);
                                                findViewById(R.id.btn_c).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }, 20, getApplicationContext()));
                                                break;

                                            case 4:
                                                lbl_d.setVisibility(View.INVISIBLE);
                                                findViewById(R.id.btn_d).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }, 20, getApplicationContext()));
                                                break;
                                        }
                                    }

                                    new PlayMusic().playPressButton(getApplicationContext());
                                    deadLock = false;
                                    time_run = true;
                                    h.post(wait);
                                }
                            }, 6000);
                        }
                    }, 20, getApplicationContext()));

                    findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            time_run = true;
                            h.post(wait);
                            if (Config.isPlayMusic)
                                mp.start();
                        }
                    }, 20, getApplicationContext()));
                }
            }
        }, 20, getApplicationContext()));

        img_call.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                if (Config.help_2) {
                    time_run = false;
                    lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + "sử dụng sự trợ giúp" + "\n\n" + "từ người thân ?");

                    findViewById(R.id.play).setVisibility(View.GONE);
                    findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                    findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            img_call.setImageResource(R.mipmap.ic_call_off);
                            Config.help_2 = false;
                            new PlayMusic().playHelpCall(getApplicationContext());
                            deadLock = true;

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.help).setVisibility(View.VISIBLE);
                                    findViewById(R.id.call).setVisibility(View.VISIBLE);
                                    findViewById(R.id.ask).setVisibility(View.GONE);

                                    findViewById(R.id.img_help_1).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            findViewById(R.id.img_help_2).setVisibility(View.GONE);
                                            findViewById(R.id.img_help_3).setVisibility(View.GONE);

                                            String dap_an = null;
                                            switch (dapan) {
                                                case 1:
                                                    dap_an = "A";
                                                    break;

                                                case 2:
                                                    dap_an = "B";
                                                    break;

                                                case 3:
                                                    dap_an = "C";
                                                    break;

                                                case 4:
                                                    dap_an = "D";
                                                    break;
                                            }

                                            TextView lbl_answer = findViewById(R.id.lbl_answer);
                                            lbl_answer.setVisibility(View.VISIBLE);
                                            lbl_answer.setText("Tôi nghĩ đáp án là " + dap_an);
                                            YoYo.with(Techniques.SlideInRight)
                                                    .duration(500)
                                                    .playOn(findViewById(R.id.lbl_answer));

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findViewById(R.id.help).setVisibility(View.GONE);
                                                    deadLock = false;
                                                    time_run = true;
                                                    h.post(wait);
                                                }
                                            }, 3000);
                                        }
                                    }, 20, getApplicationContext()));

                                    findViewById(R.id.img_help_2).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            findViewById(R.id.img_help_1).setVisibility(View.GONE);
                                            findViewById(R.id.img_help_3).setVisibility(View.GONE);

                                            String dap_an = null;
                                            switch (dapan) {
                                                case 1:
                                                    dap_an = "A";
                                                    break;

                                                case 2:
                                                    dap_an = "B";
                                                    break;

                                                case 3:
                                                    dap_an = "C";
                                                    break;

                                                case 4:
                                                    dap_an = "D";
                                                    break;
                                            }

                                            TextView lbl_answer = findViewById(R.id.lbl_answer);
                                            lbl_answer.setVisibility(View.VISIBLE);
                                            lbl_answer.setText("Tôi nghĩ đáp án là " + dap_an);
                                            YoYo.with(Techniques.SlideInRight)
                                                    .duration(500)
                                                    .playOn(findViewById(R.id.lbl_answer));

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findViewById(R.id.help).setVisibility(View.GONE);
                                                    deadLock = false;
                                                    time_run = true;
                                                    h.post(wait);
                                                }
                                            }, 3000);
                                        }
                                    }, 20, getApplicationContext()));

                                    findViewById(R.id.img_help_3).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            findViewById(R.id.img_help_1).setVisibility(View.GONE);
                                            findViewById(R.id.img_help_2).setVisibility(View.GONE);

                                            String dap_an = null;
                                            switch (dapan) {
                                                case 1:
                                                    dap_an = "A";
                                                    break;

                                                case 2:
                                                    dap_an = "B";
                                                    break;

                                                case 3:
                                                    dap_an = "C";
                                                    break;

                                                case 4:
                                                    dap_an = "D";
                                                    break;
                                            }

                                            TextView lbl_answer = findViewById(R.id.lbl_answer);
                                            lbl_answer.setVisibility(View.VISIBLE);
                                            lbl_answer.setText("Tôi nghĩ đáp án là " + dap_an);
                                            YoYo.with(Techniques.SlideInRight)
                                                    .duration(500)
                                                    .playOn(findViewById(R.id.lbl_answer));

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findViewById(R.id.help).setVisibility(View.GONE);
                                                    deadLock = false;
                                                    time_run = true;
                                                    h.post(wait);
                                                }
                                            }, 4000);
                                        }
                                    }, 20, getApplicationContext()));

                                    //end
                                }
                            }, 1000);
                        }
                    }, 20, getApplicationContext()));

                    findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            time_run = true;
                            h.post(wait);
                            if (Config.isPlayMusic)
                                mp.start();
                        }
                    }, 20, getApplicationContext()));
                }
            }
        }, 20, getApplicationContext()));

        img_audience_help.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                if (Config.help_3) {
                    time_run = false;
                    lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + "sử dụng sự trợ giúp" + "\n\n" + " từ khán giả ?");

                    findViewById(R.id.play).setVisibility(View.GONE);
                    findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                    findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            img_audience_help.setImageResource(R.mipmap.ic_audience_help_off);
                            Config.help_3 = false;
                            new PlayMusic().playHelpAsk(getApplicationContext());
                            deadLock = true;

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    View column_1 = findViewById(R.id.column_1);
                                    View column_2 = findViewById(R.id.column_2);
                                    View column_3 = findViewById(R.id.column_3);
                                    View column_4 = findViewById(R.id.column_4);

                                    int percent_1 = Utils.rand(70, 100);
                                    int percent_2 = Utils.rand(0, 100 - percent_1);
                                    int percent_3 = Utils.rand(0, 100 - percent_1 - percent_2);
                                    int percent_4 = 100 - percent_1 - percent_2 - percent_3;

                                    int height_1 = percent_1 * 170 / 100;
                                    int height_2 = percent_2 * 170 / 100;
                                    int height_3 = percent_3 * 170 / 100;
                                    int height_4 = percent_4 * 170 / 100;

                                    Resources r = getResources();
                                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, r.getDisplayMetrics());

                                    height_1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_1, r.getDisplayMetrics());
                                    height_2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_2, r.getDisplayMetrics());
                                    height_3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_3, r.getDisplayMetrics());
                                    height_4 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_4, r.getDisplayMetrics());

                                    TextView lbl_answer_a = findViewById(R.id.lbl_answer_a);
                                    TextView lbl_answer_b = findViewById(R.id.lbl_answer_b);
                                    TextView lbl_answer_c = findViewById(R.id.lbl_answer_c);
                                    TextView lbl_answer_d = findViewById(R.id.lbl_answer_d);

                                    switch (dapan) {
                                        case 1:
                                            lbl_answer_a.setText(percent_1 + " %");
                                            lbl_answer_b.setText(percent_2 + " %");
                                            lbl_answer_c.setText(percent_3 + " %");
                                            lbl_answer_d.setText(percent_4 + " %");

                                            column_1.setLayoutParams(new LinearLayout.LayoutParams(width, height_1));
                                            column_2.setLayoutParams(new LinearLayout.LayoutParams(width, height_2));
                                            column_3.setLayoutParams(new LinearLayout.LayoutParams(width, height_3));
                                            column_4.setLayoutParams(new LinearLayout.LayoutParams(width, height_4));
                                            break;

                                        case 2:
                                            lbl_answer_b.setText(percent_1 + " %");
                                            lbl_answer_a.setText(percent_2 + " %");
                                            lbl_answer_c.setText(percent_3 + " %");
                                            lbl_answer_d.setText(percent_4 + " %");

                                            column_2.setLayoutParams(new LinearLayout.LayoutParams(width, height_1));
                                            column_1.setLayoutParams(new LinearLayout.LayoutParams(width, height_2));
                                            column_3.setLayoutParams(new LinearLayout.LayoutParams(width, height_3));
                                            column_4.setLayoutParams(new LinearLayout.LayoutParams(width, height_4));
                                            break;

                                        case 3:
                                            lbl_answer_c.setText(percent_1 + " %");
                                            lbl_answer_a.setText(percent_2 + " %");
                                            lbl_answer_b.setText(percent_3 + " %");
                                            lbl_answer_d.setText(percent_4 + " %");

                                            column_3.setLayoutParams(new LinearLayout.LayoutParams(width, height_1));
                                            column_1.setLayoutParams(new LinearLayout.LayoutParams(width, height_2));
                                            column_2.setLayoutParams(new LinearLayout.LayoutParams(width, height_3));
                                            column_4.setLayoutParams(new LinearLayout.LayoutParams(width, height_4));
                                            break;

                                        case 4:
                                            lbl_answer_d.setText(percent_1 + " %");
                                            lbl_answer_a.setText(percent_2 + " %");
                                            lbl_answer_b.setText(percent_3 + " %");
                                            lbl_answer_c.setText(percent_4 + " %");

                                            column_4.setLayoutParams(new LinearLayout.LayoutParams(width, height_1));
                                            column_1.setLayoutParams(new LinearLayout.LayoutParams(width, height_2));
                                            column_2.setLayoutParams(new LinearLayout.LayoutParams(width, height_3));
                                            column_3.setLayoutParams(new LinearLayout.LayoutParams(width, height_4));
                                            break;
                                    }

                                    findViewById(R.id.help).setVisibility(View.VISIBLE);
                                    findViewById(R.id.call).setVisibility(View.GONE);
                                    findViewById(R.id.ask).setVisibility(View.VISIBLE);

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            findViewById(R.id.help).setVisibility(View.GONE);
                                            deadLock = false;
                                            time_run = true;
                                            h.post(wait);
                                        }
                                    }, 5000);
                                }
                            }, 5000);
                        }
                    }, 20, getApplicationContext()));

                    findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            time_run = true;
                            h.post(wait);
                            if (Config.isPlayMusic)
                                mp.start();
                        }
                    }, 20, getApplicationContext()));
                }
            }
        }, 20, getApplicationContext()));

        img_switch.setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                if (Config.help_4) {
                    time_run = false;
                    lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + "chuyển câu hỏi ?");

                    findViewById(R.id.play).setVisibility(View.GONE);
                    findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                    findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            Config.help_4 = false;
                            mp.stop();
                            finish();
                            startActivity(new Intent(PlayActivity.this, PlayActivity.class));
                        }
                    }, 20, getApplicationContext()));

                    findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            findViewById(R.id.play).setVisibility(View.VISIBLE);
                            findViewById(R.id.confirm).setVisibility(View.GONE);

                            time_run = true;
                            h.post(wait);
                            if (Config.isPlayMusic)
                                mp.start();
                        }
                    }, 20, getApplicationContext()));
                }
            }
        }, 20, getApplicationContext()));
    }

    // initAnswer
    public void initAnswer() {
        findViewById(R.id.btn_a).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                mp.pause();
                time_run = false;
                lbl_confirm.setText("Đáp án cuối cùng của bạn là:" + "\n\n" + "A");

                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);
                new PlayMusic().playChoooseA(getApplicationContext());

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        btn_a.setBackgroundResource(R.mipmap.btn_answer_yellow);
                        new PlayMusic().playWaitAnswer(getApplicationContext());
                        deadLock = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dapan == 1) {
                                    new PlayMusic().playTrue_1(getApplicationContext());
                                    startTrue(btn_a);
                                    next();
                                } else {
                                    playAnswer();
                                }
                            }
                        }, 5000);
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        time_run = true;
                        h.post(wait);
                        if (Config.isPlayMusic)
                            mp.start();
                    }
                }, 20, getApplicationContext()));
                //end
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_b).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                time_run = false;
                mp.pause();
                lbl_confirm.setText("Đáp án cuối cùng của bạn là:" + "\n\n" + "B");

                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);
                new PlayMusic().playChoooseB(getApplicationContext());

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        btn_b.setBackgroundResource(R.mipmap.btn_answer_yellow);
                        new PlayMusic().playWaitAnswer(getApplicationContext());
                        deadLock = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dapan == 2) {
                                    new PlayMusic().playTrue_2(getApplicationContext());
                                    startTrue(btn_b);
                                    next();
                                } else {
                                    playAnswer();
                                }
                            }
                        }, 5000);
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        time_run = true;
                        h.post(wait);
                        if (Config.isPlayMusic)
                            mp.start();
                    }
                }, 20, getApplicationContext()));
                //end
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_c).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                time_run = false;
                mp.pause();
                lbl_confirm.setText("Đáp án cuối cùng của bạn là:" + "\n\n" + "C");

                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);
                new PlayMusic().playChoooseC(getApplicationContext());

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        btn_c.setBackgroundResource(R.mipmap.btn_answer_yellow);
                        new PlayMusic().playWaitAnswer(getApplicationContext());
                        deadLock = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dapan == 3) {
                                    new PlayMusic().playTrue_3(getApplicationContext());
                                    startTrue(btn_c);
                                    next();
                                } else {
                                    playAnswer();
                                }
                            }
                        }, 5000);
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        time_run = true;
                        h.post(wait);
                        if (Config.isPlayMusic)
                            mp.start();
                    }
                }, 20, getApplicationContext()));
                //end
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.btn_d).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deadLock)
                    return;

                time_run = false;
                mp.pause();
                lbl_confirm.setText("Đáp án cuối cùng của bạn là:" + "\n\n" + "D");

                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);
                new PlayMusic().playChoooseD(getApplicationContext());

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        btn_d.setBackgroundResource(R.mipmap.btn_answer_yellow);
                        new PlayMusic().playWaitAnswer(getApplicationContext());
                        deadLock = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dapan == 4) {
                                    new PlayMusic().playTrue_4(getApplicationContext());
                                    startTrue(btn_d);
                                    next();
                                } else {
                                    playAnswer();
                                }
                            }
                        }, 5000);
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.play).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirm).setVisibility(View.GONE);

                        time_run = true;
                        h.post(wait);
                        if (Config.isPlayMusic)
                            mp.start();
                    }
                }, 20, getApplicationContext()));
                //end
            }
        }, 20, getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        if (deadLock)
            return;

        time_run = false;
        lbl_confirm.setText("Bạn có chắc chắn muốn" + "\n\n" + "dừng cuộc chơi ?");

        findViewById(R.id.play).setVisibility(View.GONE);
        findViewById(R.id.confirm).setVisibility(View.VISIBLE);

        findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.play).setVisibility(View.VISIBLE);
                findViewById(R.id.confirm).setVisibility(View.GONE);

                time_run = true;
                h.post(wait);
            }
        }, 20, getApplicationContext()));

        findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();

                if (Config.serial != 1)
                    lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + Utils.getMoney(Config.serial) + ".000 Đ");
                else
                    lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + "0 Đ");

                findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.resetData();
                        finish();
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.resetData();
                        finish();
                    }
                }, 20, getApplicationContext()));

                findViewById(R.id.img_no).setVisibility(View.INVISIBLE);
                new PlayMusic().playThankYou(getApplicationContext());
            }
        }, 20, getApplicationContext()));
    }

    int set = 1;
    int index = 0;

    public void startTrue(FrameLayout view) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (set == 1) {
                    view.setBackgroundResource(R.mipmap.btn_answer_green);
                    set = 0;
                } else {
                    view.setBackgroundResource(R.mipmap.btn_answer_yellow);
                    set = 1;
                }

                index++;

                if (index < 11)
                    handler.postDelayed(this, 100);
            }
        });
    }

    public void startWrong(FrameLayout view) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (set == 1) {
                    view.setBackgroundResource(R.mipmap.btn_answer_green);
                    set = 0;
                } else {
                    view.setBackgroundResource(R.mipmap.btn_answer);
                    set = 1;
                }

                index++;
                if (index < 11)
                    handler.postDelayed(this, 100);
            }
        });
    }

    public void playAnswer() {
        failed();

        switch (dapan) {
            case 1:
                new PlayMusic().playWrong_1(getApplicationContext());
                startWrong(btn_a);
                break;

            case 2:
                new PlayMusic().playWrong_2(getApplicationContext());
                startWrong(btn_b);
                break;

            case 3:
                new PlayMusic().playWrong_3(getApplicationContext());
                startWrong(btn_c);
                break;

            case 4:
                new PlayMusic().playWrong_4(getApplicationContext());
                startWrong(btn_d);
                break;
        }
    }

    public void failed() {
        if (Config.serial <= 5)
            Config.serial = 1;
        else if (Config.serial > 5 && Config.serial <= 10)
            Config.serial = 6;
        else
            Config.serial = 11;

        findViewById(R.id.img_no).setVisibility(View.INVISIBLE);
        insertDB();

        if (Config.serial != 1)
            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + Utils.getMoney(Config.serial) + ".000 Đ");
        else
            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + "0 Đ");

        findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.resetData();
                finish();
            }
        }, 20, getApplicationContext()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                new PlayMusic().playThankYou(getApplicationContext());
            }
        }, 2000);
    }

    public void endHere() {
        findViewById(R.id.img_no).setVisibility(View.INVISIBLE);
        insertDB();

        if (Config.serial != 1)
            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + Utils.getMoney(Config.serial) + ".000 Đ");
        else
            lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + "0 Đ");

        findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.resetData();
                finish();
            }
        }, 20, getApplicationContext()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                new PlayMusic().playThankYou(getApplicationContext());
            }
        }, 2000);
    }

    public void next() {
        findViewById(R.id.img_no).setVisibility(View.INVISIBLE);

        Config.serial++;
        lbl_confirm.setText("Tiền thưởng của bạn là:" + "\n\n" + Utils.getMoney(Config.serial) + ".000 Đ");

        findViewById(R.id.img_yes).setOnTouchListener(new OnTouchClickListener(new OnTouchClickListener.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                if (Config.serial < 16)
                    startActivity(new Intent(PlayActivity.this, PlayActivity.class));
                else
                    Utils.resetData();
            }
        }, 20, getApplicationContext()));

        //Utils.showAdPopup();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.play).setVisibility(View.GONE);
                findViewById(R.id.confirm).setVisibility(View.VISIBLE);

                if (Config.serial == 16)
                    new PlayMusic().playCongratulation(getApplicationContext());
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        time_run = false;

        if (mp.isPlaying())
            mp.pause();
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

        if (deadLock)
            return;

        if (time_left > 0) {
            time_run = true;
            h.post(wait);

            if (Config.isPlayMusic)
                mp.start();
        }
    }

    public void insertDB() {
        HighscoreHelper helper = new HighscoreHelper(this);
        Config.time += (30 - time_left);
        int reward = 0;

        switch (Config.serial) {
            case 1:
                reward = 0;
                break;

            case 2:
                reward = 200;
                break;

            case 3:
                reward = 400;
                break;

            case 4:
                reward = 600;
                break;

            case 5:
                reward = 1000;
                break;

            case 6:
                reward = 2000;
                break;

            case 7:
                reward = 3000;
                break;

            case 8:
                reward = 6000;
                break;

            case 9:
                reward = 10000;
                break;

            case 10:
                reward = 14000;
                break;

            case 11:
                reward = 22000;
                break;

            case 12:
                reward = 30000;
                break;

            case 13:
                reward = 40000;
                break;

            case 14:
                reward = 60000;
                break;

            case 15:
                reward = 85000;
                break;

            default:
                reward = 150000;
                break;
        }

        helper.insert(reward, Config.time);
    }
}
