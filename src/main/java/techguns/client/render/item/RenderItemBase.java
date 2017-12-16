package techguns.client.render.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.render.IItemRenderer;
import techguns.capabilities.TGExtendedPlayerClient;
import techguns.capabilities.TGShooterValues;
import techguns.client.ClientProxy;
import techguns.client.models.ModelMultipart;
import techguns.client.particle.ITGParticle;
import techguns.client.particle.TGParticleSystemItemAttached;

public class RenderItemBase implements IItemRenderer {

	public static final float SCALE = 0.0625f;

	protected ModelMultipart model;
	protected ResourceLocation texture;
	
	protected float baseScale = 1.0f;

	protected float scale_thirdp = 0.35f;
	protected float scale_ground = 0.5f;
	protected float scale_ego = 0.5f;
	protected float scale_gui = 0.4f;
	protected float scale_itemframe = 0.5f;

	protected float[] translateBase = { 0f, 0f, 0f };

	protected float[][] translateType = { { 0f, 0f, 0f }, // TRANSLATE FIRST
			// PERSON
			{ 0f, 0f, 0f }, // TRANSLATE THIRD PERSON
			{ 0f, 0f, 0f }, // TRANSLATE GUI
			{ 0f, 0f, 0f }, // TRANSLATE GROUND
			{ 0f, 0f, -0.05f } // TRANLATE FIXED (frame)
	};

	protected int parts = 1;
	
	public RenderItemBase(ModelMultipart model, ResourceLocation texture) {
		super();
		this.model = model;
		this.texture = texture;
	}

	public RenderItemBase setGUIScale(float guiscale) {
		this.scale_gui = guiscale;
		return this;
	}
	
	public RenderItemBase setFirstPersonScale(float scale) {
		this.scale_ego = scale;
		return this;
	}
	
	public RenderItemBase setGroundAndFrameScale(float scale) {
		this.scale_ground = scale;
		this.scale_itemframe=scale;
		return this;
	}

	/**
	 * Set the basic translation applied to ALL types
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public RenderItemBase setBaseTranslation(float x, float y, float z) {
		this.translateBase[0] = x;
		this.translateBase[1] = y;
		this.translateBase[2] = z;
		return this;
	}

	/**
	 * TRANSLATE FIRST PERSON x,y,z
	 * TRANSLATE THIRD PERSON x,y,z
	 * TRANSLATE GUI x,y,z
	 * TRANSLATEGROUND x,y,z
	 * TRANLATE FIXED (frame) x,y,z
	 * 
	 * Left hand gets automatically mirrored
	 * 
	 * @param translations
	 *            - must be a float[5][3]
	 * @return
	 */
	public RenderItemBase setTransformTranslations(float[][] translations) {
		this.translateType = translations;
		return this;
	}
	
	public RenderItemBase setBaseScale(float baseScale) {
		this.baseScale = baseScale;
		return this;
	}

	protected float getScaleFactorFromTransform(TransformType transform) {
		switch (transform) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			return baseScale * scale_ego;

		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			return baseScale * scale_thirdp;

		case GUI:
			return baseScale * scale_gui;

		case GROUND:
			return baseScale * scale_ground;

		case FIXED:
			return baseScale * scale_itemframe;

		default:
			return baseScale;

		}
	}

	protected void applyTranslation(TransformType transform) {
		int index = -1;
		boolean flip = false;

		switch (transform) {
		case FIRST_PERSON_LEFT_HAND:
			flip = true; // fallthrough
		case FIRST_PERSON_RIGHT_HAND:
			index = 0;
			break;

		case THIRD_PERSON_LEFT_HAND:
			flip = true; // fallthrough
		case THIRD_PERSON_RIGHT_HAND:
			index = 1;
			break;
		case GUI:
			index = 2;
			break;
		case GROUND:
			index = 3;
			break;
		case FIXED:
			index = 4;
			break;
		default:
			break;
		}
		if (index >= 0) {
			float mirror = flip?-1.0f:1.0f;
			//GlStateManager.translate((translateType[index][0]+Keybinds.X)*mirror, translateType[index][1]+Keybinds.Y, translateType[index][2]+Keybinds.Z);
			GlStateManager.translate((translateType[index][0])*mirror, translateType[index][1], translateType[index][2]);
		}
	}
	
	/**
	 * center the model
	 */
	protected void applyBaseTranslation(){
		GlStateManager.translate(this.translateBase[0], this.translateBase[1], this.translateBase[2]);
		//GlStateManager.translate(this.translateBase[0]+Keybinds.X, this.translateBase[1]+Keybinds.Y, this.translateBase[2]+Keybinds.Z);
	}
	
	
	@Override
	public void renderItem(TransformType transform, ItemStack stack, EntityLivingBase elb, boolean leftHanded) {
		
			GlStateManager.pushMatrix();

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			this.applyTranslation(transform);

			if (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform) {

			} else if (TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform) {

			} else if (TransformType.GUI == transform) {
				GlStateManager.rotate(40.0f, 0, 1f, 0);
				GlStateManager.rotate(20.0f, 1f, 0, 0);

			} else if (TransformType.GROUND == transform) {

			} else if (TransformType.FIXED == transform) {
				GlStateManager.rotate(-90.0f, 0, 1.0f, 0);
			}

			this.setBaseScale(elb,transform);
			this.setBaseRotation(transform);
			this.applyBaseTranslation();
			
			for (int i = 0; i < parts; i++) {
				model.render(elb, 0, 0, 0, 0, 0, SCALE, 0, 0, transform, i, 0, 0f);
			}
			this.renderItemParticles(elb, transform, ClientProxy.get().PARTIAL_TICK_TIME);
			
			GlStateManager.popMatrix();

	}

	protected void setBaseScale(EntityLivingBase entity, TransformType transform) {
		float scale = getScaleFactorFromTransform(transform);
		
		if( entity!=null && entity instanceof INPCTechgunsShooter) {
			INPCTechgunsShooter shooter = (INPCTechgunsShooter) entity;
			scale *=shooter.getGunScale();
		}
		
		GlStateManager.scale(scale, scale, scale);
	}

	protected void setBaseRotation(TransformType transform) {
		GlStateManager.rotate(-180.0f, 1.0f, 0, 0);
		GlStateManager.rotate(180.0f, 0f, 1.0f, 0);
	}

	protected void renderItemParticles(EntityLivingBase ent, TransformType transform, float ptt) {
		EnumHand hand = EnumHand.MAIN_HAND;
		
		List<ITGParticle> particles = null;
		if(ent instanceof EntityPlayer) {
			particles = TGExtendedPlayerClient.get((EntityPlayer) ent).getEntityParticlesMH();
		} else if (ent instanceof INPCTechgunsShooter){
			particles = TGShooterValues.get(ent).getEntityParticlesMH();
		} 
			
		if (particles != null && !particles.isEmpty()) {
			GlStateManager.disableCull();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			particles.forEach(p -> {
				//System.out.println("Render Particle!");
				//System.out.println("Pos:"+p.getPos());
				p.doRender(buffer, ent, ptt, 1.0f, 1.0f, 0, 0, 0);
			});
			GlStateManager.enableCull();
		}
		
	}
}
