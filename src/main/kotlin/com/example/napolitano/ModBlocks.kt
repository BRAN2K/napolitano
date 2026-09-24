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
