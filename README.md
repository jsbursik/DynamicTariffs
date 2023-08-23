# Dynamic Tariffs

This is a utility mod for [Starsector](https://fractalsoftworks.com/) that dynamically changes tariffs based on your current reputation with the faction that owns the market.

## How it works

When you open a market, an event is fired that will then modify the tariffs based on the parameters in the `settings.json` (read about that below), when you close a market, all modifications will be removed as to maintain utility status.

## Settings.json

- `dt_percents` defines what tariffs should be at each level of reputation from Suspicious to Cooperative.
- `dt_granular` if set true will offer all values in-between the set percents based on your actual reputation level. Note: if set true, if you reach 100 reputation with a faction your tariffs will be 0%.
- `dt_commission` if enabled will give you a discount when commissioned with the market's owning faction.
- `dt_commDiscount` is how much it should discount (default is 5%)
- `dt_usewhitelist` is whether or not you want to use the whitelist at all. It is enable by default.
- `dt_whitelist` contains all of the vanilla market id's, these will be the only markets that are effected, if you wish to add some markets, just add their market id's to the list.

## Stock settings

| Reputation  | Tariff |
| ----------- | ------ |
| Suspicious  | 30%    |
| Neutral     | 25%    |
| Favorable   | 20%    |
| Welcoming   | 15%    |
| Friendly    | 10%    |
| Cooperative | 5%     |

And if you are commissioned with the faction, it will be lowered an additional 5%. (This can be turned off in the `settings.json` with `dt_commission:false`)

Also, It doesn't go below "Suspicious" because you can't trade with factions below that reputation level.

## Thanks

Huge thanks to the folks in discord including: LazyWizard, stormbringer951, extremely loweffort art god, Ω Rubin Ω, and Jaghaimo (for the capital P in modPlugin).

This is my first mod for this game, and my first time using Java since high school, they helped A LOT.

### Current Workflow setup

- I am using VSCode in Windows with the [Project Manager for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-dependency&ssr=false#overview) plugin.
- I'm using the Java JDK 1.7.0_80 from the Java archive on Oracles website.
