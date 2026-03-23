package dmr.DragonMounts.server.commands.dragon.factory;

import dmr.DragonMounts.registry.DragonBreedsRegistry;
import dmr.DragonMounts.registry.ModEntities;
import dmr.DragonMounts.server.commands.dragon.models.SpawnDragonModel;
import dmr.DragonMounts.server.entity.TameableDragonEntity;
import net.minecraft.server.level.ServerLevel;

public class DragonSpawner {
	
	public static TameableDragonEntity create(ServerLevel level, SpawnDragonModel dragonModel) {
		
		var entity = ModEntities.DRAGON_ENTITY.get().create(level);
		
		if (!(entity instanceof TameableDragonEntity dragon)) {
			throw new IllegalStateException("Dragon entity type mismatch");
		}
		
		dragon.load(dragonModel.getExtras().getNbt());
		dragon.setBreed(DragonBreedsRegistry.getDragonBreed(dragonModel.getBreed()));
		dragon.setPos(dragonModel.getPosition().x, dragonModel.getPosition().y, dragonModel.getPosition().z);
		dragon.setAge(dragonModel.getAge());
		dragon.setMaxAge(dragonModel.getExtras().getMaxAge());
		
		return dragon;
	}
}