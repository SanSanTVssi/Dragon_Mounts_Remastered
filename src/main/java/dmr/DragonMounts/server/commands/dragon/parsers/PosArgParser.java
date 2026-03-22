package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class PosArgParser {
	
	public static Vec3 parse(String[] tokens, int startIndex, CommandSourceStack source) {
		
		if (startIndex + 2 >= tokens.length) {
			throw new IllegalArgumentException("Invalid pos: expected 3 coordinates");
		}
		
		var base = source.getPosition();
		
		double x = parseCoord(tokens[startIndex], base.x);
		double y = parseCoord(tokens[startIndex + 1], base.y);
		double z = parseCoord(tokens[startIndex + 2], base.z);
		
		return new Vec3(x, y, z);
	}
	
	private static double parseCoord(String token, double base) {
		
		if (token.equals("~")) return base;
		
		if (token.startsWith("~")) {
			var offset = token.substring(1);
			return base + (offset.isEmpty() ? 0 : Double.parseDouble(offset));
		}
		
		return Double.parseDouble(token);
	}
	
	public static void suggest(SuggestionsBuilder builder) {
		builder.suggest("~ ~ ~", Component.literal("relative position"));
		builder.suggest("0 64 0", Component.literal("absolute position"));
	}
}