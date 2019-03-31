package techguns.world.structures.city;

import java.util.ArrayList;
import java.util.Random;

import techguns.util.MBlock;

public class BuildingStyle {
	protected Random RND = new Random();
	
	protected ArrayList<ArrayList<MBlock>> blocklist = new ArrayList<>();
	
	protected ArrayList<MBlock[]> stairStyles = new ArrayList<>();
	
	/**
	 * No variation constructor
	 * 0 ... wall
	 * 1 ... glass
	 * 2 ... floor
	 * 3 - x ... additional 
	 * @param blocks
	 */
	public BuildingStyle(MBlock... blocks){
		for (int i=0; i<blocks.length; i++){
			ArrayList<MBlock> l = new ArrayList<>();
			l.add(blocks[i]);
			blocklist.add(l);
		}		
	}
	
	/**
	 * Multiple variation constructor
	 * 0 ... wall
	 * 1 ... glass
	 * 2 ... floor
	 * 3 - x ... additional 
	 * @param blocks
	 */
	public BuildingStyle(ArrayList<MBlock>... blocklists){
		for (int i=0; i<blocklists.length; i++){
			blocklist.add(blocklists[i]);
		}	
	}
	
	
	public BuildingStyle addStairStyle(MBlock[] stair){
		this.stairStyles.add(stair);
		return this;
	}
	
	
	public ArrayList<MBlock> rollVariation(){
		ArrayList<MBlock> variation = new ArrayList<>();
		
		for (int i=0; i<blocklist.size(); i++){
			variation.add(rollVariant(blocklist.get(i)));
		}		
		return variation;
	}
	
	public MBlock[] rollStairVariation(){
		int index = RND.nextInt(stairStyles.size());
		return stairStyles.get(index);
	}
	
	
	protected MBlock rollVariant(ArrayList<MBlock> options){
		int index = RND.nextInt(options.size());
		return options.get(index);
	}
	
	public int getBlockTypeAmount(){
		return blocklist.size();
	}
	
}