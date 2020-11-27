package com.hugman.dawn.api.creator.pack.block;

import com.hugman.dawn.api.creator.BlockCreator;
import com.hugman.dawn.api.creator.pack.Pack;
import com.hugman.dawn.api.object.block.SaplingBlock;
import com.hugman.dawn.api.util.BlockSettings;
import com.hugman.dawn.api.util.ModData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.ItemGroup;

import java.util.function.Predicate;

public class NormalWoodPack extends WoodPack {
	private final PottedPlantPack saplingPack;
	private final LeavesPack leavesPack;

	protected NormalWoodPack(ModData modData, String suffix, SaplingGenerator saplingGenerator, Predicate<BlockState> saplingSoilPredicate, MapColor planksColor, MapColor insideColor, MapColor barkColor) {
		super(modData, suffix, planksColor, insideColor, barkColor, false);
		this.saplingPack = add(new PottedPlantPack.Builder(new BlockCreator.Builder(suffix + "_sapling", new SaplingBlock(saplingGenerator, saplingSoilPredicate, BlockSettings.SAPLING)).itemGroup(ItemGroup.DECORATIONS).render(BlockCreator.Render.CUTOUT_MIPPED)), modData);
		this.leavesPack = add(new LeavesPack.Builder(suffix), modData);
	}

	public Block getSapling() {
		return saplingPack.getPlant();
	}

	public Block getPottedSapling() {
		return saplingPack.getPottedPlant();
	}

	public Block getLeaves() {
		return leavesPack.getLeaves();
	}

	public Block getLeafPile() {
		return leavesPack.getLeafPile();
	}

	public static class Builder implements Pack.Builder {
		private final String suffix;
		private final SaplingGenerator saplingGenerator;
		private final MapColor planksColor;
		private final MapColor insideColor;
		private final MapColor barkColor;
		private Predicate<BlockState> saplingSoilPredicate;

		/**
		 * Creates an entry pack containing blocks for normal wood types.
		 *
		 * @param suffix           The suffix of the wood type. (ex: <code>crimson</code>)
		 * @param saplingGenerator The tree generator for the sapling.
		 * @param woodColor        The map color of the wood.
		 * @param barkColor        The map color of the bark side of logs.
		 */
		public Builder(String suffix, SaplingGenerator saplingGenerator, MapColor woodColor, MapColor barkColor) {
			this(suffix, saplingGenerator, woodColor, woodColor, barkColor);
		}

		/**
		 * Creates an entry pack containing blocks for normal wood types.
		 *
		 * @param suffix           The suffix of the wood type. (ex: <code>crimson</code>)
		 * @param saplingGenerator The tree generator for the sapling.
		 * @param planksColor      The map color of the planks.
		 * @param insideColor      The map color of the inside of logs.
		 * @param barkColor        The map color of the bark side of logs.
		 */
		public Builder(String suffix, SaplingGenerator saplingGenerator, MapColor planksColor, MapColor insideColor, MapColor barkColor) {
			this.suffix = suffix;
			this.saplingGenerator = saplingGenerator;
			this.planksColor = planksColor;
			this.insideColor = insideColor;
			this.barkColor = barkColor;
		}

		/**
		 * Adds a predicate for blocks that the sapling can grow on.
		 *
		 * @param predicate The predicate for the allowed states.
		 */
		public Builder saplingSoil(Predicate<BlockState> predicate) {
			this.saplingSoilPredicate = predicate;
			return this;
		}

		public NormalWoodPack build(ModData modData) {
			return new NormalWoodPack(modData, suffix, saplingGenerator, saplingSoilPredicate, planksColor, insideColor, barkColor);
		}
	}
}
