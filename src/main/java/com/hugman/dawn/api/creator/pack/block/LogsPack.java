package com.hugman.dawn.api.creator.pack.block;

import com.hugman.dawn.api.creator.BlockCreator;
import com.hugman.dawn.api.creator.pack.Pack;
import com.hugman.dawn.api.object.item.AxeItem;
import com.hugman.dawn.api.util.BlockSettings;
import com.hugman.dawn.api.util.ModData;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

public class LogsPack extends Pack {
	private final Block log;
	private final Block strippedLog;
	private final Block wood;
	private final Block strippedWood;

	private final String logName;
	private final String woodName;
	private final MapColor insideColor;
	private final MapColor barkColor;
	private final boolean isNether;

	protected LogsPack(ModData modData, String name, MapColor insideColor, MapColor barkColor, boolean isNether) {
		this.logName = isNether ? "_stem" : "_log";
		this.woodName = isNether ? "_hyphae" : "_wood";
		this.insideColor = insideColor;
		this.barkColor = barkColor;
		this.isNether = isNether;
		int flammability = isNether ? 0 : 5;
		this.log = add(new BlockCreator.Builder(name + logName, createLog()).itemGroup(ItemGroup.BUILDING_BLOCKS).flammability(flammability), modData);
		this.strippedLog = add(new BlockCreator.Builder("stripped_" + name + logName, createLog(false)).itemGroup(ItemGroup.BUILDING_BLOCKS).flammability(flammability), modData);
		this.wood = add(new BlockCreator.Builder(name + woodName, createLog(true)).itemGroup(ItemGroup.BUILDING_BLOCKS).flammability(flammability), modData);
		this.strippedWood = add(new BlockCreator.Builder("stripped_" + name + woodName, createLog(false)).itemGroup(ItemGroup.BUILDING_BLOCKS).flammability(flammability), modData);
		AxeItem.BLOCK_STRIPPING_MAP.put(getLog(), getStrippedLog());
		AxeItem.BLOCK_STRIPPING_MAP.put(getWood(), getStrippedWood());

	}

	private Block createLog(boolean isSideBark) {
		FabricBlockSettings settings = isNether ? BlockSettings.STEM : BlockSettings.LOG;
		return new PillarBlock(settings.materialColor(isSideBark ? barkColor : insideColor));
	}

	private Block createLog() {
		return new PillarBlock(AbstractBlock.Settings.of(isNether ? Material.NETHER_WOOD : Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? insideColor : barkColor).strength(2.0F).sounds(isNether ? BlockSoundGroup.NETHER_STEM : BlockSoundGroup.WOOD));
	}

	public Block getLog() {
		return log;
	}

	public Block getStrippedLog() {
		return strippedLog;
	}

	public Block getWood() {
		return wood;
	}

	public Block getStrippedWood() {
		return strippedWood;
	}

	protected String getLogName() {
		return logName;
	}

	protected String getWoodName() {
		return woodName;
	}

	public static class Builder implements Pack.Builder {
		private final String name;
		private final MapColor insideColor;
		private final MapColor barkColor;
		private final boolean isNether;

		/**
		 * Creates a creator pack containing a log, a wood block and their stripped variants.
		 *
		 * @param name        The name of the wood type. (ex: <code>oak</code>)
		 * @param insideColor The map color of the inside of logs.
		 * @param barkColor   The map color of the bark side of logs.
		 * @param isNether    Defines if the wood type comes from the nether. (changes the name, sounds and materials)
		 */
		public Builder(String name, MapColor insideColor, MapColor barkColor, boolean isNether) {
			this.name = name;
			this.insideColor = insideColor;
			this.barkColor = barkColor;
			this.isNether = isNether;
		}

		public LogsPack build(ModData modData) {
			return new LogsPack(modData, name, insideColor, barkColor, isNether);
		}
	}
}
