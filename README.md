# Alexis' Random Tech

[![Maven Central Download RandomTech Release][sonatype-release-badge-randomtech]][maven-central-release-randomtech-latest-download]
[![Maven Central Download API Release][sonatype-release-badge-api]][maven-central-release-api-latest-download]
<br>
[![Maven Central Download RandomTech Snapshot][sonatype-snapshot-badge-randomtech]][maven-central-snapshot-randomtech-latest-download]
[![Maven Central Download API Snapshot][sonatype-snapshot-badge-api]][maven-central-snapshot-api-latest-download]

[![Discord Link][discord-badge]][discord-invite-link-badge]
[![Project Page Link][curseforge-release-badge-randomtech]][curseforge-release-download-randomtech]
[![Documentation Status][read-the-docs-badge]][read-the-docs-link]
<br>
[![Github Actions Link][github-actions-badge]][github-actions-link]
[![Codacy Link][codacy-badge]][codacy-link]
[![Crowdin Link][crowdin-badge]][crowdin-link]
[![API Javadocs][api-javadocs-badge]][api-javadocs-link]

<!-- Public Usage of RandomTech In Development is NOT Supported, The Javadocs Are Available For Use Anyway -->
<!--[![RandomTech Javadocs][randomtech-javadocs-badge]][randomtech-javadocs-link]-->

<!-- Just Displays Major Latest MC Version Supported -->
<!--[![MC Version Badge][curseforge-release-mc-version-badge-randomtech]][curseforge-release-download-randomtech]-->

## Setup

Not too many tech mods exist for 1.16, so I'm adding one. As I believe tech mods should work together, I'm using RebornCore as my dependency and therefore will make my machines compatible with any mods that use RebornCore (including TechReborn).

To use this mod you need RebornCore. You'll want to install Patchouli, so you can access my mod's manual. Also, this mod uses ConnectedBlockTextures for connected textures, but that is an optional dependency that you can download separately if you want to use it.

## Discord

I've made a Discord for Fabric mods I (and a potential future team) develop. You can join this server to ask for help with developing with these mods or just showcase stuff you've made using these mods (or using other mods which use features provided by these mods).

[![Discord Link][discord-banner]][discord-invite-link-banner]

## Development

To include this mod in your development environment, use `modImplementation 'me.alexisevelyn:randomtech:<latest-version>'` and include Maven Central.

The same process applies for the API. Use `modImplementation 'me.alexisevelyn:randomtechapi:<latest-version>'` and include Maven Central.

To compile my mod, use `./gradlew build`. This will build the API, remap it, build RandomTech, remap RandomTech, and then include the API in a Jar-in-Jar fashion in RandomTech, ready to use for production.

You'll find RandomTech in `./build/libs/` under a name like `alexisevelyn-randomtech-0.0.4-SNAPSHOT.jar` (without `-dev` and without `-SOURCES`). The dev is for the development environment as those aren't remapped and sources is just the source code, so you can view the code while developing in your IDE.

To compile the API by itself (dev, sources, and remapped), use `./gradlew :api:build`. You'll find the API in `./api/build/libs/`.

If you want extra info in the meantime, [Fabric Setup Tutorial][fabric-tutorial-link], is a great source. Also, you may want to refer to the commands `./gradlew downloadAssets` and `./gradlew genSources`.

## License

This project is under MIT License. You can refer to [LICENSE.md][license-link] for more info.

[fabric-tutorial-link]: <https://fabricmc.net/wiki/tutorial:setup> "Fabric Tutorial Link"

[discord-badge]: <https://discord.com/api/guilds/750301084202958899/widget.png> "Discord Badge"
[discord-banner]: <https://discord.com/api/guilds/750301084202958899/widget.png?style=banner2> "Discord Banner"
[discord-invite-link-banner]: <https://discord.gg/kBKwmdw> "Discord Invite Link Banner"
[discord-invite-link-badge]: <https://discord.gg/kBKwmdw> "Discord Invite Link Badge"

[codacy-badge]: <https://api.codacy.com/project/badge/Grade/28e0e43f7cbc4678a0d3f6a8a2d69742> "Codacy Badge"
[codacy-link]: <https://app.codacy.com/manual/alexis-evelyn/RandomTech?utm_source=github.com&utm_medium=referral&utm_content=alexis-evelyn/RandomTech&utm_campaign=Badge_Grade_Dashboard> "Codacy Link"

