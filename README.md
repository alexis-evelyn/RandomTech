# Alexis' Random Tech

## Setup

Not too many tech mods exist for 1.16, so I'm adding one. As I believe tech mods should work together, I'm using RebornCore as my dependency and therefor will make my machines compatible with any mods that use RebornCore (including TechReborn).

To use this mod you need RebornCore. You'll want to install Patchouli so you can access my mod's manual. Also, this mod uses ConnectedBlockTextures for connected textures, but that is an optional dependency that you can download separately if you want to use it.

## Discord

I'm working on a Discord server to help people with the usage of my mods. I'm currently in the process of setting up the Discord server, so I'm not releasing an invite link yet, so if you have issues, just leave an issue in the issues tab for me to see.

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