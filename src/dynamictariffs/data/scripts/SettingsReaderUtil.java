package dynamictariffs.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.SettingsAPI;
import org.apache.log4j.Logger;
import java.io.IOException;
import org.json.JSONException;


public class SettingsReaderUtil {
    
    public static SettingsAPI settings = Global.getSettings();
    public static Logger log = Global.getLogger(SettingsReaderUtil.class);
    
    public static int[] readSettings(){
        int[] ints = new int[6];
        try {
            String txt = settings.loadText("percents.txt", "dynamictariffs").replaceAll("\n", "").replaceAll("\r", "");
            if(txt != null){
                String[] values = txt.split(",");
                for(int i = 0; i < values.length ; i++){
                    ints[i] = Integer.parseInt(values[i]);
                }
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        } catch (JSONException j) {
            log.info(j.getMessage());
        }
        return ints;
    }
    
}
