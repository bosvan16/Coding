package net.wildbill22.draco;

import java.io.File;
import java.lang.reflect.Field;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.wildbill22.draco.lib.BALANCE;
import net.wildbill22.draco.lib.DefaultBoolean;
import net.wildbill22.draco.lib.DefaultDouble;
import net.wildbill22.draco.lib.DefaultInt;
import net.wildbill22.draco.lib.LogHelper;
import net.wildbill22.draco.lib.REFERENCE;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Configs {

	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
		}
		loadConfiguration();
	}
	/**
	 * Loads/refreshes the configuration and adds comments if there aren't any
	 * {@link #init(File) init} has to be called once before using this
	 */
	private static void loadConfiguration() {
		// Categories
		ConfigCategory cat_balance = config.getCategory(CATEGORY_BALANCE);
		cat_balance.setComment("You can adjust these values to change the balancing of this mod");
		ConfigCategory cat_village = config.getCategory(CATEGORY_VILLAGE);
		cat_village.setComment("Here you can configure the village generation");
		ConfigCategory cat_balance_leveling = config.getCategory(CATEGORY_BALANCE_LEVELING);
		cat_balance_leveling.setComment("You can adjust these values to change the level up requirements");
		ConfigCategory cat_balance_mobprop = config.getCategory(CATEGORY_BALANCE_MOBPROP);
		cat_balance_leveling.setComment("You can adjust the properties of the added mobs");

		// Village
		village_gen_enabled = config.get(cat_village.getQualifiedName(), "enabled", true,
				"Should the custom generator be injected? (Enables/Disables the village mod)").getBoolean();
		village_density = config.get(cat_village.getQualifiedName(), "density", 15,
				"Minecraft will try to generate 1 village per NxN chunk area. Vanilla: 32").getInt();
		village_minDist = config.get(cat_village.getQualifiedName(), "minimumDistance", 4,
				"Village centers will be at least N chunks apart. Must be smaller than density. Vanilla: 8").getInt();
		village_size = config.get(cat_village.getQualifiedName(), "size", 0, "A higher size increases the overall spawn weight of buildings.")
				.getInt();

		if (village_minDist < 0) {
			LogHelper.error("VillageDensity: Invalid config: Minimal distance must be non-negative.");
			village_gen_enabled = false;
		}
		if (village_minDist >= village_density) {
			LogHelper.error("VillageDensity: Invalid config: Minimal distance must be smaller than density.");
			village_gen_enabled = false;
		}
		if (village_size < 0) {
			village_gen_enabled = false;
			LogHelper.error("VillageDensity: Invalid config: Size must be non-negative.");
		}

		// Balance
		loadFields(cat_balance, BALANCE.class);
		loadFields(cat_balance_leveling, BALANCE.LEVELING.class);
		loadFields(cat_balance_mobprop, BALANCE.MOBPROP.class);

		if (config.hasChanged()) {
			config.save();
		}
	}
	/**
	 * This methods makes variables from a class available in the config file.
	 * To use this, the given class can only contain static non-final variables,
	 * which should have a '@Default*' annotation containing the default value.
	 * Currently only boolean, int, and double are supported
	 * 
	 * @param cat
	 *            Config Category to put the properties inside
	 * @param cls
	 *            Class to go through
	 * @author Maxanier
	 */
	@SuppressWarnings("rawtypes")
	private static void loadFields(ConfigCategory cat, Class cls) {
		for (Field f : cls.getDeclaredFields()) {
			String name = f.getName();
			Class type = f.getType();
			try {
				if (type == int.class) {
					// Possible exception should not be caught so you can't forget a default value					
					DefaultInt a = f.getAnnotation(DefaultInt.class); 
					f.set(null, config.get(cat.getQualifiedName(), name, a.value(), a.comment()).getInt());
				} else if (type == double.class) {
					// Possible exception should not be caught so you can't forget a default value					
					DefaultDouble a = f.getAnnotation(DefaultDouble.class); 
					f.set(null, config.get(cat.getQualifiedName(), name, a.value(), a.comment()).getDouble());
				} else if (type == boolean.class) {
					DefaultBoolean a = f.getAnnotation(DefaultBoolean.class); 
					f.set(null, config.get(cat.getQualifiedName(), name, a.value(), a.comment()).getBoolean());
				}
			} catch (NullPointerException e1) {
				LogHelper.error("Configs: Author probably forgot to specify a default value for " + name + " in " + cls.getCanonicalName() + e1);
				throw new Error("Please check you default values");
			} catch (Exception e) {
				LogHelper.error("Configs: Cant set " + cls.getName() + " values" + e);
				throw new Error("Please check your DracoAnimus config file");
			}
		}
	}
	public static final String CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL;
	public static final String CATEGORY_VILLAGE = "village_settings";
	public static final String CATEGORY_BALANCE = "balance";

	public static final String CATEGORY_BALANCE_LEVELING = "balance_leveling";
	public static final String CATEGORY_BALANCE_MOBPROP = "balance_mob_properties";
	public static boolean village_gen_enabled;

	public static int village_density;

	public static int village_minDist;

	public static int village_size;

	public static Configuration config;

	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.modID.equalsIgnoreCase(REFERENCE.MODID)) {
			// Resync configs
			LogHelper.info("Configs: Configuration has changed");
			Configs.loadConfiguration();
		}
	}

}