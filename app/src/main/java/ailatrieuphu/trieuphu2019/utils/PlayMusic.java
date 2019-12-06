package ailatrieuphu.trieuphu2019.utils;

import android.content.Context;
import android.media.MediaPlayer;

import ailatrieuphu.trieuphu2019.R;

/**
 * Created by keban on 3/6/2018.
 */

public class PlayMusic {

    public static MediaPlayer mp;

    public void change() {
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    public void playPressButton(Context context) {
        mp = MediaPlayer.create(context, R.raw.press_button);
        change();


        mp.start();
    }

    public void playRule(Context context) {
        mp = MediaPlayer.create(context, R.raw.rule);
        change();


        mp.start();
    }

    public void playCongratulation(Context context) {
        mp = MediaPlayer.create(context, R.raw.congratulation);
        change();


        mp.start();
    }

    public void playThankYou(Context context) {
        mp = MediaPlayer.create(context, R.raw.thank_you);
        change();


        mp.start();
    }

    public void playTimesUp(Context context) {
        mp = MediaPlayer.create(context, R.raw.timesup);
        change();


        mp.start();
    }

    public void playWaitAnswer(Context context) {
        mp = MediaPlayer.create(context, R.raw.waitanswer);
        change();


        mp.start();
    }

    public void playHelp50(Context context) {
        mp = MediaPlayer.create(context, R.raw.help50);
        change();


        mp.start();
    }

    public void playHelpAsk(Context context) {
        mp = MediaPlayer.create(context, R.raw.help_ask);
        change();


        mp.start();
    }

    public void playHelpCall(Context context) {
        mp = MediaPlayer.create(context, R.raw.help_call);
        change();


        mp.start();
    }

    public void playWrong_1(Context context) {
        mp = MediaPlayer.create(context, R.raw.wrong1);
        change();


        mp.start();
    }

    public void playWrong_2(Context context) {
        mp = MediaPlayer.create(context, R.raw.wrong2);
        change();


        mp.start();
    }

    public void playWrong_3(Context context) {
        mp = MediaPlayer.create(context, R.raw.wrong3);
        change();


        mp.start();
    }

    public void playWrong_4(Context context) {
        mp = MediaPlayer.create(context, R.raw.wrong4);
        change();


        mp.start();
    }

    public void playTrue_1(Context context) {
        mp = MediaPlayer.create(context, R.raw.true1);
        change();


        mp.start();
    }

    public void playTrue_2(Context context) {
        mp = MediaPlayer.create(context, R.raw.true2);
        change();


        mp.start();
    }

    public void playTrue_3(Context context) {
        mp = MediaPlayer.create(context, R.raw.true3);
        change();


        mp.start();
    }

    public void playTrue_4(Context context) {
        mp = MediaPlayer.create(context, R.raw.true4);
        change();


        mp.start();
    }

    public void playA(Context context) {
        mp = MediaPlayer.create(context, R.raw.a);
        change();


        mp.start();
    }

    public void playB(Context context) {
        mp = MediaPlayer.create(context, R.raw.b);
        change();


        mp.start();
    }

    public void playC(Context context) {
        mp = MediaPlayer.create(context, R.raw.c);
        change();


        mp.start();
    }

    public void playD(Context context) {
        mp = MediaPlayer.create(context, R.raw.d);
        change();


        mp.start();
    }

    public void playChoooseA(Context context) {
        mp = MediaPlayer.create(context, R.raw.choose1);
        change();


        mp.start();
    }

    public void playChoooseB(Context context) {
        mp = MediaPlayer.create(context, R.raw.choose2);
        change();


        mp.start();
    }

    public void playChoooseC(Context context) {
        mp = MediaPlayer.create(context, R.raw.choose3);
        change();


        mp.start();
    }

    public void playChoooseD(Context context) {
        mp = MediaPlayer.create(context, R.raw.choose4);
        change();


        mp.start();
    }

    public void playQuestion_1(Context context) {
        mp = MediaPlayer.create(context, R.raw.question1);
        change();


        mp.start();
    }

    public void playQuestion_2(Context context) {
        mp = MediaPlayer.create(context, R.raw.question2);
        change();


        mp.start();
    }

    public void playQuestion_3(Context context) {
        mp = MediaPlayer.create(context, R.raw.question3);
        change();


        mp.start();
    }

    public void playQuestion_4(Context context) {
        mp = MediaPlayer.create(context, R.raw.question4);
        change();


        mp.start();
    }

    public void playQuestion_5(Context context) {
        mp = MediaPlayer.create(context, R.raw.question5);
        change();


        mp.start();
    }

    public void playQuestion_6(Context context) {
        mp = MediaPlayer.create(context, R.raw.question6);
        change();


        mp.start();
    }

    public void playQuestion_7(Context context) {
        mp = MediaPlayer.create(context, R.raw.question7);
        change();


        mp.start();
    }

    public void playQuestion_8(Context context) {
        mp = MediaPlayer.create(context, R.raw.question8);
        change();


        mp.start();
    }

    public void playQuestion_9(Context context) {
        mp = MediaPlayer.create(context, R.raw.question9);
        change();


        mp.start();
    }

    public void playQuestion_10(Context context) {
        mp = MediaPlayer.create(context, R.raw.question10);
        change();


        mp.start();
    }

    public void playQuestion_11(Context context) {
        mp = MediaPlayer.create(context, R.raw.question11);
        change();


        mp.start();
    }

    public void playQuestion_12(Context context) {
        mp = MediaPlayer.create(context, R.raw.question12);
        change();


        mp.start();
    }

    public void playQuestion_13(Context context) {
        mp = MediaPlayer.create(context, R.raw.question13);
        change();


        mp.start();
    }

    public void playQuestion_14(Context context) {
        mp = MediaPlayer.create(context, R.raw.question14);
        change();


        mp.start();
    }

    public void playQuestion_15(Context context) {
        mp = MediaPlayer.create(context, R.raw.question15);
        change();


        mp.start();
    }
}
