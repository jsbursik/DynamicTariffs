# Dynamic Tariffs
This is a utility mod for [Starsector](https://fractalsoftworks.com/) that dynamically changes tariffs based on your current reputation with the faction that owns the market.

## How it works
The `settings.json` contains percents from Suspicious to Cooperative, as well as a whitelist.
When you open a market, the mod first checks if the market is whitelisted, if it is, it checks your rep with the owning faction and applies a modification to the tariff.
When you close the market, the specific modifications made by this mod will then be removed.

## Stock settings
Reputation | Tariff
---------|----------
Suspicious | 30%
Neutral | 15%
Favorable | 10%
Welcoming | 5%
Friendly | 2%
Cooperative | 0%

To modify these value, edit `settings.json`.

Also, It doesn't go below "Suspicious" because you can't trade with factions below that reputation level.

Note: You can technically make the tariffs negative and they will pay YOU for selling to them. The UI will still say "-number"

### Current Workflow setup
* Currently using Apache NetBeans IDE version 10.0 on Linux (Ubuntu 20.04 LTS) to code all of this.
* I'm using the Java JDK 1.7.0_80 from the Java archive on Oracles website.
* In order to build+test this mod I have a symlink between the mods folder and my Netbeans project folder.
