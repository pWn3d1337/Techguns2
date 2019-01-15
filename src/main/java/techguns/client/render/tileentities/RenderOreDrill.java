package techguns.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import techguns.Techguns;
import techguns.tileentities.OreDrillTileEntMaster;

public class RenderOreDrill extends TileEntitySpecialRenderer<OreDrillTileEntMaster> {
	private static final int DRILLERCUBESPERBLOCK=4;
	
	protected ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/blocks/drillhead.png");
	protected OreDrillCube tessellatorcube = new OreDrillCube(texture);
	
	protected ResourceLocation texture2 = new ResourceLocation(Techguns.MODID,"textures/blocks/drillhead_obsidian.png");
	private OreDrillCube tessellatorcube2 = new OreDrillCube(texture2);
	
	protected ResourceLocation texture3 = new ResourceLocation(Techguns.MODID,"textures/blocks/drillhead_carbon.png");
	private OreDrillCube tessellatorcube3 = new OreDrillCube(texture3);
	
	@Override
	public void render(OreDrillTileEntMaster te, double x, double y, double z, float partialTick, int destroyStage,
			float alpha) {
		//super.render(te, x, y, z, partialTick, destroyStage, alpha); //Renders floating name text
		
		OreDrillTileEntMaster tile = (OreDrillTileEntMaster) te;
		
		if (tile.isFormed()){
		
			EnumFacing drill_direction = tile.getDrill_direction();
			
			int[] vec = getNextBlockInDirOffset(0,0,0, drill_direction, tile.getEngines()+1);
			int[] dir = getNextBlockInDirOffset(0,0,0, drill_direction,1);
			
			GL11.glPushMatrix();
			    //This is setting the initial location.
	        GL11.glTranslatef((float) x +0.5f-vec[0], (float) y+1.25f -vec[1], (float) z+0.5f -vec[2]);
	        
	        GL11.glPushMatrix();
	        int rot = (int) (tile.getWorld().getTotalWorldTime()%360);
	        

           // System.out.println("Rotation:"+tile.rotation);
            if(dir[1]<0){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            } else if (dir[1]>0) {
            	GL11.glTranslatef(0, -1.5f, 0);
            } else if (dir[0]!=0){
            	GL11.glRotatef(-90F*dir[0], 0.0F, 0.0F, 1.0F);
            	GL11.glTranslatef(dir[0]*0.75f, 0, 0);
            	GL11.glTranslatef(0, -0.75f, 0);
            } else if (dir[2]!=0){
            	GL11.glRotatef(90F*dir[2], 1.0F, 0.0F, 0.0F);
            	GL11.glTranslatef(0, 0, dir[2]*0.75f);
            	GL11.glTranslatef(0, -0.75f, 0);
            }
            GL11.glTranslatef(0, 2*(tile.getEngines()+1), 0);
            
            if(tile.hasDrill()&&tile.isRedstoneEnabled()&&tile.hasPower()){
            	GL11.glRotatef(rot*10f + ((1f/36f)*partialTick), 0, 1, 0);
            }
            
            Tessellator tess = Tessellator.getInstance();
            if (tile.hasDrill()){
            	if (tile.getDrillType()==1){
            		drawDriller(tile,tessellatorcube, tess); 
            	} else if (tile.getDrillType()==2){
            		drawDriller(tile,tessellatorcube2, tess);
            	} else if (tile.getDrillType()==3){
            		drawDriller(tile,tessellatorcube3, tess);
            	}
            } else {
            	drawDrillRod(tile, tess);
            }
	        
	        
	        GL11.glPopMatrix();
	        GL11.glPopMatrix();
		}
	}

	public static int[] getNextBlockInDirOffset(int x, int y, int z, EnumFacing dir, int offset) {
		int[] pos = new int[] { x, y, z };
		if(dir.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) {
			pos[dir.getAxis().ordinal()] += offset;
		} else {
			pos[dir.getAxis().ordinal()] -= offset;
		}
		return pos;
	}
	
