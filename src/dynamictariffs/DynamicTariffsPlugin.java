package dynamictariffs;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.BaseModPlugin;
import org.apache.log4j.Logger;
import dynamictariffs.util.TariffUtil;
import dynamictariffs.util.SettingsUtil;

public class DynamicTariffsPlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(DynamicTariffsPlugin.class);

    
    /** 
     * This reads in the settings in the settings.json
     * as well as creates a TariffUtil to start listening
     * for certain events.
     * @param newGame
     */
    @Override
    public void onGameLoad(boolean newGame) {
        SettingsUtil.readSettings();
        new TariffUtil();
        log.info("DynamicTariffs: Loaded");
    }
}
