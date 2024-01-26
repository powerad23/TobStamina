package com.TobStamina;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TobStaminaTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TobStaminaPlugin.class);
		RuneLite.main(args);
	}
}