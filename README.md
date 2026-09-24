# Napolitano: Plan for a Simple Minecraft Mod With One New Block

The smallest useful mod will contain:

1. One new block
2. A placeable item form for the block
3. A name
4. A block model and texture
5. A creative-inventory entry
6. An optional loot table so breaking it drops itself

I recommend using **Fabric** for **Minecraft Java Edition** and writing the mod in **Kotlin**.

> **Important:** The code below targets **Minecraft 26.2 with JDK 25 and Kotlin**, using the current Fabric documentation. Minecraft mod code is version-specific. If you want to mod another version, such as `1.21.1`, select that exact version in the project generator and use the matching version of the Fabric documentation.

---

## 1. Install the required programs

You need:

- **JDK 25** for Minecraft 26.2
- **Visual Studio Code**
- A web browser

Kotlin runs on the Java Virtual Machine, so you still need a JDK even though the mod source code will be Kotlin.

### Install the JDK

Download JDK 25 from [Adoptium](https://adoptium.net/).

After installing it, open PowerShell and run:

```powershell
java -version
```

You should see Java version 25.

You do **not** need to install Gradle or the Kotlin compiler separately. The generated project includes Gradle Wrapper and the Kotlin Gradle plugin.

### Install Visual Studio Code and the Kotlin support

Download Visual Studio Code from:

[https://code.visualstudio.com/](https://code.visualstudio.com/)

Open the Extensions view with `Ctrl+Shift+X` and install:

1. **Extension Pack for Java** — useful for Gradle, project import, and the Java runtime used by Minecraft.
2. **Kotlin by JetBrains** — provides Kotlin language support in VS Code. You can find it in the [VS Marketplace](https://marketplace.visualstudio.com/items?itemName=JetBrains.kotlin-server).

The Kotlin extension provides syntax highlighting, completion, navigation, and diagnostics for `.kt` files. The exact extension experience can change as Kotlin tooling evolves, so keep the extensions updated.

---

## 2. Generate a Fabric project

This step creates the basic project that will contain your mod. You are not adding the block yet; you are only creating the empty project and its build configuration.

### Step 2.1: Open the generator

Open the official Fabric Template Generator:

[Fabric Template Generator](https://fabricmc.net/develop/template/)

The generator is a web form. It will create the correct Gradle files, Fabric Loader dependency, Fabric API dependency, Fabric Kotlin language adapter, and starter Kotlin files for the Minecraft version you select.

### Step 2.2: Fill in the project information

Use approximately these values:

| Setting | Value | What it means |
|---|---|---|
| Mod name | `Napolitano` | The human-readable name shown in logs and mod metadata. It may contain spaces. |
| Package name | `com.example.napolitano` | The Java/Kotlin package where your classes will live. Use lowercase letters and dots; do not use spaces. |
| Mod ID | `napolitano` | The unique identifier used by the mod. It becomes part of IDs such as `napolitano:ruby_block`. |
| Minecraft version | `26.2` | The exact Minecraft version this project will target. |
| Kotlin Programming Language | Yes | Generates `.kt` source files and adds Fabric's Kotlin language adapter. |
| Kotlin Build Script | Yes | Uses Kotlin for Gradle files such as `build.gradle.kts` and `settings.gradle.kts`. |
| Data generation | No | Data generation is not needed for this first block. |
| Split client and common sources | Leave enabled | Keeps client-only code separate from common/server code. The default is suitable for this project. |

The generator determines the JDK requirement from the Minecraft version. For Minecraft 26.2, it generates a project targeting JDK 25, so you do not need to choose a separate JDK option in the web form.

The generator first derives a default mod ID from the project name. If it does not produce `napolitano`, click **Use custom id** and enter `napolitano`.

### Difference between the mod name, mod ID, and package name

These values are related, but they are not the same:

- **Mod name** is what a person may see: `Napolitano`. The generator uses it to derive a class name such as `Napolitano`.
- **Mod ID** is the technical name used by Minecraft: `napolitano`.
- **Package name** is the Java/Kotlin namespace: `com.example.napolitano`.

For this guide, keep these values exactly as shown. If you change one of them, you must also update the corresponding Kotlin code and resource paths.

The mod ID must be unique. Do not use spaces or uppercase letters. For a simple first project, a short lowercase ID such as `napolitano` is easiest to work with.

### Enable both Kotlin options

In the generator's **Advanced Options**, enable both:

- **Kotlin Programming Language** — changes the source files from `.java` to `.kt` and configures Fabric's Kotlin support.
- **Kotlin Build Script** — changes the Gradle build files from Groovy (`.gradle`) to Kotlin (`.gradle.kts`).

This guide uses both options. The generator will also add the required `fabric-language-kotlin` dependency and configure the Kotlin entrypoint adapter for you. Do not add those dependencies manually unless the generated project is missing them.

If you already generated a Java project, the easiest and safest change is to generate a new project with these two options enabled. Simply renaming `.java` files to `.kt` is not enough because Gradle, the Fabric adapter, and the entrypoint configuration also need to change.

### Step 2.3: Generate and download the project

After checking the values:

1. Click **Generate**.
2. Download the generated ZIP file.
3. Close the download page.

The ZIP contains the starter project. Do not edit files inside the ZIP before extracting it.

### Step 2.4: Extract the project

On Windows, right-click the downloaded ZIP and choose **Extract All**. Extract it to a simple folder, for example:

```text
C:\Projects\napolitano
```

Avoid folders containing spaces, emojis, or cloud-storage folders such as OneDrive. These paths can cause problems with Gradle, the JDK, Kotlin, or Minecraft.

After extraction, the folder should contain files similar to:

```text
napolitano/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── gradle/
└── src/
```

What these important items mean:

- `build.gradle.kts` describes how the project is built using the Kotlin Gradle DSL.
- `settings.gradle.kts` contains Gradle project settings.
- `gradle.properties` contains the Minecraft, Fabric, Kotlin, and JDK version settings.
- `gradlew.bat` is the Windows Gradle Wrapper used to run commands.
- `src/main/kotlin` is where Kotlin code will go.
- `src/main/resources` is where names, models, textures, and data files will go.

If you want to use the existing `minecraft-mod` repository, extract the ZIP into a temporary folder and copy the generated contents into the repository root. Copy the contents, not the extra folder that contains them, and keep the existing `.git` folder.

### Step 2.5: Check the generated version settings

Before continuing, open `gradle.properties` and confirm that the generated Minecraft version is the version you intended to target. The generator normally fills this file correctly, so you should not need to edit it manually.

If you target a Minecraft version other than `26.2`, use that exact version in the generator. Do not mix the Kotlin code from this guide with a different Minecraft version without checking that version's Fabric documentation.

You are now ready for Step 3, where you will open this folder in VS Code.

---

## 3. Open the project in VS Code

In VS Code:

1. Select **File → Open Folder**
2. Select the folder containing `build.gradle.kts`
3. Click **Yes, I trust the authors** if VS Code asks
4. Wait for the Kotlin, Java, and Gradle extensions to import the project

VS Code will show the project files in the Explorer on the left. The Java/Kotlin extensions may ask you to choose a JDK. Select **JDK 25**.

You can also configure the Java runtime manually:

1. Press `Ctrl+Shift+P`
2. Search for **Java: Configure Java Runtime**
3. Add or select JDK 25

The project includes its own Gradle Wrapper, so you do not need to install Gradle separately. You can open the Gradle view from the Activity Bar and run tasks there, or use the integrated terminal.

---

## 4. Run the empty template first

Before adding the block, test that the template works.

Open the VS Code integrated terminal with **Terminal → New Terminal** or by pressing ``Ctrl+` ``. Make sure the terminal is opened in the project root, then run:

```powershell
.\gradlew.bat runClient
```

On Linux or macOS, use:

```sh
./gradlew runClient
```

This launches a special development version of Minecraft.

If Minecraft opens, the project is working correctly. Create a world and then close the game.

### Run with the VS Code debugger

VS Code can also create launch targets for the development client. Run this once in the integrated terminal:

```powershell
.\gradlew.bat vscode
```

Then:

1. Open the **Run and Debug** view with `Ctrl+Shift+D`
2. Select the generated Minecraft client launch target
3. Click the **Start Debugging** button or press `F5`

The terminal command is simpler for the first test, while the VS Code launch target is useful when you want to set breakpoints and debug Kotlin code.

Do not try to copy your mod files into the regular Minecraft installation. The `runClient` command is the correct way to test a development project.

---

# 5. Register the new block

We will create a block named:

```text
Ruby Block
```

Its internal ID will be:

```text
napolitano:ruby_block
```

The first part, `napolitano`, is the mod namespace. It must match your mod ID.

Create this file:

```text
src/main/kotlin/com/example/napolitano/ModBlocks.kt
```

Use this Kotlin code:

```kotlin
package com.example.napolitano

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour

object ModBlocks {
    val RUBY_BLOCK: Block = register(
        "ruby_block",
        { properties -> Block(properties) },
        BlockBehaviour.Properties.of()
            .sound(SoundType.STONE)
    )

    private fun register(
        name: String,
        blockFactory: (BlockBehaviour.Properties) -> Block,
        properties: BlockBehaviour.Properties
    ): Block {
        val blockKey: ResourceKey<Block> = ResourceKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath(Napolitano.MOD_ID, name)
        )

        val itemKey: ResourceKey<Item> = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(Napolitano.MOD_ID, name)
        )

        val block = blockFactory(properties.setId(blockKey))

        val blockItem = BlockItem(
            block,
            Item.Properties()
                .setId(itemKey)
                .useBlockDescriptionPrefix()
        )

        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem)

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block)
    }

    fun initialize() {
        CreativeModeTabEvents
            .modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
            .register { output -> output.accept(RUBY_BLOCK.asItem()) }
    }
}
```

### What this code does

This code:

- Creates a normal solid block
- Gives it the ID `napolitano:ruby_block`
- Creates a `BlockItem` so players can hold and place it
- Adds the block item to the Building Blocks creative tab

A `BlockItem` is technically an item, but it is required for the block to be placeable. It is not a second block.

The `object ModBlocks` declaration creates a Kotlin singleton. Its `initialize()` function is called by the mod's main entrypoint.

If your generated main class is not named `Napolitano`, replace `Napolitano.MOD_ID` in this file with the name of your generated main class. With the mod name `Napolitano`, the generator will normally create a class named `Napolitano`.

---

## 6. Tell Minecraft to load the block class

Open the generated Kotlin main class. With the project name `Napolitano`, it will normally be:

```text
src/main/kotlin/com/example/napolitano/Napolitano.kt
```

It should be similar to:

```kotlin
package com.example.napolitano

