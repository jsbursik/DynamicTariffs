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