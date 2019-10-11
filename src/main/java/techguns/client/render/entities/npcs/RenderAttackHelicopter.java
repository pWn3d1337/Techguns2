package techguns.client.render.entities.npcs;

import java.io.IOException;
import java.util.List;

import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import techguns.Techguns;
import techguns.entities.npcs.AttackHelicopter;

public class RenderAttackHelicopter extends RenderLiving<AttackHelicopter> {
	
	public static ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/entity/apache.png");
	
	private static final ModelResourceLocation modelLoc0 = new ModelResourceLocation(new ResourceLocation(Techguns.MODID,"helicopter0"), "inventory");
	private static final ModelResourceLocation modelLoc1 = new ModelResourceLocation(new ResourceLocation(Techguns.MODID,"helicopter1"), "inventory");
	private static final ModelResourceLocation modelLoc2 = new ModelResourceLocation(new ResourceLocation(Techguns.MODID,"helicopter2"), "inventory");
	private static IBakedModel helicopter_main;
	private static IBakedModel helicopter_rotor;
	private static IBakedModel helicopter_gun;

	public static void initModels() {
		helicopter_main = loadAndBakedModel(modelLoc0, texture);
		helicopter_rotor = loadAndBakedModel(modelLoc1, texture);
		helicopter_gun = loadAndBakedModel(modelLoc2, texture);
	}
	
	public static IBakedModel loadAndBakedModel(ModelResourceLocation model_loc, ResourceLocation tex) {
		 IModel model = ModelLoaderRegistry.getModelOrLogError(model_loc,"Could not load model:"+model_loc.toString());
	     IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, r -> {
	    	    TextureAtlasSprite sprite = new TextureAtlasSprite(texture.getResourcePath()) {};
	    	    try {
	    	    	PngSizeInfo png = PngSizeInfo.makeFromResource(Minecraft.getMinecraft().getResourceManager().getResource(tex));
	    	        sprite.loadSprite(png, false);
	    	        sprite.initSprite(png.pngWidth, png.pngHeight, 0, 0, false);
	    	    } catch (IOException e) {
	    	        throw new RuntimeException(e);
	    	    }
	    	    return sprite;
	    	});
	    return bakedModel;
	}
	
	public RenderAttackHelicopter(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBase() {}, 5.0f);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(AttackHelicopter entity) {
		return texture;
	}

	@Override
	public void doRender(AttackHelicopter entity, double x, double y, double z, float entityYaw, float partialTicks) {
		RenderHelper.disableStandardItemLighting();
        GlStateManager.pushMatrix();
        
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(-interp(entity.rotationYaw,entity.prevRotationYaw,partialTicks)-90f, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        
        //GlStateManager.rotate(180, 1, 0, 0);
        float scale = 2.5f; //1f/32f;
        GlStateManager.scale(scale,scale,scale);
        
        this.bindEntityTexture(entity);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        if (entity.deathTime==0) {
	        renderBakedModel(tessellator, builder, helicopter_main);
	        
	        GlStateManager.pushMatrix();
	        float angle = ((float)((Minecraft.getMinecraft().world.getTotalWorldTime() % 60)+partialTicks) * 24.0f);
	        GlStateManager.rotate(angle, 0f, 1f, 0f);
	        renderBakedModel(tessellator, builder, helicopter_rotor);
	        GlStateManager.popMatrix();
	        
	        GlStateManager.pushMatrix();
	        float offsetX=1.2f;
	        GlStateManager.translate(offsetX, 0, 0);
	        GlStateManager.rotate(-interp(entity.rotationPitch,entity.prevRotationPitch,partialTicks), 0f, 0f, 1f);
	        GlStateManager.translate(-offsetX, 0, 0);
	        renderBakedModel(tessellator, builder, helicopter_gun);
	        GlStateManager.popMatrix();
        
        } else {
        	
        	 float deathProgress = ((float)entity.deathTime+partialTicks) / (float)AttackHelicopter.MAX_DEATH_TIME;
        	 float p = (float) Math.pow(deathProgress, 2);
        	   
        	 GlStateManager.translate(0, p*-4f, 0);

        	 float angle = p * 720.0f;
	         GL11.glRotatef(angle, 0, 1.0f, 0);
	         GlStateManager.rotate(angle, 0, 1f, 0);
        	
	         renderBakedModel(tessellator, builder, helicopter_main);
	         renderBakedModel(tessellator, builder, helicopter_gun);


	         angle = ((float)((Minecraft.getMinecraft().world.getTotalWorldTime() % 60)+partialTicks) * 12.0f);
	         GlStateManager.rotate(angle, 0, 1f, 0);
	         renderBakedModel(tessellator, builder, helicopter_rotor);
        }
        
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
	}

	private void renderBakedModel(Tessellator tessellator, BufferBuilder buffer, IBakedModel model){
		buffer.begin(GL11.GL_QUADS, Attributes.DEFAULT_BAKED_FORMAT);
		for (BakedQuad bakedQuad : model.getQuads(null, null, 0))
			LightUtil.renderQuadColor(buffer, bakedQuad, -1);

		tessellator.draw();
	}

	
	public float interp(float val, float prev_value, float ptt) {
		return prev_value+ (val-prev_value)*ptt;
	}
}
