package dynamictariffs.data;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.BaseModPlugin;
import org.apache.log4j.Logger;
import dynamictariffs.data.scripts.ModifyTariffs;

public class DynamicTariffsPlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(DynamicTariffsPlugin.class);
    
    @Override
    public void onGameLoad(boolean newGame){
        log.info("DynamicTariffs: Dynamic Tariffs loaded!");
        new ModifyTariffs();
    }
    
}
