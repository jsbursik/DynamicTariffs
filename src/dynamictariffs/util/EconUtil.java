package dynamictariffs.util;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;

import java.util.List;

/*
*   This Class is just here to shorthand some common tasks when
*   dealing with markets/economy. Global.getSector()... is annoying to
*   type over and over. This is only really necessary because the
*   SettingsUtil reads a whitelist that contains ID's not MarketAPI's
*   Technically this could be alleviated by making a MarketAPI ArrayList
*   from the read-in market id's, but hey, it works already, right?
*/
public class EconUtil {

    
    /**
     * Takes in a String marketID returns the MarketAPI for it 
     * @param marketID
     * @return MarketAPI
     */
    public static MarketAPI getMarket(String marketID){
        return Global.getSector().getEconomy().getMarket(marketID);
    }

    
    /** 
     * Takes in a MarketAPI returns a RepLevel for the faction
     * owning the market.
     * @param market
     * @return RepLevel
     */
    public static RepLevel getRepLevel(MarketAPI market){
        return market.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
    }

    public static Float getRepFloat(MarketAPI market){
        return market.getFaction().getRelationship(Factions.PLAYER);
    }
    
    /** 
     * Removes the modifications from a given marketID
     * @param marketID
     */
    public static void unmodTariffs(String marketID){
        getMarket(marketID).getTariff().unmodify("dynamictariffs");
    }


    /** 
     * Checks to see if a market exists
     * @param marketID
     * @return boolean
     */
    public static boolean marketExists(String marketID){
        MarketAPI market = getMarket(marketID);
        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        if(markets.contains(market)){
            return true;
        } else {
            return false;
        }
    }


    /** 
     * Takes in a MarketAPI and returns whether or not you
     * are commissioned with the owning faction
     * @param market
     * @return boolean
     */
    public static boolean isCommissioned(MarketAPI market){
        String faction = market.getFactionId();
        return faction.equals(Misc.getCommissionFactionId());
    }

}
