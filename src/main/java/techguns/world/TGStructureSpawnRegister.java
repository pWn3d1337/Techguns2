package techguns.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import techguns.TGConfig;
import techguns.TGFluids;
import techguns.world.structures.AircraftCarrier;
import techguns.world.structures.AlienBugNestStructure;
import techguns.world.structures.CastleStructure;
import techguns.world.structures.DesertOilCluster;
import techguns.world.structures.FactoryHouseSmall;
import techguns.world.structures.GasStation;
import techguns.world.structures.MilitaryBaseStructure;
import techguns.world.structures.NetherAcidHole;
import techguns.world.structures.NetherAltarMedium;
import techguns.world.structures.NetherAltarSmall;
import techguns.world.structures.NetherDungeonStructure;
import techguns.world.structures.NetherGhastSpawner;
import techguns.world.structures.NetherLoot01;
import techguns.world.structures.NetherOreClusterCastle;
import techguns.world.structures.NetherOreClusterSmall;
import techguns.world.structures.NetherSoulPlatform;
import techguns.world.structures.OreClusterMeteorBasis;
import techguns.world.structures.OreClusterSpike;
import techguns.world.structures.PoliceStation;
import techguns.world.structures.SmallMine;
import techguns.world.structures.SmallTrainstation;
import techguns.world.structures.SurvivorHideout;
import techguns.world.structures.WorldgenStructure;

public class TGStructureSpawnRegister {
	protected static ArrayList<TGStructureSpawn> spawns_small = new ArrayList<TGStructureSpawn>();
	protected static ArrayList<TGStructureSpawn> spawns_big = new ArrayList<TGStructureSpawn>();
	protected static ArrayList<TGStructureSpawn> spawns_medium = new ArrayList<TGStructureSpawn>();
	
	protected static ArrayList<Integer> OVERWORLD = new ArrayList<Integer>(1);
	protected static ArrayList<Integer> NETHER = new ArrayList<Integer>(1);
	
	protected static ArrayList<StructureLandType> LAND = new ArrayList<>(1);
	protected static ArrayList<StructureLandType> WATER = new ArrayList<>(1);
	
	protected static ArrayList<BiomeDictionary.Type> DESERTS_ONLY = new ArrayList<>(2);
	
