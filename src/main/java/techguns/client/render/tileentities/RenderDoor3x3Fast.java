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
	
	protected static final ResourceLocation doorframe_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_frame");
	protected static final ResourceLocation door_left_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_left");
	protected static final ResourceLocation door_right_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_right");
	
	
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
	
	protected static IBakedModel doorsegment_l;
	protected static IBakedModel doorsegment_l_90;
	
	protected static IBakedModel doorsegment_r;
	protected static IBakedModel doorsegment_r_90;
	
	public static void initModels() {
		doorframe = loadBakedModel(doorframe_loc, TRSRTransformation.identity());
		doorframe_90 = loadBakedModel(doorframe_loc, rot90);
		
		doorsegment_l = loadBakedModel(door_left_loc, TRSRTransformation.identity());
		doorsegment_l_90 = loadBakedModel(door_left_loc, rot90);
		
		doorsegment_r = loadBakedModel(door_right_loc, TRSRTransformation.identity());
		doorsegment_r_90 = loadBakedModel(door_right_loc, rot90);
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
        IBakedModel model_seg1 = doorsegment_l;
        IBakedModel model_seg2 = doorsegment_r;
        int dx = 1;
        int dz = 0;
        if(state.getValue(BlockTGDoor3x3.ZPLANE)) {
        	model = doorframe_90;
        	model_seg1 = doorsegment_l_90;
        	model_seg2 = doorsegment_r_90;
        	pz+=1D;
        	dx = 0;
        	dz = 1;
        }
        
        long timestamp = te.getLastStateChangeTime();
        long diff = System.currentTimeMillis() - timestamp;
 
        float prog = 0;
        if (diff < 2000) {
        	prog = diff/2000.0f;
        }
        
        double distance = 1.125d; //18 pixel
         
        buffer.setTranslation(px,py,pz);

        blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos, buffer, false);
        	
        if (prog==0.0f) {
        	 if(state.getValue(BlockTGDoor3x3.OPENED)) {
        		 buffer.setTranslation(px - ((double)dx*distance), py, pz + ((double)dz*distance));
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, false);
	        	 
	        	 buffer.setTranslation(px + ((double)dx*distance), py, pz - ((double)dz*distance));
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, false);
        	 } else {
        		 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, false);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, false);
        	 }
        } else {
        	if(!state.getValue(BlockTGDoor3x3.OPENED)){
        		prog = 1.0f-prog;
        	}
        	
        	 buffer.setTranslation(px - ((double)dx*distance*prog), py, pz + ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, false);
        	 
        	 buffer.setTranslation(px + ((double)dx*distance*prog), py, pz - ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, false);
        }
        buffer.setTranslation(0, 0, 0);
	}

}
