package techguns.core;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Don't ask why it's needed, it just is.
 * Without this, getCommonSuperClass crashes with ClassNotFound on the Tessellator
 *
 */
public class CustomClassWriter extends ClassWriter {

	public CustomClassWriter(int flags) {
		super(flags);
	}

	public CustomClassWriter(ClassReader classReader, int flags) {
		super(classReader, flags);
	}

	@Override
	protected String getCommonSuperClass(String type1, String type2) {
		
		//System.out.println("Common Superclass: "+type1 + " "+type2);
		
		return super.getCommonSuperClass(type1, type2);
	}

	
	
}