	static {
		OVERWORLD.add(0);
		NETHER.add(-1);
		
		LAND.add(StructureLandType.LAND);
		WATER.add(StructureLandType.WATER);
		
		DESERTS_ONLY.add(BiomeDictionary.Type.SANDY);
		DESERTS_ONLY.add(BiomeDictionary.Type.WASTELAND);
		
		spawns_small.add(new TGStructureSpawn(new FactoryHouseSmall(8,0,7,9,5,10).setXZSize(11, 10),10,null,OVERWORLD,LAND,StructureSize.SMALL));
	    spawns_small.add(new TGStructureSpawn(new SmallTrainstation(0, 0, 0, 0, 0, 0).setXZSize(11, 12),10,null,OVERWORLD,LAND,StructureSize.SMALL));
	    spawns_small.add(new TGStructureSpawn(new SmallMine().setXZSize(17, 11),10,null,OVERWORLD,LAND,StructureSize.SMALL));
	    spawns_small.add(new TGStructureSpawn(new GasStation(),10,null,OVERWORLD,LAND,StructureSize.SMALL));

		//spawns_medium.add(new TGStructureSpawn(new HouseMedium(16, 12, 16, 16, 12, 16).setXZSize(16, 16),1,null,OVERWORLD,LAND,StructureSize.MEDIUM));	
		spawns_medium.add(new TGStructureSpawn(new AlienBugNestStructure().setXZSize(4, 4),20,DESERTS_ONLY,OVERWORLD,LAND,StructureSize.MEDIUM));
		
		spawns_medium.add(new TGStructureSpawn(new PoliceStation(),10,null,OVERWORLD,LAND,StructureSize.MEDIUM));
		
		spawns_medium.add(new TGStructureSpawn(new SurvivorHideout(),10,null,OVERWORLD,LAND,StructureSize.MEDIUM));
		
		if(TGConfig.spawnOreClusterStructures) {
			spawns_medium.add(new TGStructureSpawn(new OreClusterSpike().setXZSize(8, 8),10,null,OVERWORLD,LAND,StructureSize.MEDIUM));
			spawns_medium.add(new TGStructureSpawn(new OreClusterMeteorBasis().setXZSize(17, 17),5,null,OVERWORLD,LAND,StructureSize.MEDIUM));
		
			if(TGFluids.OIL_WORLDSPAWN!=null) {
				spawns_medium.add(new TGStructureSpawn(new DesertOilCluster().setXZSize(11, 11), 15, DESERTS_ONLY, OVERWORLD, LAND, StructureSize.MEDIUM));
			}
		}
		
		//spawns_medium.add(new TGStructureSpawn(new BigBunker(32,14,17,32,14,17).setXZSize(32, 17), 1, null, OVERWORLD, LAND, StructureSize.MEDIUM));
				
		spawns_big.add(new TGStructureSpawn(new MilitaryBaseStructure(0, 0, 0, 0, 0, 0),1,null,OVERWORLD,LAND,StructureSize.BIG));
		
		spawns_big.add(new TGStructureSpawn(new CastleStructure(), 1, null, OVERWORLD, LAND, StructureSize.BIG));
		
		spawns_big.add(new TGStructureSpawn(new AircraftCarrier(54,24,21,54,24,21).setXZSize(54, 21),1,null, OVERWORLD, WATER, StructureSize.BIG));
		
		
		//NETHER
		
		spawns_small.add(new TGStructureSpawn(new NetherAltarSmall().setXZSize(11, 11),10,null,NETHER,LAND,StructureSize.SMALL));
		spawns_small.add(new TGStructureSpawn(new NetherSoulPlatform().setXZSize(11, 11),10,null,NETHER,LAND,StructureSize.SMALL));
		spawns_small.add(new TGStructureSpawn(new NetherLoot01().setXZSize(6, 6),10,null,NETHER,LAND,StructureSize.SMALL));
		spawns_small.add(new TGStructureSpawn(new NetherAcidHole().setXZSize(9, 9),10,null,NETHER,LAND,StructureSize.SMALL));
		
		if(TGConfig.spawnOreClusterStructures) {
		spawns_small.add(new TGStructureSpawn(new NetherOreClusterSmall().setXZSize(3, 3),10,null,NETHER,LAND,StructureSize.SMALL));
		}
		
		spawns_medium.add(new TGStructureSpawn(new NetherAltarMedium().setXZSize(16, 16),10,null,NETHER,LAND,StructureSize.MEDIUM));
		spawns_medium.add(new TGStructureSpawn(new NetherGhastSpawner().setXZSize(10, 10),10,null,NETHER,LAND,StructureSize.MEDIUM));
		
		if(TGConfig.spawnOreClusterStructures) {
			spawns_medium.add(new TGStructureSpawn(new NetherOreClusterCastle().setXZSize(11, 11),1000,null,NETHER,LAND,StructureSize.MEDIUM));
		}
		
		//TODO: finish
		//spawns_big.add(new TGStructureSpawn(new NetherDungeonStructure(), 1, null, NETHER, LAND, StructureSize.BIG));
		
		
		//spawns_big.add(new TGStructureSpawn(new Submarine().setXZSize(33, 7),1,null, OVERWORLD, WATER, StructureSize.BIG));
		
		//spawns_big.add(new TGStructureSpawn(new CityStructure(),1,null,OVERWORLD,LAND,StructureSize.BIG));
	}
	
	
	
	
	public static WorldgenStructure choseStructure(Random rnd, Biome biome, StructureSize size, StructureLandType type, int dimension){
		int totalweight=0;		
				
		ArrayList<TGStructureSpawn> spawns;
		if (size==StructureSize.BIG){
			spawns=spawns_big;
			//System.out.println("Trying to Spawn BIG STRUCTURE");
		} else if (size==StructureSize.MEDIUM) {
			spawns=spawns_medium;
		} else {
			spawns=spawns_small;
		}
		
		
		for(int i=0;i<spawns.size();i++){	
			totalweight+=spawns.get(i).getWeightForBiome(biome,size,type, dimension);
		}
		
		if (totalweight>0){
		
			int roll = rnd.nextInt(totalweight)+1;
			//System.out.println("Totalweight:"+totalweight+" , rolled:"+roll);
			
			int weight=0;
			for(int i=0; i<spawns.size();i++){
				weight += spawns.get(i).getWeightForBiome(biome,size,type, dimension);
				if (roll<=weight) {
				//	System.out.println("Chosen:"+weight);
					//System.out.println("Chosen:"+spawns.get(i).structure.getClass());
					return spawns.get(i).structure;
				} else {	
					
				}
				
			}
		} else {
			//System.out.println("SPAWNWEIGHT IS NULL type:"+size);
		}
		return null;
	}
	
}