package dynamictariffs.util;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import org.apache.log4j.Logger;

public class TariffUtil extends BaseCampaignEventListener {
    
    public static Logger log = Global.getLogger(TariffUtil.class);

    public TariffUtil() {
        super(false);
        Global.getSector().addTransientListener(this);
    }
    /*  
    *   This method just logs your current Rep with the faction
    *   and shows the current modified tariff, mostly for dev
    *   purposes.
    */
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {
        RepLevel rep = EconUtil.getRepLevel(market);
        log.info("DynamicTariffs: " + rep + " " + market.getTariff().getModifiedValue());
    }
    /*
    *   This modifies the Tariff of a given Market based on Rep
    */
    public static void modifyTariff(MarketAPI market){
        float flat = 0.0f;
        int[] percents = SettingsUtil.getPercents();
        RepLevel rep = EconUtil.getRepLevel(market);
        switch(rep)
        {
            case SUSPICIOUS:
                flat = getOffset(percents[0], market);
                break;
            case NEUTRAL:
                flat = getOffset(percents[1], market);
                break;
            case FAVORABLE:
                flat = getOffset(percents[2], market);
                break;
            case WELCOMING:
                flat = getOffset(percents[3], market);
                break;
            case FRIENDLY:
                flat = getOffset(percents[4], market);
                break;
            case COOPERATIVE:
                flat = getOffset(percents[5], market);
                break;
            default:
                // This is to stop it warning me about the untradeable rep levels
        }
        
        market.getTariff().modifyFlat("dynamictariffs", flat);
    }
    /*
    *   This takes a number like 40, subtracts the vanilla tariff
    *   of 30 to get the offset, then turns it into something like 0.1f
    *   If commissioned it will subtract the commission percent as well
    */
    public static float getOffset(int percent, MarketAPI market) {
        if(SettingsUtil.commission && EconUtil.isCommissioned(market)){
            int result = percent - 30 - SettingsUtil.commModifier;
            if(result < -30){
                return -30 / 100f;
            }
            return result / 100f;
        }
        return (percent - 30) / 100f;
    }
    /*
    *   This makes sure that if your Rep changes on the fly, it get's updated
    */
    @Override
    public void reportPlayerReputationChange(String faction, float delta) {
        for(String m : SettingsUtil.whitelist){
            MarketAPI market = EconUtil.getMarket(m);
            if(EconUtil.marketExists(m)){
                if(market.getFactionId() == faction){
                    modifyTariff(market);
                }
            } 
        }
    }
}
