package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dmr.DragonMounts.server.commands.dragon.models.SpawnExtras;
import net.minecraft.commands.CommandSourceStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public final class SpawnExtrasParser {
	
	public static SpawnExtras parse(String input) {
		
		var extras = new SpawnExtras();
		if (input == null || input.isBlank()) return extras;
		
		var tokens = input.split(" ");
		
		for (int i = 0; i < tokens.length; i++) {
			var token = tokens[i];
			
			if (!token.startsWith("--")) continue;
			
			var key = token.substring(2);
			
			switch (key) {
				
				case "max-age" -> {
					if (i + 1 >= tokens.length) {
						throw new IllegalArgumentException("Missing value for --max-age");
					}
					extras.setMaxAge(Integer.parseInt(tokens[++i]));
				}
				
				case "nbt" -> {
					var raw = String.join(" ",
					                      java.util.Arrays.copyOfRange(tokens, i + 1, tokens.length));
					
					try {
						extras.setNbt(net.minecraft.nbt.TagParser.parseTag(raw));
					} catch (Exception e) {
						throw new IllegalArgumentException("Invalid NBT");
					}
					
					return extras;
				}
			}
		}
		
		return extras;
	}
	
	public static CompletableFuture<Suggestions> suggest(
			CommandContext<CommandSourceStack> ctx,
			SuggestionsBuilder builder
	) {
		var input = builder.getRemaining();
		var tokens = input.split(" ");
		var used = new HashSet<>(Arrays.asList(tokens));
		
		if (tokens.length == 0 || input.endsWith(" ")) {
			
			if (!used.contains("--max-age")) {
				builder.suggest("--max-age");
			}
			
			if (!used.contains("--nbt")) {
				builder.suggest("--nbt");
			}
			
			return builder.buildFuture();
		}
		
		var last = tokens[tokens.length - 1];
		
		if (!last.startsWith("--")) {
			
			if (!used.contains("--max-age")) {
				builder.suggest("--max-age");
			}
			
			if (!used.contains("--nbt")) {
				builder.suggest("--nbt");
			}
			
			return builder.buildFuture();
		}
		
		var key = last.substring(2);
		
		switch (key) {
			case "max-age" -> {
				builder.suggest("100");
				builder.suggest("200");
			}
			case "nbt" -> {
				builder.suggest("{");
			}
		}
		
		return builder.buildFuture();
	}
}