import net.fabricmc.api.ModInitializer

object Napolitano : ModInitializer {
    const val MOD_ID: String = "napolitano"

    override fun onInitialize() {
        ModBlocks.initialize()
    }
}
```

Make sure this line exists inside `onInitialize()`:

```kotlin
ModBlocks.initialize()
```

If the file already contains logging or other code, keep it and add the line instead of replacing everything.

Also check that:

```kotlin
const val MOD_ID: String = "napolitano"
```

uses the same mod ID that you chose in the generator.

The Kotlin template uses Fabric's Kotlin language adapter. The generator configures the adapter and the `fabric-language-kotlin` dependency automatically. If the game reports that the Kotlin adapter or entrypoint cannot be found, check that the project was generated with **Kotlin Programming Language** enabled and that the main class is in `src/main/kotlin`.

---

# 7. Add the block name

Create this file:

```text
src/main/resources/assets/napolitano/lang/en_us.json
```

Add:

```json
{
  "block.napolitano.ruby_block": "Ruby Block"
}
```

If the file already contains other translations, add this entry inside the existing JSON object and include the required comma.

---

# 8. Add the block model

Create this file:

```text
src/main/resources/assets/napolitano/models/block/ruby_block.json
```

Use:

```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "napolitano:block/ruby_block"
  }
}
```

This tells Minecraft to use the standard cube-shaped block model.

---

# 9. Add the block state

Create this file:

```text
src/main/resources/assets/napolitano/blockstates/ruby_block.json
```

Use:

```json
{
  "variants": {
    "": {
      "model": "napolitano:block/ruby_block"
    }
  }
}
```

The block state tells Minecraft which model to use when the block is placed in the world.

---

# 10. Add the inventory appearance

For Minecraft 26.2, create:

```text
src/main/resources/assets/napolitano/items/ruby_block.json
```

Use:

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "napolitano:block/ruby_block"
  }
}
```

