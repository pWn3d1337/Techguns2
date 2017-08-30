package techguns.packets;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class HandlerTemplate<T extends IMessage> implements IMessageHandler<T, IMessage> {

	@Override
	public IMessage onMessage(T message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
		return null;
	}

	protected abstract void handle(T message, MessageContext ctx);
	
}
