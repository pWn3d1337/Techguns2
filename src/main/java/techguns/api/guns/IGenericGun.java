package techguns.api.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.items.guns.GenericGunCharge;

public interface IGenericGun {

	public boolean isShootWithLeftClick();

	public boolean isSemiAuto();

	@SideOnly(Side.CLIENT)
	public boolean isZooming();

	public void shootGunPrimary(ItemStack stack, World world, EntityPlayer player, boolean zooming, EnumHand hand);

	public int getAmmoLeft(ItemStack stack);
	
	public GunHandType getGunHandType();

	public boolean isHoldZoom();

	public float getZoomMult();

	public default boolean canCharge() {return false;};

	public ResourceLocation getCurrentTexture(ItemStack stack);
}
