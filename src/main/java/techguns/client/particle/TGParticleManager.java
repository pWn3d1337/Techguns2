package techguns.client.particle;

import java.util.Comparator;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import techguns.TGConfig;
import techguns.client.particle.list.ParticleList;
import techguns.client.particle.list.ParticleList.ParticleListIterator;
import techguns.client.render.GLStateSnapshot;

public class TGParticleManager {
	
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;

	protected ParticleList<TGParticleSystem> list_systems = new ParticleList<>();
	protected ParticleList<ITGParticle> list = new ParticleList<>();
	protected ParticleList<ITGParticle> list_nosort = new ParticleList<>();
	protected ComparatorParticleDepth compare = new ComparatorParticleDepth();
	
	public void addEffect(ITGParticle effect)
    {
        if (effect == null) return;
        if(effect instanceof TGParticleSystem) {
        	list_systems.add((TGParticleSystem) effect);
        } else {
        	if (effect.doNotSort()) {
        		list_nosort.add(effect);
        	} else {
        		list.add(effect);
        	}
        }
    }
	
	public void tickParticles() {
		if(Minecraft.getMinecraft().isGamePaused()) return;
		
		Entity viewEnt = Minecraft.getMinecraft().getRenderViewEntity();
		
		Iterator<TGParticleSystem> sysit = list_systems.iterator();
		while(sysit.hasNext()) {
			TGParticleSystem p = sysit.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				sysit.remove();
			}
		}
		
		ParticleListIterator<ITGParticle> it = list.iterator();
		while(it.hasNext()) {
			ITGParticle p = it.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				it.remove();
			} else {
				if(viewEnt!=null) {
					p.setDepth(this.distanceToPlane(viewEnt, p.getPos()));
				}
			}
		}
		
		Iterator<ITGParticle> it2 = list_nosort.iterator();
		while(it2.hasNext()) {
			ITGParticle p = it2.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				it2.remove();
			}
		}
		//list.debugPrintList();
		
		if(TGConfig.cl_sortPassesPerTick>0) {
			this.doSorting();
		}
	}

	public void doSorting() {
		this.list.doBubbleSort(TGConfig.cl_sortPassesPerTick, compare);
	}
	
	/*public ParticleList<ITGParticle> getList() {
		return list;
	}*/

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
        
        interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        
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
        
        this.list_nosort.forEach(p -> {	
        	//if(frust.isBoundingBoxInFrustum(p.getRenderBoundingBox(partialTicks, entity)))
        	p.doRender(bufferbuilder, entityIn, partialTicks, f1, f5, f2, f3, f4);
        });
        GlStateManager.color(1f, 1f, 1f, 1f);
        //GlStateManager.enableCull();


    }

	
	public double distanceToPlane(Entity viewEntity, Vec3d pos) {
		//Formula from: http://geomalgorithms.com/a04-_planes.html
		Vec3d n = viewEntity.getLookVec();
		double dot1 = -n.dotProduct(pos.subtract(viewEntity.getPositionVector()));
		double dot2 = n.dotProduct(n);
		double f = dot1/dot2;
		
		Vec3d pos2 = pos.add(n.scale(f));
		return pos.squareDistanceTo(pos2);
	}
	
	public static class ComparatorParticleDepth implements Comparator<ITGParticle> {

		@Override
		public int compare(ITGParticle p1, ITGParticle p2) {
			if(p1.doNotSort() && p2.doNotSort()) {
				return 0;
			}
			//Entity view = Minecraft.getMinecraft().getRenderViewEntity();
			//if(view!=null) {
				double dist1=p1.getDepth();
				double dist2=p2.getDepth();
				//double dist1 = p1.getPos().squareDistanceTo(view.posX, view.posY, view.posZ);
				//double dist2 = p2.getPos().squareDistanceTo(view.posX, view.posY, view.posZ);
				
				if(dist1<dist2) {
					return 1;
				} else if(dist1>dist2) {
					return -1;
				} else {
					return 0;
				}
			//}
			//return 0;
		}
		

	}
}
