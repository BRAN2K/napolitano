package com.example.napolitano.client

import com.example.napolitano.ModBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey
import net.fabricmc.fabric.api.client.model.loading.v1.FabricModelManager
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel
import net.fabricmc.fabric.api.client.model.loading.v1.wrapper.WrapperBlockStateModel
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.BlockAndTintGetter
import net.minecraft.client.renderer.chunk.ChunkSectionLayer
import net.minecraft.client.renderer.block.dispatch.BlockStateModel
import net.minecraft.client.resources.model.geometry.BakedQuad
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.Identifier
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Predicate

private val RUBY_BLOCK_EMISSIVE_MODEL_ID = Identifier.fromNamespaceAndPath(
    "napolitano",
    "block/ruby_block_emissive"
)
private val RUBY_BLOCK_EMISSIVE_MODEL_KEY = ExtraModelKey.create<BlockStateModel> {
    "napolitano:block/ruby_block_emissive"
}

object NapolitanoClient : ClientModInitializer {
	override fun onInitializeClient() {
		ModelLoadingPlugin.register { context ->
			context.addModel(
				RUBY_BLOCK_EMISSIVE_MODEL_KEY,
				SimpleUnbakedExtraModel.blockStateModel(RUBY_BLOCK_EMISSIVE_MODEL_ID)
			)
			context.modifyBlockModelAfterBake().register { model, modelContext ->
				if (modelContext.state().block === ModBlocks.RUBY_BLOCK) {
					EmissiveRubyBlockModel(model)
				} else {
					model
				}
			}
		}
	}
}

private class EmissiveRubyBlockModel(private val baseModel: BlockStateModel) : WrapperBlockStateModel(baseModel) {
	override fun materialFlags(): Int = baseModel.materialFlags() or BakedQuad.FLAG_TRANSLUCENT

	override fun materialFlags(
		level: BlockAndTintGetter,
		pos: BlockPos,
		state: BlockState,
		random: RandomSource
	): Int = baseModel.materialFlags(level, pos, state, random) or BakedQuad.FLAG_TRANSLUCENT

	override fun emitQuads(
		emitter: QuadEmitter,
		level: BlockAndTintGetter,
		pos: BlockPos,
		state: BlockState,
		random: RandomSource,
		cullTest: Predicate<Direction?>
	) {
		super.emitQuads(emitter, level, pos, state, random, cullTest)

		val modelManager = Minecraft.getInstance().modelManager as FabricModelManager
		val emissiveModel = modelManager.getModel(RUBY_BLOCK_EMISSIVE_MODEL_KEY) ?: return
		emitter.pushTransform { quad ->
			val face = quad.lightFace()
			val offset = 0.001F
			for (vertex in 0..3) {
				quad.pos(
					vertex,
					quad.x(vertex) + face.stepX * offset,
					quad.y(vertex) + face.stepY * offset,
					quad.z(vertex) + face.stepZ * offset
				)
			}
			quad.chunkLayer(ChunkSectionLayer.TRANSLUCENT)
			quad.emissive(true)
			quad.diffuseShade(false)
			quad.ambientOcclusion(TriState.FALSE)
			true
		}
		try {
			emissiveModel.emitQuads(emitter, level, pos, state, random, cullTest)
		} finally {
			emitter.popTransform()
		}
	}
}
