//Add code here.
package net.draco.src;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
	
@Mod(modid = "draco_animus", name = "Draco Animus", version = "v0.03")
public class Draco_Animus_ModCore {

	public Draco_Animus_ModCore() {
	
	}
		@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
			Draco_Animus_ModBlocks.BlockInformation();
	OthersDraco_Animus.OtherInfo();
				
	}
	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		
	}
}
