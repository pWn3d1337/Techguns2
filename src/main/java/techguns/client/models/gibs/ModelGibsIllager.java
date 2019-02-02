package techguns.client.models.gibs;

import net.minecraft.client.model.ModelIllager;
import net.minecraft.entity.Entity;

public class ModelGibsIllager extends ModelGibs {

	ModelIllager model;
	
	
	
	public ModelGibsIllager(ModelIllager model) {
		super();
		this.model = model;
    	this.model.head.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.body.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.arms.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg0.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg1.setRotationPoint(0.0f,0.0f,0.0f);
	}

	@Override
	public void render(Entity entityIn, float scale, int part) {

        switch(part) {
	        case 0:
	        	this.model.head.render(scale);
	        	break;
	        case 1:
	        	 this.model.body.render(scale);
	        	break;
	        case 2:
	        	this.model.leg0.render(scale);
	        	break;
	        case 3:
	        	this.model.leg1.render(scale);
	        	break;
	        case 4:
	        	this.model.arms.render(scale);
	        	break;
        }
	}

	@Override
	public int getNumGibs() {
		return 5;
	}

}
