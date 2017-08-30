package techguns.items.additionalslots;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.audio.TGSoundCategory;
import techguns.items.armors.TGArmorBonus;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.TextUtil;

public class ItemJumpPack extends ItemTGSpecialSlotAmmo{

	private static int durPerUse=1;
	
	private static final float JUMPBOOST=0.8f;
	private static final float FREEHEIGHT=8.0f;
	private static final float FALLDMG = 0.2f;
	
	public ItemJumpPack(String unlocalizedName, int camoCount, int dur) {
		super(unlocalizedName, TGSlotType.BACKSLOT, camoCount, dur, TGItems.COMPRESSED_AIR_TANK, TGItems.COMPRESSED_AIR_TANK_EMPTY);
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		if ((item.getItemDamage()+this.durPerUse>item.getMaxDamage())){
			this.tryReloadAndRepair(item, event.player);
		}
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {	
		if (type==TGArmorBonus.JUMP){
						
			if (consume){
				TGExtendedPlayer props = TGExtendedPlayer.get(player);
						
				if (props.enableJetpack && (stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){

					stack.setItemDamage(stack.getItemDamage()+this.durPerUse);
					if ( !player.world.isRemote){
						this.doJumpEffect(player);
					}
					return JUMPBOOST;
				}
			} else {
				if ((stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){
					return JUMPBOOST;
				}
			}
		} else if (type==TGArmorBonus.FREEHEIGHT){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return FREEHEIGHT;
			}
		} else if (type==TGArmorBonus.FALLDMG){
			if (consume){
				
				TGExtendedPlayer props = TGExtendedPlayer.get(player);
					
				if (props.enableJetpack && (stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){
					stack.setItemDamage(stack.getItemDamage()+this.durPerUse);
					if ( !player.world.isRemote){
						this.doFallEffect(player);
					}
					return FALLDMG;
				}
				
			} else {
				if (stack.getItemDamage()<stack.getMaxDamage()){
					return FALLDMG;
				}
			}
		}
		return 0;
	}
	
	public void doJumpEffect(EntityPlayer ply){
		//send out the effects
		if (!ply.world.isRemote){
			TargetPoint targetPoint = TGPackets.targetPointAroundEnt(ply, 50);
			
			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("JumpPackBoost",ply), targetPoint);
			TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.STEAM_JUMP_2, ply, 1.0f, 1.0f, false, true, false, true, TGSoundCategory.PLAYER_EFFECT), targetPoint);
		}
	}

	public void doFallEffect(EntityPlayer ply){
		if (!ply.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("JumpPackFall",ply.posX, ply.posY, ply.posZ), TGPackets.targetPointAroundEnt(ply, 50));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextUtil.trans("techguns.armorTooltip.jumpheight")+": +"+JUMPBOOST);
		tooltip.add(TextUtil.trans("techguns.armorTooltip.falldamage")+": -"+FALLDMG*100.0f+"%");
		tooltip.add(TextUtil.trans("techguns.armorTooltip.fallheight")+": -"+FREEHEIGHT);
	}

	
}