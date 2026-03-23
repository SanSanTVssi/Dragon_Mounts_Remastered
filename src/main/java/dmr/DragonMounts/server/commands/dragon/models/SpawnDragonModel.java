package dmr.DragonMounts.server.commands.dragon.models;

import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.parsers.BrigadierArgs;
import dmr.DragonMounts.server.commands.dragon.parsers.SpawnExtrasParser;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.phys.Vec3;

@Getter
@Setter
public class SpawnDragonModel {
	
	private String breed;
	private Vec3 position;
	private Integer age;
	
	private SpawnExtras extras = new SpawnExtras();
	
	public static SpawnDragonModel parse(CommandContext<CommandSourceStack> ctx) {
		var model = new SpawnDragonModel();
		
		var age = BrigadierArgs.parseAge(ctx);
		if (age != null) model.setAge(age);
		
		var pos = BrigadierArgs.parsePos(ctx);
		if (pos != null) model.setPosition(pos);
		
		var breed = BrigadierArgs.parseBreed(ctx);
		if (breed != null) model.setBreed(breed);
		
		var argsRaw = BrigadierArgs.parseArgsRaw(ctx);
		if (argsRaw != null) {
			model.setExtras(SpawnExtrasParser.parse(argsRaw));
		}
		
		return model;
	}
}