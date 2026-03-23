package dmr.DragonMounts.server.commands.dragon.models;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.parsers.ArgParsers;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

@Getter
@Setter
public class SpawnDragonModel {
	
	private String breed;
	private Vec3 position;
	private CompoundTag nbt = new CompoundTag();
	private Integer age;
	
	public static SpawnDragonModel parse(CommandContext<CommandSourceStack> ctx) {
		var model = new SpawnDragonModel();
		
		var age = ArgParsers.parseAge(ctx);
		if (age != null) model.setAge(age);
		
		var pos = ArgParsers.parsePos(ctx);
		if (pos != null) model.setPosition(pos);
		
		var breed = ArgParsers.parseBreed(ctx);
		if (breed != null) model.setBreed(breed);
		
		return model;
	}
}