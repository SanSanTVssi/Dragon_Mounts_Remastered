package dmr.DragonMounts.server.commands.dragon.handlers;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.models.RecallModel;
import dmr.DragonMounts.server.commands.dragon.parsers.ArgParsers;
import dmr.DragonMounts.server.entity.TameableDragonEntity;
import dmr.DragonMounts.server.worlddata.DragonWorldDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class RecallCommandHandler {
	public static int handle(CommandContext<CommandSourceStack> ctx) {
		var id = UuidArgument.getUuid(ctx, "id");
		var pos = ArgParsers.parsePos(ctx);
		
		if (pos == null) {
			pos = ctx.getSource().getPosition();
		}
		
		return 1;
	}
}
