package com.TobStamina;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Tob Stamina"
)
public class TobStaminaPlugin extends Plugin {
	private TobStaminaOverlayPanel gearCheckerOverlayPanel = null;
	public boolean inTOB = false;

	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private TobStaminaConfig config;

	@Override
	protected void startUp() {
		//startup the display panel
		gearCheckerOverlayPanel = new TobStaminaOverlayPanel(this, false);

	}
	@Subscribe
	public void onGameTick(GameTick event) {
		//Check first to ensure we are in Sote
		if (!isInSote()) {
			//if we aren't in sote
			log.info("Not In Sote!");
			inTOB = false;
		}
		if (isInSote()) {
			log.info("In Sote!");
			inTOB = true;
			//function to check inventory for stam
			if (!hasStam()) {
				//player does not have a stamina, warn them!!!
				log.info("No stam Sote!");
				updateInfoBox();
			}

		}
		updateInfoBox();
	}

	public boolean hasStam() {
		//create container object with players inventory as Inventory ID
		ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);
		if (container == null) {
			return false;
		}
		Item[] items = container.getItems(); //the list of all items in Inventory
		boolean invContainsStam = false;
		//for each item in items list
		for (Item item : items) {
			if (item == null) {
				continue;
			}
			if (item.getId() == ItemID.STAMINA_POTION4) {
				invContainsStam = true;
			}
			return invContainsStam;
		}
		return invContainsStam;
	}

	//Check to see if player is in the sote room
	public boolean isInSote() {
		Player localPlayer = client.getLocalPlayer();
		int test = localPlayer.getWorldLocation().getRegionID();
		//64311 is region ID
		if (localPlayer == null) {
			return false;
		} else return test == 64311;
	}

	@Provides
	TobStaminaConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TobStaminaConfig.class);
	}

	private void updateInfoBox() {
		if (!isInSote() && gearCheckerOverlayPanel.isVisible()) {
			overlayManager.remove(gearCheckerOverlayPanel);
			gearCheckerOverlayPanel.setVisible(false);
			log.info("Example stopped!");
		} else if(isInSote() && !gearCheckerOverlayPanel.isVisible()) {
			overlayManager.add(gearCheckerOverlayPanel);
			gearCheckerOverlayPanel.setVisible(true);
			log.info("Example started!");
		}
	}
}

