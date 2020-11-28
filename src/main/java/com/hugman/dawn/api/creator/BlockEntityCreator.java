package com.hugman.dawn.api.creator;

import com.hugman.dawn.api.util.ModData;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class BlockEntityCreator<E extends BlockEntity> extends Creator<BlockEntityType<E>> {
	private BlockEntityCreator(String name, FabricBlockEntityTypeBuilder<E> builder, ModData modData) {
		super(modData, name, builder.build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, name)));
	}

	@Override
	public void register() {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, modData.id(name), value);
	}

	public static class Builder<E extends BlockEntity> implements Creator.Builder<BlockEntityType<E>> {
		protected final String name;
		protected final FabricBlockEntityTypeBuilder<E> builder;

		/**
		 * Creates a block entity type.
		 *
		 * @param name    The name of the block entity.
		 * @param builder The block entity type builder.
		 */
		public Builder(String name, FabricBlockEntityTypeBuilder<E> builder) {
			this.name = name;
			this.builder = builder;
		}

		public BlockEntityCreator<E> build(ModData modData) {
			return new BlockEntityCreator<>(this.name, this.builder, modData);
		}
	}
}
