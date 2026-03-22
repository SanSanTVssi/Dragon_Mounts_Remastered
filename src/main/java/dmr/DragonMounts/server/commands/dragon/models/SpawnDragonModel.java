package dmr.DragonMounts.server.commands.dragon.models;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class SpawnDragonModel {
	
	private final String breed;
	private final Vec3 position;
	private final CompoundTag nbt;
	private final Integer age;
	private final DragonSpawnExtras extras;
	
	private SpawnDragonModel(
			String breed,
			Vec3 position,
			Integer age,
			DragonSpawnExtras extras,
			CompoundTag nbt
	) {
		this.breed = breed;
		this.position = position;
		this.age = age;
		this.extras = extras;
		this.nbt = nbt;
	}
	
	public static SpawnDragonModel create(
			String breed,
			Vec3 position,
			Integer age,
			DragonSpawnExtras extras,
			CompoundTag nbt,
			CommandSourceStack source
	) {
		return new SpawnDragonModel(
				breed,
				position != null ? position : source.getPosition(),
				age,
				extras != null ? extras : new DragonSpawnExtras(),
				nbt != null ? nbt : new CompoundTag()
		);
	}
	
	public String breed() {
		return breed;
	}
	
	public Vec3 position() {
		return position;
	}
	
	public Integer age() {
		return age;
	}
	
	public DragonSpawnExtras extras() {
		return extras;
	}
	
	public CompoundTag nbt() {
		return nbt;
	}
}