package techguns.client.particle;


import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelVex;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.client.models.gibs.ModelGibs;
import techguns.client.models.gibs.ModelGibsBiped;
import techguns.client.models.gibs.ModelGibsGeneric;
import techguns.client.models.gibs.ModelGibsHorse;
import techguns.client.models.gibs.ModelGibsIllager;
import techguns.client.models.gibs.ModelGibsQuadruped;
import techguns.client.models.gibs.ModelGibsSlime;
import techguns.client.models.gibs.ModelGibsVillager;
import techguns.client.models.npcs.ModelAlienBug;
import techguns.client.models.npcs.ModelCyberDemon;
import techguns.client.models.npcs.ModelSuperMutant;
import techguns.client.render.entities.projectiles.DeathEffectEntityRenderer;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.npcs.AlienBug;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.Bandit;
import techguns.entities.npcs.Commando;
import techguns.entities.npcs.CyberDemon;
import techguns.entities.npcs.DictatorDave;
import techguns.entities.npcs.PsychoSteve;
import techguns.entities.npcs.SkeletonSoldier;
import techguns.entities.npcs.StormTrooper;
import techguns.entities.npcs.SuperMutantBasic;
import techguns.entities.npcs.SuperMutantElite;
import techguns.entities.npcs.SuperMutantHeavy;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieMiner;
import techguns.entities.npcs.ZombiePigmanSoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.entities.projectiles.FlyingGibs;

public class DeathEffect {
	
	public static HashMap<Class<? extends EntityLivingBase>, GoreData> goreStats = new HashMap<Class<? extends EntityLivingBase>, GoreData>();
	
	private static GoreData genericGore;
	static {
		ModelGibs modelBiped = new ModelGibsBiped(new ModelBiped(0.0f, 0.0f, 64, 64));
		ModelGibs modelBipedPlayer = new ModelGibsBiped(new ModelBiped(0.0f, 0.0f, 64, 32));
		goreStats.put(EntityPlayer.class, (new GoreData(modelBipedPlayer, 160,21,31)));
		goreStats.put(EntityZombie.class, (new GoreData(modelBiped, 110,21,41)));
		goreStats.put(ZombieSoldier.class, (new GoreData(modelBiped, 110,21,41)));
		goreStats.put(ArmySoldier.class, (new GoreData(modelBiped, 160,21,31)));
		goreStats.put(EntitySkeleton.class, (new GoreData(new ModelGibsBiped(new ModelSkeleton()), 255,255,255)));
		goreStats.put(EntityVillager.class, (new GoreData(new ModelGibsVillager(new ModelVillager(0.0f)), 150,21,51)));
		goreStats.put(EntityCow.class, (new GoreData(new ModelGibsQuadruped(new ModelCow()), 170,26,37)));
		goreStats.put(EntitySheep.class, (new GoreData(new ModelGibsQuadruped(new ModelSheep1()), 170,26,37)).setFXscale(0.8f));
		goreStats.put(EntityChicken.class, (new GoreData(new ModelGibsGeneric(new ModelChicken()), 170,26,37)).setFXscale(0.5f));
		goreStats.put(EntityCreeper.class, (new GoreData(new ModelGibsGeneric(new ModelCreeper()), 50,175,57)));		
		goreStats.put(EntityEnderman.class, (new GoreData(new ModelGibsBiped(new ModelEnderman(0.0f)),160,36,167)));
		goreStats.put(EntityPig.class, (new GoreData(new ModelGibsQuadruped(new ModelPig()), 170,26,37)).setFXscale(0.8f));
		goreStats.put(EntitySpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()), 85,156,17)));		
		goreStats.put(EntityCaveSpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()), 85,156,17)).setFXscale(0.7f));		
