package ailatrieuphu.trieuphu2019.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import ailatrieuphu.trieuphu2019.Config;
import ailatrieuphu.trieuphu2019.Interface.ITimeReturn;
import ailatrieuphu.trieuphu2019.utils.GetTimePopup;
import ailatrieuphu.trieuphu2019.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manh Dang on 01/08/2018.
 */

public class ShowAdService extends Service implements ITimeReturn {

    private String idDevice;
    private String installTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("lllllllllll", "OK");

        idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        PackageManager pm = getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Log.d("packagename", getPackageName());

            installTime = dateFormat.format(new Date(packageInfo.firstInstallTime));
            installTime = installTime.replaceAll("\\s", "");
            new GetTimePopup(Config.codeAccessAPI, idDevice, installTime, ShowAdService.this).execute();

        } catch (PackageManager.NameNotFoundException e) {
            Log.d("rrrrrrrrrrrrrrr", "Error service");
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTimeReturn(String key_banner_ads, String key_popup_ads, String key_video_ads, int time_start_show_popup, int offset_time_show_popup, int show_ads) {
        Log.d("adsssssssssssss", show_ads + "");

        if (!key_banner_ads.equals(null))
            Config.banner_ads = key_banner_ads;

        if (!key_popup_ads.equals(null)) {
            Config.popup_ads = key_popup_ads;

            Config.iad = new InterstitialAd(getApplicationContext());
            // set the ad unit ID
            Config.iad.setAdUnitId(Config.popup_ads);
            AdRequest adRequest = new AdRequest.Builder().build();
            // Load ads into Interstitial Ads
            Config.iad.loadAd(adRequest);
        }

        Config.time_start_show_popup = time_start_show_popup;
        Config.offset_time_show_popup = offset_time_show_popup;

        Log.d("ssssssssss", "start=" + Config.time_start_show_popup + " offset=" + Config.offset_time_show_popup);

        stopService(new Intent(Config.showAdService));
    }
}
