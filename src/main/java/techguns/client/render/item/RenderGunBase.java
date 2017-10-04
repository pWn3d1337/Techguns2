package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import techguns.api.capabilities.AttackTime;
import techguns.api.capabilities.ITGShooterValues;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.capabilities.TGExtendedPlayer;
import techguns.capabilities.TGShooterValues;
import techguns.client.ClientProxy;
import techguns.client.models.ModelMultipart;
import techguns.client.render.fx.ScreenEffect;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;

public class RenderGunBase extends RenderItemBase {

	protected ScreenEffect muzzleFX= null; //ScreenEffect.muzzleFlash2;
	
	protected ScreenEffect scope=null;
	
	protected float muzzleFX_x_l=0f;
	protected float muzzleFX_x_r=0f;
	protected float muzzleFX_y=0f;
	protected float muzzleFX_z=0f;
	protected float muzzleFX_scale=1.0f;

	protected float muzzleFX_3p_y=0f;
	protected float muzzleFX_3p_z=0f;
	protected float muzzleFX_3p_scale=0.5f;
		
	protected float mf_jitterX = 0f;
	protected float mf_jitterY = 0f;
	protected float mf_jitterAngle = 0f;
	protected float mf_jitterScale = 0f;
	
	protected GunAnimation recoilAnim = GunAnimation.genericRecoil;
	protected float[] recoilParams = new float[] {0.15f, 5.0f};
	protected GunAnimation reloadAnim = GunAnimation.genericReload;
	protected float[] reloadParams = new float[] {1.0f, 40.0f};
	
	protected GunAnimation scopeRecoilAnim = null; //GunAnimation.scopeRecoil;
	protected float[] scopeRecoilParams = null; //new float[] {0.25f, 1.0f};	
	
	protected GunAnimation recoilAnim3p = GunAnimation.genericRecoil;
	protected float[] recoilParams3p = new float[] {0f, 5.0f};
	protected GunAnimation reloadAnim3p = GunAnimation.genericReload;
	protected float[] reloadParams3p = new float[] {0f, 40.0f};
	
	protected float scopescale=3.0f;
	
	protected float chargeTranslation=0.25f;
	
	public RenderGunBase(ModelMultipart model, int parts) {
		super(model, null);
		this.parts = parts;
		this.scale_ground=this.scale_thirdp;
	}

