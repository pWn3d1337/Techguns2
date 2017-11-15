package techguns.entities.spawn;

import java.util.ArrayList;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import techguns.entities.npcs.GenericNPC;

public class TGNpcSpawn {

	protected Class<? extends GenericNPC> type;	
	protected int spawnWeight;
	
	/**
	 * Restricted to these biomeTypes, null = all biomes
	 */
	ArrayList<Biome> biomeWhitelist;
	
	ArrayList<Integer> dimensionIDs;
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight){
		this.type=type;
		this.spawnWeight=spawnWeight;
		this.biomeWhitelist = null;
		this.dimensionIDs=new ArrayList<Integer>(1);
		this.dimensionIDs.add(0);
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, Biome... biomes) {
		this(type,spawnWeight);
		this.biomeWhitelist = new ArrayList<Biome>(biomes.length);
		for (int i =0; i<biomes.length; i++){
			this.biomeWhitelist.add(biomes[i]);
		}
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, ArrayList<Integer> dimensions, Biome... biomes) {
		this(type,spawnWeight,dimensions);
		this.biomeWhitelist = new ArrayList<Biome>(biomes.length);
		for (int i =0; i<biomes.length; i++){
			this.biomeWhitelist.add(biomes[i]);
		}
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, ArrayList<Integer> dimensions) {
		this(type,spawnWeight);
		this.dimensionIDs=dimensions;
	}
	
	public int getWeightForBiome(Biome biome){
		if (this.biomeWhitelist == null) {
			return this.spawnWeight;
		} else {
			if(this.biomeWhitelist.contains(biome)){
				return this.spawnWeight;
			} else {
				return 0;
			}
		}
	}
	
	public boolean dimensionMatches(World w){
		int id = w.provider.getDimension();
		if(this.dimensionIDs == null || this.dimensionIDs.contains(id)){
			return true;
		} 
		return false;
	}
}