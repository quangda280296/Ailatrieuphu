package ailatrieuphu.trieuphu2019.utils;

import android.os.AsyncTask;
import android.util.Log;

import ailatrieuphu.trieuphu2019.Config;
import ailatrieuphu.trieuphu2019.Interface.ITimeReturn;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manh Dang on 01/08/2018.
 */

public class GetTimePopup extends AsyncTask {

    private String code;
    private String deviceID;
    private String time_install;
    private ITimeReturn listenner;

    private String URL;
    private String json;

    public GetTimePopup(String code, String deviceID, String time_install, ITimeReturn listenner) {
        this.code = code;
        this.deviceID = deviceID;
        this.time_install = time_install;
        this.listenner = listenner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        URL = Config.popup_API_URL + "code=" + code
                + "&deviceID=" + deviceID
                + "&time_install=" + time_install;
        Log.d("bbbbbbbb", URL);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        HttpHandler jsonParser = new HttpHandler();
        json = jsonParser.callService(URL, HttpHandler.GET);

        Log.d("aaaaaaaaaa", URL);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (json != null) {
            try {
                Log.d("testtttttt", "okkkkkk");
                JSONObject jsonObject = new JSONObject(json);

                if (jsonObject != null) {
                    Log.d("testtttttt", "okkkkkk");

                    JSONObject admob = (JSONObject) jsonObject.get("admob");
                    JSONObject config = (JSONObject) jsonObject.get("config");
                    int show_ads = jsonObject.getInt("show_ads");

                    if (config != null && admob != null) {
                        listenner.onTimeReturn(admob.getString("banner"), admob.getString("popup"), admob.getString("video"),
                                config.getInt("time_start_show_popup"), config.getInt("offset_time_show_popup"), show_ads);

                        Log.d("testtttttt", "okkkkkk");
                    }
                }

            } catch (JSONException e) {
                Log.d("ppppppp", "error1242131");
                e.printStackTrace();
            }
        }
    }
}
