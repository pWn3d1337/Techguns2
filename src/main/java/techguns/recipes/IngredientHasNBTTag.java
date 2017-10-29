package techguns.recipes;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunMeleeCharge;

public class IngredientHasNBTTag extends Ingredient {

	protected final String key;
	protected final Object value;
	protected final byte id;
	
    public IngredientHasNBTTag(@Nonnull String key, @Nonnull Object value, @Nonnull ItemStack... stacks)
    {
        super(stacks);
        this.key=key;
        this.value=value;
        this.id = getIdforClass(value);
    }

    
    
	@Override
	public ItemStack[] getMatchingStacks() {
		int size = super.getMatchingStacks().length;
		ItemStack[] matchingStacks = new ItemStack[size];
		
		for(int i=0;i<size;i++) {
			matchingStacks[i]=super.getMatchingStacks()[i].copy();
			if(matchingStacks[i].getItem() instanceof GenericGun) {
				GenericGun g = (GenericGun) matchingStacks[i].getItem();
				g.onCreated(matchingStacks[i], null, null);
				NBTTagCompound tags = matchingStacks[i].getTagCompound();
				setTagByType(id, tags, key, value);
			}
		}
		
		return matchingStacks;
	}



	@Override
	public boolean apply(ItemStack stack) {
		if(super.apply(stack)) {
			NBTTagCompound tags = stack.getTagCompound();
			if(tags!=null && tags.hasKey(key,id)) {
				Object o = getTagByType(id, tags, key);
				return value.equals(o);
			}
		}
		return false;
	}
	
	protected static byte getIdforClass(Object o) {
		if(o instanceof Byte) {
			return 1;
		} else if (o instanceof Short) {
			return 2;
		} else if (o instanceof Integer) {
			return 3;
		} else if (o instanceof Long) {
			return 4;
		} else if (o instanceof Float) {
			return 5;
		} else if (o instanceof Double) {
			return 6;
		} else if (o instanceof Byte[]) {
			return 7;
		} else if (o instanceof String) {
			return 8;
		} 
		/* 9 */
		else if (o instanceof NBTTagCompound) {
			return 10;
		} else if (o instanceof Integer[]) {
			return 11;
		}
		throw new UnsupportedOperationException("Invalid datatype for NBT tag: "+o.getClass().getName());
	}
	
	protected static Object getTagByType(byte id, NBTTagCompound tags, String key)
    {
        switch (id)
        {
            /*case 0:
                return new NBTTagEnd();*/
            case 1:
                return tags.getByte(key);
            case 2:
                return tags.getShort(key);
            case 3:
                return tags.getInteger(key);
            case 4:
                return tags.getLong(key);
            case 5:
                return tags.getFloat(key);
            case 6:
                return tags.getDouble(key);
            case 7:
                return tags.getByteArray(key);
            case 8:
                return tags.getString(key);
            /*case 9:
                return new NBTTagList();*/
            case 10:
                return tags.getCompoundTag(key);
            case 11:
                return tags.getIntArray(key);
            /*case 12:
                return new NBTTagLongArray();*/
            default:
                return null;
        }
    }
	
	protected static void setTagByType(byte id, NBTTagCompound tags, String key, Object value)
    {
        switch (id)
        {
            /*case 0:
                return new NBTTagEnd();*/
            case 1:
                tags.setByte(key, (byte) value);
                break;
            case 2:
            	 tags.setShort(key, (short) value);
                 break;
            case 3:
            	 tags.setInteger(key, (int) value);
                 break;
            case 4:
            	tags.setLong(key, (long) value);
                break;
            case 5:
            	tags.setFloat(key, (float) value);
                break;
            case 6:
            	tags.setDouble(key, (double) value);
                break;
            case 7:
            	tags.setByteArray(key, (byte[]) value);
                break;
            case 8:
            	tags.setString(key, (String) value);
                break;
            /*case 9:
                return new NBTTagList();*/
            case 10:
                tags.setTag(key, (NBTBase) value);
                break;
            case 11:
                tags.setIntArray(key, (int[]) value);
                break;
            /*case 12:
                return new NBTTagLongArray();*/
            default:
                break;
        }
    }
    
    
}