//		
		goreStats.put(EntityPigZombie.class, (new GoreData(modelBiped, 110,51,11)));	
		goreStats.put(ZombiePigmanSoldier.class, (new GoreData(modelBiped, 110,51,11)));	
		goreStats.put(CyberDemon.class, (new GoreData(new ModelGibsBiped(new ModelCyberDemon()), 85,156,17)));	
		
		goreStats.put(SuperMutantBasic.class, (new GoreData(new ModelGibsBiped(new ModelSuperMutant()), 109,60,25)).setFXscale(1.2f));
		goreStats.put(SuperMutantElite.class, (new GoreData(new ModelGibsBiped(new ModelSuperMutant()), 109,60,25)).setFXscale(1.2f));
		goreStats.put(SuperMutantHeavy.class, (new GoreData(new ModelGibsBiped(new ModelSuperMutant()), 109,60,25)).setFXscale(1.2f));
		
		goreStats.put(StormTrooper.class, (new GoreData(modelBiped, 160,21,31)));
		goreStats.put(Commando.class, (new GoreData(modelBiped, 160,21,31)));
		goreStats.put(DictatorDave.class, (new GoreData(modelBiped, 160,21,31)));
		goreStats.put(PsychoSteve.class, (new GoreData(modelBiped, 160,21,31)));
		
		goreStats.put(EntityWitch.class, (new GoreData(new ModelGibsVillager(new ModelWitch(1.0f)), 160,21,31)));			
		goreStats.put(EntitySlime.class, (new GoreData(new ModelGibsSlime(),40,255,40)));
//		
		goreStats.put(ZombieFarmer.class, (new GoreData(modelBiped, 110,21,41)));
		goreStats.put(ZombieMiner.class, (new GoreData(modelBiped, 110,21,41)));
