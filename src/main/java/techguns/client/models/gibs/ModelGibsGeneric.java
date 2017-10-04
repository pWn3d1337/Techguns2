package techguns.client.models.gibs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsGeneric extends ModelGibs{

	ModelBase model;
	
	List<ModelRenderer> gibs = new ArrayList<ModelRenderer>();
	
	public ModelGibsGeneric(ModelBase model) {
		this.model = model;
		
		HashSet<ModelRenderer> childBoxes = new HashSet<ModelRenderer>(64);
		
        for (Object o : model.boxList) {
        	ModelRenderer box = (ModelRenderer)o;
        	if (box.childModels != null) {
        		childBoxes.addAll(box.childModels);
        	}
        }
        
        
        for (Object o : model.boxList) {
        	ModelRenderer box = (ModelRenderer)o;
        	if (!childBoxes.contains(box) && !box.isHidden && box.showModel) {
//        		if (box.cubeList.size() >= 1) {
//    				ModelBox mb = box.cubeList.get(0);
//    				float dx = Math.abs(mb.posX1-mb.posX2);
//    				float dy = Math.abs(mb.posY1-mb.posY2);
//    				float dz = Math.abs(mb.posZ1-mb.posZ2);
//    				box.offsetX = -dx*0.5f;
//    				box.offsetY = -dy*0.5f;
//    				box.offsetZ = -dz*0.5f;
//    			}			
    			box.setRotationPoint(0.0f, 0.0f, 0.0f);
    			gibs.add(box);
        	}
        }
		
//		for (Object o : this.model.boxList) {
//			ModelRenderer box  = (ModelRenderer)o;
//			if (box.cubeList.size() >= 1) {
//				ModelBox mb = box.cubeList.get(0);
//				float dx = Math.abs(mb.posX1-mb.posX2);
//				float dy = Math.abs(mb.posY1-mb.posY2);
//				float dz = Math.abs(mb.posZ1-mb.posZ2);
//				box.offsetX = -dx*0.5f;
//				box.offsetY = -dy*0.5f;
//				box.offsetZ = -dz*0.5f;
//			}			
//			box.setRotationPoint(0.0f, 0.0f, 0.0f);
//		}
	}
	
	@Override
	public void render(Entity entityIn, float scale, int part) {
		gibs.get(part).render(scale);
//		int i = 0;
//		for (Object o : this.model.boxList) {
//			ModelRenderer box  = (ModelRenderer)o;
//			if (i++ == part) {
//				box.render(scale);
//			}
//		}
	}

	public int getNumGibs() {
		return gibs.size(); //this.model.boxList.size();
	}

}
