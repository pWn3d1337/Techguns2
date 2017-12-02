package techguns.client.particle;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class TGParticleSystemItemAttached extends TGParticleSystem {

	protected Item itemtype;
	protected EnumHand hand;
	
	public TGParticleSystemItemAttached(Entity entity,Item itemtype, EnumHand hand, TGParticleSystemType type) {
		super(entity, type);
		this.itemtype=itemtype;
		this.hand=hand;
	}
	
	
}
