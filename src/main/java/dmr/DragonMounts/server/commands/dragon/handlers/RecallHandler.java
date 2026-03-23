package dmr.DragonMounts.server.commands.dragon.handlers;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.parsers.BrigadierArgs;
import dmr.DragonMounts.server.entity.TameableDragonEntity;
import dmr.DragonMounts.server.worlddata.DragonWorldDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class RecallHandler {
	
	public static int handle(CommandContext<CommandSourceStack> ctx) {
		var source = ctx.getSource();
		var level = source.getLevel();
		
		var id = UuidArgument.getUuid(ctx, "id");
		var pos = BrigadierArgs.parsePos(ctx);
		
		if (pos == null) {
			pos = source.getPosition();
		}
		
		var history = DragonWorldDataManager.getDragonHistory(level, id);
		if (history == null) {
			source.sendFailure(Component.literal("Dragon not found"));
			return 0;
		}
		
		var nbt = history.compoundTag();
		var type = EntityType.by(nbt);
		
		if (type.isEmpty()) return 0;
		
		var entity = type.get().create(level);
		
		if (entity instanceof TameableDragonEntity dragon) {
			dragon.load(nbt);
			dragon.setUUID(id);
			dragon.setPos(pos.x, pos.y, pos.z);
			dragon.setHealth(Math.max(1, dragon.getHealth()));
			
			level.addFreshEntity(dragon);
		}
		
		return 1;
	}
}