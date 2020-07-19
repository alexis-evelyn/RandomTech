package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.nbt.Tag;

// The idea behind this class is to implement a dictionary much like Forge's OreDictionary
// I plan on using TechReborn's tag implementation which uses the namespace "c".
// But first I have to figure out why only TR's planks are considered planks
// before I switch my crafting recipes over to use tags.
public class Dictionary {
    Dictionary() {

    }
}

/* Wooden Sword - Use as basis on how to create tagged crafting recipes
 * Do Note that #minecraft:planks are broken at the moment with only Rubber Tree planks working.

{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "X",
    "X",
    "#"
  ],
  "key": {
    "#": {
      "item": "minecraft:stick"
    },
    "X": {
      "tag": "minecraft:planks"
    }
  },
  "result": {
    "item": "minecraft:wooden_sword"
  }
}

 */