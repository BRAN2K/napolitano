package com.example.napolitano

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.RandomSource
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

object ModBlocks {
    private val RUBY_BLOCK_SOUND_TYPE = SoundType(
        1.0F,
        1.0F,
        SoundEvents.STONE_BREAK,
        SoundEvents.AMETHYST_BLOCK_STEP,
        SoundEvents.STONE_PLACE,
        SoundEvents.STONE_HIT,
        SoundEvents.STONE_FALL
    )

    val RUBY_BLOCK: Block = register(
        "ruby_block",
        { properties -> RubyBlock(properties) },
        BlockBehaviour.Properties.of()
            .sound(RUBY_BLOCK_SOUND_TYPE)
            .lightLevel { 5 }
            .strength(5.0F, 6.0F)
            .requiresCorrectToolForDrops()
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

private class RubyBlock(properties: BlockBehaviour.Properties) : Block(properties) {
    private val particleColors = intArrayOf(
        0xC8321B,
        0xD84020,
        0xE64B24,
        0xF05B2A,
        0xF47A32
    )

    override fun animateTick(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        random: RandomSource
    ) {
        repeat(2 + random.nextInt(3)) {
            spawnRubyParticle(level, pos, random)
        }
    }

    private fun spawnRubyParticle(level: Level, pos: BlockPos, random: RandomSource) {
        var x = pos.x + random.nextDouble()
        var y = pos.y + random.nextDouble()
        var z = pos.z + random.nextDouble()
        var velocityX = (random.nextDouble() - 0.5) * 0.01
        var velocityY = 0.004 + random.nextDouble() * 0.006
        var velocityZ = (random.nextDouble() - 0.5) * 0.01

        when (random.nextInt(6)) {
            0 -> {
                x = pos.x - 0.02
                velocityX = -0.008 - random.nextDouble() * 0.008
            }
            1 -> {
                x = pos.x + 1.02
                velocityX = 0.008 + random.nextDouble() * 0.008
            }
            2 -> {
                y = pos.y - 0.02
                velocityY = -0.008 - random.nextDouble() * 0.008
            }
            3 -> {
                y = pos.y + 1.02
                velocityY = 0.008 + random.nextDouble() * 0.008
            }
            4 -> {
                z = pos.z - 0.02
                velocityZ = -0.008 - random.nextDouble() * 0.008
            }
            else -> {
                z = pos.z + 1.02
                velocityZ = 0.008 + random.nextDouble() * 0.008
            }
        }

        level.addParticle(
            DustParticleOptions(
                particleColors[random.nextInt(particleColors.size)],
                1.0F + random.nextFloat()
            ),
            x,
            y,
            z,
            velocityX,
            velocityY,
            velocityZ
        )
    }
}
