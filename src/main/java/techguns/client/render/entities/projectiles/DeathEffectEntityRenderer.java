package techguns.client.render.entities.projectiles;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;

public class DeathEffectEntityRenderer {
	
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static final ResourceLocation RES_BIO_EFFECT = new ResourceLocation(Techguns.MODID,"textures/fx/bio.png");
	private static final ResourceLocation RES_LASER_EFFECT = new ResourceLocation(Techguns.MODID,"textures/fx/laserdeath.png");
	private static final int MAX_DEATH_TIME = 20;

	public static Field RLB_mainModel = ReflectionHelper.findField(RenderLivingBase.class, "mainModel", "field_77045_g");
	protected static Method RLB_preRenderCallback = ReflectionHelper.findMethod(RenderLivingBase.class, "preRenderCallback", "func_77041_b", EntityLivingBase.class, float.class);
	protected static Method R_bindEntityTexture = ReflectionHelper.findMethod(Render.class, "bindEntityTexture", "func_180548_c", Entity.class);

	public static Field R_renderManager = ReflectionHelper.findField(Render.class, "renderManager","field_76990_c");
	
	protected static Method R_bindTexture = ReflectionHelper.findMethod(Render.class, "bindTexture","func_110776_a", ResourceLocation.class);
	protected static Method RLB_getColorMultiplier = ReflectionHelper.findMethod(RenderLivingBase.class, "getColorMultiplier","func_77030_a" ,EntityLivingBase.class, float.class, float.class);

	

	
	
