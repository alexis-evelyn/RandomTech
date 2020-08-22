# Alexis' Random Tech

## Setup

Not too many tech mods exist for 1.16, so I'm adding one. As I believe tech mods should work together, I'm using RebornCore as my dependency and therefor will make my machines compatible with any mods that use RebornCore (including TechReborn).

To use this mod you need RebornCore. You'll want to install Patchouli so you can access my mod's manual. Also, this mod uses ConnectedBlockTextures for connected textures, but that is included as part of this mod so you can enjoy without having to download it. Screenshots of proof will be provided soon.

## Development

To include this mod in your development environment, use `modImplementation 'me.alexisevelyn:randomtech:0.0.4'` and include the maven repository below.

An api will be released as a separate jar sometime soon. I just have to figure out how mixins will behave when there's more than one instance of my api being jij.

```Gradle
maven {
    name "Alexis' Tech Mods"
    url "https://dl.bintray.com/alexis-evelyn/mods" 
}
```

## License

This project is under MIT License. You can refer to LICENSE.md for more info.