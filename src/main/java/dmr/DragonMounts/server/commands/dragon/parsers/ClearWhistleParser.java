package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dmr.DragonMounts.server.commands.dragon.models.ClearWhistleModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.concurrent.CompletableFuture;

public class ClearWhistleParser implements CommandParser<ClearWhistleModel> {
	
	@Override
	public ClearWhistleModel parse(String input, CommandSourceStack source) {
		return new ClearWhistleModel(
				DyeColor.byName(input.trim(), DyeColor.WHITE)
		);
	}
	
	@Override
	public CompletableFuture<Suggestions> suggest(String input, SuggestionsBuilder builder) {
		
		var trimmed = input.trim();
		
		if (trimmed.isEmpty() || !trimmed.contains(" ")) {
			for (DyeColor color : DyeColor.values()) {
				builder.suggest(
						color.getName(),
						Component.literal("color")
				);
			}
		}
		
		return builder.buildFuture();
	}
}