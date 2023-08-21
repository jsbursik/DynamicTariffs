package dynamictariffs.util;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;


public class SettingsUtil {
    
    public static SettingsAPI settings = Global.getSettings();
    public static Logger log = Global.getLogger(SettingsUtil.class);

    public static Float[] percents = new Float[6];
    public static ArrayList<String> whitelist = new ArrayList<String>();
    public static Boolean useGranular = false;
    public static Boolean commission = false; // States whether you want Tariffs to also be modified by commission
    public static Float commDiscount = 0f; // This will be how much the tariff should change based on commission
    public static Boolean useWhitelist = true;

    /**
     * This reads in the settings.json file
     */
    public static void readSettings(){
        whitelist.clear();
        try {
            JSONObject modSettings = settings.loadJSON("settings.json", "dynamictariffs");

            JSONArray jsonPercents = modSettings.getJSONArray("dt_percents");
            for(int i = 0; i < jsonPercents.length(); i++){
                percents[i] = jsonPercents.getInt(i) / 100f;
            }

            useWhitelist = modSettings.getBoolean("dt_useWhitelist");
            if(useWhitelist){
                JSONArray jsonWhitelist = modSettings.getJSONArray("dt_whitelist");
                for(int i = 0; i < jsonWhitelist.length(); i++){
                    whitelist.add(jsonWhitelist.getString(i));
                }
            }

            useGranular = modSettings.getBoolean("dt_granular");
            commission = modSettings.getBoolean("dt_commission");
            commDiscount = modSettings.getInt("dt_commDiscount") / 100f;
            useWhitelist = modSettings.getBoolean("dt_useWhitelist");
        } catch (IOException | JSONException e) {
            log.info(e.getMessage());
        }
    }


    
}
