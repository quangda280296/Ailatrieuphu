package com.quangda280296.ailatrieuphu.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.quangda280296.ailatrieuphu.Config;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by keban on 3/6/2018.
 */

public class Utils {
    public static void shortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void shortSnackbar(Activity activity, String text) {
        Snackbar.make(activity.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    public static void longSnackbar(Activity activity, String text) {
        Snackbar.make(activity.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show();
    }

    public static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void resetData() {
        Config.serial = 1;
        Config.time = 0;

        Config.help_1 = true;
        Config.help_2 = true;
        Config.help_3 = true;
        Config.help_4 = true;
    }

    public static String getMoney(int serial) {
        switch (serial) {
            case 1:
                return "0";

            case 2:
                return "200";

            case 3:
                return "400";

            case 4:
                return "600";

            case 5:
                return "1.000";

            case 6:
                return "2.000";

            case 7:
                return "3.000";

            case 8:
                return "6.000";

            case 9:
                return "10.000";

            case 10:
                return "14.000";

            case 11:
                return "22.000";

            case 12:
                return "30.000";

            case 13:
                return "40.000";

            case 14:
                return "60.000";

            case 15:
                return "85.000";

            default:
                return "150.000";
        }
    }

    public static void PlayMusic(Context context, int serial) {
        switch (serial) {
            case 1:
                new PlayMusic().playQuestion_1(context);
                break;

            case 2:
                new PlayMusic().playQuestion_2(context);
                break;

            case 3:
                new PlayMusic().playQuestion_3(context);
                break;

            case 4:
                new PlayMusic().playQuestion_4(context);
                break;

            case 5:
                new PlayMusic().playQuestion_5(context);
                break;

            case 6:
                new PlayMusic().playQuestion_6(context);
                break;

            case 7:
                new PlayMusic().playQuestion_7(context);
                break;

            case 8:
                new PlayMusic().playQuestion_8(context);
                break;

            case 9:
                new PlayMusic().playQuestion_9(context);
                break;

            case 10:
                new PlayMusic().playQuestion_10(context);
                break;

            case 11:
                new PlayMusic().playQuestion_11(context);
                break;

            case 12:
                new PlayMusic().playQuestion_12(context);
                break;

            case 13:
                new PlayMusic().playQuestion_13(context);
                break;

            case 14:
                new PlayMusic().playQuestion_14(context);
                break;

            case 15:
                new PlayMusic().playQuestion_15(context);
                break;
        }
    }

    public static void showAdPopup() {
        Calendar calendar = Calendar.getInstance();
        int end = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);

        Log.d("tttttt", "time_start=" + (end - Config.time_start) + " / time_offset=" + (end - Config.time_end));

        if (end - Config.time_start >= Config.time_start_show_popup)
            if (end - Config.time_end >= Config.offset_time_show_popup) {
                calendar = Calendar.getInstance();
                Config.time_end = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                        + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);

                Config.iad.show();

                // Load ads into Interstitial Ads
                AdRequest adRequest = new AdRequest.Builder().build();
                Config.iad.loadAd(adRequest);
            }
    }

    public static void showAd(Context context, RelativeLayout layout, final FrameLayout layout_ads) {
        AdView banner = new AdView(context);
        banner.setAdSize(AdSize.SMART_BANNER);
        banner.setAdUnitId(Config.banner_ads);

        layout.addView(banner);

        // load advertisement
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        banner.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                layout_ads.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //banner.loadAd(adRequest);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                layout_ads.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                layout_ads.setVisibility(View.VISIBLE);
                Log.d("uuuuuuuuuuuuu", "Okkkkkkkkkk");
            }
        });
    }

    // handleInterstitialAd
    public static void handleInterstitialAd(Context context) {
        // load ad
        Config.iad = new InterstitialAd(context);
        // set the ad unit ID
        Config.iad.setAdUnitId(Config.popup_ads);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
        Config.iad.loadAd(adRequest);
        Config.iad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //Config.iad.loadAd(adRequest);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

        });
    }
}
