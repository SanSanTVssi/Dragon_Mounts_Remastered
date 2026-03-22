package dmr.DragonMounts.server.commands.dragon.handlers;

import dmr.DragonMounts.server.commands.dragon.models.RecallModel;
import dmr.DragonMounts.server.entity.TameableDragonEntity;
import dmr.DragonMounts.server.worlddata.DragonWorldDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class RecallCommandHandler implements CommandHandler<RecallModel> {
	
	@Override
	public int handle(CommandSourceStack source, RecallModel model) {
		
		var history = DragonWorldDataManager.getDragonHistory(source.getLevel(), model.id());
		
		if (history == null) {
			source.sendFailure(Component.literal("Dragon not found"));
			return 0;
		}
		
		var nbt = history.compoundTag();
		var level = source.getLevel();
		
		var type = EntityType.by(nbt);
		if (type.isEmpty()) return 0;
		
		var entity = type.get().create(level);
		
		if (entity instanceof TameableDragonEntity dragon) {
			dragon.load(nbt);
			dragon.setUUID(model.id());
			
			var pos = model.pos() != null ? model.pos() : source.getPosition();
			dragon.setPos(pos.x, pos.y, pos.z);
			
			level.addFreshEntity(dragon);
		}
		
		return 1;
	}
}
