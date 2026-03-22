package dmr.DragonMounts.server.commands.dragon.factory;

import dmr.DragonMounts.registry.DragonBreedsRegistry;
import dmr.DragonMounts.registry.ModEntities;
import dmr.DragonMounts.server.commands.dragon.models.SpawnDragonModel;
import dmr.DragonMounts.server.entity.TameableDragonEntity;
import net.minecraft.server.level.ServerLevel;

public class DragonFactory {
	
	public static TameableDragonEntity create(ServerLevel level, SpawnDragonModel dragonModel) {
		
		var entity = ModEntities.DRAGON_ENTITY.get().create(level);
		
		if (!(entity instanceof TameableDragonEntity dragon)) {
			throw new IllegalStateException("Dragon entity type mismatch");
		}
		
		dragon.load(dragonModel.nbt());
		dragon.setBreed(DragonBreedsRegistry.getDragonBreed(dragonModel.breed()));
		dragon.setPos(dragonModel.position().x, dragonModel.position().y, dragonModel.position().z);
		dragon.setAge(dragonModel.age());
		
		return dragon;
	}
}