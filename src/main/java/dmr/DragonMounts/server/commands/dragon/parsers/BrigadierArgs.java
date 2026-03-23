package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.server.commands.dragon.models.DragonAge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

public final class BrigadierArgs {
	
	public static Integer parseAge(CommandContext<CommandSourceStack> ctx) {
		if (has(ctx, "age")) return null;
		
		var raw = StringArgumentType.getString(ctx, "age");
		
		try {
			return DragonAge.valueOf(raw.toUpperCase()).ticks();
		} catch (IllegalArgumentException ignored) {
		}

		try {
			return Integer.parseInt(raw);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid age: " + raw);
		}
	}
	
	public static Vec3 parsePos(CommandContext<CommandSourceStack> ctx) {
		if (has(ctx, "pos")) return null;
		return Vec3Argument.getVec3(ctx, "pos");
	}
	
	public static String parseBreed(CommandContext<CommandSourceStack> ctx) {
		if (has(ctx, "breed")) return null;
		return StringArgumentType.getString(ctx, "breed");
	}
	
	public static String parseArgsRaw(CommandContext<CommandSourceStack> ctx) {
		if (has(ctx, "args")) return null;
		return StringArgumentType.getString(ctx, "args");
	}
	
	private static boolean has(CommandContext<?> ctx, String name) {
		return ctx.getNodes().stream()
		          .noneMatch(n -> n.getNode().getName().equals(name));
	}
}