	public static void preRenderCallback(RenderLivingBase<? extends EntityLivingBase> renderer, EntityLivingBase elb, float ptt) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RLB_preRenderCallback.invoke(renderer, elb, ptt);
	}

	public static void bindEntityTexture(Render<? extends Entity> renderer, Entity entity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		R_bindEntityTexture.invoke(renderer, entity);
	}
	
	
	
	
	
	
	
	
	
	public static void setRenderScalingForEntity(EntityLivingBase elb){
		if (elb instanceof EntitySlime){
			EntitySlime slime = (EntitySlime)elb;
			int size = slime.getSlimeSize();
			GlStateManager.scale((float)size, (float)size, (float)size);
			
			//slimes are 1,2 and 4
			if (size==2){
				GlStateManager.translate(0, -0.8f, 0);
			} else if (size==4){
				GlStateManager.translate(0, -1.2f, 0);
			}
		}
		
	}
	/**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
	public static void doRender(RenderLivingBase renderer, EntityLivingBase entity, double x, double y, double z, float ptt, DeathType deathType)
	{
		
		//System.out.println("BIODEATH!2");
		GlStateManager.pushMatrix();
        GlStateManager.disableCull();
	        //renderer.mainModel.onGround = renderer.renderSwingProgress(entity, ptt);

	     //   if (renderer.renderPassModel != null)
	     //   {
	     //       renderer.renderPassModel.onGround = renderer.mainModel.onGround;
	     //   }

	     //   renderer.mainModel.isRiding = entity.isRiding();

	     //   if (renderer.renderPassModel != null)
	     //   {
	     //       renderer.renderPassModel.isRiding = renderer.mainModel.isRiding;
	     //   }
	        ModelBase mainModel = null;
//	        ModelBase renderPassModel = null;
	        try {
	        	mainModel = (ModelBase)RLB_mainModel.get(renderer);
//	        	renderPassModel = (ModelBase)RLB_renderPassModel.get(renderer);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        mainModel.isChild = entity.isChild();

//	        if (renderPassModel != null)
//	        {
//	            renderPassModel.isChild = mainModel.isChild;
//	        }

	        try
	        {
	            float f2 = MathUtil.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, ptt);
	            float f3 = MathUtil.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, ptt);
	            float f4;        

	            float f13 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * ptt;
	            
	            GlStateManager.translate((float)x, (float)y, (float)z);
	            
	            f4 = (float)entity.ticksExisted + ptt;
	                      
	            float f5 = 0.0625F;
	            //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            GlStateManager.enableRescaleNormal();
	            GlStateManager.scale(-1.0f, -1.0f, -1.0f);
	            //GL11.glScalef(-1.0F, -1.0F, 1.0F);
	            
	            //renderer.preRenderCallback(entity, ptt);
	            
	            GlStateManager.translate(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
	            
	            float f6 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * ptt;
	            float f7 = entity.limbSwing - entity.limbSwingAmount * (1.0F - ptt);

	            if (entity.isChild())
	            {
	                f7 *= 3.0F;
	            }

	            if (f6 > 1.0F)
	            {
	                f6 = 1.0F;
	            }

	            //GL11.glEnable(GL11.GL_ALPHA_TEST);
	            GlStateManager.enableAlpha();
	            
	            switch(deathType) {
				case BIO:
					mainModel.setLivingAnimations(entity, f7, f6, ptt);
		            //renderModel(renderer, entity, f7, f6, f4, f3 - f2, f13, f5, null, RenderType.SOLID);
		            //renderModel(renderer, entity, f7, f6, f4, f3 - f2, f13, f5, RES_BIO_EFFECT, RenderType.ADDITIVE);
					preRenderCallback(renderer, entity, ptt);
					renderModelDeathBio(renderer, entity, f7, f6, f4, f3 - f2, f13, f5);
					break;
				case LASER:
					mainModel.setLivingAnimations(entity, f7, f6, ptt);
					preRenderCallback(renderer, entity, ptt);
					renderModelDeathLaser(renderer, entity, f7, f6, f4, f3 - f2, f13, f5);
					break;
//				case DISMEMBER:
//					mainModel.setLivingAnimations(entity, f7, f6, ptt);
//					renderModelDeathDismember(renderer, entity, f7, f6, f4, f3 - f2, f13, f5, ptt);
//					break;
				case GORE:
				case DEFAULT:
				default:
					break;
	            
	            }
	            /**DO NOT DISABLE ALPHA, VANILLA DOESN'T DO IT EITHER**/
	        //    GlStateManager.disableAlpha();    
	            GlStateManager.disableRescaleNormal();
	            //renderExtraPasses(renderer, entity, f7, f6, f4, f3-f2, f13, f5, ptt);
	            
	        }catch (Exception exception)
	        {
	            //logger.error("Couldn\'t render entity", exception);
	        }
	            

	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.enableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	        GlStateManager.enableCull();
	        GlStateManager.popMatrix();
	        //renderer.passSpecialRender(entity, x, y, z);
	}
	
	//------------
	
	/**
     * Renders the model in RenderLiving
     */
    static void renderModelDeathBio(RenderLivingBase renderer, EntityLivingBase entity, float f7, float f6, float f4, float p_77036_5_, float f13, float f5)
    {
    	float prog = ((float)entity.deathTime / (float)MAX_DEATH_TIME); 
    	
    	Random rand = new Random(entity.getEntityId());
    	ResourceLocation texture = RES_BIO_EFFECT;
    	RenderType renderType = RenderType.ADDITIVE;
    	ModelBase mainModel = null;
//    	ModelBase renderPassModel;
    	RenderManager renderManager = null;
    	try {
    		mainModel = (ModelBase)RLB_mainModel.get(renderer);
    		renderManager = (RenderManager)R_renderManager.get(renderer);
//            renderPassModel = (ModelBase)RLB_renderPassModel.get(renderer);
           	R_bindEntityTexture.invoke(renderer, entity);
        	
        }catch (Exception e) {
        	e.printStackTrace();
        }
    	//System.out.println("BoxList: "+mainModel.boxList.size());
    	//1st: Entity Texture
        //mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
        
    	if (mainModel instanceof ModelBiped) {
    		((ModelBiped)mainModel).setRotationAngles(f7, f6, f4, p_77036_5_, f13, f5, entity);
    	}
    	
    	HashSet<ModelRenderer> childBoxes = new HashSet<ModelRenderer>(64);
        for (Object o : mainModel.boxList) {
        	ModelRenderer box = (ModelRenderer)o;
        	if (box.childModels != null) {
        		childBoxes.addAll(box.childModels);
        	}
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
                
        setRenderScalingForEntity(entity);
        
        for (Object o : mainModel.boxList) {
        	ModelRenderer box = (ModelRenderer)o;
        	if (!childBoxes.contains(box) && !box.isHidden && box.showModel) {
        		float scale = 1.0f+(rand.nextFloat()*prog);
        		GlStateManager.pushMatrix();
        		GlStateManager.translate(-box.offsetX, -box.offsetY, -box.offsetZ);
        		GlStateManager.scale(scale, scale, scale);
        		GlStateManager.translate(box.offsetX, box.offsetY, box.offsetZ);
        		double mainColor = 1.0-Math.pow(prog, 2.0);
        		double mainAlpha = Math.pow(1.0-prog, 2.0);
        		GlStateManager.color((float)mainColor, 1.0f, (float)mainColor, (float)mainAlpha);
        		box.render(f5);
        		renderManager.renderEngine.bindTexture(RES_BIO_EFFECT);
            	TGRenderHelper.enableBlendMode(renderType);
            	double overlayColor = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2);
            	GlStateManager.color((float)overlayColor, (float)overlayColor, (float)overlayColor);
        		box.render(f5);
                TGRenderHelper.disableBlendMode(renderType);
                GlStateManager.popMatrix();
        	}
        }
        
        GlStateManager.popMatrix();
        
        //2nd: Render Overlay texture
//        renderManager.renderEngine.bindTexture(RES_BIO_EFFECT);
//    	TGRenderHelper.enableBlendMode(renderType);
//        mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);      
//        TGRenderHelper.disableBlendMode(renderType);
    }
	
	/**
     * Renders the model in RenderLiving
     */
    static void renderModelDeathLaser(RenderLivingBase renderer, EntityLivingBase entity, float f7, float f6, float f4, float p_77036_5_, float f13, float f5)
    {
    	float prog = ((float)entity.deathTime / (float)MAX_DEATH_TIME); 
    	
    	Random rand = new Random(entity.getEntityId());
    	//ResourceLocation texture = RES_BIO_EFFECT;
    	RenderType renderType = RenderType.ADDITIVE;
    	ModelBase mainModel = null;
    	//ModelBase renderPassModel;
    	RenderManager renderManager = null;
    	try {
    		mainModel = (ModelBase)RLB_mainModel.get(renderer);
    		renderManager = (RenderManager)R_renderManager.get(renderer);
            //renderPassModel = (ModelBase)R_renderPassModel.get(renderer);
           	R_bindEntityTexture.invoke(renderer, entity);
        	
        }catch (Exception e) {
        	e.printStackTrace();
        }
    	//System.out.println("BoxList: "+mainModel.boxList.size());
    	//1st: Entity Texture
        //mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
        
    	if (mainModel instanceof ModelBiped) {
    		((ModelBiped)mainModel).setRotationAngles(f7, f6, f4, p_77036_5_, f13, f5, entity);
    	}
    	
//    	HashSet<ModelRenderer> childBoxes = new HashSet<ModelRenderer>(64);
//        for (Object o : mainModel.boxList) {
//        	ModelRenderer box = (ModelRenderer)o;
//        	if (box.childModels != null) {
//        		childBoxes.addAll(box.childModels);
//        	}
//        }
        
        GlStateManager.pushMatrix();
        GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
        
        setRenderScalingForEntity(entity);
        
//        for (Object o : mainModel.boxList) {
//        	ModelRenderer box = (ModelRenderer)o;
//        	if (!childBoxes.contains(box) && !box.isHidden && box.showModel) {
//        		float scale = 1.0f-(rand.nextFloat()*prog);
//        		GL11.glPushMatrix();		
//        		GL11.glTranslatef(-box.offsetX, -box.offsetY, -box.offsetZ);
//        		GL11.glScalef(scale, scale, scale);
//        		GL11.glTranslatef(box.offsetX, box.offsetY, box.offsetZ);
//        		double mainColor = 1.0-Math.pow(prog, 2.0);
//        		double mainAlpha = Math.pow(1.0-prog, 2.0);
//        		GL11.glColor4d(1.0,mainColor, mainColor, mainAlpha);
//        		box.render(f5);
//        		renderManager.renderEngine.bindTexture(RES_LASER_EFFECT);
//            	TGRenderHelper.enableBlendMode(renderType);
//            	double overlayColor = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2);
//            	GL11.glColor3d(overlayColor, overlayColor, overlayColor);
//        		box.render(f5);
//                TGRenderHelper.disableBlendMode(renderType);
//
//        		GL11.glPopMatrix();
//        	}
//        }
        
      //  GlStateManager.pushMatrix();		
		double mainColor = 1.0-Math.pow(prog, 2.0);
		double mainAlpha = Math.pow(1.0-prog, 2.0);
		GlStateManager.color(1.0f, (float)mainColor, (float)mainColor, (float)mainAlpha);
		mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
		renderManager.renderEngine.bindTexture(RES_LASER_EFFECT);
    	TGRenderHelper.enableBlendMode(renderType);
    	double overlayColor = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2);
    	GlStateManager.color((float)overlayColor, (float)overlayColor, (float)overlayColor);
    	mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
        TGRenderHelper.disableBlendMode(renderType);

		//GlStateManager.popMatrix();
 
        GlStateManager.popMatrix();
        
    }
	

    /**
     * Renders the model in RenderLiving
     */
    
    /*
    static void renderModelDeathDismember(RenderLivingBase renderer, EntityLivingBase entity, float f7, float f6, float f4, float p_77036_5_, float f13, float f5, float ptt)
    {
    	float prog = (((float)entity.deathTime)-ptt) / (float)MAX_DEATH_TIME; 
    	
    	Random rand = new Random(entity.getEntityId());
    	ResourceLocation texture = RES_BIO_EFFECT;
    	RenderType renderType = RenderType.ADDITIVE;
    	ModelBase mainModel = null;
    	ModelBase renderPassModel;
    	RenderManager renderManager = null;
    	try {
    		mainModel = (ModelBase)R_mainModel.get(renderer);
    		renderManager = (RenderManager)R_renderManager.get(renderer);
            renderPassModel = (ModelBase)R_renderPassModel.get(renderer);
           	R_bindEntityTexture.invoke(renderer, entity);
        	
        }catch (Exception e) {
        	e.printStackTrace();
        }
    	
    	GL11.glPushMatrix();
	    GL11.glRotated(entity.rotationYaw, 0, 1, 0);	                
	    setRenderScalingForEntity(entity);
	      
	    
    	if (mainModel instanceof ModelBiped) {
    		GL11.glPushMatrix();
    		GLTransformDeath_Body(prog, entity, 1, 0, 0);
    		
    		ModelBiped modelBiped = (ModelBiped)mainModel;
    		modelBiped.setRotationAngles(f7, f6, f4, p_77036_5_, f13, f5, entity);
    		
    		modelBiped.bipedBody.render(f5);
    		//modelBiped.bipedHead.render(f5);
    		modelBiped.bipedLeftArm.render(f5);
    		modelBiped.bipedRightArm.render(f5);
    		modelBiped.bipedLeftLeg.render(f5);
    		modelBiped.bipedRightLeg.render(f5);
    		
    		//biped_renderEquippedItems(renderer, renderManager, modelBiped, (EntityLiving)entity, ptt);
    		GL11.glPopMatrix();
    		
    		GL11.glPushMatrix();
    		GLTransformDeath_Head(prog, entity, rand, modelBiped.bipedHead);
    		modelBiped.bipedHead.render(f5);
    		GL11.glPopMatrix();
    		
    	}else if (mainModel instanceof ModelQuadruped) {
    		GL11.glPushMatrix();
    		GLTransformDeath_Body(prog, entity, 0, 0, 1);
    		
    		ModelQuadruped modelQ = (ModelQuadruped)mainModel;
    		modelQ.setRotationAngles(f7, f6, f4, p_77036_5_, f13, f5, entity);

    		//modelQ.head.render(f5);
    		modelQ.body.render(f5);
    		modelQ.leg1.render(f5);
    		modelQ.leg2.render(f5);
    		modelQ.leg3.render(f5);
    		modelQ.leg4.render(f5);

    		GL11.glPopMatrix();
    		GL11.glPushMatrix();
    		GLTransformDeath_Head(prog, entity, rand, modelQ.head);
    		modelQ.head.render(f5);
    		GL11.glPopMatrix();
    		
    	}else {
    	
	    	HashSet<ModelRenderer> childBoxes = new HashSet<ModelRenderer>(64);
	        for (Object o : mainModel.boxList) {
	        	ModelRenderer box = (ModelRenderer)o;
	        	if (box.childModels != null) {
	        		childBoxes.addAll(box.childModels);
	        	}
	        }             
	        
	        System.out.println("boxes.size = "+ childBoxes.size());
	        for (Object o : mainModel.boxList) {
	        	ModelRenderer box = (ModelRenderer)o;
	        	if (!childBoxes.contains(box) && !box.isHidden && box.showModel) {
	
	        		//if (rand.nextDouble() > 0.25) {
	        			box.render(f5);
	        		//}
	        			
	        			
	        	}
	        }
	        
    	}
        GL11.glPopMatrix();
        
        //2nd: Render Overlay texture
//        renderManager.renderEngine.bindTexture(RES_BIO_EFFECT);
//    	TGRenderHelper.enableBlendMode(renderType);
//        mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);      
//        TGRenderHelper.disableBlendMode(renderType);
    }
    */
	
    
    static void GLTransformDeath_Body(float prog, EntityLivingBase entity, double xdir, double ydir, double zdir) {
	    double p = Math.min(1.0, prog*2.0);
	    double y = Math.sin(p*Math.PI);//(1.0-Math.cos(p*2.0*Math.PI))*0.5;
	    double y2 = (1.0-Math.cos(p*Math.PI))*0.5;	       
	    GlStateManager.translate(0, +entity.height*0.75, 0);
	    GlStateManager.rotate(-90.0f*(float)y2, (float)xdir, (float)ydir, (float)zdir);
	    GlStateManager.translate(0, -entity.height*0.75, 0);   
	    GlStateManager.translate(0, (0.25*+y), 0);
    }
    
    static void GLTransformDeath_Head(float prog, EntityLivingBase entity, Random rand, ModelRenderer head) {
	    double p = Math.min(1.0, prog*2.0);
	    double a = Math.sin(p*Math.PI);//(1.0-Math.cos(p*2.0*Math.PI))*0.5;
	    double a2 = (1.0-Math.cos(p*Math.PI))*0.5;	  
	    
	    //Get Head Center
	    double x1=-1, x2=-1;
	    double y1=-1, y2=-1;
	    double z1=-1, z2=-1;
	    for (Object b : head.cubeList) {
	    	ModelBox box = ((ModelBox)b);
	    	if (x1 == -1 || box.posX1 < x1) x1 = box.posX1;
	    	if (x2 == -1 || box.posX2 > x2) x2 = box.posX2;
	    	if (y1 == -1 || box.posY1 < y1) y1 = box.posY1;
	    	if (y2 == -1 || box.posY2 > y2) y2 = box.posY2;
	    	if (z1 == -1 || box.posZ1 < z1) z1 = box.posZ1;
	    	if (z2 == -1 || box.posZ2 > z2) z2 = box.posZ2;
	    }
	    double d = 0.0625;
	    double offsetX = /*(head.offsetX+*/(x1+(x2-x1)*0.5)*d;
	    double offsetY = /*(head.offsetY+*/(y1+(y2-y1)*0.5)*d;
	    double offsetZ = /*(head.offsetZ+*/(z1+(z2-z1)*0.5)*d;
	    
	    //System.out.println("offsets: "+offsetX+" / "+offsetY+" / " + offsetZ);
	    //GL11.glTranslated(a2, (2.5*a), a2);
	    
	    GlStateManager.translate(offsetX, offsetY, offsetZ);
	    GlStateManager.rotate(360.0f*prog, rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	    GlStateManager.translate(-offsetX, -offsetY, -offsetZ);  

    }
	
	//---------------
	
	
	/**
     * Renders the model in RenderLiving
     */
    static void renderModel(RenderLivingBase renderer, EntityLivingBase entity, float f7, float f6, float f4, float p_77036_5_, float f13, float f5, ResourceLocation texture, RenderType renderType)
    {
    	
    	ModelBase mainModel = null;
    	ModelBase renderPassModel;
    	RenderManager renderManager = null;
    	try {
    		mainModel = (ModelBase)RLB_mainModel.get(renderer);
    		renderManager = (RenderManager)R_renderManager.get(renderer);
//            renderPassModel = (ModelBase)RLB_renderPassModel.get(renderer);
            
            if (texture != null) {
            	renderManager.renderEngine.bindTexture(RES_BIO_EFFECT);
            }else {
            	R_bindEntityTexture.invoke(renderer, entity);
            }
        	
        }catch (Exception e) {
        	e.printStackTrace();
        }
    
    	TGRenderHelper.enableBlendMode(renderType);
    	
        if (!entity.isInvisible())
        {
            mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
        }
//        else if (!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
//        {
//            GL11.glPushMatrix();
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
//            GL11.glDepthMask(false);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
//            mainModel.render(entity, f7, f6, f4, p_77036_5_, f13, f5);
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
//            GL11.glPopMatrix();
//            GL11.glDepthMask(true);
//        }
//        else
//        {
//            mainModel.setRotationAngles(f7, f6, f4, p_77036_5_, f13, f5, entity);
//        }
        
        TGRenderHelper.disableBlendMode(renderType);
    }

  /*  
    static void renderExtraPasses(RenderLivingBase renderer, EntityLivingBase entity, float f7, float f6, float f4, float f3f2, float f13, float f5, float ptt) {
    	ModelBase mainModel = null;
        ModelBase renderPassModel = null;
        try {
        	mainModel = (ModelBase)RLB_mainModel.get(renderer);
        	renderPassModel = (ModelBase)RLB_renderPassModel.get(renderer);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
    	try {
    		int j = 0;
    		float f8;
    		float f9;
    		float f10;
	    	for (int i = 0; i < 4; ++i)
	        {
	        	try {
	            	j = (int) RLB_shouldRenderPass.invoke(renderer, entity, i, ptt);
	            }catch (Exception e) {
	            	e.printStackTrace();
	            }
	
	            if (j > 0)
	            {
	                renderPassModel.setLivingAnimations(entity, f7, f6, ptt);
	                renderPassModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	
	                if ((j & 240) == 16)
	                {
	                    renderPassModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	                }
	
	                if ((j & 15) == 15)
	                {
	                    f8 = (float)entity.ticksExisted + ptt;
	                    try {
			            	R_bindTexture.invoke(renderer, RES_ITEM_GLINT);
			            }catch (Exception e) {
			            	e.printStackTrace();
			            }
	                    GL11.glEnable(GL11.GL_BLEND);
	                    f9 = 0.5F;
	                    GL11.glColor4f(f9, f9, f9, 1.0F);
	                    GL11.glDepthFunc(GL11.GL_EQUAL);
	                    GL11.glDepthMask(false);
	
	                    for (int k = 0; k < 2; ++k)
	                    {
	                        GL11.glDisable(GL11.GL_LIGHTING);
	                        f10 = 0.76F;
	                        GL11.glColor4f(0.5F * f10, 0.25F * f10, 0.8F * f10, 1.0F);
	                        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
	                        GL11.glMatrixMode(GL11.GL_TEXTURE);
	                        GL11.glLoadIdentity();
	                        float f11 = f8 * (0.001F + (float)k * 0.003F) * 20.0F;
	                        float f12 = 0.33333334F;
	                        GL11.glScalef(f12, f12, f12);
	                        GL11.glRotatef(30.0F - (float)k * 60.0F, 0.0F, 0.0F, 1.0F);
	                        GL11.glTranslatef(0.0F, f11, 0.0F);
	                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	                        renderPassModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	                    }
	
	                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	                    GL11.glMatrixMode(GL11.GL_TEXTURE);
	                    GL11.glDepthMask(true);
	                    GL11.glLoadIdentity();
	                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	                    GL11.glEnable(GL11.GL_LIGHTING);
	                    GL11.glDisable(GL11.GL_BLEND);
	                    GL11.glDepthFunc(GL11.GL_LEQUAL);
	                }
	
	                GL11.glDisable(GL11.GL_BLEND);
	                GL11.glEnable(GL11.GL_ALPHA_TEST);
	            }
	        }
	
	        GL11.glDepthMask(true);
	        try {
	        	R_renderEquippedItems.invoke(renderer, entity, ptt);
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	        float f14 = entity.getBrightness(ptt);
	        
	        try {
	        	j = (int) R_getColorMultiplier.invoke(renderer, entity, f14, ptt);
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	
	        if ((j >> 24 & 255) > 0 || entity.hurtTime > 0 || entity.deathTime > 0)
	        {
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            GL11.glDisable(GL11.GL_ALPHA_TEST);
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            GL11.glDepthFunc(GL11.GL_EQUAL);
	
	            if (entity.hurtTime > 0 || entity.deathTime > 0)
	            {
	                GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
	                mainModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	
	                for (int l = 0; l < 4; ++l)
	                {
	                	int iRP = 0;
	                	try {
			            	iRP = (int) R_inheritRenderPass.invoke(renderer, entity, l, ptt);
			            }catch (Exception e) {
			            	e.printStackTrace();
			            }
	                    if (iRP >= 0)
	                    {
	                        GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
	                        renderPassModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	                    }
	                }
	            }
	
	            if ((j >> 24 & 255) > 0)
	            {
	                f8 = (float)(j >> 16 & 255) / 255.0F;
	                f9 = (float)(j >> 8 & 255) / 255.0F;
	                float f15 = (float)(j & 255) / 255.0F;
	                f10 = (float)(j >> 24 & 255) / 255.0F;
	                GL11.glColor4f(f8, f9, f15, f10);
	                mainModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	
	                for (int i1 = 0; i1 < 4; ++i1)
	                {
	                	int iRP = 0;
	                	try {
			            	iRP = (int) R_inheritRenderPass.invoke(renderer, entity, i1, ptt);
			            }catch (Exception e) {
			            	e.printStackTrace();
			            }
	                    if (iRP >= 0)
	                    {
	                        GL11.glColor4f(f8, f9, f15, f10);
	                        renderPassModel.render(entity, f7, f6, f4, f3f2, f13, f5);
	                    }
	                }
	            }
	
	            GL11.glDepthFunc(GL11.GL_LEQUAL);
	            GL11.glDisable(GL11.GL_BLEND);
	            GL11.glEnable(GL11.GL_ALPHA_TEST);
	            GL11.glEnable(GL11.GL_TEXTURE_2D);
	        }
	
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	    }catch (Exception exception){
	        //logger.error("Couldn\'t render entity", exception);
	    }
    }

*/
    
    /*
    static void biped_renderEquippedItems(RendererLivingEntity renderer, RenderManager renderManager, ModelBiped modelBipedMain, EntityLiving p_77029_1_, float p_77029_2_)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        //super.renderEquippedItems(p_77029_1_, p_77029_2_);
        ItemStack itemstack = p_77029_1_.getHeldItem();
        ItemStack itemstack1 = p_77029_1_.func_130225_q(3);
        Item item;
        float f1;

        if (itemstack1 != null)
        {
            GL11.glPushMatrix();
            modelBipedMain.bipedHead.postRender(0.0625F);
            item = itemstack1.getItem();

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack1, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof ItemBlock)
            {
                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))
                {
                    f1 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f1, -f1, -f1);
                }

                renderManager.itemRenderer.renderItem(p_77029_1_, itemstack1, 0);
            }
            else if (item == Items.skull)
            {
                f1 = 1.0625F;
                GL11.glScalef(f1, -f1, -f1);
                GameProfile gameprofile = null;

                if (itemstack1.hasTagCompound())
                {
                    NBTTagCompound nbttagcompound = itemstack1.getTagCompound();

                    if (nbttagcompound.hasKey("SkullOwner", 10))
                    {
                        gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
                    }
                    else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner")))
                    {
                        gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
                    }
                }

                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(), gameprofile);
            }

            GL11.glPopMatrix();
        }

        if (itemstack != null && itemstack.getItem() != null)
        {
            item = itemstack.getItem();
            GL11.glPushMatrix();

            if (modelBipedMain.isChild)
            {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())))
            {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f1 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f1, -f1, f1);
            }
            else if (item == Items.bow)
            {
                f1 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (item.isFull3D())
            {
                f1 = 0.625F;

                if (item.shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                f1 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f1, f1, f1);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f2;
            int i;
            float f5;

            if (itemstack.getItem().requiresMultipleRenderPasses())
            {
                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i)
                {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (float)(j >> 16 & 255) / 255.0F;
                    f2 = (float)(j >> 8 & 255) / 255.0F;
                    float f3 = (float)(j & 255) / 255.0F;
                    GL11.glColor4f(f5, f2, f3, 1.0F);
                    renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, i);
                }
            }
            else
            {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (float)(i >> 16 & 255) / 255.0F;
                f5 = (float)(i >> 8 & 255) / 255.0F;
                f2 = (float)(i & 255) / 255.0F;
                GL11.glColor4f(f4, f5, f2, 1.0F);
                renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            }

            GL11.glPopMatrix();
        }
    }
    */
}
