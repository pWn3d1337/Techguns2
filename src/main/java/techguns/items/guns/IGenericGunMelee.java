package techguns.items.guns;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IGenericGunMelee<T extends GenericGun> {

	public default T setMeleeDmg(float dmgPowered, float dmgUnpowered) {
		GenericGun g = (GenericGun) this;
		g.meleeDamagePwr=dmgPowered;
		g.meleeDamageEmpty=dmgUnpowered;
		return (T) this;
	}
	
	public default T setTool(String toolclass, int harvestLevel){
		if (!this.getMiningLevels().containsKey("default")) {
			this.getMiningLevels().put("default", harvestLevel);
		}
		
		this.getMiningLevels().put(toolclass, harvestLevel);
		return (T) this;
	}	
	
	/**
	 * Call this in getStrVsBlock()
	 * @param itemstack
	 * @param state
	 * @return
	 */
	public default float getDigSpeed(ItemStack itemstack, IBlockState state) {
		GenericGun g = (GenericGun) this;
		if(g.getCurrentAmmo(itemstack)>=g.getMiningAmmoConsumption()){
			
			String toolclass = state.getBlock().getHarvestTool(state);			
			
			if (toolclass!=null){
				if(this.getMiningLevels().containsKey(toolclass)){
					return this.getEffectiveDigSpeed();
				} else{
					return 1.0f;
				}
			} else{
				return this.getEffectiveDigSpeed();
			}
		} else {
			return 1.0f;
		}
	}
	
	/**
	 * call this in getHarvetLevel
	 * @param stack
	 * @param toolClass
	 * @return
	 */
	public default int getToolHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		if(this.getMiningLevels()==null){
			return -1;
		}
		if(toolClass==null){
			return this.getMiningLevels().get("default");
		} else if(this.getMiningLevels().containsKey(toolClass)){
			return this.getMiningLevels().get(toolClass);
		} else {
			return -1;
		}
	}
	
	public T setDigSpeed(float speed);
	
	public float getEffectiveDigSpeed();
	
	public HashMap<String,Integer> getMiningLevels();
}
