package techguns.items.armors;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ICamoChangeable {
	public int getCamoCount();
	
	public default int switchCamo(ItemStack item){
		return this.switchCamo(item, false);
	}
	
	public default int switchCamo(ItemStack item,boolean back){
		NBTTagCompound tags = item.getTagCompound();
		if (tags== null){
			tags = new NBTTagCompound();
			item.setTagCompound(tags);
		}
		byte camoID=0;
		if (tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		
		ICamoChangeable it = (ICamoChangeable) item.getItem();
		
		if(back){
			camoID--;
		} else {
			camoID++;
		}
		
		if (camoID>=it.getCamoCount()){
			camoID=0;
		} else if(camoID<0){
			camoID=(byte) (it.getCamoCount()-1);
		}
		
		tags.setByte("camo", camoID);
		return camoID;
	}
	
	public default int getCurrentCamoIndex(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		return camoID;
	};
	
	public String getCurrentCamoName(ItemStack item);
}
