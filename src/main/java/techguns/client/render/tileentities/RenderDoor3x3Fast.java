package techguns.client.render.tileentities;

import java.io.IOException;

import javax.vecmath.Matrix4f;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import techguns.Techguns;
import techguns.blocks.BlockTGDoor3x3;
import techguns.tileentities.Door3x3TileEntity;
import techguns.util.MathUtil;

public class RenderDoor3x3Fast extends FastTESR<Door3x3TileEntity> {

	protected static BlockRendererDispatcher blockRenderer;
	
	protected static final ResourceLocation doorframe_loc = new ResourceLocation(Techguns.MODID,"block/testdoor_frame");
	protected static final ResourceLocation doorsegment_loc = new ResourceLocation(Techguns.MODID,"block/testdoor_segment");
	
	
	public static IBakedModel loadBakedModel(ResourceLocation model_loc, IModelState transform) {
		 IModel model = ModelLoaderRegistry.getModelOrLogError(model_loc,"Could not load model:"+model_loc.toString());
	     return model.bake(transform,DefaultVertexFormats.BLOCK,ModelLoader.defaultTextureGetter());
	}
	
	protected static final TRSRTransformation rot90;
	static {
		Matrix4f rot = new Matrix4f();
		rot.rotY((float) (90.0f*MathUtil.D2R));
		rot90 = new TRSRTransformation(rot);
	}
	
	protected static IBakedModel doorframe;
	protected static IBakedModel doorframe_90;
	
	protected static IBakedModel doorsegment;
	protected static IBakedModel doorsegment_90;
	
	public static void initModels() {
		doorframe = loadBakedModel(doorframe_loc, TRSRTransformation.identity());
		doorframe_90 = loadBakedModel(doorframe_loc, rot90);
		
		doorsegment = loadBakedModel(doorsegment_loc, TRSRTransformation.identity());
		doorsegment_90 = loadBakedModel(doorsegment_loc, rot90);
	}
	
	@Override
	public void renderTileEntityFast(Door3x3TileEntity te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {

		if(blockRenderer == null) blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
	       
	    BlockPos pos = te.getPos();
        IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
        IBlockState state = world.getBlockState(pos);
		
        double px = x-pos.getX();
        double py = y-pos.getY();
        double pz = z-pos.getZ();
        
        IBakedModel model = doorframe;
        IBakedModel model_seg = doorsegment;
        if(state.getValue(BlockTGDoor3x3.ZPLANE)) {
        	model = doorframe_90;
        	model_seg = doorsegment_90;
        	pz+=1D;
        }
        
        long timestamp = te.getLastStateChangeTime();
        long diff = System.currentTimeMillis() - timestamp;
 
        float prog = 0;
        if (diff < 2000) {
        	prog = diff/2000.0f;
        }
         
        buffer.setTranslation(px,py,pz);

        blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos, buffer, false);
        	
        if (prog==0.0f) {
        	 if(state.getValue(BlockTGDoor3x3.OPENED)) {
        		 buffer.setTranslation(px, py+5*(1D/3D), pz);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg, state, pos, buffer, false);
        	 } else {
		        for (int i=-3;i<6;i++) {
		        	
		        	buffer.setTranslation(px, py+i*(1D/3D), pz);
		        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg, state, pos, buffer, false);
		     		
		        }
        	 }
        } else {
        	if(!state.getValue(BlockTGDoor3x3.OPENED)){
        		prog = 1.0f-prog;
        	}
        	
        	int max = (int) (8*(1-prog))-2;
        	
        	 for (int i=-3;i<max;i++) {
		        	
		        	buffer.setTranslation(px, py+i*(1D/3D)+prog*3, pz);
		        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg, state, pos, buffer, false);
		     		
		        }
        	
        	//do animation
        	
        	
        }
        buffer.setTranslation(0, 0, 0);
	}

}
