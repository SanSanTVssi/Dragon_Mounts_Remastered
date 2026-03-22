package dmr.DragonMounts.server.commands.dragon.handlers;

import dmr.DragonMounts.registry.DragonBreedsRegistry;
import dmr.DragonMounts.server.commands.dragon.factory.DragonFactory;
import dmr.DragonMounts.server.commands.dragon.models.SpawnDragonModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class SpawnCommandHandler implements CommandHandler<SpawnDragonModel> {
	
	@Override
	public int handle(CommandSourceStack source, SpawnDragonModel model) {
		
		if (!DragonBreedsRegistry.hasDragonBreed(model.breed())) {
			source.sendFailure(Component.literal("Invalid breed: " + model.breed()));
			return 0;
		}
		
		var level = source.getLevel();
		
		var dragon = DragonFactory.create(
				level,
				model
		);
		
		level.addFreshEntity(dragon);
		
		source.sendSuccess(
				() -> Component.translatable("dmr.commands.dragon_spawn.success", model.breed()),
				true
		);
		
		return 1;
	}
}
