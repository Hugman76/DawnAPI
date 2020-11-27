package com.hugman.dawn.api.creator.pack.block;

import com.hugman.dawn.api.creator.pack.Pack;
import com.hugman.dawn.api.util.ModData;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class NetherWoodPack extends WoodPack {
	private final PottedPlantPack fungusPack;

	protected NetherWoodPack(ModData modData, String suffix, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> hugeFungusSupplier, MapColor planksColor, MapColor insideColor, MapColor barkColor) {
		super(modData, suffix, planksColor, insideColor, barkColor, true);
		this.fungusPack = add(new FungusPack.Builder(suffix, hugeFungusSupplier), modData);
	}

	public Block getStem() {
		return getLog();
	}

	public Block getStrippedStem() {
		return getStrippedLog();
	}

	public Block getHyphae() {
		return getWood();
	}

	public Block getStrippedHyphae() {
		return getStrippedWood();
	}

	public Block getHyphaeStairs() {
		return getWoodStairs();
	}

	public Block getHyphaeSlab() {
		return getWoodSlab();
	}

	public Block getHyphaeButton() {
		return getWoodButton();
	}

	public Block getFungus() {
		return fungusPack.getPlant();
	}

	public Block getPottedFungus() {
		return fungusPack.getPottedPlant();
	}

	public static class Builder implements Pack.Builder {
		private final String suffix;
		private final Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> hugeFungusSupplier;
		private final MapColor planksColor;
		private final MapColor insideColor;
		private final MapColor barkColor;

		/**
		 * Creates an entry pack containing blocks for nether wood types.
		 *
		 * @param suffix             The suffix of the wood type. (ex: <code>crimson</code>)
		 * @param hugeFungusSupplier The supplier for the huge fungus feature.
		 * @param woodColor          The map color of the wood.
		 * @param barkColor          The map color of the bark side of stems.
		 */
		public Builder(String suffix, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> hugeFungusSupplier, MapColor woodColor, MapColor barkColor) {
			this(suffix, hugeFungusSupplier, woodColor, woodColor, barkColor);
		}

		/**
		 * Creates an entry pack containing blocks for nether wood types.
		 *
		 * @param suffix             The suffix of the wood type. (ex: <code>crimson</code>)
		 * @param hugeFungusSupplier The supplier for the huge fungus feature.
		 * @param planksColor        The map color of the planks.
		 * @param insideColor        The map color of the inside of stems.
		 * @param barkColor          The map color of the bark side of stems.
		 */
		public Builder(String suffix, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> hugeFungusSupplier, MapColor planksColor, MapColor insideColor, MapColor barkColor) {
			this.suffix = suffix;
			this.hugeFungusSupplier = hugeFungusSupplier;
			this.planksColor = planksColor;
			this.insideColor = insideColor;
			this.barkColor = barkColor;
		}

		public NetherWoodPack build(ModData modData) {
			return new NetherWoodPack(modData, suffix, hugeFungusSupplier, planksColor, insideColor, barkColor);
		}
	}
}
