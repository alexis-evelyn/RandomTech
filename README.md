# Alexis' Random Tech

[![Codacy Badge][codacy-badge]][codacy-link] **Github Actions Here** **Crowdin Here** **Discord Badge Here**

## Setup

Not too many tech mods exist for 1.16, so I'm adding one. As I believe tech mods should work together, I'm using RebornCore as my dependency and therefor will make my machines compatible with any mods that use RebornCore (including TechReborn).

To use this mod you need RebornCore. You'll want to install Patchouli so you can access my mod's manual. Also, this mod uses ConnectedBlockTextures for connected textures, but that is an optional dependency that you can download separately if you want to use it.

## Discord

I've made a Discord for Fabric mods I (and a potential future team) develop. You can join this server to ask for help with developing with these mods or just showcase stuff you've made using these mods (or using other mods which use features provided by these mods).

[![Discord Banner][discord-banner]]
[discord-invite-link]

## Development

To include this mod in your development environment, use `modImplementation 'me.alexisevelyn:randomtech:0.0.4'` and include the maven repository below.

```Gradle
maven {
    name "Alexis' Tech Mods"
    url "https://dl.bintray.com/alexis-evelyn/mods" 
}
```

To compile my mod, use `./gradlew build`. This will build the API, remap it, build RandomTech, remap RandomTech, and then include the API in a Jar-in-Jar fashion in RandomTech, ready to use for production.

You'll find RandomTech in `./build/libs/` under a name like `alexisevelyn-randomtech-0.0.4-SNAPSHOT.jar` (without `-dev` and without `-SOURCES`). The dev is for the development environment as those aren't remapped and sources is just the source code so you can view the code while developing in your IDE.

To compile the API by itself (dev, sources, and remapped), use `./gradlew :api:build`. You'll find the API in `./api/build/libs/`. It's the same deal for naming. It probably won't include `-SNAPSHOT`, but if it does, it just means it's not a release commit. Release commits just means the API is available for production use. Being a snapshot doesn't mean it isn't ready for production, just that we don't know.

The API is not yet setup to be sent to Maven. It can be used from commit though. More information about building will be provided later.

If you want extra info in the meantime, https://fabricmc.net/wiki/tutorial:setup, is a great source. Also, you may want to refer to the commands `./gradlew downloadAssets` and `./gradlew genSources`.

### Jitpack

Note, Jitpack doesn't ever play nice with me, so I don't recommend trying to use it to download commit versions of my mod. If you can get it to work, then by all means, use it if you want to. I, however, don't know why Jitpack has so many issues when I try to use it, so I personally just recommend using the official repo I provide.

### Notes

If you have a problem with Log4J not being able to be read, build the api first `./gradlew :api:build`. Then build the mod `./gradlew build`. I'm not sure why the bug came back, but it has something to do with loom and my multi-project gradle setup. If anyone knows how to fix it, please send a PR.

## License

This project is under MIT License. You can refer to LICENSE.md for more info.

[discord-banner]: <https://discord.com/api/guilds/750301084202958899/widget.png?style=banner2> "Discord Banner"
[discord-invite-link]: <https://discord.gg/kBKwmdw> "Discord Invite Link"

[codacy-badge]: <https://api.codacy.com/project/badge/Grade/28e0e43f7cbc4678a0d3f6a8a2d69742> "Codacy Badge"
[codacy-link]: <https://app.codacy.com/manual/alexis-evelyn/RandomTech?utm_source=github.com&utm_medium=referral&utm_content=alexis-evelyn/RandomTech&utm_campaign=Badge_Grade_Dashboard> "Codacy Link"