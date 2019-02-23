package techguns.tileentities;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.blocks.machines.BasicMachine;
import techguns.items.guns.GenericGun;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.GrinderRecipes;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineOperationChance;
import techguns.tileentities.operation.MachineSlotItem;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.util.InventoryUtil;

public class GrinderTileEnt extends BasicMachineTileEnt {

	public static final int SLOT_INPUT=0;
	public static final int SLOT_UPGRADE=1;
	public static final int SLOT_OUTPUT0=2;
	public static final int SLOT_OUTPUT_LAST=SLOT_OUTPUT0+9;
	
	public MachineSlotItem input;
	
	public static final int POWER_PER_TICK=5;
	
	public GrinderTileEnt() {
		super(11, false, 20000);
		
		input = new MachineSlotItem(this, SLOT_INPUT);
		
		this.inventory = new ItemStackHandlerPlus(11) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				switch (slot) {
				case SLOT_INPUT:
					return true;
				case SLOT_UPGRADE:
					return TGItems.isMachineUpgrade(stack);
				}
				return false;
			}

			@Override
			protected boolean allowExtractFromSlot(int slot, int amount) {
				return slot >= SLOT_OUTPUT0 && slot <= SLOT_OUTPUT_LAST;
			}
		};
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("techguns.container.grinder", new Object[0]);
	}
	
	@Override
	protected BasicMachine getMachineBlockType() {
		return TGBlocks.SIMPLE_MACHINE2;
	}
	
	@Override
	protected int getNeededPower() {
		return POWER_PER_TICK;
	}

	public int getValidSlotForItemInMachine(ItemStack item){

		if( TGItems.isMachineUpgrade(item)){
			return SLOT_UPGRADE;
		} else if(GrinderRecipes.hasRecipeForInput(item)) {
			return SLOT_INPUT;
		}
		return -1;
	}
	
	@Override
	protected void checkAndStartOperation() {
		this.setContentsChanged(false);
		
		MachineOperation op = GrinderRecipes.getOperationForInput(this.input.get(), this);
		
		if(op instanceof MachineOperationChance) {
			((MachineOperationChance) op).setTile(this);
		}
		
		if (op != null && canOutput(op)) {
			
			//check multiplier
			
			int maxStack=this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE);
			
			int multiplier=1;
			//try higher stacksize
			int i;
			for (i=maxStack;i>1;--i){
				op.setStackMultiplier(i);
				if(canConsume(op) && this.canOutput(op)){
					multiplier=i;
					break;
				}
			}
			
			op.setStackMultiplier(multiplier);
		
			this.input.consume(op.getNeededAmountItem(SLOT_INPUT));
			
			this.currentOperation = op;
			this.progress = 0;
			this.totaltime = op.getTime();

			if (!this.world.isRemote) {
				this.needUpdate();
			}
		}
	}

	protected boolean canOutput(MachineOperation output) {
		return this.inventory.canInsertAll(SLOT_OUTPUT0, SLOT_OUTPUT_LAST, output.getOutputsWithMult());
	}

	protected boolean canConsume(MachineOperation output) {
		int multi = output.getStackMultiplier();
		ItemStack in1 = output.getInputs().get(0);
		
		return this.input.canConsumeWithMultiplier(in1,multi);
	}
	
	@Override
	protected void finishedOperation() {
		List<ItemStack> list = this.currentOperation.getOutputsWithMult();
		List<ItemStack> leftover = this.inventory.mergeAll(SLOT_OUTPUT0, SLOT_OUTPUT_LAST, list);
		
		//should not happen cause canOutput is checked
		if(!this.world.isRemote) {
			leftover.forEach(s -> {
				if(!s.isEmpty()) {
					this.world.spawnEntity(new EntityItem(this.world, this.pos.getX()+0.5d, this.pos.getY()+0.5d, this.pos.getZ()+0.5d, s));
				}
			});
		}
	}

	@Override
	protected void playAmbientSound() {
		float prog = (float)this.progress / (float)this.totaltime;

		int sWork1 = Math.round((float)this.totaltime*0.2f)+1;
		int sWork2 = (Math.round((float)totaltime*0.6f))-1;
		
		//if(world.isRemote) {
		if(this.progress%2 == 0) {
				//particle
			ItemStack it = this.currentOperation.getItemInputI(0);
			float f=0.1f;
			float f1 = 0.05f;
			//for(int i=0;i<3;i++) {
			if(it.getItem() instanceof GenericGun) {
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.pos.getX()+0.5d, this.pos.getY()+0.6d, this.pos.getZ()+0.5d, Math.random()*f-f1, Math.random()*f+f, Math.random()*f-f1, Item.getIdFromItem(TGItems.PLATE_STEEL.getItem()), TGItems.PLATE_STEEL.getItemDamage());
				
			} else {
			
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.pos.getX()+0.5d, this.pos.getY()+0.6d, this.pos.getZ()+0.5d, Math.random()*f-f1, Math.random()*f+f, Math.random()*f-f1, Item.getIdFromItem(it.getItem()), it.getItemDamage());
			}
		//}
		}
		//}
		if (this.progress == 1) {
			world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.GRINDER_START,SoundCategory.BLOCKS, 1.0f, 1.0F, true );
		} else if (this.progress == sWork1 || this.progress == sWork2) {
			world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.GRINDER_WORK,SoundCategory.BLOCKS, 1.0f, 1.0F, true );
		}
	}

}
