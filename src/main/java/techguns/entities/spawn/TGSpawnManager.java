package techguns.entities.spawn;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import techguns.TGConfig;
import techguns.entities.npcs.GenericNPC;
import techguns.util.MathUtil.Vec2;


/**
 * Handles spawning of Techguns NPCs
 *
 */
public class TGSpawnManager {

	public static TGNpcSpawnTable spawnTableOverworld = new TGNpcSpawnTable(5);
	public static TGNpcSpawnTable spawnTableNether = new TGNpcSpawnTable(5);

	protected static Random rnd = new Random();
	
	public static void handleSpawn(World w, Entity dummyEnt){
		
		GenericNPC entNew = null;
		
		Biome biome = w.getBiome(new BlockPos(Math.round(dummyEnt.posX), 64, Math.round(dummyEnt.posZ)));
			
		int dist_danger = getDistanceDanger(w, dummyEnt);
		int biome_danger = getBiomeDanger(w, dummyEnt,biome);
		
		int danger = dist_danger+biome_danger;
		
		TGNpcSpawn chosenSpawn=null;
		
		TGNpcSpawnTable spawntable = getSpawnTableForDimensionId(w.provider.getDimension());
		
		int totalweight=0;
		//calculate possible weights
		for (int d=0;d<=danger;d++){
			
			ArrayList<TGNpcSpawn> list = spawntable.get(d);
			for(int i=0;i<list.size();i++){
				TGNpcSpawn spawn = list.get(i);
				
				//if(spawn.dimensionMatches(w)){
				totalweight+=spawn.getWeightForBiome(biome);
				//}
			}
			
		}
		
		if (totalweight>0){
		
			int roll = rnd.nextInt(totalweight);
			
			//System.out.println("Danger:"+danger+" TotalWeight:"+totalweight+" Roll:"+roll);			
			totalweight=0;

			for (int d=0;d<=danger;d++){
				ArrayList<TGNpcSpawn> list = spawntable.get(d);
				for(int i=0;i<list.size();i++){
					TGNpcSpawn spawn = list.get(i);
					//if(spawn.dimensionMatches(w)){
						totalweight+=spawn.getWeightForBiome(biome);
						if (totalweight >=roll){
							chosenSpawn = spawn;
							
							//System.out.println("Spawning: "+spawn.type.toString());
							
							break;
						}
					//}
				}
				if (chosenSpawn !=null){
					break;
				}
			}
			
			if (chosenSpawn!=null){
			
				try {
					entNew = chosenSpawn.type.getDeclaredConstructor(World.class).newInstance(w);
					setPositionsAndReplace(w, entNew, dummyEnt, danger);
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} finally {
					dummyEnt.setDead();
				}
			} else {
				dummyEnt.setDead();
			}
		
		} else {
			if(dummyEnt!=null) {
				dummyEnt.setDead();
			}
			//System.out.println("TotalWeight 0, nothing to spawn???");
		}
		
		
	}
	
	private static int getBiomeDanger(World w, Entity ent, Biome biome){
			
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)){
			return 0;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)){
			return 0;
		}
		
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.WASTELAND)){
			return 2;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)){
			return 2;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)){
			return 2;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)){
			return 2;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SPOOKY)){
			return 2;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.DEAD)){
			return 2;
		}
			
		
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)){
			return 1;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SPARSE)){
			return 1;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)){
			return 1;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.DRY)){
			return 1;
		}
		
		return 0;
	}
	
	protected static TGNpcSpawnTable getSpawnTableForDimensionId(int id) {
		switch(id) {
		case -1:
			return spawnTableNether;
		default:
			return spawnTableOverworld;
		}
	}
	
	private static int getDistanceDanger(World w, Entity ent){
		Vec2 spawn = new Vec2(w.getSpawnPoint().getX(),w.getSpawnPoint().getZ());
		Vec2 pos = new Vec2(ent.posX, ent.posZ);
		
		double distance = spawn.getVecTo(pos).len();
		
		//System.out.println("Distance:"+distance);
		
		float factor=1;
		
		//if nether distance is halfed
		if(w.provider.getDimension()==-1){
			factor = 0.5f;
		}
		
		
		if (distance < TGConfig.distanceSpawnLevel0*factor){
			return 0;
		} else if (distance<TGConfig.distanceSpawnLevel1*factor){
			return 1;
		} else if (distance<TGConfig.distanceSpawnLevel2*factor){
			return 2;
		} else {
			return 3;
		}
		
	}
	
	public static boolean isInArray(Biome biome, Biome[] biomes){
		for (int i=0;i<biomes.length;i++){
			
			if (biome.equals(biomes[i])){
				return true;
			}
		}
		
		return false;
	}
	
	private static void setPositionsAndReplace(World w, GenericNPC newNpc, Entity entity, int difficulty){
		newNpc.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0f);
		//newNpc.setRotationYawHead(entity.rotationYaw);
		//newNpc.onSpawnWithEgg((IEntityLivingData)null);
		newNpc.onSpawnByManager(difficulty);
		w.spawnEntity(newNpc);
		
		entity.setDead();
	}
	
	
}
