package dmr.DragonMounts.server.commands.dragon.handlers;

import net.minecraft.commands.CommandSourceStack;

public interface CommandHandler<T> {
	int handle(CommandSourceStack source, T model);
}
