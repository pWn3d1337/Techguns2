package techguns.client.models.gibs;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.entity.Entity;

public class ModelGibsQuadruped extends ModelGibs{    
    public ModelQuadruped model;
    
    public ModelGibsQuadruped(ModelQuadruped model)
    {
    	this.model = model;
    	this.model.head.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.body.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg1.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg2.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg3.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leg4.setRotationPoint(0.0f,0.0f,0.0f);   	
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
	        	this.model.leg1.render(scale);
	        	break;
	        case 3:
	        	this.model.leg2.render(scale);
	        	break;
	        case 4:
	        	this.model.leg3.render(scale);
	        	break;
	        case 5:
	        	this.model.leg4.render(scale);
	        	break;
	    }
	}


	@Override
	public int getNumGibs() {
		return 6;
	}

}
