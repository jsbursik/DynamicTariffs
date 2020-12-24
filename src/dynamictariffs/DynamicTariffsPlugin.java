package dynamictariffs;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.BaseModPlugin;
import org.apache.log4j.Logger;
import dynamictariffs.util.TariffUtil;
import dynamictariffs.util.SettingsUtil;
import dynamictariffs.util.EconUtil;

public class DynamicTariffsPlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(DynamicTariffsPlugin.class);
    /*
    *   When you load in, it reads in the settings.json and modifies all
    *   tariffs of every market contained within the settings.json whitelist.
    *   It also establishes a listener by creating an instance of TariffUtil.
    */
    @Override
    public void onGameLoad(boolean newGame) {
        new TariffUtil();
        SettingsUtil.readSettings();
        for(String market : SettingsUtil.whitelist){
            if(EconUtil.marketExists(market)){
                TariffUtil.modifyTariff(EconUtil.getMarket(market)); 
            }
        }
        log.info("DynamicTariffs: Loaded, Tariffs modified");
    }
    /*
    *   This makes sure that before you save, none of the markets are
    *   modified.
    */
    @Override
    public void beforeGameSave() {
        for(String market : SettingsUtil.whitelist){
            if(EconUtil.marketExists(market)){
                EconUtil.unmodTariffs(market);
            }
        }
        log.info("DynamicTariffs: Unmodified all Tariffs");
    }
    /*
    *   This will make sure that if you're just quicksaving, the
    *   tariff mods come back.
    */
    @Override
    public void afterGameSave() {
        for(String market : SettingsUtil.whitelist){
            if(EconUtil.marketExists(market)){
                TariffUtil.modifyTariff(EconUtil.getMarket(market));
            } 
        }
        log.info("DynamicTariffs: Remodified Tariffs");
    }
}
