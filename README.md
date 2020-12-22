# Dynamic Tariffs
This is a utility mod for [Starsector](https://fractalsoftworks.com/) that dynamically changes tariffs based on your current reputation with the faction that owns the market.

## How it works
The `settings.json` contains percents from Suspicious to Cooperative, as well as a whitelist.
When you load a save, it will go through all the whitelisted markets and modify their tariffs.
Before you save it will unmodify them all, and after the save it will reimplement the modified tariffs.

## Stock settings
Reputation | Tariff
---------|----------
Suspicious | 30%
Neutral | 25%
Favorable | 20%
Welcoming | 15%
Friendly | 10%
Cooperative | 5%

And if you are commissioned with the faction, it will be lowered an additional 5%. (This can be turned off in the `settings.json` with `dt_commission:false`)

Also, It doesn't go below "Suspicious" because you can't trade with factions below that reputation level.

Note: You can technically make the tariffs negative and they will pay YOU for selling to them. The UI will still say "-number"

## Thanks
Huge thanks to the folks in discord including: LazyWizard, stormbringer951, extremely loweffort art god, Ω Rubin Ω, and Jaghaimo (for the capital P in modPlugin).

This is my first mod for this game, and my first time using Java since high school, they helped A LOT.

### Current Workflow setup
* Currently using Apache NetBeans IDE version 10.0 on Linux (Ubuntu 20.04 LTS) to code all of this.
* I also have a working setup in Windows with VSCode using the [Project Manager for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-dependency&ssr=false#overview) plugin.
* I'm using the Java JDK 1.7.0_80 from the Java archive on Oracles website.
* In order to build+test this mod I have a symlink between the mods folder and my Netbeans project folder.
