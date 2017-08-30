package techguns.util;

import java.io.IOException;
import java.util.BitSet;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class DataUtil {

	public static byte compress (boolean... args) {
		if (args.length>8) {
			throw new RuntimeException(new UnsupportedOperationException("Can't store more than 8 booleans into 1 byte!"));
		}
		/*BitSet b = new BitSet();
		for(int i=0;i<args.length;i++) {
			b.set(i, args[i]);
		}
		byte[] data = b.toByteArray();
		if (data.length>0) {
			return data[0];
		}*/
		
		byte b =0;
		for (int i=0;i<args.length;i++) {
			if (args[i]) {
				b |= 1 << i;
			}
		}
		
		return b;
	}
	
	public static BitSet uncompress(byte data) {
		BitSet b = BitSet.valueOf(new byte[]{data});
		return b;
	}

	
	/**
     * Writes a compressed NBTTagCompound to the buffer
     */
    public static void writeCompoundTag(ByteBuf buffer, NBTTagCompound nbt)
    {
        try
        {
            CompressedStreamTools.write(nbt, new ByteBufOutputStream(buffer));
        }
        catch (IOException ioexception)
        {
            throw new EncoderException(ioexception);
        }
    }

    /**
     * Reads a compressed NBTTagCompound from the buffer
     */
    public static NBTTagCompound readCompoundTag(ByteBuf buffer)
    {
        int i = buffer.readerIndex();
        byte b0 = buffer.readByte();

        if (b0 == 0)
        {
            return null;
        }
        else
        {
            buffer.readerIndex(i);
            try
            {
                return CompressedStreamTools.read(new ByteBufInputStream(buffer), new NBTSizeTracker(2097152L));
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                return null;
            }
        }
    }
    
    /**
     * Writes the ItemStack's ID (short), then size (byte), then damage. (short)
     */
    public static void writeItemStack(ByteBuf buf,ItemStack stack)
    {
        if (stack.isEmpty())
        {
            buf.writeShort(-1);
        }
        else
        {
            buf.writeShort(Item.getIdFromItem(stack.getItem()));
            buf.writeByte(stack.getCount());
            buf.writeShort(stack.getMetadata());
            NBTTagCompound nbttagcompound = null;

            if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
            {
                nbttagcompound = stack.getItem().getNBTShareTag(stack);
            }

            writeCompoundTag(buf, nbttagcompound);
        }

    }

    /**
     * Reads an ItemStack from this buffer
     */
    public static ItemStack readItemStack(ByteBuf buf)
    {
        int i = buf.readShort();

        if (i < 0)
        {
            return ItemStack.EMPTY;
        }
        else
        {
            int j = buf.readByte();
            int k = buf.readShort();
            ItemStack itemstack = new ItemStack(Item.getItemById(i), j, k);
            itemstack.setTagCompound(readCompoundTag(buf));
            return itemstack;
        }
    }
}