This makes the block item appear correctly in the inventory.

> Older Minecraft versions may use a different item-model format. If you are targeting an older version, follow the version-specific Fabric block guide instead of using this file unchanged.

---

# 11. Add a texture

Create this folder:

```text
src/main/resources/assets/napolitano/textures/block
```

Add a PNG file named exactly:

```text
ruby_block.png
```

For a first test, you can create a simple 16 × 16 image using Paint or another image editor.

A Minecraft block texture is normally square, and 16 × 16 pixels is a good starting size.

Make sure Windows is not saving it as:

```text
ruby_block.png.txt
```

You can verify the file extension in the editor or File Explorer.

### Temporary texture-free test

If you do not have a texture yet, temporarily change this in the block model:

```json
"all": "napolitano:block/ruby_block"
```

to:

```json
"all": "minecraft:block/diamond_block"
```

That will use a vanilla texture for testing.

---

# 12. Make the block drop itself

This is optional, but recommended.

Create this file:

```text
src/main/resources/data/napolitano/loot_table/blocks/ruby_block.json
```

Use:

```json
{
  "type": "minecraft:block",
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "napolitano:ruby_block"
        }
      ],
      "conditions": [
        {
          "condition": "minecraft:survives_explosion"
        }
      ]
    }
  ]
}
```

