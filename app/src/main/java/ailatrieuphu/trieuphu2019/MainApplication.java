package ailatrieuphu.trieuphu2019;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

//import com.noname.quangcaoads.QuangCaoSetup;

/**
 * Created by keban on 3/6/2018.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        /*QuangCaoSetup.setConfig(getApplicationContext(), Config.codeAccessAPI, "1.0");
        QuangCaoSetup.setLinkServer(getApplicationContext(), Config.link_server);*/

        // Handle adService
        /*Calendar calendar = Calendar.getInstance();
        Config.time_start = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        Config.time_end = Config.time_start;

        Utils.handleInterstitialAd(getApplicationContext());

        Config.showAdService = new Intent(this, ShowAdService.class);
        startService(Config.showAdService);*/
    }
}