package techguns.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techguns.Techguns;
import techguns.items.GenericItemShared;
import techguns.items.GenericItemShared.SharedItemEntry;
import techguns.items.guns.GenericGun;
import techguns.util.TGLogger;

public class ItemJsonCreator {

	private static final String prefix = "../src/main/resources/assets/"+Techguns.MODID+"/";
	private static final String texpath = "items/";
	
	public static void writeItemJsonFileForPath(String path, String name, String texname){
		File f = new File(prefix+path+name+".json");
		//TGLogger.logger_client.log(Level.INFO, "Checking Item JSON:"+f.getAbsolutePath());
		if(!f.exists()){
			TGLogger.logger_client.log(Level.INFO, "Creating Item JSON:"+f.getAbsolutePath());
			
			Writer writer;
			try {
				writer = new FileWriter(f);

		        Gson gson = new GsonBuilder().setPrettyPrinting().create();
		        
		        JsonObject parent = new JsonObject();
		        parent.addProperty("parent","item/generated");

		        JsonObject model = new JsonObject();
		        model.addProperty("layer0", Techguns.MODID+":"+texpath+texname);
		        
		        parent.add("textures", model);
		        
		        //System.out.println(gson.toJson(parent));
		     
				writer.write(gson.toJson(parent));
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeJsonFilesForGun(GenericGun item){
		String path = "models/item/";
		if(item.isModelBase(new ItemStack(item))) {
			writeItemJsonFileForPath(path, item.getRegistryName().getResourcePath(), "dummy");
		}
	}
	
	public static void writeJsonFilesForSharedItem(GenericItemShared item){
		
		//System.out.println("Working Directory = "+System.getProperty("user.dir"));
		
		String path = "models/item/";
		
		for (SharedItemEntry s : item.getSharedItems()){
			
			writeItemJsonFileForPath(path, s.getName(), s.getName());
			
		}
		
	}
	
	public static void writeJsonFilesForGenericItem(Item item){
		
		//System.out.println("Working Directory = "+System.getProperty("user.dir"));
		
		String path = "models/item/";
		writeItemJsonFileForPath(path, item.getRegistryName().getResourcePath(), item.getRegistryName().getResourcePath());
		
	}
	
}
