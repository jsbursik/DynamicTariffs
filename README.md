# Dynamic Tariffs
This is a mod for [Starsector](https://fractalsoftworks.com/) that dynamically changes tariffs based on your current reputation with the faction that owns the market.

## How it works
Currently I am utilizing the `BaseCampaignEventListener` to `reportPlayerOpenedMarket(MarketAPI market)` to modify the tariffs as soon as you open a market.
The specific modifaction is done with `modifyFlat(String source, float value)` which allows the modifications to be removed with `unmodify(String source)` on `reportPlayerClosedMarket(MarketAPI market)`.
Because of this behavior the mod can be installed/removed mid playthrough ONCE I transition the code to use a Transient Listener instead of a Permanent Listener.

## TO-DO
- [X] Write an actual README.md
- [X] Streamline the Git repo
- [X] Restructure packages
- [ ] Implement a settings.json for percents
- [ ] Whitelist Vanilla markets in said json
- [ ] Refactor for Transient Listener vs Permanent (utility:true)

And an ongoing todo will be to just slim down the code/refactor for efficiency.

### Current Workflow setup
* Currently using Apache NetBeans IDE version 10.0 on Linux (Ubuntu 20.04 LTS) to code all of this.
* I'm using the Java JDK 1.7.0_80 from the Java archive on Oracles website.
* In order to build+test this mod I have a symlink between the mods folder and my Netbeans project folder.