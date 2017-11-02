package techguns.client.models.guns;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import techguns.client.models.ModelMultipart;

public class ModelBaseBaked extends ModelMultipart {

	protected static Method RenderItem_renderModel = ReflectionHelper.findMethod(RenderItem.class, "renderModel", "func_191961_a", IBakedModel.class, ItemStack.class);
	
	
	ArrayList<IBakedModel> bakedModels = new ArrayList<IBakedModel>();
	
	public ModelBaseBaked(IBakedModel... bakedModels) {
		Arrays.stream(bakedModels).forEach(b -> this.bakedModels.add(b));
	}

	public ModelBaseBaked(ResourceLocation texture_loc, ModelResourceLocation... modellocs) {
		
		Arrays.stream(modellocs).forEach(modelLoc -> {
		
		 IModel model = ModelLoaderRegistry.getModelOrLogError(modelLoc,"Could not load model"+modelLoc.toString());
	     IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, r -> {
	    	    TextureAtlasSprite sprite = new TextureAtlasSprite(texture_loc.getResourcePath()) {};
	    	    try {
	    	    	PngSizeInfo png = PngSizeInfo.makeFromResource(Minecraft.getMinecraft().getResourceManager().getResource(texture_loc));
	    	        sprite.loadSprite(png, false);
	    	        sprite.initSprite(png.pngWidth, png.pngHeight, 0, 0, false);
	    	    } catch (IOException e) {
	    	        throw new RuntimeException(e);
	    	    }
	    	    return sprite;
	    	});
	    bakedModels.add(bakedModel);
		});
	}
	
	public ModelBaseBaked(Item item, int parts) {
		for (int i=0;i<parts;i++) {
			IBakedModel m = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager()
					.getModel(new ModelResourceLocation(item.getRegistryName()+(i==0?"":("_"+i)), "inventory"));
			bakedModels.add(m);
		}
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {
		
		try {
			RenderItem_renderModel.invoke(Minecraft.getMinecraft().getRenderItem(), bakedModels.get(0), ItemStack.EMPTY);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
