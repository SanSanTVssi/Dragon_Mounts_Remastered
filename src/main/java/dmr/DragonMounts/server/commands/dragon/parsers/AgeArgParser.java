package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dmr.DragonMounts.server.commands.dragon.models.DragonAge;
import net.minecraft.network.chat.Component;

public class AgeArgParser {
	public static Integer parse(String token) {
		
		try {
			return Integer.parseInt(token);
		} catch (NumberFormatException ignored) {}
		
		try {
			return DragonAge.valueOf(token.toUpperCase()).ticks();
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid age: " + token);
		}
	}
	
	public static void suggest(SuggestionsBuilder builder) {
		
		for (var age : DragonAge.values()) {
			builder.suggest(
					age.name().toLowerCase(),
					Component.literal("age preset")
			);
		}
		
		builder.suggest("0", Component.literal("baby"));
		builder.suggest("24000", Component.literal("adult"));
	}
}
