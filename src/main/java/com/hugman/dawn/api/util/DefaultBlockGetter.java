package com.hugman.dawn.api.util;

import com.hugman.dawn.api.creator.BlockCreator;
import com.hugman.dawn.api.object.block.DoorBlock;
import com.hugman.dawn.api.object.block.UnfertilizableMushroomPlantBlock;
import com.hugman.dawn.api.object.block.PressurePlateBlock;
import com.hugman.dawn.api.object.block.StairsBlock;
import com.hugman.dawn.api.object.block.StoneButtonBlock;
import com.hugman.dawn.api.object.block.TrapdoorBlock;
import com.hugman.dawn.api.object.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.ItemGroup;

public enum DefaultBlockGetter implements BlockGetter {
	CUBE("", ItemGroup.BUILDING_BLOCKS),
	PLANKS("planks", ItemGroup.BUILDING_BLOCKS),
	STAIRS("stairs", ItemGroup.BUILDING_BLOCKS),
	SLAB("slab", ItemGroup.BUILDING_BLOCKS),
	WALL("wall", ItemGroup.DECORATIONS),
	STONE_BUTTON("button", ItemGroup.REDSTONE),
	WOOD_BUTTON("button", ItemGroup.REDSTONE),
	STONE_PRESSURE_PLATE("pressure_plate", ItemGroup.REDSTONE),
	WOOD_PRESSURE_PLATE("pressure_plate", ItemGroup.REDSTONE),
	TRAPDOOR("trapdoor", ItemGroup.REDSTONE, BlockCreator.Render.CUTOUT),
	DOOR("door", ItemGroup.REDSTONE, BlockCreator.Render.CUTOUT),
	FENCE("fence", ItemGroup.DECORATIONS),
	FENCE_GATE("fence_gate", ItemGroup.REDSTONE),
	LEAVES("leaves", ItemGroup.DECORATIONS, BlockCreator.Render.CUTOUT_MIPPED),
	LEAF_PILE("leaf_pile", ItemGroup.DECORATIONS, BlockCreator.Render.CUTOUT_MIPPED),
	PLANT_PILE("pile", ItemGroup.DECORATIONS, BlockCreator.Render.CUTOUT_MIPPED),
	MUSHROOM_BLOCK("mushroom_block", ItemGroup.DECORATIONS),
	MUSHROOM("mushroom", ItemGroup.DECORATIONS);

	private final String suffix;
	private final ItemGroup itemGroup;
	private final BlockCreator.Render render;

	DefaultBlockGetter(String suffix, ItemGroup itemGroup, BlockCreator.Render render) {
		this.suffix = suffix;
		this.itemGroup = itemGroup;
		this.render = render;
	}

	DefaultBlockGetter(String suffix, ItemGroup itemGroup) {
		this(suffix, itemGroup, null);
	}

	public String getSuffix() {
		return suffix;
	}

	public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public BlockCreator.Render getRender() {
		return render;
	}

	public Block getBlock(AbstractBlock.Settings settings) {
		FabricBlockSettings newSettings = FabricBlockSettings.copyOf(settings);
		switch(this) {
			case CUBE:
			default:
				return new Block(newSettings);
			case SLAB:
				return new SlabBlock(newSettings);
			case STAIRS:
				return new StairsBlock(newSettings);
			case WALL:
				return new WallBlock(newSettings);
			case STONE_PRESSURE_PLATE:
				return new PressurePlateBlock(ActivationRule.MOBS, newSettings.requiresTool().noCollision().strength(0.5F));
			case WOOD_PRESSURE_PLATE:
				return new PressurePlateBlock(ActivationRule.EVERYTHING, newSettings.noCollision().strength(0.5F));
			case STONE_BUTTON:
				return new StoneButtonBlock(newSettings.noCollision().strength(0.5F));
			case WOOD_BUTTON:
				return new WoodButtonBlock(newSettings.noCollision().strength(0.5F));
			case TRAPDOOR:
				return new TrapdoorBlock(newSettings.strength(3.0F).nonOpaque().allowsSpawning(BlockSettings::never));
			case DOOR:
				return new DoorBlock(newSettings.strength(3.0F).nonOpaque());
			case FENCE:
				return new FenceBlock(newSettings);
			case FENCE_GATE:
				return new FenceGateBlock(newSettings);
			case LEAVES:
				return new LeavesBlock(newSettings);
			case LEAF_PILE:
			case PLANT_PILE:
				return new PlantPileBlock(newSettings);
			case MUSHROOM:
				return new UnfertilizableMushroomPlantBlock(newSettings);
			case MUSHROOM_BLOCK:
				return new MushroomBlock(newSettings);
		}
	}
}
