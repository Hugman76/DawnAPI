package com.hugman.dawn.api.creator.pack.block;

import com.hugman.dawn.api.creator.BlockCreator;
import com.hugman.dawn.api.creator.pack.Pack;
import com.hugman.dawn.api.util.DefaultBlockGetter;
import com.hugman.dawn.api.util.ModData;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;

public class WoodPack extends Pack {
	private final Block planks;
	private final LogsPack logs;
	private final MSBlockPack woodenBlocks;
	private final MSBlockPack barkBlocks;

	protected WoodPack(ModData modData, String name, MapColor planksColor, MapColor insideColor, MapColor barkColor, boolean isNether) {
		FabricBlockSettings settings = FabricBlockSettings.copyOf(isNether ? Blocks.CRIMSON_PLANKS : Blocks.OAK_PLANKS).mapColor(planksColor);
		this.planks = add(new BlockCreator.Builder(name, DefaultBlockGetter.PLANKS, settings), modData);
		this.logs = add(new LogsPack.Builder(name, insideColor, barkColor, isNether), modData);
		this.woodenBlocks = add(new MSBlockPack.Builder(name, settings, DefaultBlockGetter.STAIRS, DefaultBlockGetter.SLAB, DefaultBlockGetter.TRAPDOOR, DefaultBlockGetter.WOOD_PRESSURE_PLATE, DefaultBlockGetter.WOOD_BUTTON, DefaultBlockGetter.FENCE, DefaultBlockGetter.FENCE_GATE, DefaultBlockGetter.DOOR), modData);
		this.barkBlocks = add(new MSBlockPack.Builder(name + logs.getWoodName(), settings.mapColor(barkColor), DefaultBlockGetter.STAIRS, DefaultBlockGetter.SLAB, DefaultBlockGetter.WOOD_BUTTON), modData);
	}

	public Block getPlanks() {
		return planks;
	}

	public Block getLog() {
		return logs.getLog();
	}

	public Block getStrippedLog() {
		return logs.getStrippedLog();
	}

	public Block getWood() {
		return logs.getWood();
	}

	public Block getStrippedWood() {
		return logs.getStrippedWood();
	}

	public Block getWoodStairs() {
		return barkBlocks.getBlock(DefaultBlockGetter.STAIRS);
	}

	public Block getWoodSlab() {
		return barkBlocks.getBlock(DefaultBlockGetter.SLAB);
	}

	public Block getWoodButton() {
		return barkBlocks.getBlock(DefaultBlockGetter.WOOD_BUTTON);
	}

	public Block getPressurePlate() {
		return woodenBlocks.getBlock(DefaultBlockGetter.WOOD_PRESSURE_PLATE);
	}

	public Block getTrapdoor() {
		return woodenBlocks.getBlock(DefaultBlockGetter.TRAPDOOR);
	}

	public Block getButton() {
		return woodenBlocks.getBlock(DefaultBlockGetter.WOOD_BUTTON);
	}

	public Block getStairs() {
		return woodenBlocks.getBlock(DefaultBlockGetter.STAIRS);
	}

	public Block getSlab() {
		return woodenBlocks.getBlock(DefaultBlockGetter.SLAB);
	}

	public Block getFenceGate() {
		return woodenBlocks.getBlock(DefaultBlockGetter.FENCE_GATE);
	}

	public Block getFence() {
		return woodenBlocks.getBlock(DefaultBlockGetter.FENCE);
	}

	public Block getDoor() {
		return woodenBlocks.getBlock(DefaultBlockGetter.DOOR);
	}

	public static class Builder implements Pack.Builder {
		private final String name;
		private final MapColor planksColor;
		private final MapColor insideColor;
		private final MapColor barkColor;
		private final boolean isNether;

		/**
		 * Creates an entry pack containing blocks for basic wood types.
		 *
		 * @param name        The name of the wood type. (ex: <code>oak</code>)
		 * @param planksColor The map color of the planks.
		 * @param insideColor The map color of the inside of logs.
		 * @param barkColor   The map color of the bark side of logs.
		 * @param isNether    Defines if the wood type comes from the nether. (changes the name, sounds and materials)
		 */
		public Builder(String name, MapColor planksColor, MapColor insideColor, MapColor barkColor, boolean isNether) {
			this.name = name;
			this.planksColor = planksColor;
			this.insideColor = insideColor;
			this.barkColor = barkColor;
			this.isNether = isNether;
		}

		public WoodPack build(ModData modData) {
			return new WoodPack(modData, name, planksColor, insideColor, barkColor, isNether);
		}
	}
}
