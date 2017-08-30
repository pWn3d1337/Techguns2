package techguns.deatheffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;


public class EntityDeathUtils {
	
	public static HashMap<DeathType, List<Class<? extends EntityLivingBase>>> entityDeathTypes;

	static {
		entityDeathTypes = new HashMap<DeathType, List<Class<? extends EntityLivingBase>>>();
		//Gore
		ArrayList<Class<? extends EntityLivingBase>> listGore = new ArrayList<Class<? extends EntityLivingBase>>();
		listGore.add(EntityPlayer.class);
		listGore.add(EntityZombie.class);
		listGore.add(EntitySkeleton.class);
		listGore.add(EntityEnderman.class);
		listGore.add(EntityCreeper.class);
		listGore.add(EntityCow.class);
		listGore.add(EntitySheep.class);
		listGore.add(EntityPig.class);
		listGore.add(EntityChicken.class);
		listGore.add(EntityPigZombie.class);
	//	listGore.add(ZombieSoldier.class);
	//	listGore.add(ArmySoldier.class);
	//	listGore.add(CyberDemon.class);
	//	listGore.add(ZombiePigmanSoldier.class);
		listGore.add(EntitySpider.class);
		listGore.add(EntityCaveSpider.class);
		listGore.add(EntityWitch.class);
		listGore.add(EntitySlime.class);
	//	listGore.add(ZombieFarmer.class);
	//	listGore.add(ZombieMiner.class);
	//	listGore.add(Bandit.class);
	//	listGore.add(ZombieSoldier.class);
		listGore.add(EntityHorse.class);
		listGore.add(EntityMooshroom.class);
		listGore.add(EntityWolf.class);
		listGore.add(EntitySquid.class);
		listGore.add(EntityGhast.class);
		listGore.add(EntityVillager.class);
	//	listGore.add(PsychoSteve.class);
	//	listGore.add(DictatorDave.class);
	//	listGore.add(SkeletonSoldier.class);
	//	listGore.add(AlienBug.class);
		entityDeathTypes.put(DeathType.GORE, listGore);
	}
	
	public static boolean hasSpecialDeathAnim(EntityLivingBase entityLiving, DeathType deathtype) {
		/*if (entityLiving instanceof IBossDisplayData){
			if(entityLiving instanceof GenericNPC){
				return true;
			}
			return false;
		}*/
		
		//TEST CODE:
		if (deathtype == DeathType.BIO || deathtype == DeathType.LASER || deathtype == DeathType.GORE) return true;
		
		//GenericGore
		if (entityDeathTypes.get(DeathType.GORE).contains(entityLiving.getClass())){
			return true;
		}
		
		return false;
		
		//try {
		//	return entityDeathTypes.get(deathtype).contains(entityLiving.getClass());
		//} catch (Exception e) {
		//	return false;
		//}
	}

	
//	public enum EntityDT {
//		ZOMBIE,
//		SKELETON,
//		PLAYER,
//		COW,
//		SHEEP,
//		CREEPER,
//		CHICKEN,
//		VILLAGER,
//		ZOMBIE_SOLDIER,
//		ARMY_SOLDIER,
//		SPIDER,
//		ENDERMAN,
//		PIG,
//		GENERIC;
//	}
//	
//	
//	public static EntityDT getEntityDeathType(Entity entity) {
//		EntityDT entityType;
//		if (entity instanceof EntityPlayer) entityType = EntityDT.PLAYER;
//		else if (entity instanceof EntityZombie) entityType = EntityDT.ZOMBIE;
//		else if (entity instanceof EntitySkeleton) entityType = EntityDT.SKELETON;
//		else if (entity instanceof ZombieSoldier) entityType = EntityDT.ZOMBIE_SOLDIER;
//		else if (entity instanceof ArmySoldier) entityType = EntityDT.ARMY_SOLDIER;
//		else if (entity instanceof EntityVillager) entityType = EntityDT.VILLAGER;
//		else if (entity instanceof EntityCow) entityType = EntityDT.COW;
//		else if (entity instanceof EntityChicken) entityType = EntityDT.CHICKEN;
//		else if (entity instanceof EntityCreeper) entityType = EntityDT.CREEPER;
//		else if (entity instanceof EntitySheep) entityType = EntityDT.SHEEP;
//		else if (entity instanceof EntitySpider) entityType = EntityDT.SPIDER;
//		else if (entity instanceof EntityEnderman) entityType = EntityDT.ENDERMAN;
//		else if (entity instanceof EntityPig) entityType = EntityDT.PIG;
//		else entityType = EntityDT.GENERIC;
//		return entityType;
//	}
//	
    public enum DeathType {
    	DEFAULT(0), GORE(1), BIO(2), LASER(3), DISMEMBER(4);
    	
    	int value;
    	
    	private DeathType(int value) {
    		this.value = value;
    	}
    	
    	public int getValue() {
    		return value;
    	}
    }
}
