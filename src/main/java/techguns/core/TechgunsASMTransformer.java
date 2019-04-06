package techguns.core;

import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.FCONST_1;
import static org.objectweb.asm.Opcodes.FSTORE;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class TechgunsASMTransformer implements IClassTransformer {

	protected static List classesToPatch  = Arrays.asList(new String[]{
			"net.minecraft.client.renderer.RenderItem",
			"net.minecraft.client.renderer.entity.RenderEntityItem",
			"net.minecraft.client.renderer.ItemRenderer"
			});
	
	
	protected static HashMap<String,String> mappings_obf = new HashMap<>();
	protected static HashMap<String,String> mappings_deobf = new HashMap<>();
	
	protected static final String ITEMRENDERHACK = "techguns/client/render/ItemRenderHack";
	
	static {
		addMapping("TransformType","bwa$b","net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType");
		addMapping("EntityItem","acj","net/minecraft/entity/item/EntityItem");
		addMapping("ItemStack","ain","net/minecraft/item/ItemStack");
		addMapping("ELB","vn","net/minecraft/entity/EntityLivingBase");
		addMapping("IBakedModel","cfw","net/minecraft/client/renderer/block/model/IBakedModel");
		addMapping("GUI","g","GUI");
		addMapping("GROUND","h","GROUND");
		addMapping("EntityPlayer","aeb","net/minecraft/entity/player/EntityPlayer");
	}
	
	
	private static void addMapping(String key, String obf, String deobf){
		mappings_obf.put(key, obf);
		mappings_deobf.put(key, deobf);
	}
	
	private static String getMapping(String key, boolean deobf){
		//if (deobf){
			return mappings_deobf.get(key);
		//} else {
		//	return mappings_obf.get(key);
		//}
	}
	
	private static String getHookSignature(boolean deobf){
		return "(L"+getMapping("ItemStack",deobf)+";L"+getMapping("ELB",deobf)+";L"+getMapping("TransformType",deobf)+";Z)Z";
	}
	/**
	 * signature of getAttackStrengthForRendering hook
	 * EntityPlayer, float, ItemStack, ItemStack
	 * @param deobf
	 * @return
	 */
	private static String getHookSignature2(boolean deobf){
		//return "(L"+getMapping("EntityPlayer",deobf)+";FL"+getMapping("ItemStack",deobf)+";L"+getMapping("ItemStack",deobf)+";)F";
		return "()F";
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		int index = classesToPatch.indexOf(transformedName);
		if (index!=-1){
			boolean deobf = name.equals(transformedName);
			
			try {
				//System.out.println("Transforming:"+transformedName);
				//Setup ASM
				ClassNode classNode = new ClassNode();
				ClassReader classReader = new ClassReader(basicClass);
				classReader.accept(classNode, 0);
			
				switch(index){
					case 0:
						patchRenderItem(classNode,deobf);
						break;
					case 1:
						patchRenderEntityItem(classNode,deobf);
						break;
					case 2:
						patchItemRenderer(classNode,deobf);
						break;
				}
			
				
				ClassWriter classWriter  = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
				classNode.accept(classWriter);
				return classWriter.toByteArray();
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return basicClass;
	}

	protected void patchRenderEntityItem(ClassNode classNode, boolean deobf){
		
		String targetMethodName = deobf ? "doRender" : "func_76986_a";//"a";
		
		String signature = "(L"+getMapping("EntityItem",deobf)+";DDDFF)V";
		
		for (MethodNode m : classNode.methods){
			
			if (m.name.equals(targetMethodName) && m.desc.equals(signature)) {
				
				AbstractInsnNode targetNode = null;
				AbstractInsnNode targetNode2 = null;
					
				//Shity code ahead
		        for (AbstractInsnNode instruction : m.instructions.toArray())
		        {
		        	
		            if (instruction.getOpcode() == INVOKEVIRTUAL)
		            {

	            		targetNode=instruction;

		                if ( instruction.getPrevious() !=null && instruction.getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious()).var == 17)
		                {  	
		                	if ( instruction.getPrevious().getPrevious() !=null && instruction.getPrevious().getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious().getPrevious()).var == 10)
		                    {
		                    	
	                			targetNode2 = instruction.getPrevious().getPrevious().getPrevious().getPrevious();
	                		
	        			        InsnList toInsert = new InsnList();
	        	            	
	        	            	toInsert.add(new VarInsnNode(ALOAD, 10));
	        	            	toInsert.add(new InsnNode(ACONST_NULL));
	        	            	//toInsert.add(new VarInsnNode(ALOAD, 2));
	        	            	toInsert.add(new FieldInsnNode(GETSTATIC, getMapping("TransformType",deobf), getMapping("GROUND",deobf), "L"+getMapping("TransformType",deobf)+";"));
	        	            	toInsert.add(new InsnNode(ICONST_0));
	        	            	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "renderItem", getHookSignature(deobf), false));
	        	            	LabelNode labelNode1 = new LabelNode();
	        	            	toInsert.add(new JumpInsnNode(IFNE, labelNode1));
	        	            	
	        	            	m.instructions.insertBefore(targetNode2, toInsert);
	        	            	
	        	            	m.instructions.insert(targetNode,labelNode1);
	        	                
	                            System.out.println("Successfully patched RenderEntityItem with ASM");
	                           
		                    }
		                }
		            }
		        }
		       
			}
		}
	}
	
	
	protected void patchRenderItem(ClassNode classNode, boolean deobf){
		
		String targetMethodName = deobf ? "renderItem" : "func_184392_a";//"a";
		
		String targetMethodName2arg = deobf ? "renderItem" : "func_181564_a";
		
		String targetMethodName2 = deobf ? "renderModel" : "func_191961_a";//"a";
		
		String targetMethodNameGui = deobf ? "renderItemModelIntoGUI" : "func_191962_a";//"a";
		
		String itemStackName = "L"+getMapping("ItemStack",deobf);
		String camTransformTypeName = "L"+getMapping("TransformType",deobf);
		
		String signature = "("+itemStackName+";L"+getMapping("ELB",deobf)+";"+camTransformTypeName+";Z)V";
		String signature2 = "("+itemStackName+";"+camTransformTypeName+";)V"; 
		
		String signature_renderModel = "(L"+getMapping("IBakedModel",deobf)+";"+itemStackName+";)V";

		String signature3 = "("+itemStackName+";IIL"+getMapping("IBakedModel",deobf)+";)V";
		
	
		/*System.out.println("Target1:"+targetMethodName+":"+signature);
		System.out.println("Target2:"+targetMethodName+":"+signature2);
		System.out.println("Target3:"+targetMethodNameGui+":"+signature3);
		System.out.println("Target3:"+targetMethodName2+":"+signature_renderModel);*/
		
		for (MethodNode m : classNode.methods){
			
			//System.out.println("Method:"+m.name+ " Desc:"+m.desc);
			
			if (m.name.equals(targetMethodName) && m.desc.equals(signature)) {
				patchRenderItem_patchMethodRenderItem(m,deobf);
			}
			if (m.name.equals(targetMethodName2arg) && m.desc.equals(signature2)) {
				patchRenderItem_patchMethodRenderItem2(m,deobf);
			}
			if (m.name.equals(targetMethodNameGui) && m.desc.equals(signature3)) {
				patchRenderItem_patchMethodRenderItemGUI(m,deobf);
			}
			
			if (m.name.equals(targetMethodName2) && m.desc.equals(signature_renderModel)) {
				patchRenderItem_patchMethodRenderModel(m, deobf);
			}
		}
	}
	
	protected void patchItemRenderer(ClassNode classNode, boolean deobf){
		String targetMethodName1 = deobf ? "updateEquippedItem" : "func_78441_a";//"a";
		String signature1 = "()V";
		
		for (MethodNode m : classNode.methods){
			//System.out.println("Checking Method:"+m.name+" "+m.desc);
			if (m.name.equals(targetMethodName1) && m.desc.equals(signature1)) {
				patchItemRenderer_updateEquippedItem(m,deobf);
			}
		}
	}
	
	/**
	 * patch the 4 argument version
	 * @param method
	 * @param deobf
	 */
	protected static void patchRenderItem_patchMethodRenderItem(MethodNode method, boolean deobf){
		AbstractInsnNode targetNode = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ALOAD)
            {
                if (((VarInsnNode) instruction).var == 1 && instruction.getNext().getOpcode() == INVOKEVIRTUAL)
                {
                    targetNode = instruction;
                    break;
                }
            }
        }
        
        AbstractInsnNode targetNode2 = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ILOAD)
            {
                if (((VarInsnNode) instruction).var == 4 && instruction.getNext().getOpcode() == INVOKEVIRTUAL)
                {
                    targetNode2 = instruction.getNext();
                    break;
                }
            }
        }
        
		
		
        if (targetNode!=null && targetNode2!=null){
	
        	/**
        	mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitMethodInsn(INVOKESTATIC, "techguns/client/render/ItemRenderHack", "renderItem", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)Z", false);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNE, l1);
        	 */
        	
        	InsnList toInsert = new InsnList();
        	
        	toInsert.add(new VarInsnNode(ALOAD, 1));
        	toInsert.add(new VarInsnNode(ALOAD, 2));
        	toInsert.add(new VarInsnNode(ALOAD, 3));
        	toInsert.add(new VarInsnNode(ILOAD, 4));
        	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "renderItem", getHookSignature(deobf), false));
        	LabelNode labelNode1 = new LabelNode();
        	toInsert.add(new JumpInsnNode(IFNE, labelNode1));
        	
        	method.instructions.insertBefore(targetNode, toInsert);
        	
        	method.instructions.insert(targetNode2,labelNode1);
        	
        	System.out.println("Successfully patched 4 arg RenderItem.RenderItem with ASM");
        	
        } else {
        	System.out.println("Error Patching RenderItem.RenderItem (4args) with ASM");
        }
	};
	
	/**
	 * Patch the 2 argument renderItem Method
	 * @param method
	 * @param deobf
	 */
	protected static void patchRenderItem_patchMethodRenderItem2(MethodNode method, boolean deobf){
		AbstractInsnNode targetNode = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ALOAD)
            {
                if (((VarInsnNode) instruction).var == 1 && instruction.getNext().getOpcode() == INVOKEVIRTUAL)
                {
                    targetNode = instruction;
                    break;
                }
            }
        }
        
        AbstractInsnNode targetNode2 = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ICONST_0)
            {
                if (instruction.getNext().getOpcode() == INVOKEVIRTUAL)
                {
                    targetNode2 = instruction.getNext();
                    break;
                }
            }
        }
        
		
		
        if (targetNode!=null && targetNode2!=null){
	
        	/**
        	mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitMethodInsn(INVOKESTATIC, "techguns/client/render/ItemRenderHack", "renderItem", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)Z", false);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNE, l1);
        	 */
        	
        	InsnList toInsert = new InsnList();
        	
        	toInsert.add(new VarInsnNode(ALOAD, 1));
        	toInsert.add(new InsnNode(ACONST_NULL));
        	toInsert.add(new VarInsnNode(ALOAD, 2));
        	toInsert.add(new InsnNode(ICONST_0));
        	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "renderItem", getHookSignature(deobf), false));
        	LabelNode labelNode1 = new LabelNode();
        	toInsert.add(new JumpInsnNode(IFNE, labelNode1));
        	
        	method.instructions.insertBefore(targetNode, toInsert);
        	
        	method.instructions.insert(targetNode2,labelNode1);
        	
        	System.out.println("Successfully patched 2 arg RenderItem.RenderItem with ASM");
        	
        } else {
        	System.out.println("Error Patching RenderItem.RenderItem (2 args) with ASM");
        }
	};
	
	
	protected static void patchRenderItem_patchMethodRenderItemGUI(MethodNode method, boolean deobf){
		AbstractInsnNode targetNode = null;
		AbstractInsnNode targetNode2 = null;
		
		//Shity code ahead
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == INVOKEVIRTUAL)
            {
            	targetNode=instruction;
                if ( instruction.getPrevious() !=null && instruction.getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious()).var == 4)
                {
                	
                	if ( instruction.getPrevious().getPrevious() !=null && instruction.getPrevious().getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious().getPrevious()).var == 1)
                    {
                    	
                		if ( instruction.getPrevious().getPrevious().getPrevious() !=null && instruction.getPrevious().getPrevious().getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious().getPrevious().getPrevious()).var == 0)
                        {
                        	
                            targetNode2 = instruction.getPrevious().getPrevious().getPrevious();
                            break;
                        }
                    }
                }
            }
        }
       
		
		
        if (targetNode!=null && targetNode2!=null){
	
          	InsnList toInsert = new InsnList();
        	
        	toInsert.add(new VarInsnNode(ALOAD, 1));
        	toInsert.add(new InsnNode(ACONST_NULL));
        	//toInsert.add(new VarInsnNode(ALOAD, 2));
        	toInsert.add(new FieldInsnNode(GETSTATIC, getMapping("TransformType",deobf), getMapping("GUI",deobf), "L"+getMapping("TransformType",deobf)+";"));
        	toInsert.add(new InsnNode(ICONST_0));
        	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "renderItem", getHookSignature(deobf), false));
        	LabelNode labelNode1 = new LabelNode();
        	toInsert.add(new JumpInsnNode(IFNE, labelNode1));
        	
        	method.instructions.insertBefore(targetNode2, toInsert);
        	
        	method.instructions.insert(targetNode,labelNode1);
        	
        	System.out.println("Successfully patched RenderItem.renderItemModelIntoGUI with ASM");
        	
        } else {
        	System.out.println("Error Patching RenderItem.renderItemModelIntoGUI with ASM");
        }
	};
	
	/**
	 * Patch the 2 argument renderModel Method
	 * @param method
	 * @param deobf
	 */
	protected static void patchRenderItem_patchMethodRenderModel(MethodNode method, boolean deobf){
		AbstractInsnNode targetNode = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ALOAD)
            {
                if (((VarInsnNode) instruction).var == 0 && instruction.getNext().getOpcode() == ALOAD && ((VarInsnNode) instruction.getNext()).var == 1)
                {
                    targetNode = instruction;
                    break;
                }
            }
        }
        
        AbstractInsnNode targetNode2 = null;
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == ALOAD && ((VarInsnNode) instruction).var == 2)
            {
                if (instruction.getNext().getOpcode() == INVOKESPECIAL)
                {
                    targetNode2 = instruction.getNext();
                    break;
                }
            }
        }
		
        if (targetNode!=null && targetNode2!=null){
	
        	InsnList toInsert = new InsnList();
        	
        	toInsert.add(new VarInsnNode(ALOAD, 2));
        	toInsert.add(new InsnNode(ACONST_NULL));
        	toInsert.add(new FieldInsnNode(GETSTATIC, getMapping("TransformType",deobf), getMapping("GUI",deobf), "L"+getMapping("TransformType",deobf)+";"));
        	toInsert.add(new InsnNode(ICONST_1));
        	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "renderItem", getHookSignature(deobf), false));
        	LabelNode labelNode1 = new LabelNode();
        	toInsert.add(new JumpInsnNode(IFNE, labelNode1));
        	
        	method.instructions.insertBefore(targetNode, toInsert);
        	
        	method.instructions.insert(targetNode2,labelNode1);
        	
        	System.out.println("Successfully patched 2 arg RenderItem.RenderModel with ASM");
        	
        } else {
        	System.out.println("Error Patching RenderItem.RenderModel (2 args) with ASM");
        }
	};
	
	protected static void patchItemRenderer_updateEquippedItem(MethodNode method, boolean deobf){
	
		AbstractInsnNode targetNode = null;
		
		//find target sequence
        for (AbstractInsnNode instruction : method.instructions.toArray())
        {
        	
            if (instruction.getOpcode() == FSTORE && ((VarInsnNode) instruction).var==4)
            {
                if ( instruction.getPrevious() !=null && instruction.getPrevious().getOpcode()== INVOKEVIRTUAL)
                {
                	
                	if ( instruction.getPrevious().getPrevious() !=null && instruction.getPrevious().getPrevious().getOpcode()== FCONST_1)
                    {
                    	
                		if ( instruction.getPrevious().getPrevious().getPrevious() !=null && instruction.getPrevious().getPrevious().getPrevious().getOpcode()== ALOAD && ((VarInsnNode) instruction.getPrevious().getPrevious().getPrevious()).var == 1)
                        {
                            targetNode = instruction;
                            break;
                        }
                    }
                }
            }
        }
        
        if (targetNode!=null) {
        	
        	InsnList toInsert = new InsnList();
        	
        	//toInsert.add(new VarInsnNode(ALOAD, 1));
        	/*toInsert.add(new InsnNode(FCONST_1));
        	toInsert.add(new VarInsnNode(ALOAD, 2));
        	toInsert.add(new VarInsnNode(ALOAD, 3));*/
        	//System.out.println("PATCH: with Hook Signature:"+getHookSignature2(deobf));
        	
        	toInsert.add(new MethodInsnNode(INVOKESTATIC, ITEMRENDERHACK, "getAttackStrengthForRendering", getHookSignature2(deobf), false));
        	toInsert.add(new VarInsnNode(FSTORE, 4));
        	
        	method.instructions.insert(targetNode, toInsert);
        	
        	System.out.println("Successfully patched ItemRenderer.updateEquippedItem with ASM");
        	
        } else {
        	System.out.println("Error Patching ItemRenderer.updateEquippedItem with ASM");
        }
	}
	
}