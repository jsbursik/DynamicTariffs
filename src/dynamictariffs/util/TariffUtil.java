package dynamictariffs.util;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class TariffUtil extends BaseCampaignEventListener {
    
    public static Logger log = Global.getLogger(TariffUtil.class);
    public static Float[] percents = SettingsUtil.percents;

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
        // Whitelist setting & check goes here
        log.info("DynamicTariffs: Player opened market");
        if(SettingsUtil.useWhitelist){
            if(isWhitelisted(market)){
                modTariff(market);
            }
        } else {
            modTariff(market);
        }
    }

    /** 
     * This will undo the tariff modifications when you close
     * a market
     * @param market
     */
    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        if(SettingsUtil.useWhitelist){
            if(isWhitelisted(market)) {
                market.getTariff().unmodify("dynamictariffs");
            }
        } else {
            market.getTariff().unmodify("dynamictariffs");
        }
        log.info("DynamicTariffs: Player closed market");
    }

    /**
     * This will go down 2 routes based on whether you are using
     * the granular tariff settings. The math is rough because
     * of all the floating points.
     * @param market
     */
    public static void modTariff(MarketAPI market) {
        // Granular Check goes somewhere in this function

        String[] RepArr = {"SUSPICIOUS", "NEUTRAL", "FAVORABLE", "WELCOMING", "FRIENDLY", "COOPERATIVE"};
        List<String> RepLevels = Arrays.asList(RepArr);

        Float[] RepValues = new Float[] { -0.25f, -0.10f, 0.10f, 0.25f, 0.50f, 0.75f, 1.0f };

        String repLevel = market.getFaction().getRelationshipLevel(Factions.PLAYER).toString();
        Float repValue = market.getFaction().getRelationship(Factions.PLAYER);

        Float tariff = 0f;

        int i = RepLevels.indexOf(repLevel);

        if(SettingsUtil.useGranular){
            float stepAmt = Math.abs(RepValues[i+1] - RepValues[i]) / Math.abs(percents[i] - percents[i+1]);
            tariff = percents[i] - ((1 / stepAmt) * (repValue - RepValues[i]));
            if(isCommissioned(market) && SettingsUtil.commission){
                tariff -= SettingsUtil.commDiscount;
                tariff = tariff < 0f ? -0.3f : tariff;
            }
            tariff = -(0.3f - tariff);
            market.getTariff().modifyFlat("dynamictariffs", tariff);
        }
        tariff = -(0.3f - percents[i]);
        market.getTariff().modifyFlat("dynamictariffs", tariff);
    }

    /**
     * This is just a utility function to check if you're commissioned
     * with a faction based on the market given.
     * @param market
     * @return boolean
     */
    public static boolean isCommissioned(MarketAPI market){
        String faction = market.getFactionId();
        return faction.equals(Misc.getCommissionFactionId());
    }

    /**
     * This checks to see if a market is whitelisted, instead
     * of other classes requesting the List and doing the Boolean
     * logic for themselves
     * @param market
     * @return boolean
     */
    public static boolean isWhitelisted(MarketAPI market){
        boolean result = false;
        if(SettingsUtil.whitelist.contains(market.getId())){
            result = true;
        }
        return result;
    }

}