[crowdin-badge]: <https://badges.crowdin.net/randomtech/localized.svg> "Crowdin Badge"
[crowdin-link]: <https://crwd.in/randomtech> "Crowdin Link"

[github-actions-badge]: <https://github.com/alexis-evelyn/RandomTech/workflows/Build%20Mods/badge.svg> "Github Actions Badge"
[github-actions-link]: <https://github.com/alexis-evelyn/RandomTech/actions> "Github Actions Link"

[randomtech-javadocs-badge]: <https://javadoc.io/badge2/me.alexisevelyn/randomtech/RandomTech%20Javadocs.svg> "RandomTech Javadocs Badge"
[api-javadocs-badge]: <https://javadoc.io/badge2/me.alexisevelyn/randomtechapi/API%20Javadocs.svg> "API Javadocs Badge"

[randomtech-javadocs-link]: <https://javadoc.io/doc/me.alexisevelyn/randomtech> "RandomTech Javadocs Link"
[api-javadocs-link]: <https://javadoc.io/doc/me.alexisevelyn/randomtechapi> "API Javadocs Link"

[read-the-docs-badge]: <https://readthedocs.org/projects/randomtech/badge/?version=latest> "Read The Docs Badge"
[read-the-docs-link]: <https://randomtech.alexisevelyn.me/en/latest/?badge=latest> "Read The Docs Link"

[license-link]: <LICENSE.md> "License Markdown File"

