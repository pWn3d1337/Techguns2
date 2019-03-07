package techguns.world.dungeon.presets;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.entities.npcs.SkeletonSoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.IDungeonPath;
import techguns.world.dungeon.MazeDungeonPath;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class PresetNetherDungeon implements IDungeonPreset{

	int sizeXZ = 0;
	int sizeY = 0;
	
	private static final ResourceLocation LOOTTABLE = new ResourceLocation(Techguns.MODID, "chests/castle");
	
	ArrayList<DungeonTemplate> bottomTemplates = new ArrayList<>();
	
	ArrayList<DungeonTemplate> lowerTemplates = new ArrayList<>();
	
	ArrayList<DungeonTemplate> midTemplates = new ArrayList<>();
	
	ArrayList<DungeonTemplate> upperTemplates = new ArrayList<>();
	
	//ArrayList<DungeonTemplate> topTemplates = new ArrayList<>();
	
	//ArrayList<DungeonTemplate> roofTemplates = new ArrayList<>();
	
	public PresetNetherDungeon() {
		//"ncdung1", "nclower1", "ncmid1", "ncupper1", "nctop1", "ncroof1" 
		
		bottomTemplates.add(DungeonTemplate.dungeonTemplates.get("nether_b0").setLoottable(LOOTTABLE));
		
		lowerTemplates.add(DungeonTemplate.dungeonTemplates.get("nether_b1").setLoottable(LOOTTABLE));
		
		midTemplates.add(DungeonTemplate.dungeonTemplates.get("nether0").setLoottable(LOOTTABLE));
		
		upperTemplates.add(DungeonTemplate.dungeonTemplates.get("nether1").setLoottable(LOOTTABLE));
		
		//topTemplates.add(DungeonTemplate.dungeonTemplates.get("nctop1").setLoottable(LOOTTABLE));
		
		//roofTemplates.add(DungeonTemplate.dungeonTemplates.get("ncroof1").setLoottable(LOOTTABLE));
	}

	@Override
	public DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove, boolean isSegmentBelow, int seed) {
		Random rand = new Random(seed);
//		if (!isSegmentAbove && y > 0) { //y == yMax) {
//			return topTemplates.get(rand.nextInt(topTemplates.size())).segments.get(type);
//		}else {
//			return mainTemplates.get(rand.nextInt(topTemplates.size())).segments.get(type);
//		}
//		if (y == -1) { // && type == SegmentType.PILLARS) {
//			return roofTemplates.get(rand.nextInt(roofTemplates.size())).segments.get(type);
//		}else 
		if (y == yMin) {
			return bottomTemplates.get(rand.nextInt(bottomTemplates.size())).segments.get(type);
		}else if (y <= yMin +1) {
			return lowerTemplates.get(rand.nextInt(lowerTemplates.size())).segments.get(type);
		}else if (y < yMax -1){
			return midTemplates.get(rand.nextInt(midTemplates.size())).segments.get(type);
		}else { // if (y < yMax){
			return upperTemplates.get(rand.nextInt(upperTemplates.size())).segments.get(type);
		}
			//		}else { //if y == yMax-1)
//			return topTemplates.get(rand.nextInt(topTemplates.size())).segments.get(type);
//		}
	}

	@Override
	public int getSizeXZ() {
		if (this.sizeXZ == 0) this.sizeXZ = this.midTemplates.get(0).sizeXZ;
		return this.sizeXZ;
	}

	@Override
	public int getSizeY() {
		if (this.sizeY == 0) this.sizeY = this.midTemplates.get(0).sizeY;
		return this.sizeY;
	}

	@Override
	public void initDungeonPath(IDungeonPath d_path) {
		MazeDungeonPath path = (MazeDungeonPath) d_path;
		path.startHeightLevel = 0; //actually it's 1 because of bottom layer
		path.chanceStraight = 0.8f; //next segment will go forward; -> inverse chance to left/right
		path.chanceRamp = 0.5f; //when straight, roll again for a ramp
		path.chanceRoom = 0.25f; //else, roll for a room
		path.chanceFork = 0.4f; //roll again for another direction, when not a ramp
		path.chanceUp = 0.65f;
		path.useFoundations = true;
		path.usePillars = false;
		path.useRoof = false;
		path.useBottomLayer = true;
	}

	@Override
	public void initSpawner(TGSpawnerTileEnt spawner) {
		spawner.setParams(2, 2, 200, 2);
		spawner.addMobType(ZombieSoldier.class, 1);
		spawner.addMobType(SkeletonSoldier.class, 1);
	}


}
