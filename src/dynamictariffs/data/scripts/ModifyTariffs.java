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
        super(false);
        Global.getSector().addTransientListener(this);
    }

    /*  
    *   This method is called anytime you open a market
    *   and modifies the tariff according to your rep
    *   ONLY if it is a part of the whitelist
    */
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        log.info("DynamicTariffs: Player opened Market");
        if(SettingsUtil.isWhitelisted(market)){
            modifyTariff(market);
        } else {
            log.info("DynamicTariffs: Market is not on whitelist");
        }
    }
    
    /*
    *   This is where all the heavy lifting happens
    */
    public static void modifyTariff(MarketAPI market){
        float flat = 0.0f;
        int[] percents = SettingsUtil.getPercents();
        RepLevel rep = market.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
        
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
        
        market.getTariff().modifyFlat("dynamictariffs", flat);
        
        log.info("DynamicTariffs: " + rep + " " + market.getTariff().getModifiedValue());
        // For Reference, this is how the modified value is recomputed
        // modified = base + base * percentMod / 100f + flatMod
    }
    
    /*
    * This takes a number like 40, subtracts the vanilla tariff
    * of 30 to get the offset, then turns it into something like 0.1f
    */
    public static float getOffset(float percent) {
        return (percent - 30) / 100f;
    }
    
    /*
    *   This makes sure all tariff changes made by this mod
    *   are removed when you leave a market.
    */
    @Override
    public void reportPlayerClosedMarket(MarketAPI market){
        log.info("DynamicTariffs: Player Closed Market");
        if(SettingsUtil.isWhitelisted(market)){
            market.getTariff().unmodify("dynamictariffs");
        }
    }
    
}
