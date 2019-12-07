package com.quangda280296.ailatrieuphu;

import android.content.Intent;

import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by keban on 4/25/2018.
 */

public class Config {
    public static int serial = 1;
    public static boolean isPlayMusic = true;
    public static int time = 0;

    public static boolean help_1 = true;
    public static boolean help_2 = true;
    public static boolean help_3 = true;
    public static boolean help_4 = true;

    public static String banner_ads = "/112517806/123661526286027";
    public static String popup_ads = "/112517806/323661526286027";

    public static String popup_API_URL = "http://gamemobileglobal.com/api/control-altp.php?";
    public static String codeAccessAPI = "40209";

    public static Intent showAdService;
    public static InterstitialAd iad;

    public static int time_start = 0;
    public static int time_end = 0;
    public static int time_start_show_popup = 0;
    public static int offset_time_show_popup = 0;

    public static String link_server = "http://gamemobileglobal.com/api/control_s.php";
}
