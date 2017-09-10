package techguns.world.dungeon;

import java.io.Serializable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.world.Structure;
import techguns.world.dungeon.TemplateSegment.SegmentType;

/**
 * An 'instance' of a TemplateSegment for a specific DungeonTemplate
 */
public class DungeonSegment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public transient DungeonTemplate template;	
	public SegmentType type;
	public Structure structure;
	

	public DungeonSegment(DungeonTemplate template, SegmentType type) {
		this.template = template;
		this.type = type;
	}


	/**
	 * x, y, z = World position of Scanner TileEnt
	 */
	public void scanBlocks(World world, int posX, int posY, int posZ) {
		TemplateSegment segment = TemplateSegment.templateSegments.get(type);
		
//		if (type == SegmentType.CURVE) {
//			System.out.println("debug");
//		}
		
		int x = posX+ DungeonScannerTileEnt.SPACING + segment.col * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
		int y = posY;
		int z = posZ+ DungeonScannerTileEnt.SPACING + segment.row * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
		
		this.structure = Structure.scanBlocks(world, x, y, z, template.sizeXZ, template.sizeY*segment.sizeY, template.sizeXZ);
	}


	public void placeSegment(World world, int posX, int posY, int posZ, int rotation) {
		TemplateSegment segment = TemplateSegment.templateSegments.get(type);
		
//		if (type == SegmentType.CURVE) {
//			System.out.println("debug");
//		}
		
		int x = posX+ DungeonScannerTileEnt.SPACING + segment.col * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
		int y = posY;
		int z = posZ+ DungeonScannerTileEnt.SPACING + segment.row * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
		
		this.structure.placeBlocks(world, x, y, z, EnumFacing.getHorizontal(rotation));
	}
	
	
	
}
