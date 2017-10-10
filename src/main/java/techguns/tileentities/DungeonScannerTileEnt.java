package techguns.tileentities;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.gui.ButtonConstants;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.TemplateSegment;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class DungeonScannerTileEnt extends BasicOwnedTileEnt {
	
	public static final int BUTTON_ID_GHOST = ButtonConstants.BUTTON_ID_SECURITY+1;
	
	public static final int BUTTON_ID_SIZE_XZ_PLUS = ButtonConstants.BUTTON_ID_SECURITY+2;
	public static final int BUTTON_ID_SIZE_XZ_MINUS = ButtonConstants.BUTTON_ID_SECURITY+3;
	
	public static final int BUTTON_ID_SIZE_Y_PLUS = ButtonConstants.BUTTON_ID_SECURITY+4;
	public static final int BUTTON_ID_SIZE_Y_MINUS = ButtonConstants.BUTTON_ID_SECURITY+5;
	
	public static final int BUTTON_ID_SCAN = ButtonConstants.BUTTON_ID_SECURITY+6;
	public static final int BUTTON_ID_PLACE = ButtonConstants.BUTTON_ID_SECURITY+7;
	
	public static final int BUTTON_ID_CLEAR = ButtonConstants.BUTTON_ID_SECURITY+8;
	
	public static final int BUTTON_ID_COPY_FIRST = ButtonConstants.BUTTON_ID_SECURITY+9; 

	//---
	
	public static final int SPACING = 1;


	
	public int sizeXZ = 5;
	public int sizeY = 4;
	
	public boolean showGhost = true;
	
	public HashMap<SegmentType, Boolean> template_filter = new HashMap<>();

	public DungeonScannerTileEnt() {
		super(0, false);
		
		for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
			this.template_filter.put(type, true);
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("techguns.container.dungeonscanner",new Object[0]);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.sizeXZ = tags.getInteger("sizeXZ");
		this.sizeY = tags.getInteger("sizeY");
		this.showGhost = tags.getBoolean("showGhost");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setInteger("sizeXZ", sizeXZ);
		tags.setInteger("sizeY", sizeY);
		tags.setBoolean("showGhost", showGhost);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if (!isUseableByPlayer(ply)) return;	
		switch(id) {
			case BUTTON_ID_GHOST:
				showGhost = !showGhost;
				break;
			case BUTTON_ID_SIZE_XZ_MINUS:
				sizeXZ = Math.max(1, sizeXZ-1);
				break;
			case BUTTON_ID_SIZE_XZ_PLUS:
				sizeXZ = sizeXZ+1;
				break;
			case BUTTON_ID_SIZE_Y_MINUS:
				sizeY = Math.max(1, sizeY-1);
				break;
			case BUTTON_ID_SIZE_Y_PLUS:
				sizeY = sizeY+1;
				break;
			case BUTTON_ID_SCAN:
				this.scanTemplate(data);
				break;
			case BUTTON_ID_PLACE:
				this.placeTemplate(data);
				break;
			case BUTTON_ID_CLEAR:
				this.clearBlocks();
				break;
			case BUTTON_ID_COPY_FIRST:
				this.copyFirstSegment();
				break;
			default:
				super.buttonClicked(id, ply,data);
				break;
		}
		this.needUpdate();
	}

	private void copyFirstSegment() {
		DungeonTemplate template = new DungeonTemplate(this.sizeXZ,  this.sizeY);
		DungeonSegment firstSegment = new DungeonSegment(template, SegmentType.STRAIGHT);
		firstSegment.scanBlocks(world, this.pos.getX(), this.pos.getY(), this.pos.getZ());
		for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
			if (type != SegmentType.STRAIGHT) {
				firstSegment.type = type;
				firstSegment.placeTemplateSegment(world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 0);
			}
		}
	}

	private void clearBlocks() {
		Vec3i max = TemplateSegment.getMaxExtents(this.sizeXZ, this.sizeY);
		for (int x = SPACING; x < max.getX(); x++) {
			for (int y = 0; y < max.getY(); y++) {
				for (int z = SPACING; z < max.getZ(); z++) {
					BlockPos bpos = this.pos.add(x, y, z);
					this.world.setBlockToAir(bpos);
				}	
			}
		}
	}

	private void scanTemplate(String name) {
		if (name == null || name.equals("") || name.length() ==0) return;
		DungeonTemplate template = DungeonTemplate.scanTemplate(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.sizeXZ, this.sizeY, name);
		try {
			FileOutputStream fout = new FileOutputStream(new File(DungeonTemplate.SCAN_DIR+name+".ser"));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(template);
			fout.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void placeTemplate(String name) {
		if (name == null || name.equals("") || name.length() ==0) return;
		DungeonTemplate template = DungeonTemplate.dungeonTemplates.get(name);
		if (template != null) {
			template.placeTemplate(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ());
		}
	} 

	
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		int renderbounds = SPACING+(sizeXZ+SPACING)*30;
		return new net.minecraft.util.math.AxisAlignedBB(pos, pos.add(renderbounds, sizeY, renderbounds));
	}
	
}
