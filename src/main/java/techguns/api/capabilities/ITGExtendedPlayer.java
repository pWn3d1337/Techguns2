package techguns.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Should only be used on EntityPlayer
 * 
 */
public interface ITGExtendedPlayer extends ITGShooterValues {

	public EntityPlayer getEntity();
	
	public int getFireDelay(EnumHand hand);
	public void setFireDelay(EnumHand hand, int delay);
	public ItemStackHandler getTGInventory();
	
	public void saveToNBT(final NBTTagCompound tags);
	public void loadFromNBT(final NBTTagCompound tags);
}
