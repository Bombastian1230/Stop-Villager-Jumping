package stop.villager.jumping;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stop.villager.jumping.item.Legcuffs;

public class StopVillagerJumpingMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String MODID = "stop-villager-jumping";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);


	public static final Legcuffs BALL_AND_CHAIN_ITEM =
			Registry.register(Registries.ITEM, new Identifier(MODID, "ball_and_chain"),
					new Legcuffs(new FabricItemSettings().maxCount(1)));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.add(BALL_AND_CHAIN_ITEM);
		});
	}
}