package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dmr.DragonMounts.server.commands.dragon.models.RecallModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RecallCommandParser implements CommandParser<RecallModel> {
	
	@Override
	public RecallModel parse(String input, CommandSourceStack source) {
		
		var tokens = input.split(" ");
		
		if (tokens.length == 0 || tokens[0].isEmpty()) {
			throw new IllegalArgumentException("Missing UUID");
		}
		
		UUID id = UUID.fromString(tokens[0]);
		
		Vec3 pos = null;
		
		if (tokens.length >= 2 && tokens[1].equals("pos")) {
			pos = PosArgParser.parse(tokens, 2, source);
		}
		
		return new RecallModel(id, pos);
	}
	
	@Override
	public CompletableFuture<Suggestions> suggest(String input, SuggestionsBuilder builder) {
		
		var tokens = input.split(" ");
		
		if (tokens.length == 0 || tokens[0].isEmpty()) {
			builder.suggest("uuid", Component.literal("dragon UUID"));
			return builder.buildFuture();
		}
		
		if (tokens.length == 1) {
			builder.suggest("pos", Component.literal("position: x y z"));
			return builder.buildFuture();
		}
		
		if (tokens.length == 2 && tokens[1].equals("pos")) {
			PosArgParser.suggest(builder);
			return builder.buildFuture();
		}
		
		return builder.buildFuture();
	}
}
