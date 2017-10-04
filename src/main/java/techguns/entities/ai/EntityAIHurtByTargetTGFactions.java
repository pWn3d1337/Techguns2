package techguns.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.factions.TGNpcFactions;

public class EntityAIHurtByTargetTGFactions extends EntityAIHurtByTarget {

	public EntityAIHurtByTargetTGFactions(EntityCreature ent, boolean callsForHelp) {
		super(ent, callsForHelp);
	}

	@Override
	protected boolean isSuitableTarget(EntityLivingBase elb, boolean b) {
		
		if (this.taskOwner instanceof ITGNpcTeam && elb instanceof ITGNpcTeam){
			ITGNpcTeam owner = (ITGNpcTeam) this.taskOwner;
			ITGNpcTeam target = (ITGNpcTeam) elb;
			
			return TGNpcFactions.isHostile(owner.getTGFaction(), target.getTGFaction());
			
			//TODO: TURRET AI
		} /*else if (this.taskOwner instanceof NPCTurret && elb instanceof EntityPlayer){
			NPCTurret turret = (NPCTurret) this.taskOwner;
			if (turret.mountedTileEnt != null){
				UUID owner = turret.mountedTileEnt.getOwner();
				if (owner!=null){
					return TGNpcFactions.isHostile(owner, ((EntityPlayer)elb).getGameProfile().getId());
				}
			}
		}*/
		
		return super.isSuitableTarget(elb, b);
	}
	
}