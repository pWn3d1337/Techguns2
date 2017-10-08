package techguns.client.particle;

import java.util.Comparator;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import techguns.client.particle.list.ParticleList;
import techguns.client.particle.list.ParticleList.ParticleListIterator;

public class TGParticleManager {

	protected ParticleList<ITGParticle> list = new ParticleList<>();
	protected ComparatorParticleDepth compare = new ComparatorParticleDepth();
	
	public void addEffect(ITGParticle effect)
    {
        if (effect == null) return;
        list.add(effect);
    }
	
	public void tickParticles() {
		ParticleListIterator<ITGParticle> it = list.iterator();
		while(it.hasNext()) {
			ITGParticle p = it.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				it.remove();
			}
		}
		
		this.doSorting();
	}

	public void doSorting() {
		this.list.doBubbleSort(10, compare);
	}
	
	public ParticleList<ITGParticle> getList() {
		return list;
	}

	/**
	 * 
	 * @param entityIn renderViewEntity
	 * @param partialTick
	 */
	public void renderParticles(Entity entityIn, float partialTicks)
    {
        float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
        float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
        float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
        float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
        float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        /*Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        Frustum frust = new Frustum();
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
        frust.setPosition(d0, d1, d2);*/
        
        GlStateManager.disableCull();
        this.list.forEach(p -> {
        	//if(frust.isBoundingBoxInFrustum(p.getRenderBoundingBox(partialTicks, entity)))
        	p.doRender(bufferbuilder, entityIn, partialTicks, f1, f5, f2, f3, f4);
        });
        GlStateManager.enableCull();
    }
	
	public static class ComparatorParticleDepth implements Comparator<ITGParticle> {

		@Override
		public int compare(ITGParticle p1, ITGParticle p2) {
			if(p1.doNotSort() && p2.doNotSort()) {
				return 0;
			}
			Entity view = Minecraft.getMinecraft().getRenderViewEntity();
			if(view!=null) {
				double dist1 = p1.getPos().squareDistanceTo(view.posX, view.posY, view.posZ);
				double dist2 = p2.getPos().squareDistanceTo(view.posX, view.posY, view.posZ);
				
				if(dist1<dist2) {
					return 1;
				} else if(dist1>dist2) {
					return -1;
				} else {
					return 0;
				}
			}
			return 0;
		}
		

	}
}
