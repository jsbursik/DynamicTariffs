package dynamictariffs.util;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import org.apache.log4j.Logger;

public class TariffUtil extends BaseCampaignEventListener {
    
    public static Logger log = Global.getLogger(TariffUtil.class);
    public static int[] percents = SettingsUtil.getPercents();

    
    public TariffUtil() {
        super(false);
        Global.getSector().addTransientListener(this);    
    }

    
    /** 
     * This will start the process of modifying tariffs when
     * a player opens a market. It can go granular or flat.
     * @param market
     */
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {
        if(SettingsUtil.isWhitelisted(market)){
            if(SettingsUtil.getGranular()){
                modTariffGran(market);
                log.info("DynamicTariffs: Player has Granular enabled");
            } else {
                modTariff(market);
            }
        } 
        log.info("DynamicTariffs: Player opened market");
    }

    
    /** 
     * This will undo the tariff modifications when you close
     * a market
     * @param market
     */
    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        if(SettingsUtil.isWhitelisted(market)){
            market.getTariff().unmodify("dynamictariffs");
        }
        log.info("DynamicTariffs: Player closed market");
    }

    
    /**
     * This modifies tariffs just based on reputation name,
     * not by the actual level (SUSPICIOUS vs -0.25f)
     * @param market
     */
    public static void modTariff(MarketAPI market){
        float flat = 0.0f;
        RepLevel rep = EconUtil.getRepLevel(market);
        switch(rep) {
            case SUSPICIOUS:
                flat = getOffsetFlat(percents[0], market);
                break;
            case NEUTRAL:
                flat = getOffsetFlat(percents[1], market);
                break;
            case FAVORABLE:
                flat = getOffsetFlat(percents[2], market);
                break;
            case WELCOMING:
                flat = getOffsetFlat(percents[3], market);
                break;
            case FRIENDLY:
                flat = getOffsetFlat(percents[4], market);
                break;
            case COOPERATIVE:
                flat = getOffsetFlat(percents[5], market);
                break;
            default:
                // This is to stop it warning me about the untradeable rep levels
        }
        market.getTariff().modifyFlat("dynamictariffs", flat);
    }

    
    /** 
     * This just provides modTariff() a way to calculate the
     * modified tariff from the default value of 0.3f
     * @param percent
     * @param market
     * @return float
     */
    public static float getOffsetFlat(int percent, MarketAPI market) {
        if(EconUtil.isCommissioned(market)){
            int commD = SettingsUtil.commDiscount;
            if(percent - commD < 0){
                return -0.3f;
            } else {
                return (percent - 30 - commD) / 100f;
            }
        }
        return (percent - 30) / 100f;
    }

    
    /** 
     * This looks into your actual reputation level and
     * offers some granularity between values for tariffs
     * @param market
     */
    public static void modTariffGran(MarketAPI market){
        Float repValue = EconUtil.getRepFloat(market) * 100f;
        float gran = 0f;
        if(repValue < -10){
            gran = getOffsetGran(percents[0], percents[1], -25f, -10f, repValue, market);
        } else if(repValue < 10) {
            gran = getOffsetGran(percents[1], percents[2], -10f, 10f, repValue, market);
        } else if(repValue < 25) {
            gran = getOffsetGran(percents[2], percents[3], 10f, 25f, repValue, market);
        } else if(repValue < 50) {
            gran = getOffsetGran(percents[3], percents[4], 25f, 50f, repValue, market);
        } else if(repValue < 75) {
            gran = getOffsetGran(percents[4], percents[5], 50, 75f, repValue, market);
        } else if(repValue <= 100) {
            gran = getOffsetGran(percents[5], 0, 75f, 100f, repValue, market);
        }
        log.info("DynamicTariffs: gran = " + gran + " repValue = " + repValue);
        market.getTariff().modifyFlat("dynamictariffs", gran);
    }

    
    /**
     * This rats nest of a function basically finds the equation for
     * the line between two tariff percents and its respective reputation
     * values. (like -25 to -10 for SUSPICIOUS)
     * @param p0
     * @param p1
     * @param r0
     * @param r1
     * @param repFloat
     * @param market
     * @return float
     */
    public static float getOffsetGran(int p0, int p1, float r0, float r1, float repFloat, MarketAPI market){
        int tariff = Math.round(((p0 - p1) / (r0 - r1)) * (repFloat - r0) + p0);
        if(EconUtil.isCommissioned(market)){
            int commD = SettingsUtil.commDiscount;
            if((tariff - commD) < 0){
                return -0.3f;
            } else {
                return (tariff - 30 - commD) / 100f;
            }
        }
        return (tariff - 30) / 100f;
    }

}
