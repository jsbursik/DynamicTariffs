package dynamictariffs.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.campaign.RepLevel;
import org.apache.log4j.Logger;

public class ModifyTariffs extends BaseCampaignEventListener {
    
    public static Logger log = Global.getLogger(ModifyTariffs.class);
    
    public ModifyTariffs() {
        super(true);
    }

    /*  
    *   This will go off anytime you click on a planet/station
    *   Markets have a MutableStat for their tariff (getTariff())
    *   it has a base value (float) and modified value (float), 
    *   the base is 0.0 and the modified value at gen will be 0.3f (30% tariff).
    *
    *   This takes your RepLevel with the Faction of the market you just opened,
    *   and then modifies the Tariff based on that RepLevel. 
    *
    *   Here is the list from hostile to friendly:
    *   40%, 30%, 20%, 15%, 10%, 5%, 0%
    */
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        int[] percents = SettingsReaderUtil.readSettings();
        log.info("DynamicTariffs: Player opened Market");
        RepLevel rep = market.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
        float flat = 0.0f;
        switch(rep)
        {
            case SUSPICIOUS:
                flat = getOffset(percents[0]);
                break;
            case NEUTRAL:
                flat = getOffset(percents[1]);
                break;
            case FAVORABLE:
                flat = getOffset(percents[2]);
                break;
            case WELCOMING:
                flat = getOffset(percents[3]);
                break;
            case FRIENDLY:
                flat = getOffset(percents[4]);
                break;
            case COOPERATIVE:
                flat = getOffset(percents[5]);
                break;
        }
        
        market.getTariff().modifyFlat("dynamictariffs", flat); // flat gets ADDED to the Modified Value (0.3f)
        log.info("DynamicTariffs: " + rep + " " + market.getTariff().getModifiedValue());
        
        // For Reference, this is how the modified value is recomputed
        // modified = base + base * percentMod / 100f + flatMod
    }
    
    /*
    *   This takes the percentage (40 = 40%) and 
    *   subtracts the normal tariff (30%) then divides by 100
    *   to yield the offset from the base tariff (40-30)/100f=0.1f
    */
    public float getOffset(float percent) {
        return (percent - 30) / 100f;
    }
    
    /*
    *   This makes sure that when you leave a market, it goes back to normal.
    *   If you don't want this mod anymore, all the modifications
    *   will be removed, so this mod can be installed/removed
    *   at any time.
    */
    @Override
    public void reportPlayerClosedMarket(MarketAPI market){
        log.info("DynamicTariffs: Player Closed Market");
        market.getTariff().unmodify("dynamictariffs");
    }
    
}