	/**
	 * Set the basic translation applied to ALL types
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	@Override
	public RenderGunBase setBaseTranslation(float x, float y, float z) {
		return (RenderGunBase) super.setBaseTranslation(x, y, z);
	}

	@Override
	public RenderGunBase setGUIScale(float guiscale){
		return (RenderGunBase) super.setGUIScale(guiscale);
	}
	
	public RenderGunBase setReloadAnim(GunAnimation anim, float... params) {
		this.reloadAnim = anim;
		this.reloadParams = params;
		return this;
	}
	
	public RenderGunBase setRecoilAnim(GunAnimation anim, float... params) {
		this.recoilAnim = anim;
		this.recoilParams = params;
		return this;
	}
	
	public RenderGunBase setScopeRecoilAnim(GunAnimation anim, float... params) {
		this.scopeRecoilAnim = anim;
		this.scopeRecoilParams = params;
		return this;
	}
	
	public RenderGunBase setReloadAnim3p(GunAnimation anim, float... params) {
		this.reloadAnim3p = anim;
		this.reloadParams3p = params;
		return this;
	}
	
	public RenderGunBase setRecoilAnim3p(GunAnimation anim, float... params) {
		this.recoilAnim3p = anim;
		this.recoilParams3p = params;
		return this;
	}
	
	public RenderGunBase setChargeTranslationAmount(float value) {
		this.chargeTranslation=value;
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
	public RenderGunBase setTransformTranslations(float[][] translations) {
		return (RenderGunBase) super.setTransformTranslations(translations);
	}

	public RenderGunBase setMuzzleFx(ScreenEffect muzzleFX, float x, float y, float z, float scale, float x_l){
		this.muzzleFX=muzzleFX;
		this.muzzleFX_x_r=x;
		this.muzzleFX_x_l=x_l;
		this.muzzleFX_y=y;
		this.muzzleFX_z=z;
		this.muzzleFX_scale=scale;
		return this;
	}
	public RenderGunBase setMuzzleFXPos3P(float y, float z) {
		this.muzzleFX_3p_y=y;
		this.muzzleFX_3p_z=z;
		return this;
	}
	
	public RenderGunBase setScope(ScreenEffect scope) {
		this.scope=scope;
		return this;
	}
	
	public RenderGunBase setScope(ScreenEffect scope, float scale) {
		this.scope=scope;
		this.scopescale=scale;
		return this;
	}
	
	protected static ITGShooterValues getShooterValues(EntityLivingBase ent) {
		ITGShooterValues values = null;
		if (ent != null) {
			if (ent instanceof EntityPlayer) {
				values = TGExtendedPlayer.get((EntityPlayer) ent);
			} else {
				values = TGShooterValues.get(ent);
			}
		}
		return values;
	}

	@Override
	public void renderItem(TransformType transform, ItemStack stack, EntityLivingBase entityIn, boolean leftHand) {
		GenericGun gun = ((GenericGun) stack.getItem());
		ITGShooterValues values = getShooterValues(entityIn);
		
		//System.out.println("Render:"+stack+" for "+entityIn);
		
		boolean sneaking = false;
		boolean isOffhand = false;
		if (entityIn!=null){
			sneaking = entityIn.isSneaking();
			isOffhand = ((!leftHand) && entityIn.getPrimaryHand() == EnumHandSide.LEFT) || ((leftHand) && entityIn.getPrimaryHand() == EnumHandSide.RIGHT);
		
			if (entityIn.getPrimaryHand()== EnumHandSide.LEFT){
				leftHand = !leftHand;
			}
		}

		GlStateManager.pushMatrix();
		
		if(gun.hasCustomTexture) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(gun.getCurrentTexture(stack));
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		}

		float fireProgress = 0.0f;
		float reloadProgress = 0.0f;
		float muzzleFlashProgress = 0.0f;
		float chargeProgress = 0.0f;
		
		byte attackType=0;
		
		boolean renderScope = false;
		
		if (values != null && (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform
				|| TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform)) {
			AttackTime attack = values.getAttackTime(leftHand);
			attackType = attack.getAttackType();
			
			if (gun.canCharge() && !isOffhand && !entityIn.getActiveItemStack().isEmpty()) {
			
				int dur = stack.getItem().getMaxItemUseDuration(stack)-entityIn.getItemInUseCount();

				chargeProgress = dur / ((GenericGunCharge)stack.getItem()).fullChargeTime;
				
				if (chargeProgress < 0.0f) {
					chargeProgress = 0.0f;
				} else if (chargeProgress > 1.0f) {
					chargeProgress = 1.0f;
				}
				
			} else if (attack.isReloading()) {
				long diff = attack.getReloadTime() - System.currentTimeMillis();

				if (diff <= 0) {
					attack.setReloadTime(0);
					attack.setReloadTimeTotal(0);
					attack.setAttackType((byte) 0);
				} else {
					reloadProgress = 1.0f - ((float) diff / (float) attack.getReloadTimeTotal());
				}
			} else if (attack.isRecoiling()) {

				long diff = attack.getRecoilTime() - System.currentTimeMillis();

				if (diff <= 0) {
					attack.setRecoilTime(0);
					attack.setRecoilTimeTotal(0);
					attack.setAttackType((byte) 0);
				} else {
					fireProgress = 1.0f - ((float) diff / (float) attack.getRecoilTimeTotal());
				}
			}
			
			if (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform  || TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform){
				//Calculate muzzleFlash progress
								
				if(attack.getMuzzleFlashTime()>0) {
					long diff = attack.getMuzzleFlashTime() - System.currentTimeMillis();
					if (diff <= 0 || diff > attack.getMuzzleFlashTimeTotal()) {
						attack.setMuzzleFlashTime(0L);
						attack.setMuzzleFlashTimeTotal(0);
					}else{			
						muzzleFlashProgress = 1.0f-((float)diff / (float)attack.getMuzzleFlashTimeTotal());
					}		
				}
				
			} 
			
		}

		this.applyTranslation(transform);

		if (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform) {
			
			if (!isOffhand && gun.isZooming() && this.scope!=null) {
				renderScope = true;
			} else {
			
				/*if (!isOffhand && gun.getZoomMult()>0f && gun.isZooming()) {
					this.transformADS();
				}*/
				
