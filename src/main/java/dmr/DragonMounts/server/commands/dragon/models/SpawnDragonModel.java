package dmr.DragonMounts.server.commands.dragon.models;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class SpawnDragonModel {
	
	private final String breed;
	private final Vec3 position;
	private final CompoundTag nbt;
	private final Integer age;
	
	private SpawnDragonModel(String breed, Vec3 position, CompoundTag nbt, Integer age) {
		this.breed = breed;
		this.position = position;
		this.nbt = nbt;
		this.age = age;
	}
	
	public static SpawnDragonModel create(
			String breed,
			Vec3 position,
			CompoundTag nbt,
			Integer age,
			CommandSourceStack source
	) {
		return new SpawnDragonModel(
				breed,
				position != null ? position : source.getPosition(),
				nbt != null ? nbt : new CompoundTag(),
				age
		);
	}
	
	public String breed() {
		return breed;
	}
	
	public Vec3 position() {
		return position;
	}
	
	public CompoundTag nbt() {
		return nbt;
	}
	
	public Integer age() {
		return age;
	}
}