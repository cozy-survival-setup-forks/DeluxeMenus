# DeluxeMenus (cozy fork)

A fork of [HelpChat/DeluxeMenus](https://github.com/HelpChat/DeluxeMenus) for Paper 1.21.11, same GPL-3.0 license, built on the current upstream main. Everything else works as upstream.

What is different:

- **`HIDE_DURABILITY` in `item_flags`.** Hides the durability bar of an item, next to `HIDE_ATTRIBUTES`, `HIDE_ENCHANTS` and the other flags. It makes the item unbreakable (which removes the bar) and hides the unbreakable line of the tooltip. An `unbreakable` item with `HIDE_UNBREAKABLE` looks the same.
- **MiniMessage next to the `&` codes.** Menu titles, item names, lore, `[message]` and `[broadcast]` accept MiniMessage tags (`<gradient:red:blue>`, `<#ff8800>`, `<bold>`) mixed with `&7` and `&#rrggbb`. Text without a tag that looks like MiniMessage is handled exactly as before, so old menus do not change. Hover and click events do not work in item text, use the `[minimessage]` action for them. `[message]` and `[broadcast]` keep click and hover events (`<click:open_url:...>`), and `[minimessage]` works on Paper 1.21.11 (it sends through the server's own Adventure).
- **YAML anchors** (`&base` and `<<: *base`) already work in menu files, this is plain YAML and not a DeluxeMenus feature. A key that an item sets itself replaces the anchored one, lists are not merged.

```yaml
anchors:
  base: &base
    item_flags:
      - HIDE_ATTRIBUTES
      - HIDE_DURABILITY

items:
  pick:
    <<: *base
    material: DIAMOND_PICKAXE
    slot: 10
    display_name: '<gradient:gold:red>Pickaxe'
```

---

[logo]: https://github.com/HelpChat/DeluxeMenus/assets/52609756/f24ac57d-98db-4d57-a723-791a2654e73f

[issues]: https://github.com/HelpChat/DeluxeMenus/issues
[licenseImg]: https://img.shields.io/github/license/helpchat/deluxemenus?&logo=github
[license]: https://github.com/HelpChat/DeluxeMenus/blob/master/LICENSE

[bstatsImg]: https://img.shields.io/bstats/servers/445
[bstats]: https://bstats.org/plugin/bukkit/DeluxeMenus/445

[discordImg]: https://img.shields.io/discord/164280494874165248?color=5562e9&logo=discord&logoColor=white
[discord]: https://helpch.at/discord
[spigot]: https://www.spigotmc.org/resources/11734/

[ci]: http://ci.extendedclip.com/job/DeluxeMenus/
[ciImg]: http://ci.extendedclip.com/buildStatus/icon?job=DeluxeMenus

[contributing]: https://github.com/HelpChat/DeluxeMenus/blob/main/CONTRIBUTING.md

[![logo]][spigot]

[![ciImg]][ci] [![bstatsImg]][bstats] [![discordImg]][discord] [![licenseImg]][license] [![GitBook](https://img.shields.io/static/v1?message=Documented%20on%20GitBook&logo=gitbook&logoColor=ffffff&label=%20&labelColor=5c5c5c&color=3F89A1)](https://wiki.helpch.at/helpchat-plugins/deluxemenus)


# Information
[DeluxeMenus][spigot] is the all in one inventory GUI menu plugin!
You can create GUI menus that open with custom commands that will show stats or perform actions specific to the player who opened it. Your menus are fully configurable. You can create menus that show specific items to different players, or perform different actions depending on what javascript requirement they have for the specific slot in a certain GUI.

DeluxeMenus depends on [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/).

## Contribute
If you would like to contribute towards DeluxeMenus should you take a look at our [Contributing file][contributing] for the ins and outs on how you can do that and what you need to keep in mind.

## Support
- [Issue Tracker][issues]
- [Discord Support][discord]

## Quick Links
- [Wiki](https://wiki.helpch.at/clips-plugins/deluxemenus/)
- [CI Server][ci]
- [Spigot Page][spigot]
- [Plugin Statistics][bstats]

