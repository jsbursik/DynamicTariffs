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
    /*
    *   Gets a MarketAPI for a given Market ID
    */
    public static MarketAPI getMarket(String marketID){
        return Global.getSector().getEconomy().getMarket(marketID);
    }
    /*
    *   This gets your reputation level based on a markets faction
    */
    public static RepLevel getRepLevel(MarketAPI market){
        return market.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
    }
    /*
    *   This removes dynamictariffs modifications from a given Market ID
    */
    public static void unmodTariffs(String marketID){
        getMarket(marketID).getTariff().unmodify("dynamictariffs");
    }
    /*
    *   Scaedumar found a flaw, need to check if a market exists first
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
    /*
    *   Need to check if the player is commissioned with the given market faction
    */
    public static boolean isCommissioned(MarketAPI market){
        String faction = market.getFactionId();
        return faction.equals(Misc.getCommissionFactionId());
    }

}
