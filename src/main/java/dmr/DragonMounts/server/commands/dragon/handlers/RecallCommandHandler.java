package dmr.DragonMounts.server.commands.dragon.handlers;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.parsers.BrigadierArgParsers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;

public class RecallCommandHandler {
	public static int handle(CommandContext<CommandSourceStack> ctx) {
		var id = UuidArgument.getUuid(ctx, "id");
		var pos = BrigadierArgParsers.parsePos(ctx);
		
		if (pos == null) {
			pos = ctx.getSource().getPosition();
		}
		
		return 1;
	}
}
