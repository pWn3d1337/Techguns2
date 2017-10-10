package techguns.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.logging.Level;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.blocks.GenericBlockMetaEnum;
import techguns.util.TGLogger;

public class BlockJsonCreator {

	private static final String prefix = "../src/main/resources/assets/"+Techguns.MODID+"/";
	private static final String blockstates = "blockstates/";
	private static final String blocks = "models/block/";
	
	public static void writeBlockModelFile(String name) {
		
		File f = new File(prefix+blocks+name+".json");
		
		if(!f.exists()){
			TGLogger.logger_client.log(Level.INFO, "Creating Block Model JSON:"+f.getAbsolutePath());
			
			Writer writer;
			try {
				writer = new FileWriter(f);

		        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		        
		        JsonObject parent = new JsonObject();
		        parent.addProperty("parent","block/cube_all");

		        JsonObject model = new JsonObject();
		        model.addProperty("all", Techguns.MODID+":"+"blocks/"+name);
		        
		        parent.add("textures", model);
		        
				writer.write(gson.toJson(parent));
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void writeBlockstateJsonFileForBlock(GenericBlockMetaEnum block){

		String blockname = block.getRegistryName().getResourcePath();
		
		File f = new File(prefix+blockstates+blockname+".json");
		//TGLogger.logger_client.log(Level.INFO, "Checking Item JSON:"+f.getAbsolutePath());
		if(!f.exists()){
			TGLogger.logger_client.log(Level.INFO, "Creating Blockstate JSON:"+f.getAbsolutePath());
			
			Writer writer;
			try {
				writer = new FileWriter(f);

		        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		        
		        //JsonObject parent = new JsonObject();
		        
		        //HashMap<String,ArrayList<HashMap<String,HashMap<String,String>>>> root = new HashMap<>();
		        //ArrayList<HashMap<String,ImmutableMap<String,String>>> variants = new ArrayList<>();
		
		        
	        	HashMap<String,ImmutableMap<String,String>> entry = new HashMap<>();
		        for (Object t : block.getClazz().getEnumConstants()) {
		        	String name = t.toString().toLowerCase();
		        	
		        	String key = "type="+name;
		        	
		        	HashMap<String,String> model = new HashMap<>();
		        	model.put("model",new ResourceLocation(Techguns.MODID,name).toString());
		        	
		        	entry.put(key, ImmutableMap.copyOf(model));
		        	
		        	writeBlockModelFile(name);
		        }
		        
		        ImmutableMap<String,ImmutableMap<String,String>> map = ImmutableMap.copyOf(entry);
		        
		        //System.out.println(gson.toJson(parent));

				writer.write(gson.toJson(ImmutableMap.of("variants",map)));
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