<!-- To Generate the URI From The PNG, Use https://onlinepngtools.com/convert-png-to-data-uri -->
[sonatype-release-badge-randomtech]: <https://img.shields.io/nexus/r/me.alexisevelyn/randomtech.svg?server=https%3A%2F%2Foss.sonatype.org&style=flat&color=brightgreen&label=RandomTech%20Release&logo=data:image/png;base64,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> "Sonatype Release Badge For RandomTech"
[sonatype-snapshot-badge-randomtech]: <https://img.shields.io/nexus/s/me.alexisevelyn/randomtech.svg?server=https%3A%2F%2Foss.sonatype.org&style=flat&color=brightgreen&label=RandomTech%20Snapshot&logo=data:image/png;base64,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> "Sonatype Snapshot Badge For RandomTech"
[sonatype-release-badge-api]: <https://img.shields.io/nexus/r/me.alexisevelyn/randomtechapi.svg?server=https%3A%2F%2Foss.sonatype.org&style=flat&color=brightgreen&label=API%20Release&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACAEAYAAACTrr2IAAAABmJLR0QACAAaAG3rgcv9AAAE0klEQVR42u3dT0sbWxgH4JNghBZi0ILd5Dv4TbpwWVqk0GLowgRc1KWgCykIma7EdtHQP0sX3fsd6kcoBNy4aEsJmhi9i1C8lwq94ryaic+z6KKlr2cmM7++Z3LmtJS41NLS0tLS0vl5VP1Op9PpdEqlvOs2Go1GoxE37p2dnZ2dnfzH3Ww2m81m3LizLMuyLP9xF13ZKQABAAgAQAAAAgAQAIAAAAQAIAAAAQAIAKCIwtdGt1qtVquV/xrvdrvdbrfj1navr6+vr6/HrU0fDofD4fDsLO+6vV6v1+uVw4K9Wq1Wq9XBIO+6p6enp6enlUrUuDc3Nzc3N+Oul9XV1dXV1fyvl+3t7e3t7bhx6wDAFAAQAIAAAAQAIAAAAQAIAEAAAAIAKLLCbpO8sbGxsbFxchJVv9/v9/v96emo+oeHh4eHh/nXHQwGg/wX6l6oVCqViAW78/Pz8/Pzgf/Slcvlcnk4jKo/Wmo8NaUDAAQAIAAAAQAIAEAAAAIAEACAAAAEACAAgBtQajabzWYzbvvrubm5ubm5/OseHx8fHx/HnZjR9tpx9bMsy7KsVHIJjiwvLy8vL8ddh6PtzOPGX6vVarVa/nWPjo6Ojo50AIAAAAQAIAAAAQAIAEAAAAIAEACAAAAEAPAf4WvR19bW1tbW+v2864721Y/YoX6k0+l0Oh1r9SfFysrKyspK3LsGMzMzMzMz+dcd/X8DcdfhlEvj9w2/v7+//+/fqdfr9bgL5kK32+1e5QO+qXFN1vG8ebO3t7d3WTAsLi4umgK48W/NuN/QTDIdwB8WFhYWbuLnHBwcHBRhnJN6PNcdrw4A0AFo9WOnAledU4/rcRT1eHQAbvyxvqEcDzqAAs6d/zaOos1RJ+14BMAdF3XB3lYAjfvx/G18V/05AscUgAK4nWcCd/37fx3AjV2o5rzXO7/XPX+jutErAQsbAM+fp5RS3Im5f39ra2sr/7r/fxFwvV6v+6DHb93AzYreRn52dnZ2djaiU4kNLh0AY/6MAVOAsaCVL/KcHwGAGxwBYM4MAsAcHawDAB0A5ujoAAAdAKFzZg8X0QEAOoC7MHe2gIgxDoB371JKKe6h0MuXKaV0dpZ33Wo1dtxMlvPz3d3d3bj6376llNJgkHfd9+9TSml62hQAEACAZwBMjOhnJKP6b99e/qcvXnS73a4AgFuW99ej9ig0BQAEACAAAAEACADgLn4LcNWnv9d9ffb33//9dVdeT58n7Sm2p/K3ofTsWUqR24JHuXcvtv6PH7H1P31KyVLmC69exV6HtVrs+Hu9mLpfv5oCAAIAEACAAAAEACAAAAEACABAAAACABAAwB8Kuxa90UgppZOTqPr9fkqR2zFHrR1/8CD2vH//HlP34cPYcQ+HF79GyLKUUpoq3Mt1OgAwBQAEACAAAAEACABAAAACABAAgAAABABQSOHvAjx5klLEfu8fP6YUua9+9D71P3/G1B+9CxB3Xn79SimlwSDvuqO3LiqVqHG/fh17Xh49ivk8v3yJHbcOAEwBAAEACABAAAACABAAgAAABAAgAIAiKzkFl3v8OKXIpcCfP6cUscSz1Yodd7sdM+6nT2PH/eFDzLh1AIAAAAQAIAAAAQAIAEAAAAIAEACAAAAEAHAL/gHRgKt//M6IpAAAAABJRU5ErkJggg==> "Sonatype Release Badge For API"
[sonatype-snapshot-badge-api]: <https://img.shields.io/nexus/s/me.alexisevelyn/randomtechapi.svg?server=https%3A%2F%2Foss.sonatype.org&style=flat&color=brightgreen&label=API%20Snapshot&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACAEAYAAACTrr2IAAAABmJLR0QACAAaAG3rgcv9AAAE0klEQVR42u3dT0sbWxgH4JNghBZi0ILd5Dv4TbpwWVqk0GLowgRc1KWgCykIma7EdtHQP0sX3fsd6kcoBNy4aEsJmhi9i1C8lwq94ryaic+z6KKlr2cmM7++Z3LmtJS41NLS0tLS0vl5VP1Op9PpdEqlvOs2Go1GoxE37p2dnZ2dnfzH3Ww2m81m3LizLMuyLP9xF13ZKQABAAgAQAAAAgAQAIAAAAQAIAAAAQAIAKCIwtdGt1qtVquV/xrvdrvdbrfj1navr6+vr6/HrU0fDofD4fDsLO+6vV6v1+uVw4K9Wq1Wq9XBIO+6p6enp6enlUrUuDc3Nzc3N+Oul9XV1dXV1fyvl+3t7e3t7bhx6wDAFAAQAIAAAAQAIAAAAQAIAEAAAAIAKLLCbpO8sbGxsbFxchJVv9/v9/v96emo+oeHh4eHh/nXHQwGg/wX6l6oVCqViAW78/Pz8/Pzgf/Slcvlcnk4jKo/Wmo8NaUDAAQAIAAAAQAIAEAAAAIAEACAAAAEACAAgBtQajabzWYzbvvrubm5ubm5/OseHx8fHx/HnZjR9tpx9bMsy7KsVHIJjiwvLy8vL8ddh6PtzOPGX6vVarVa/nWPjo6Ojo50AIAAAAQAIAAAAQAIAEAAAAIAEACAAAAEAPAf4WvR19bW1tbW+v2864721Y/YoX6k0+l0Oh1r9SfFysrKyspK3LsGMzMzMzMz+dcd/X8DcdfhlEvj9w2/v7+//+/fqdfr9bgL5kK32+1e5QO+qXFN1vG8ebO3t7d3WTAsLi4umgK48W/NuN/QTDIdwB8WFhYWbuLnHBwcHBRhnJN6PNcdrw4A0AFo9WOnAledU4/rcRT1eHQAbvyxvqEcDzqAAs6d/zaOos1RJ+14BMAdF3XB3lYAjfvx/G18V/05AscUgAK4nWcCd/37fx3AjV2o5rzXO7/XPX+jutErAQsbAM+fp5RS3Im5f39ra2sr/7r/fxFwvV6v+6DHb93AzYreRn52dnZ2djaiU4kNLh0AY/6MAVOAsaCVL/KcHwGAGxwBYM4MAsAcHawDAB0A5ujoAAAdAKFzZg8X0QEAOoC7MHe2gIgxDoB371JKKe6h0MuXKaV0dpZ33Wo1dtxMlvPz3d3d3bj6376llNJgkHfd9+9TSml62hQAEACAZwBMjOhnJKP6b99e/qcvXnS73a4AgFuW99ej9ig0BQAEACAAAAEACADgLn4LcNWnv9d9ffb33//9dVdeT58n7Sm2p/K3ofTsWUqR24JHuXcvtv6PH7H1P31KyVLmC69exV6HtVrs+Hu9mLpfv5oCAAIAEACAAAAEACAAAAEACABAAAACABAAwB8Kuxa90UgppZOTqPr9fkqR2zFHrR1/8CD2vH//HlP34cPYcQ+HF79GyLKUUpoq3Mt1OgAwBQAEACAAAAEACABAAAACABAAgAAABABQSOHvAjx5klLEfu8fP6YUua9+9D71P3/G1B+9CxB3Xn79SimlwSDvuqO3LiqVqHG/fh17Xh49ivk8v3yJHbcOAEwBAAEACABAAAACABAAgAAABAAgAIAiKzkFl3v8OKXIpcCfP6cUscSz1Yodd7sdM+6nT2PH/eFDzLh1AIAAAAQAIAAAAQAIAEAAAAIAEACAAAAEAHAL/gHRgKt//M6IpAAAAABJRU5ErkJggg==> "Sonatype Snapshot Badge For API"

