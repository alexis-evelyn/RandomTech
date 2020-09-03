# Alexis' Random Tech

[![Bintray Download Badge][bintray-badge]][bintray-download-link]
[![Github Actions Badge][github-actions-badge]][github-actions-link]
[![Codacy Badge][codacy-badge]][codacy-link]
[![Crowdin Badge][crowdin-badge]][crowdin-link]
[![Discord Badge][discord-badge]][discord-invite-link-badge]

## Setup

Not too many tech mods exist for 1.16, so I'm adding one. As I believe tech mods should work together, I'm using RebornCore as my dependency and therefor will make my machines compatible with any mods that use RebornCore (including TechReborn).

To use this mod you need RebornCore. You'll want to install Patchouli, so you can access my mod's manual. Also, this mod uses ConnectedBlockTextures for connected textures, but that is an optional dependency that you can download separately if you want to use it.

## Discord

I've made a Discord for Fabric mods I (and a potential future team) develop. You can join this server to ask for help with developing with these mods or just showcase stuff you've made using these mods (or using other mods which use features provided by these mods).

[![Discord Banner][discord-banner]][discord-invite-link-banner]

## Development

To include this mod in your development environment, use `modImplementation 'me.alexisevelyn:randomtech:0.0.5'` and include the maven repository below.

The same process applies for the API. Use `modImplementation 'me.alexisevelyn:randomtechapi:0.0.5'` and include the below repo.

```Gradle
maven {
    name "Alexis' Tech Mods"
    url "https://dl.bintray.com/alexis-evelyn/mods" 
}
```

To compile my mod, use `./gradlew build`. This will build the API, remap it, build RandomTech, remap RandomTech, and then include the API in a Jar-in-Jar fashion in RandomTech, ready to use for production.

You'll find RandomTech in `./build/libs/` under a name like `alexisevelyn-randomtech-0.0.4-SNAPSHOT.jar` (without `-dev` and without `-SOURCES`). The dev is for the development environment as those aren't remapped and sources is just the source code so you can view the code while developing in your IDE.

To compile the API by itself (dev, sources, and remapped), use `./gradlew :api:build`. You'll find the API in `./api/build/libs/`. It's the same deal for naming. It probably won't include `-SNAPSHOT`, but if it does, it just means it's not a release commit. Release commits just means the API is available for production use. Being a snapshot doesn't mean it isn't ready for production, just that we don't know.

If you want extra info in the meantime, [Fabric Setup Tutorial][fabric-tutorial-link], is a great source. Also, you may want to refer to the commands `./gradlew downloadAssets` and `./gradlew genSources`.

### Notes

Note, Jitpack doesn't ever play nice with me, so I don't recommend trying to use it to download commit versions of my mod. If you can get it to work, then by all means, use it if you want to. I, however, don't know why Jitpack has so many issues when I try to use it, so I personally just recommend using the official repo I provide.

## License

This project is under MIT License. You can refer to [LICENSE.md][license-link] for more info.

[fabric-tutorial-link]: <https://fabricmc.net/wiki/tutorial:setup> "Fabric Tutorial Link"

[discord-badge]: <https://discord.com/api/guilds/750301084202958899/widget.png> "Discord Badge"
[discord-banner]: <https://discord.com/api/guilds/750301084202958899/widget.png?style=banner2> "Discord Banner"
[discord-invite-link-banner]: <https://discord.gg/kBKwmdw> "Discord Invite Link Banner"
[discord-invite-link-badge]: <https://discord.gg/m2wTSDZ> "Discord Invite Link Badge"

[codacy-badge]: <https://api.codacy.com/project/badge/Grade/28e0e43f7cbc4678a0d3f6a8a2d69742> "Codacy Badge"
[codacy-link]: <https://app.codacy.com/manual/alexis-evelyn/RandomTech?utm_source=github.com&utm_medium=referral&utm_content=alexis-evelyn/RandomTech&utm_campaign=Badge_Grade_Dashboard> "Codacy Link"

[crowdin-badge]: <https://badges.crowdin.net/randomtech/localized.svg> "Crowdin Badge"
[crowdin-link]: <https://crwd.in/randomtech> "Crowdin Link"

[github-actions-badge]: <https://github.com/alexis-evelyn/RandomTech/workflows/Build%20Mod/badge.svg> "Github Actions Badge"
[github-actions-link]: <https://github.com/alexis-evelyn/RandomTech/actions> "Github Actions Link"

[bintray-badge]: <https://api.bintray.com/packages/alexis-evelyn/mods/RandomTech/images/download.svg> "Bintray Badge"
[bintray-download-link]: <https://bintray.com/alexis-evelyn/mods/RandomTech/_latestVersion> "Bintray Download Link"

[license-link]: <LICENSE.md> "License Markdown File"