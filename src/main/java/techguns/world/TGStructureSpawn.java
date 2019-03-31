package techguns.world;

import java.util.ArrayList;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import techguns.world.structures.WorldgenStructure;

public class TGStructureSpawn {
	WorldgenStructure structure;
	int spawnWeight;
	/**
	 * Restricted to these biomes, null = all biomes
	 */
	ArrayList<BiomeDictionary.Type> biomeWhitelist;
	
	ArrayList<Integer> dimensionIDs;
	
	StructureSize size;

	ArrayList<StructureLandType> allowedTypes;	
	
	public TGStructureSpawn(WorldgenStructure structure, int spawnWeight, ArrayList<BiomeDictionary.Type> biomeWhitelist, ArrayList<Integer> dimensionIDs, ArrayList<StructureLandType> allowedTypes, StructureSize size) {
		super();
		this.structure = structure;
		this.spawnWeight = spawnWeight;
		this.biomeWhitelist = biomeWhitelist;
		this.dimensionIDs = dimensionIDs;
		this.allowedTypes = allowedTypes;
		this.size = size;
	}

	public int getWeightForBiome(Biome biome, StructureSize size, StructureLandType type, int dimensionid){
		if (this.size != size){
			return 0;
		} else if (!this.allowedTypes.contains(type)){
			return 0;
		} else if (!dimensionMatches(dimensionid)) {
			return 0;
		}
		
		if (this.biomeWhitelist == null) {
			return this.spawnWeight;
		} else {
			for(int i=0; i<this.biomeWhitelist.size(); i++){
				if (BiomeDictionary.hasType(biome, this.biomeWhitelist.get(i))){
					return this.spawnWeight;
				}
			}
			
			return 0;
		}
	}
	
	public boolean dimensionMatches(World w){
		int id = w.provider.getDimension();
		if(this.dimensionIDs.contains(id)){
			return true;
		} 
		return false;
	}
	
	public boolean dimensionMatches(int id){
		if(this.dimensionIDs.contains(id)){
			return true;
		} 
		return false;
	}
	
}