//		
		goreStats.put(Bandit.class, new GoreData(modelBiped, 160,21,31));
		goreStats.put(SkeletonSoldier.class, (new GoreData(new ModelGibsBiped(new ModelSkeleton()), 255,255,255)));
		goreStats.put(AlienBug.class, (new GoreData(new ModelGibsGeneric(new ModelAlienBug()), 235, 255, 70)));
		
		goreStats.put(EntityLlama.class, (new GoreData(new ModelGibsQuadruped(new ModelLlama(0f)), 170,26,37)));
		goreStats.put(EntityEvoker.class, (new GoreData(new ModelGibsIllager(new ModelIllager(0.0F, 0.0F, 64, 64)), 110,21,41)));
		goreStats.put(EntityHusk.class, (new GoreData(modelBiped, 110,21,41)));
		goreStats.put(EntityPolarBear.class, (new GoreData(new ModelGibsQuadruped(new ModelPolarBear()), 170,26,37)).setFXscale(0.8f));
		goreStats.put(EntityMagmaCube.class, (new GoreData(new ModelGibsSlime(),92,26,0)));
		goreStats.put(EntityParrot.class, (new GoreData(new ModelGibsGeneric(new ModelParrot()), 170,26,37)).setFXscale(0.5f));
		goreStats.put(EntityRabbit.class, (new GoreData(new ModelGibsGeneric(new ModelRabbit()), 170,26,37)).setFXscale(0.3f));
		goreStats.put(EntityStray.class, (new GoreData(new ModelGibsBiped(new ModelSkeleton()), 255,255,255)));
		goreStats.put(EntitySilverfish.class, (new GoreData(new ModelGibsGeneric(new ModelSilverfish()), 90,16,27)).setFXscale(0.4f));
		goreStats.put(EntityVindicator.class, (new GoreData(new ModelGibsIllager(new ModelIllager(0.0F, 0.0F, 64, 64)), 110,21,41)));
		goreStats.put(EntityVex.class, (new GoreData(new ModelGibsSlime(), 215,215,215)).setFXscale(0.4f));
		goreStats.put(EntityShulker.class, (new GoreData(new ModelGibsSlime(), 125,0,106)).setFXscale(1.5f));
		goreStats.put(EntityWitherSkeleton.class, (new GoreData(new ModelGibsBiped(new ModelSkeleton()), 50,50,50).setFXscale(1.4f)));
		goreStats.put(EntityGhast.class, (new GoreData(new ModelGibsSlime(), 255,255,255)).setFXscale(3.5f));
		goreStats.put(EntityZombieVillager.class, (new GoreData(modelBiped, 110,21,41)));
		goreStats.put(EntityHorse.class, (new GoreData(new ModelGibsHorse(), 170,26,37)).setFXscale(1.0f));
		goreStats.put(EntityDonkey.class, (new GoreData(new ModelGibsHorse(), 170,26,37)).setFXscale(1.0f));
		goreStats.put(EntityMule.class, (new GoreData(new ModelGibsHorse(), 170,26,37)).setFXscale(1.0f));
		
		genericGore = (new GoreData(modelBiped, 160,21,31)).setTexture(new ResourceLocation(Techguns.MODID,"textures/entity/gore.png"));
		genericGore.setRandomScale(0.5f, 0.8f);
		//ModelHorse horse = new ModelHorse();
		//goreStats.put(EntityHorse.class, new GoreData(new ModelGibsHorse(horse), horse.boxList.size(), new ResourceLocation("textures/entity/horse/horse_brown.png"), 0.66f, 150,21,51));
	}
	
	/**
	 * Use this method to put GoreData into the map, call this BEFORE postInit()
	 * @param entityClass
	 * @param data
	 */
	public static void addGoreData(Class<? extends EntityLivingBase> entityClass, GoreData data ) {
		goreStats.put(entityClass, data);
	}
	
	/**
	 * Called from ClientProxy in postInit
	 */
	public static void postInit() {
		goreStats.values().forEach(stat -> stat.init());
		genericGore.init();
	}
	
	public static GoreData getGoreData(Class<? extends EntityLivingBase> entityClass) {
		GoreData data = DeathEffect.goreStats.get(entityClass);
		if (data != null) {
			return data;
		}else {
			data = new GoreData();
			data.bloodColorR = genericGore.bloodColorR;
			data.bloodColorG = genericGore.bloodColorG;
			data.bloodColorB = genericGore.bloodColorB;
			data.type_main = genericGore.type_main;
			data.type_trail = genericGore.type_trail;
			data.sound = genericGore.sound;
			data.numGibs = -1; //TODO
			goreStats.put(entityClass, data);
			return data;
		}
	}
	
	public static void createDeathEffect(EntityLivingBase entity, DeathType deathtype, float motionX, float motionY, float motionZ) {
		//GetEntityType
		//EntityDT entityDT = EntityDeathUtils.getEntityDeathType(entity);
		
		double x = entity.posX;
		double y = entity.posY+(entity.height/2.0f);
		double z = entity.posZ;
		
		if (deathtype == DeathType.GORE) {			
			
			GoreData data = DeathEffect.getGoreData(entity.getClass());
			Render render = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());

			try {
				if (data.model == null && render!=null) {
					ModelBase mainModel = (ModelBase) DeathEffectEntityRenderer.RLB_mainModel.get((RenderLivingBase) render);
					if (mainModel instanceof ModelBiped) {
						data.model = new ModelGibsBiped(((ModelBiped)mainModel).getClass().newInstance());
					}else if (mainModel instanceof ModelQuadruped) {
						data.model = new ModelGibsQuadruped(((ModelQuadruped)mainModel).getClass().newInstance());
					}else if (mainModel instanceof ModelVillager) {
						data.model = new ModelGibsVillager(((ModelVillager)mainModel).getClass().newInstance());
					}else {
						data.model = genericGore.model; //new ModelGibsGeneric(mainModel.getClass().newInstance());
						data.texture = genericGore.texture;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}

			
			ClientProxy.get().playSoundOnPosition(data.sound, (float)x,(float)y,(float)z, 1.0f, 1.0f, false, TGSoundCategory.DEATHEFFECT);
			
			//Spawn MainFX
			TGParticleSystem sys = new TGParticleSystem(entity.world, data.type_main, x, entity.posY, z, entity.motionX, entity.motionY, entity.motionZ);
			ClientProxy.get().particleManager.addEffect(sys);

			int count;
			if (data.numGibs >= 0) { 
				count = data.numGibs;
			} else { 
				if(data.model==null) {
					return;
				}
				count = data.model.getNumGibs();
			}
			
			for (int i = 0; i < count; i++) {
				double vx = (0.5-entity.world.rand.nextDouble()) * 0.35;
				double vy;			
				if (entity.onGround) {
					vy = (entity.world.rand.nextDouble()) * 0.35;
				}else {
					vy = (0.5-entity.world.rand.nextDouble()) * 0.35;
				}
				double vz = (0.5-entity.world.rand.nextDouble()) * 0.35;
				
				FlyingGibs ent = new FlyingGibs(entity.world, entity, data, x,y,z, motionX*0.35+vx, motionY*0.35+vy, motionZ*0.35+vz, (entity.width+entity.height)/2.0f, i);
				
				entity.world.spawnEntity(ent);
			}
		}else if (deathtype == DeathType.BIO) {
			ClientProxy.get().createFX("biodeath", entity.world, x,y,z, (double)motionX, (double)motionY, (double)motionZ);
			ClientProxy.get().playSoundOnPosition(TGSounds.DEATH_BIO, (float)x,(float)y,(float)z, 1.0f, 1.0f, false, TGSoundCategory.DEATHEFFECT);
		}else if (deathtype == DeathType.LASER) {
			ClientProxy.get().createFX("laserdeathFire", entity.world, x,y,z, (double)motionX, 0, (double)motionZ);
			ClientProxy.get().createFX("laserdeathAsh", entity.world, x,y,z, (double)motionX, 0, (double)motionZ);
			ClientProxy.get().playSoundOnPosition(TGSounds.DEATH_LASER, (float)x,(float)y,(float)z, 1.0f, 1.0f, false, TGSoundCategory.DEATHEFFECT);
		}
	}
	
	public static class GoreData {
		public ModelGibs model = null;
		public ResourceLocation texture = null;
		int numGibs = -1;
		public float particleScale = 1.0f;
		public float modelScale = 1.0f;
		 	
		int bloodColorR;
		int bloodColorG;
		int bloodColorB;
		
		//public boolean showBlood = true;
		String fx_main = "GoreFX_Blood";
		String fx_trail ="GoreTrailFX_Blood";
		public SoundEvent sound = TGSounds.DEATH_GORE;
		
		public TGParticleSystemType type_main;
		public TGParticleSystemType type_trail;

		public float minPartScale = 1.0f;
		public float maxPartScale = 1.0f;

		public GoreData() {}
		
		public GoreData(ModelGibs model, int bloodColorR, int bloodColorG, int bloodColorB) {
			this.model = model;
	//		this.modelScale = modelScale;
			this.bloodColorR = bloodColorR;
			this.bloodColorG = bloodColorG;
			this.bloodColorB = bloodColorB;
		}
		
		public GoreData setNumGibs(int gibs) {
			this.numGibs = gibs;
			return this;
		}
		
		public GoreData setTexture(ResourceLocation texture) {
			this.texture = texture;
			return this;
		}
		
		public GoreData setFXscale(float scale) {
			this.particleScale = scale;
			return this;
		}
		
		public GoreData setFX(String fx_main, String fx_trail) {
			this.fx_main = fx_main;
			this.fx_trail = fx_trail;
			return this;
		}
		
		public GoreData setSound(SoundEvent sound) {
			this.sound = sound;
			return this;
		}
		
		public void init() {
			type_main = new TGParticleSystemType();

			if (TGFX.FXList.containsKey(fx_main.toLowerCase())) {
				TGFXType fxtype_main = TGFX.FXList.get(fx_main.toLowerCase());
				if (fxtype_main instanceof TGParticleSystemType) {
					this.type_main = getExtendedType((TGParticleSystemType) fxtype_main);
				}else {
					this.type_main = null;
				}
			}else {
				this.type_main = null;
			}
			
			type_trail = new TGParticleSystemType();
			
			if (TGFX.FXList.containsKey(fx_trail.toLowerCase())) {
				TGFXType fxtype_trail = TGFX.FXList.get(fx_trail.toLowerCase());
				if (fxtype_trail instanceof TGParticleSystemType) {
					this.type_trail = getExtendedType((TGParticleSystemType) fxtype_trail);
				}else {
					this.type_trail = null;
				}
			}else {
				this.type_trail = null;
			}
		}

		/**
		 * Add a random scale to individual gibs.
		 * @param f
		 * @param g
		 */
		public void setRandomScale(float min, float max) {
			minPartScale = min;
			maxPartScale = max;
		}
		
		
		private TGParticleSystemType getExtendedType(TGParticleSystemType supertype) {
			TGParticleSystemType type = new TGParticleSystemType();
			type.extend(supertype);
			if (type.colorEntries.size() >= 1) {
				type.colorEntries.get(0).r = (float)this.bloodColorR /255.0f;
				type.colorEntries.get(0).g = (float)this.bloodColorG /255.0f;
				type.colorEntries.get(0).b = (float)this.bloodColorB /255.0f;
			}
			type.sizeMin *= particleScale;
			type.sizeMax *= particleScale;
			type.sizeRateMin *= particleScale;
			type.sizeRateMax *= particleScale;
			type.startSizeRateDampingMin *= particleScale;
			type.startSizeRateMin *= particleScale;
			type.startSizeRateMax *= particleScale;
			for (int i = 0; i < type.volumeData.length; i++) {
				type.volumeData[i]*=particleScale;
			}
			return type;
		}
	}



}