				this.transformFirstPerson(fireProgress, reloadProgress, chargeProgress, TransformType.FIRST_PERSON_LEFT_HAND == transform, sneaking&&isOffhand);
			}

		} else if (TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform) {
			this.transformThirdPerson(entityIn, fireProgress, reloadProgress, TransformType.THIRD_PERSON_LEFT_HAND == transform);

		} else if (TransformType.GUI == transform) {
			this.transformGUI();

		} else if (TransformType.GROUND == transform) {
			this.transformGround();

		} else if (TransformType.FIXED == transform) {
			this.transformFixed();
		}

		
		if (!renderScope) {		
			this.setBaseScale(entityIn, transform);
			this.setBaseRotation(transform);
			this.applyBaseTranslation();

			for (int i = 0; i < parts; i++) {	
				model.render(entityIn, 0, 0, 0, 0, 0, SCALE, gun.getAmmoLeft(stack), reloadProgress, transform, i, fireProgress);
			}
			
			GlStateManager.popMatrix();
			//Draw muzzle FX
			if (muzzleFlashProgress>0){
				if (TransformType.FIRST_PERSON_LEFT_HAND== transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform ) {
					this.drawMuzzleFx(muzzleFlashProgress, attackType, leftHand);
				} else {
					this.drawMuzzleFx3P(muzzleFlashProgress, attackType, leftHand);
				}
			}else if (reloadProgress<=0){
				if (TransformType.FIRST_PERSON_LEFT_HAND== transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform ) {
					this.drawIdleFx(leftHand);
				} else {
					this.drawIdleFx3P(leftHand);
				}
			}
			
		} else {
			GlStateManager.popMatrix();
			if (this.scopeRecoilAnim != null && fireProgress > 0f) {
				this.scopeRecoilAnim.play(fireProgress, TransformType.FIRST_PERSON_LEFT_HAND == transform, this.scopeRecoilParams);
			}
			this.renderScope(fireProgress);
		}

	}

	protected void drawIdleFx(boolean leftHand) {}
	protected void drawIdleFx3P(boolean leftHand) {}

	protected void transformFirstPerson(float fireProgress, float reloadProgress, float chargeProgress, boolean left, boolean shoudLowerWeapon) {
		if (chargeProgress>0) {
		
			GlStateManager.translate(0, 0, this.chargeTranslation*chargeProgress);
			
		} else if (fireProgress >0){

			this.recoilAnim.play(fireProgress, left, this.recoilParams);
			
		} else if (reloadProgress>0){
			
			this.reloadAnim.play(reloadProgress, left, this.reloadParams);
			
		} else if (shoudLowerWeapon){
			GlStateManager.rotate(-35f, 1, 0, 0);
		}
	}

	protected void transformThirdPerson(EntityLivingBase ent, float fireProgress, float reloadProgress, boolean left) {
		if( ent!=null && ent instanceof INPCTechgunsShooter) {
			INPCTechgunsShooter shooter = (INPCTechgunsShooter) ent;
			if(!shooter.hasWeaponArmPose()) {
				GlStateManager.rotate(90.0f, 1, 0, 0);
				//GlStateManager.translate(Keybinds.X, Keybinds.Y, Keybinds.Z);
			}
			
		}
		
		
		if (fireProgress >0){
			
			this.recoilAnim3p.play(fireProgress, left, this.recoilParams3p);
			
		} else if (reloadProgress>0){	
			
			this.reloadAnim3p.play(reloadProgress, left, this.reloadParams3p);
		}
	}

	protected void transformGUI() {
		// GlStateManager.rotate(-22.5f, 1f, 2f, 0f);
		GlStateManager.rotate(40.0f, 0, 1f, 0);
		GlStateManager.rotate(20.0f, 1f, 0, 0);
	}

	protected void transformGround() {
		// GlStateManager.rotate(Keybinds.Y, 0, 1, 0);
	}

	protected void transformFixed() {
		GlStateManager.rotate(-90.0f, 0, 1.0f, 0);
	}
	
	protected void drawMuzzleFx(float progress, byte attackType, boolean leftHand){
		if (this.muzzleFX!=null){
			float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			//float leftOffset = leftHand?0.05f:0f;
			
			//Muzzle flash jitter
			ClientProxy cp = ClientProxy.get();
			float scale = this.muzzleFX_scale;
			float offsetX = x;
			float offsetY = this.muzzleFX_y;
			if (this.mf_jitterScale > 0.0f) scale += mf_jitterScale*cp.muzzleFlashJitterScale;
			if (this.mf_jitterX > 0.0f) offsetX += mf_jitterX*cp.muzzleFlashJitterX;
			if (this.mf_jitterY > 0.0f) offsetY += mf_jitterY*cp.muzzleFlashJitterY;
			//if (this.jitterAngle > 0.0f) angle += jitterAngle*cp.muzzleFlashJitterAngle*f;

			//this.muzzleFX.doRender(progress, (x+Keybinds.X)/*+leftOffset*/, this.muzzleFX_y+Keybinds.Y, this.muzzleFX_z+Keybinds.Z, this.muzzleFX_scale, false);
			
			this.muzzleFX.doRender(progress, offsetX, offsetY, this.muzzleFX_z, scale, false);
			
		}
	}

	protected void drawMuzzleFx3P(float progress, byte attackType, boolean leftHand) {
		if (this.muzzleFX!=null) {
			//float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			//this.muzzleFX.doRender(progress, 0, this.muzzleFX_3p_y+Keybinds.Y, this.muzzleFX_3p_z+Keybinds.Z, this.muzzleFX_scale*this.muzzleFX_3p_scale, true);
			this.muzzleFX.doRender(progress, 0, this.muzzleFX_3p_y, this.muzzleFX_3p_z, this.muzzleFX_scale*this.muzzleFX_3p_scale, true);
		}
	}
	
	protected void renderScope(float fireProgress) {
		if (this.scope!=null) {
			this.scope.doRender(fireProgress, -0.56f, 0.52f, 0f, scopescale, false);
		}
	}
	
	@Override
	public RenderGunBase setBaseScale(float baseScale) {
		return (RenderGunBase) super.setBaseScale(baseScale);
	}
	
	/*protected void transformADS() {
		GlStateManager.translate(-0.56f, 0.15f, 0.31f);
	}*/
	
	public RenderGunBase setMuzzleFlashJitter(float jX, float jY, float jAngle, float jScale) {
		this.mf_jitterX = jX;
		this.mf_jitterY = jY;
		this.mf_jitterAngle = jAngle;
		this.mf_jitterScale = jScale;
		return this;
	}
}
