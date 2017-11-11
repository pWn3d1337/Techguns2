package techguns.entities.npcs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.items.armors.GenericArmor;

public abstract class GenericNPCGearSpecificStats extends GenericNPC {

	public GenericNPCGearSpecificStats(World world) {
		super(world);
	}
	
	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		Iterable<ItemStack> inv = this.getArmorInventoryList();
		//ItemStack[] inv = new ItemStack[]{this.getEquipmentInSlot(1),this.getEquipmentInSlot(2),this.getEquipmentInSlot(3),this.getEquipmentInSlot(4)};
		float totalArmor=0.0f;
		for (ItemStack stack: inv){
			if(!stack.isEmpty() && stack.getItem() instanceof ItemArmor){
				
				if(stack.getItem() instanceof GenericArmor){
					GenericArmor genArmor = (GenericArmor) stack.getItem();
					float armorvalue = genArmor.getArmorValue(stack, dmgsrc.damageType);
					totalArmor+=armorvalue;
				} else {
					ItemArmor armor = (ItemArmor) stack.getItem();
					float armorvalue = armor.getArmorMaterial().getDamageReductionAmount(armor.armorType);
					armorvalue = DamageSystem.getArmorAgainstDamageTypeDefault(this, armorvalue, dmgsrc.damageType);
					totalArmor+=armorvalue;
				}
			}
		}
		
		return totalArmor;
	}

	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		Iterable<ItemStack> inv = this.getArmorInventoryList();
		float res=0.0f;
		for (ItemStack stack: inv){
			if(!stack.isEmpty() && stack.getItem() instanceof GenericArmor){	
				GenericArmor genArmor = (GenericArmor) stack.getItem();
				float antipen = genArmor.getPenetrationResistance();
				res+=antipen;
			}
		}
		return res;
	}

	
	
	@Override
	public float getToughnessAfterPentration(EntityLivingBase elb, TGDamageSource src) {
		Iterable<ItemStack> inv = this.getArmorInventoryList();
		float t=0.0f;
		for (ItemStack stack: inv){
			if(!stack.isEmpty() && stack.getItem() instanceof GenericArmor){	
				GenericArmor genArmor = (GenericArmor) stack.getItem();
				float dt = genArmor.toughness-(Math.max(src.armorPenetration-genArmor.getPenetrationResistance(), 0f));
				t+=Math.max(dt, 0f);
			} else if (!stack.isEmpty() && stack.getItem() instanceof ItemArmor) {
				ItemArmor a = (ItemArmor) stack.getItem();
				float dt = a.toughness- src.armorPenetration;
				t+=Math.max(dt, 0f);
			}
		}
		return t;
	}

}
