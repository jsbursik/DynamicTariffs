package dynamictariffs.data.scripts;

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
    
    public static int[] percents = new int[6];
    public static ArrayList<String> whitelist = new ArrayList<String>();
    
    
    /*
    *   This reads in the settings.json and populates the percents and whitelist arrays
    */
    public static void readSettings(){
        try {
            JSONObject modSettings = settings.loadJSON("settings.json", "dynamictariffs");
            JSONArray jsonPercents = modSettings.getJSONArray("dt_percents");
            JSONArray jsonWhitelist = modSettings.getJSONArray("dt_whitelist");
            for(int i = 0; i < jsonPercents.length(); i++){
                percents[i] = jsonPercents.getInt(i);
            }
            for(int i = 0; i < jsonWhitelist.length(); i++){
                whitelist.add(jsonWhitelist.getString(i));
            }
        } catch (IOException | JSONException e) {
            log.info(e.getMessage());
        }
    }
    
    /*
    *   Checks for whitelist of the market being opened
    */
    public static boolean isWhitelisted(MarketAPI market){
        readSettings(); // Called here because ModifyTariffs.class checks whitelist FIRST
        boolean result = false;
        if(whitelist.contains(market.getId())){
            result = true;
        }
        return result;
    }
    
    /*
    *   Returns the percents array after its been read in
    */
    public static int[] getPercents(){
        return percents;
    }
    
}
