package techguns.client.render.entities.projectiles;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.common.model.TRSRTransformation;
import techguns.Techguns;
import techguns.entities.projectiles.Grenade40mmProjectile;

public class RenderGrenade40mmProjectile extends Render<Grenade40mmProjectile>{

	private static final ResourceLocation texture = new ResourceLocation(Techguns.MODID, "textures/entity/launchergrenade.png");
	private static final ModelResourceLocation modelLoc = new ModelResourceLocation(new ResourceLocation(Techguns.MODID,"grenade40mm"), "inventory");
	private static IBakedModel bakedModel;
	private final VertexLighterFlat lighter = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors());
	public static void initModel() {
		 IModel model = ModelLoaderRegistry.getModelOrLogError(modelLoc,"Could not load grenande launcher projectile model");
	     bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, r -> {
	    	    TextureAtlasSprite sprite = new TextureAtlasSprite(texture.getResourcePath()) {};
	    	    try {
	    	    	PngSizeInfo png = PngSizeInfo.makeFromResource(Minecraft.getMinecraft().getResourceManager().getResource(texture));
	    	        sprite.loadSprite(png, false);
	    	        sprite.initSprite(png.pngWidth, png.pngHeight, 0, 0, false);
	    	    } catch (IOException e) {
	    	        throw new RuntimeException(e);
	    	    }
	    	    return sprite;
	    	});
	}
	
	public RenderGrenade40mmProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(Grenade40mmProjectile entity) {
		return texture; //TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public void doRender(Grenade40mmProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
		
		BlockPos pos = new BlockPos(entity.posX, entity.posY + entity.height, entity.posZ);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.pushMatrix();
        
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        
        GlStateManager.rotate(180, 1, 0, 0);
        float scale = 0.5f; //1f/32f;
        GlStateManager.scale(scale,scale,scale);
        
        this.bindEntityTexture(entity);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        //builder.setTranslation(-0.5, -1.5, -0.5);

        lighter.setParent(new VertexBufferConsumer(builder));
        lighter.setWorld(entity.world);
        lighter.setState(Blocks.AIR.getDefaultState());
        lighter.setBlockPos(pos);
        boolean empty = true;
        List<BakedQuad> quads = bakedModel.getQuads(null, null, 0);
        if(!quads.isEmpty())
        {
            lighter.updateBlockInfo();
            empty = false;
            for(BakedQuad quad : quads)
            {
                quad.pipe(lighter);
            }
        }
        for(EnumFacing side : EnumFacing.values())
        {
            quads = bakedModel.getQuads(null, side, 0);
            if(!quads.isEmpty())
            {
                if(empty) lighter.updateBlockInfo();
                empty = false;
                for(BakedQuad quad : quads)
                {
                    quad.pipe(lighter);
                }
            }
        }

        // debug quad
        /*VertexBuffer.pos(0, 1, 0).color(0xFF, 0xFF, 0xFF, 0xFF).tex(0, 0).lightmap(240, 0).endVertex();
        VertexBuffer.pos(0, 1, 1).color(0xFF, 0xFF, 0xFF, 0xFF).tex(0, 1).lightmap(240, 0).endVertex();
        VertexBuffer.pos(1, 1, 1).color(0xFF, 0xFF, 0xFF, 0xFF).tex(1, 1).lightmap(240, 0).endVertex();
        VertexBuffer.pos(1, 1, 0).color(0xFF, 0xFF, 0xFF, 0xFF).tex(1, 0).lightmap(240, 0).endVertex();*/

        builder.setTranslation(0, 0, 0);

        tessellator.draw();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
		
		
	}

	
	
}