	protected void drawDriller(OreDrillTileEntMaster tile, OreDrillCube cube, Tessellator tess){

		int length = tile.getRods();
		
		GL11.glPushMatrix();
		
		int[] axis = getNextBlockInDirOffset(0, 0, 0, tile.getDrill_direction(),1);
		float height = 1.0f/DRILLERCUBESPERBLOCK;
		float pos = height;
		boolean negative=false;

		axis[0]=0;
		axis[1]=1;
		axis[2]=0;
		
		float max = (float) Math.log((length*DRILLERCUBESPERBLOCK+1.0f));
		float radiusfactor=1.0f;
		switch(tile.getRadius()){
			case 0:
			case 1:
				radiusfactor=0.35f;
				break;
			case 2:
				radiusfactor=0.5f;
				break;
			case 3:
				radiusfactor=0.55f;
				break;
			case 4:
				radiusfactor=0.60f;
				break;
		} 
		
		BlockPos tilePos=tile.getPos();
		int[] worldpos = getNextBlockInDirOffset(tilePos.getX(), tilePos.getY(), tilePos.getZ(), tile.getDrill_direction(), tile.getEngines());
		
		for (int i=0;i<length*DRILLERCUBESPERBLOCK;i++){
			boolean rotate=false;
			if(i%2==1){
				rotate=true;
			}
			float factor = (float) Math.log((length*DRILLERCUBESPERBLOCK)-i+1.0f);
			float size = ((tile.getRadius()==0?1.0f:tile.getRadius()*1.0f)*factor)*(1.0f/max)*radiusfactor;
			
			GL11.glTranslatef(axis[0]*height, axis[1]*height, axis[2]*height);
			
			if (rotate){
				GL11.glPushMatrix();
				GL11.glRotatef(45.0f, Math.abs(axis[0]),Math.abs(axis[1]),Math.abs(axis[2]));
			}
			
			if(i % DRILLERCUBESPERBLOCK==0){
				//this.adjustLightFixture(tile.getWorld(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
				worldpos = getNextBlockInDirOffset(worldpos[0],worldpos[1],worldpos[2],tile.getDrill_direction(),1);
			}
			
			cube.drawCube(axis[0]==0?size:pos, (axis[1]==0?size:pos), (axis[2]==0?size:pos), tess);
					
			if(rotate){
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();
	}
	
	protected void drawDrillRod(OreDrillTileEntMaster tile, Tessellator tess){

			int length = tile.getRods();
			
			GL11.glPushMatrix();
			
			int[] axis = getNextBlockInDirOffset(0, 0, 0, tile.getDrill_direction(),1);
			float height = 0.5f;
			float pos = height;

			axis[0]=0;
			axis[1]=1;
			axis[2]=0;
			
			BlockPos tilePos=tile.getPos();
			int[] worldpos = getNextBlockInDirOffset(tilePos.getX(), tilePos.getY(), tilePos.getZ(), tile.getDrill_direction(), tile.getEngines());
			
			float f = -0.4f;
			GL11.glTranslatef(axis[0]*height*f, axis[1]*height*f, axis[2]*height*f);
			for (int i=0;i<length;i++){
				float size = 0.2f;
				
				GL11.glTranslatef(axis[0]*height*2, axis[1]*height*2, axis[2]*height*2);

				GL11.glPushMatrix();
				GL11.glRotatef(45.0f, Math.abs(axis[0]),Math.abs(axis[1]),Math.abs(axis[2]));

				//this.adjustLightFixture(tile.getWorldObj(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
				worldpos = getNextBlockInDirOffset(worldpos[0],worldpos[1],worldpos[2],tile.getDrill_direction(),1);

				tessellatorcube.drawCube(axis[0]==0?size:pos, (axis[1]==0?size:pos), (axis[2]==0?size:pos), tess);

				GL11.glPopMatrix();

			}
			GL11.glPopMatrix();
		}
}