This makes the block drop one Ruby Block when broken.

Without this file, the block can still be placed, but it may not drop anything in Survival mode.

---

# 13. Test the block

Run:

```powershell
.\gradlew.bat runClient
```

In the development Minecraft client:

1. Open the creative inventory
2. Go to **Building Blocks**
3. Find **Ruby Block**
4. Place it on the ground
5. Check that the texture appears
6. Break it in Survival mode
7. Check that it drops a Ruby Block

You can also test it with commands:

```text
/give @s napolitano:ruby_block
```

If you change resources while the game is open, try reloading resources with:

```text
F3 + T
```

If that does not work, close `runClient` and start it again.

---

# 14. Build the mod

When the block works, build the distributable JAR file:

```powershell
.\gradlew.bat build
```

The finished file will be in:

```text
build/libs/
```

It will have a name similar to:

```text
napolitano-1.0.0.jar
```

To play with it in a normal Minecraft launcher, you will later need to:

1. Install Fabric Loader for the same Minecraft version
2. Install the matching Fabric API
3. Put the JAR file in the launcher's `mods` folder

---

## Common problems

### The block does not appear

Check:

- `ModBlocks.initialize()` is inside `onInitialize()`
- The mod ID is exactly `napolitano`
- All resource folders use `napolitano`
- The file names use lowercase: `ruby_block.json`
- The game was restarted

### The block is invisible or has a missing texture

The log message `Missing model for variant` usually means that Minecraft cannot find the blockstate file. For Minecraft 26.2, the folder must be named exactly:

```text
blockstates
```

It must be plural. `blockstate` is incorrect.

Check that this file exists:

```text
src/main/resources/assets/napolitano/blockstates/ruby_block.json
```

Then check that this file exists:

```text
src/main/resources/assets/napolitano/textures/block/ruby_block.png
```

Also check:

```text
src/main/resources/assets/napolitano/models/block/ruby_block.json
```

The model file must reference the texture with the same namespace and name:

```json
"all": "napolitano:block/ruby_block"
```

Finally, make sure the mod ID in `fabric.mod.json`, the block code, and the resource folders all use the same namespace. This project uses `napolitano`.

### The block appears but has no name

Check:

```text
src/main/resources/assets/napolitano/lang/en_us.json
```

The translation key must be:

```text
block.napolitano.ruby_block
```

### The block does not drop anything

Add the loot table file and make sure the path is correct.

### Kotlin compilation errors

The most common causes are:

- Using code for a different Minecraft version
- Not selecting **Kotlin Programming Language** in the generator
- Not selecting **Kotlin Build Script** when using Kotlin Gradle files
- Having the file in `src/main/java` instead of `src/main/kotlin`
- Having a package or class name that does not match its file path
- Not waiting for the Kotlin extension to finish indexing the project

Fabric APIs and mappings change between versions. Check the selected Minecraft version before changing the code.

The first milestone is:

- The development client opens
- Ruby Block appears in the creative inventory
- Ruby Block can be placed
- Ruby Block has a texture
- Ruby Block drops itself when broken

---

## Useful official links

- [Fabric Template Generator](https://fabricmc.net/develop/template/)
- [Fabric documentation](https://docs.fabricmc.net/develop/)
- [Fabric first-block guide](https://docs.fabricmc.net/develop/blocks/first-block)
- [Fabric version downloads](https://fabricmc.net/develop/)
- [VS Code download](https://code.visualstudio.com/)
- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- [Kotlin by JetBrains for VS Code](https://marketplace.visualstudio.com/items?itemName=JetBrains.kotlin-server)
- [Fabric Language Kotlin](https://github.com/FabricMC/fabric-language-kotlin)
- [Fabric VS Code setup guide](https://docs.fabricmc.net/develop/getting-started/vscode/setting-up)
- [Adoptium JDK downloads](https://adoptium.net/)
