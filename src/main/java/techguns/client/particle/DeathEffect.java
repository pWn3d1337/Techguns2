package techguns.client.particle;


import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
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
import techguns.client.models.gibs.ModelGibsQuadruped;
import techguns.client.models.gibs.ModelGibsVillager;
import techguns.client.render.entities.projectiles.DeathEffectEntityRenderer;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.projectiles.FlyingGibs;

public class DeathEffect {
	
	public static HashMap<Class<? extends EntityLivingBase>, GoreData> goreStats = new HashMap<Class<? extends EntityLivingBase>, GoreData>();
	
	private static GoreData genericGore;
	static {
		ModelGibs modelBiped = new ModelGibsBiped(new ModelBiped(0.0f));
		ModelGibs modelBipedPlayer = new ModelGibsBiped(new ModelBiped(0.0f, 0.0f, 64, 32));
		goreStats.put(EntityPlayer.class, (new GoreData(modelBipedPlayer, 160,21,31)));
		goreStats.put(EntityZombie.class, (new GoreData(modelBiped, 110,21,41)));
//		goreStats.put(ZombieSoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
//		goreStats.put(ArmySoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/army_soldier.png"), 0.66f, 160,21,31)));
		goreStats.put(EntitySkeleton.class, (new GoreData(new ModelGibsBiped(new ModelSkeleton()), 255,255,255)));
		goreStats.put(EntityVillager.class, (new GoreData(new ModelGibsVillager(new ModelVillager(0.0f)), 150,21,51)));
		goreStats.put(EntityCow.class, (new GoreData(new ModelGibsQuadruped(new ModelCow()), 170,26,37)));
		goreStats.put(EntitySheep.class, (new GoreData(new ModelGibsQuadruped(new ModelSheep1()), 170,26,37)).setFXscale(0.8f));
		goreStats.put(EntityChicken.class, (new GoreData(new ModelGibsGeneric(new ModelChicken()), 170,26,37)).setFXscale(0.5f));
		goreStats.put(EntityCreeper.class, (new GoreData(new ModelGibsGeneric(new ModelCreeper()), 50,175,57)));		
		goreStats.put(EntityEnderman.class, (new GoreData(new ModelGibsBiped(new ModelEnderman(0.0f)),160,36,167)));
		goreStats.put(EntityPig.class, (new GoreData(new ModelGibsQuadruped(new ModelPig()), 170,26,37)).setFXscale(0.8f));
		goreStats.put(EntitySpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()),  85,156,17)));		
		goreStats.put(EntityCaveSpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()), 85,156,17)).setFXscale(0.7f));		
//		
//		goreStats.put(EntityPigZombie.class, (new GoreData(modelBiped, 6,new ResourceLocation("textures/entity/zombie_pigman.png"), 0.66f, 110,51,11)));	
//		goreStats.put(ZombiePigmanSoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("textures/entity/zombie_pigman.png"), 0.66f, 110,51,11)));	
//		goreStats.put(CyberDemon.class, (new GoreData(new ModelGibsBipedGeneric(new ModelCyberDemon()), 6,new ResourceLocation("techguns","textures/entity/cyberdemon.png"), 1.00f, 85,156,17)));			
		goreStats.put(EntityWitch.class, (new GoreData(new ModelGibsVillager(new ModelWitch(1.0f)), 160,21,31)));			
		goreStats.put(EntitySlime.class, (new GoreData(null,40,255,40)));
//		
//		goreStats.put(ZombieFarmer.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
//		goreStats.put(ZombieMiner.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
//		
//		goreStats.put(Bandit.class, new GoreData(modelBiped,6,new ResourceLocation("techguns","textures/entity/bandit.png"), 0.66f, 160,21,31));
//		goreStats.put(SkeletonSoldier.class, (new GoreData(new ModelGibsSkeleton(1.0f), 6,new ResourceLocation("textures/entity/skeleton/skeleton.png"), 0.66f, 255,255,255).setBlood(false)));
//		goreStats.put(AlienBug.class, (new GoreData(new ModelGibsAlienBug(),8, new ResourceLocation("techguns", "textures/entity/alienbug.png"), 0.8f, 235, 255, 70)));
		
		goreStats.values().forEach(stat -> stat.init());
		
		genericGore = (new GoreData(modelBiped, 160,21,31)).setTexture(new ResourceLocation(Techguns.MODID,"textures/entity/gore.png"));
		genericGore.setRandomScale(0.5f, 0.8f);
		genericGore.init();
		//ModelHorse horse = new ModelHorse();
		//goreStats.put(EntityHorse.class, new GoreData(new ModelGibsHorse(horse), horse.boxList.size(), new ResourceLocation("textures/entity/horse/horse_brown.png"), 0.66f, 150,21,51));
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
