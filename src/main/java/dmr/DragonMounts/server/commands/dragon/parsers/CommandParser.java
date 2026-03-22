package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.concurrent.CompletableFuture;

public interface CommandParser<T> {
	T parse(String input, CommandSourceStack source);
	CompletableFuture<Suggestions> suggest(String input, SuggestionsBuilder builder);
}

