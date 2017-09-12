package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.Techguns;
import techguns.gui.ButtonConstants;
import techguns.world.dungeon.Dungeon;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.TemplateSegment;

public class DungeonGeneratorTileEnt extends BasicOwnedTileEnt {
	
	public static final int BUTTON_ID_GHOST = ButtonConstants.BUTTON_ID_SECURITY+1;
	
	public static final int BUTTON_ID_GENERATE = ButtonConstants.BUTTON_ID_SECURITY+2;
	
	public static final int BUTTON_ID_CLEAR = ButtonConstants.BUTTON_ID_SECURITY+8;
	
	
	public int sizeX = 48;
	public int sizeY = 16;
	public int sizeZ = 32;
	
	public boolean showGhost = true;
	
	
	public DungeonGeneratorTileEnt() {
		super(0, false);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.dungeongenerator", new Object[0]);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		// TODO Auto-generated method stub
		super.readClientDataFromNBT(tags);
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		// TODO Auto-generated method stub
		super.writeClientDataToNBT(tags);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if (!isUseableByPlayer(ply)) return;	
		switch(id) {
			case BUTTON_ID_GHOST:
				showGhost = !showGhost;
				break;
			case BUTTON_ID_CLEAR:
				this.clearBlocks();
				break;
			case BUTTON_ID_GENERATE:
				this.generateDungeon(data);
				break;
			default:
				super.buttonClicked(id, ply,data);
		}
		this.needUpdate();
	}
	
	private void generateDungeon(String name) {
		if (name == null || name.equals("") || name.length() ==0 || !DungeonTemplate.dungeonTemplates.containsKey(name)) return;
		
		Dungeon dungeon = new Dungeon(DungeonTemplate.dungeonTemplates.get(name));
		dungeon.generate(this.world, this.pos.getX()+1, this.pos.getY(), this.pos.getZ()+1, this.sizeX,this.sizeY, this.sizeZ);
		//TODO
	}
	
	private void clearBlocks() {
//		for (int x = 1; x < sizeX+1; x++) {
//			for (int y = 0; y < sizeY; y++) {
//				for (int z = 1; z < sizeZ+1; z++) {
//					BlockPos bpos = this.pos.add(x, y, z);
//					this.world.setBlockToAir(bpos);
//				}	
//			}
//		}
		
		//TEMP solution
		for (int x = 1; x < sizeX*2+1; x++) {
			for (int y = 0; y < sizeY*2; y++) {
				for (int z = 1; z < sizeZ*2+1; z++) {
					BlockPos bpos = this.pos.add(x, y, z);
					this.world.setBlockToAir(bpos);
				}	
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new net.minecraft.util.math.AxisAlignedBB(pos, pos.add(sizeX+1,sizeY,sizeZ+1));
	}
	
}
