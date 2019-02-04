package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.Techguns;
import techguns.gui.ButtonConstants;
import techguns.world.dungeon.Dungeon;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.presets.IDungeonPreset;
import techguns.world.dungeon.presets.PresetTemplateTest;

public class DungeonGeneratorTileEnt extends BasicOwnedTileEnt {
	
	public static final int BUTTON_ID_GHOST = ButtonConstants.BUTTON_ID_SECURITY+1;
	
	public static final int BUTTON_ID_GENERATE = ButtonConstants.BUTTON_ID_SECURITY+2;
	
	public static final int BUTTON_ID_CLEAR = ButtonConstants.BUTTON_ID_SECURITY+8;
	
	
	public int sizeX = 48;
	public int sizeY = 26;
	public int sizeZ = 48;
	
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
		super.readClientDataFromNBT(tags);
		this.sizeX = tags.getInteger("sizeX");
		this.sizeY = tags.getInteger("sizeY");
		this.sizeZ = tags.getInteger("sizeZ");
		this.showGhost = tags.getBoolean("showGhost");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setInteger("sizeX", sizeX);
		tags.setInteger("sizeY", sizeY);
		tags.setInteger("sizeZ", sizeZ);
		tags.setBoolean("showGhost", showGhost);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if (!isUseableByPlayer(ply)) return;	
		switch(id) {
			case BUTTON_ID_GHOST:
				this.showGhost = !this.showGhost;
				break;
			case BUTTON_ID_CLEAR:
				this.clearBlocks();
				break;
			case BUTTON_ID_GENERATE:
				this.generateDungeon(data);
				break;
			default:
				super.buttonClicked(id, ply,data);
				break;
		}
		this.needUpdate();
	}
	
	private void generateDungeon(String name) {
		Dungeon dungeon;
		if (name == null || name.equals("") || name.length() ==0 || !DungeonTemplate.dungeonTemplates.containsKey(name)) {
			dungeon = new Dungeon(IDungeonPreset.PRESET_NETHER);
		}else {
			dungeon = new Dungeon(new PresetTemplateTest(DungeonTemplate.dungeonTemplates.get(name)));
		}
		
		 
		dungeon.generate(this.world, this.pos.getX()+1, this.pos.getY(), this.pos.getZ()+1, this.sizeX,this.sizeY, this.sizeZ);
		//TODO
	}
	
	private void clearBlocks() {
		for (int x = 1; x < sizeX+1; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 1; z < sizeZ+1; z++) {
					BlockPos bpos = this.pos.add(x, y, z);
					this.world.setBlockToAir(bpos);
				}	
			}
		}
		
//		//TEMP solution
//		for (int x = 1; x < sizeX*2+1; x++) {
//			for (int y = 0; y < sizeY*2; y++) {
//				for (int z = 1; z < sizeZ*2+1; z++) {
//					BlockPos bpos = this.pos.add(x, y, z);
//					this.world.setBlockToAir(bpos);
//				}	
//			}
//		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new net.minecraft.util.math.AxisAlignedBB(pos, pos.add(sizeX+1,sizeY,sizeZ+1));
	}
	
}
