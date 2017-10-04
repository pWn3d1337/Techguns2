package techguns.client.render.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import techguns.blocks.machines.SimpleMachine;
import techguns.tileentities.ChargingStationTileEnt;

public class RenderChargingStation extends TileEntitySpecialRenderer<ChargingStationTileEnt> {
	
	@Override
	public void render(ChargingStationTileEnt te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		if(te!=null && (te.isWorking() || te.charging)) {
			ItemStack item = ItemStack.EMPTY;
			if(te.charging) {
				item = te.getInventory().getStackInSlot(ChargingStationTileEnt.SLOT_INPUT);
			} else {
				item = te.currentOperation.getItemInputI(0);
			}
			if(!item.isEmpty()) {
				IBlockState bs = te.getWorld().getBlockState(te.getPos());
				EnumFacing dir = bs.getValue(SimpleMachine.FACING);
				
				GlStateManager.pushMatrix();
				GlStateManager.translate( x + 0.5d, y + 0.4d, z + 0.5d);
				
				GlStateManager.rotate(-90.0f*dir.getHorizontalIndex(), 0f, 1f, 0f);

				Minecraft.getMinecraft().getRenderItem().renderItem(item, TransformType.GROUND);
				
				GlStateManager.popMatrix();
			}
		}
	}

}
