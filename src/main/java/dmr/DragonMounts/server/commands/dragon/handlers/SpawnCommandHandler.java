package dmr.DragonMounts.server.commands.dragon.handlers;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.factory.DragonFactory;
import dmr.DragonMounts.server.commands.dragon.models.SpawnDragonModel;
import net.minecraft.commands.CommandSourceStack;

public class SpawnCommandHandler {
	
	public static int handle(CommandContext<CommandSourceStack> ctx) {
		
		var model = SpawnDragonModel.parse(ctx);
		
		var source = ctx.getSource();
		var level = source.getLevel();
		
		var dragon = DragonFactory.create(level, model);
		
		level.addFreshEntity(dragon);
		
		return 1;
	}
}