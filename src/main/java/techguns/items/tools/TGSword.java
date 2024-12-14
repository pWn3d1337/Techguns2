package techguns.items.tools;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.api.damagesystem.IToolTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.util.TextUtil;

public class TGSword extends ItemSword implements IToolTGDamageSystem{

	protected DamageType dmgType=DamageType.PHYSICAL;
	protected float penetration=0.0f;
	
	public TGSword(ToolMaterial mat, String name) {
		super(mat);
		setCreativeTab(Techguns.tabTechgun);
		setRegistryName(name);
		setTranslationKey(Techguns.MODID+"."+name);
	}
	
	public TGSword(ToolMaterial mat, String name, DamageType dmgType,float penetration) {
		this(mat,name);
		this.dmgType=dmgType;
		this.penetration=penetration;
	}

	@Override
	public TGDamageSource getDamageSource(DamageSource original) {
		TGDamageSource src = new TGDamageSource(original.damageType,original.getImmediateSource(),original.getTrueSource(),this.dmgType, DeathType.GORE);
		src.ignoreHurtresistTime=false;
		src.goreChance=0.25f;
		return src;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextUtil.trans("techguns.gun.tooltip.damageType")+": "+this.dmgType.toString());
		if (this.penetration>0.0f){
			tooltip.add(TextUtil.trans("techguns.gun.tooltip.armorPen")+": "+this.penetration);
		}

	}
}