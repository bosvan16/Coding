package net.wildbill22.draco.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * 
 * @author WILLIAM
 *
 */
public class BiomeTownForest extends BiomeGenBase {
	public final static String name = "Town Forest";

	public BiomeTownForest(int id) {
		super(id);

		this.setTemperatureRainfall(0.7F, 0.8F);
        this.setHeight(BiomeGenBase.height_LowPlains);

        this.fillerBlock = Blocks.dirt;
    	this.theBiomeDecorator.treesPerChunk = 6;
		this.theBiomeDecorator.grassPerChunk = 5;
		this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 4;        
	}
	
    /**
     * Normally provides the basic grass color based on the biome temperature and rainfall
     */
//	@Override
//    @SideOnly(Side.CLIENT)
//    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
//    {
//		int grassColor = 0x7A317A; // dark purple
//        return getModdedBiomeGrassColor(grassColor);
//    }	
}
