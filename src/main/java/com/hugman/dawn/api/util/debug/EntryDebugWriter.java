package com.hugman.dawn.api.util.debug;

import com.hugman.dawn.Dawn;
import com.hugman.dawn.api.util.FileUtil;
import com.hugman.dawn.api.util.debug.data.BlockEntryData;
import com.hugman.dawn.api.util.debug.data.SimpleEntryData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class EntryDebugWriter {
	public final void load() {
		for(Registry<?> registry : Registry.REGISTRIES) {
			Map<String, LinkedHashSet<Identifier>> entryDataList = new HashMap<>();
			for(Identifier entryID : registry.getIds()) {
				LinkedHashSet<Identifier> set = entryDataList.get(entryID.getNamespace());
				if(set != null) {
					set.add(entryID);
				}
				else {
					LinkedHashSet<Identifier> newSet = new LinkedHashSet<>();
					newSet.add(entryID);
					entryDataList.put(entryID.getNamespace(), newSet);
				}
			}
			if(registry.getKey().getValue().equals(Registry.BLOCK.getKey().getValue())) {
				entryDataList.forEach((namespace, identifiers) -> new BlockEntryData(namespace, identifiers).save());
			}
			else {
				entryDataList.forEach((namespace, identifiers) -> new SimpleEntryData(namespace, registry, identifiers).save());
			}
		}
		Dawn.LOGGER.info("Created debug entry files");
	}
}