<!-- So, apparently, I cannot find a "latest release" link from directly on Maven Central (search.maven.org), so I'm using MVNRepository Instead. I'm also going to have to look for snapshot links too -->
[maven-central-release-randomtech-latest-download]: <https://mvnrepository.com/artifact/me.alexisevelyn/randomtech/latest> "Maven Central Release RandomTech Latest Download"
[maven-central-release-api-latest-download]: <https://mvnrepository.com/artifact/me.alexisevelyn/randomtechapi/latest> "Maven Central Release API Latest Download"

<!-- These are psuedo-links as I have not found a way to "auto-link" to the latest snapshot -->
[maven-central-snapshot-randomtech-latest-download]: <https://oss.sonatype.org/#nexus-search;gav~me.alexisevelyn~randomtech~~~> "Maven Central Snapshot RandomTech Search"
[maven-central-snapshot-api-latest-download]: <https://oss.sonatype.org/#nexus-search;gav~me.alexisevelyn~randomtechapi~~~> "Maven Central Snapshot API Search"

<!-- Some CurseForge Available Badges -->
[curseforge-release-badge-randomtech]: <http://cf.way2muchnoise.eu/title/407461_%20_Available%20On%20CurseForge.svg> "RandomTech Release Badge For CurseForge"
[curseforge-release-badge-api]: <http://cf.way2muchnoise.eu/title/407465_%20_Available%20On%20CurseForge.svg> "API Release Badge For CurseForge"

<!-- Some CurseForge MC Version Badges -->
[curseforge-release-mc-version-badge-randomtech]: <http://cf.way2muchnoise.eu/versions/407461_latest.svg> "RandomTech MC Version Badge For CurseForge"
[curseforge-release-mc-version-badge-api]: <http://cf.way2muchnoise.eu/versions/407465_latest.svg> "API MC Version Badge For CurseForge"

<!-- CurseForge Project Page Links -->
[curseforge-release-download-randomtech]: <https://www.curseforge.com/minecraft/mc-mods/randomtech> "RandomTech Project Page Link For CurseForge"
[curseforge-release-download-api]: <https://www.curseforge.com/minecraft/mc-mods/randomtech-api> "API Project Page Link For CurseForge"