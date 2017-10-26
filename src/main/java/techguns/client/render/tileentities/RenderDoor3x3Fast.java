package techguns.client.render.tileentities;

import javax.vecmath.Matrix4f;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.BlockTGDoor3x3;
import techguns.blocks.EnumDoorState;
import techguns.tileentities.Door3x3TileEntity;
import techguns.util.MathUtil;

public class RenderDoor3x3Fast extends FastTESR<Door3x3TileEntity> {

	protected static BlockRendererDispatcher blockRenderer;
	
	protected static final ResourceLocation doorframe_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_frame");
	protected static final ResourceLocation door_left_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_left");
	protected static final ResourceLocation door_right_loc = new ResourceLocation(Techguns.MODID,"block/techdoor3x3_right");
	
	protected static final ResourceLocation hangar_up_upper_loc = new ResourceLocation(Techguns.MODID,"block/hangar_door_upperj");
	protected static final ResourceLocation hangar_up_mid_loc = new ResourceLocation(Techguns.MODID,"block/hangar_door_mid1j");
	protected static final ResourceLocation hangar_up_mid2_loc = new ResourceLocation(Techguns.MODID,"block/hangar_door_mid2j");
	protected static final ResourceLocation hangar_up_mid3_loc = new ResourceLocation(Techguns.MODID,"block/hangar_door_mid3j");
	protected static final ResourceLocation hangar_up_lower_loc = new ResourceLocation(Techguns.MODID,"block/hangar_door_lower.obj");
	
	
	public static IBakedModel loadBakedModel(ResourceLocation model_loc, IModelState transform) {
		 IModel model = ModelLoaderRegistry.getModelOrLogError(model_loc,"Could not load model:"+model_loc.toString());
	     return model.bake(transform,Attributes.DEFAULT_BAKED_FORMAT,ModelLoader.defaultTextureGetter());
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
	
	
	protected static IBakedModel hangar_up_upper;
	protected static IBakedModel hangar_up_upper_90;
	
	protected static IBakedModel hangar_up_mid;
	protected static IBakedModel hangar_up_mid_90;
	
	protected static IBakedModel hangar_up_mid2;
	protected static IBakedModel hangar_up_mid2_90;
	
	protected static IBakedModel hangar_up_mid3;
	protected static IBakedModel hangar_up_mid3_90;
	
	protected static IBakedModel hangar_up_lower;
	protected static IBakedModel hangar_up_lower_90;
	
	public static void stitchTextures(TextureMap map) {
		map.registerSprite(new ResourceLocation(Techguns.MODID,"blocks/techdoor3x3"));
		map.registerSprite(new ResourceLocation(Techguns.MODID,"blocks/hangar_door"));
	}
	
	public static void initModels() {
		doorframe = loadBakedModel(doorframe_loc, TRSRTransformation.identity());
		doorframe_90 = loadBakedModel(doorframe_loc, rot90);
		
		doorsegment_l = loadBakedModel(door_left_loc, TRSRTransformation.identity());
		doorsegment_l_90 = loadBakedModel(door_left_loc, rot90);
		
		doorsegment_r = loadBakedModel(door_right_loc, TRSRTransformation.identity());
		doorsegment_r_90 = loadBakedModel(door_right_loc, rot90);
		
		
		hangar_up_upper = loadBakedModel(hangar_up_upper_loc, TRSRTransformation.identity());
		hangar_up_upper_90 = loadBakedModel(hangar_up_upper_loc, rot90);
		
		hangar_up_mid = loadBakedModel(hangar_up_mid_loc, TRSRTransformation.identity());
		hangar_up_mid_90 = loadBakedModel(hangar_up_mid_loc, rot90);
		
		hangar_up_mid2 = loadBakedModel(hangar_up_mid2_loc, TRSRTransformation.identity());
		hangar_up_mid2_90 = loadBakedModel(hangar_up_mid2_loc, rot90);
		
		hangar_up_mid3 = loadBakedModel(hangar_up_mid3_loc, TRSRTransformation.identity());
		hangar_up_mid3_90 = loadBakedModel(hangar_up_mid3_loc, rot90);
		
		hangar_up_lower = loadBakedModel(hangar_up_lower_loc, TRSRTransformation.identity());
		hangar_up_lower_90 = loadBakedModel(hangar_up_lower_loc, rot90);
	}
	
	@Override
	public void renderTileEntityFast(Door3x3TileEntity te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {

		if (te == null) return;
		
		if(blockRenderer == null) blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
	       
	    BlockPos pos = te.getPos();
        IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
        IBlockState state = world.getBlockState(pos);
        //if(state.getBlock() != TGBlocks.DOOR3x3) return;
		
        if(state.getValue(BlockTGDoor3x3.STATE)==EnumDoorState.OPENED || state.getValue(BlockTGDoor3x3.STATE)==EnumDoorState.CLOSED){
        	return;
        }
        
        double px = x-pos.getX();
        double py = y-pos.getY();
        double pz = z-pos.getZ();
        
        long timestamp = te.getLastStateChangeTime();
        long diff = System.currentTimeMillis() - timestamp;
 
        float prog = 0;
        if (diff < 1000) {
        	prog = diff/1000.0f;
        }
        boolean zplane = state.getValue(BlockTGDoor3x3.ZPLANE);

        switch(te.getDoorType()) {
        case 0:
        	this.renderDoor0(world, state, buffer, pos, zplane, px, py, pz, prog);
        	break;
        case 1:
        	this.renderDoor1(world, state, buffer, pos, zplane, px, py, pz, prog);
        	break;
        }
      
        buffer.setTranslation(0, 0, 0);
	}



	protected void renderDoor0(IBlockAccess world, IBlockState state, BufferBuilder buffer, BlockPos pos, boolean zplane, double px, double py, double pz, float prog) {
		
		IBakedModel model = doorframe;
        IBakedModel model_seg1 = doorsegment_l;
        IBakedModel model_seg2 = doorsegment_r;
        int dx = 1;
        int dz = 0;
        if(zplane) {
        	model = doorframe_90;
        	model_seg1 = doorsegment_l_90;
        	model_seg2 = doorsegment_r_90;
        	pz+=1D;
        	dx = 0;
        	dz = 1;
        }
        
        buffer.setTranslation(px,py,pz);
        double distance = 1.125d; //18 pixel
         
        blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos, buffer, true);
        	
        if (prog==0.0f) {
        	 if(state.getValue(BlockTGDoor3x3.STATE) == EnumDoorState.OPENING) {
        		 buffer.setTranslation(px - ((double)dx*distance), py, pz + ((double)dz*distance));
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, true);
	        	 
	        	 buffer.setTranslation(px + ((double)dx*distance), py, pz - ((double)dz*distance));
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, true);
        	 } else {
        		 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, true);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, true);
        	 }
        } else {
        	if(state.getValue(BlockTGDoor3x3.STATE)==EnumDoorState.CLOSING){
        		prog = 1.0f-prog;
        	}
        	
        	 buffer.setTranslation(px - ((double)dx*distance*prog), py, pz + ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, true);
        	 
        	 buffer.setTranslation(px + ((double)dx*distance*prog), py, pz - ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, true);
        }
	}
	/**
	 * HangarDoor up
	 */
	protected void renderDoor1(IBlockAccess world, IBlockState state, BufferBuilder buffer, BlockPos pos, boolean zplane, double px, double py, double pz, float prog) {
		IBakedModel model_up = hangar_up_upper;
        IBakedModel model_segment = hangar_up_mid;
        IBakedModel model_segment2 = hangar_up_mid2;
        IBakedModel model_segment3 = hangar_up_mid3;
        IBakedModel model_lower = hangar_up_lower;
        int dx = 1;
        int dz = 0;
        if(zplane) {
        	model_up = hangar_up_upper_90;
        	model_segment = hangar_up_mid_90;
        	model_segment2 = hangar_up_mid2_90;
        	model_segment3 = hangar_up_mid3_90;
        	model_lower = hangar_up_lower_90;
        	pz+=1D;
        	dx = 0;
        	dz = 1;
        } 
         
        buffer.setTranslation(px,py,pz);
        blockRenderer.getBlockModelRenderer().renderModel(world, model_up, state, pos, buffer, false);
        	
        if (prog==0.0f) {
        	 if(state.getValue(BlockTGDoor3x3.STATE)==EnumDoorState.OPENING) {
        		 //Render opened door
        		 buffer.setTranslation(px,py+3d,pz);
        		 blockRenderer.getBlockModelRenderer().renderModel(world, model_lower, state, pos, buffer, false);
        	 } else {
        		 //render closed door
        		 blockRenderer.getBlockModelRenderer().renderModel(world, model_lower, state, pos, buffer, false);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_segment, state, pos, buffer, false);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_segment2, state, pos, buffer, false);
	        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_segment3, state, pos, buffer, false); 
        	 }
        } else {
			if (state.getValue(BlockTGDoor3x3.STATE)==EnumDoorState.CLOSING) {
				prog = 1.0f - prog;
			}

			double dist = prog * 3d;
			buffer.setTranslation(px, py + dist, pz);
			blockRenderer.getBlockModelRenderer().renderModel(world, model_lower, state, pos, buffer, false);
			blockRenderer.getBlockModelRenderer().renderModel(world, model_segment, state, pos, buffer, false);

			if(dist<=2d) {
				buffer.setTranslation(px, py + dist, pz);
				blockRenderer.getBlockModelRenderer().renderModel(world, model_segment2, state, pos, buffer, false);
			}
			if(dist<=1d) {
				buffer.setTranslation(px, py + dist, pz);
				blockRenderer.getBlockModelRenderer().renderModel(world, model_segment3, state, pos, buffer, false);
			}
        	/*
        	 buffer.setTranslation(px - ((double)dx*distance*prog), py, pz + ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg1, state, pos, buffer, false);
        	 
        	 buffer.setTranslation(px + ((double)dx*distance*prog), py, pz - ((double)dz*distance*prog));
        	 blockRenderer.getBlockModelRenderer().renderModel(world, model_seg2, state, pos, buffer, false);*/
        }
	}
}