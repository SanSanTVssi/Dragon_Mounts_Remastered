package dmr.DragonMounts.server.commands.dragon.router;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dmr.DragonMounts.registry.DragonBreedsRegistry;
import dmr.DragonMounts.server.commands.dragon.handlers.ClearWhistleHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.RecallHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.SpawnHandler;
import dmr.DragonMounts.server.commands.dragon.models.DragonAge;
import dmr.DragonMounts.server.commands.dragon.parsers.SpawnExtrasParser;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.item.DyeColor;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class DragonCommandRouter {
	
	public static void register(CommandDispatcher<CommandSourceStack> d) {
		d.register(
				literal("dragon")
						.requires(s -> s.hasPermission(2))
						.then(spawn())
						.then(recall())
						.then(clearWhistle())
		);
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> spawn() {
		return literal("spawn")
				
				// regular
				.then(
						argument("breed", StringArgumentType.word())
								.suggests(DragonCommandRouter::suggestBreeds)
								.executes(SpawnHandler::handle)
								.then(spawnTail())
				)
				
				// hybrid
				.then(
						literal("hybrid")
								.then(argument("parent1", StringArgumentType.word())
										      .suggests(DragonCommandRouter::suggestBreeds)
										      .then(argument("parent2", StringArgumentType.word())
												            .suggests(DragonCommandRouter::suggestBreeds)
												            .executes(SpawnHandler::handle)
												            .then(spawnTail())
										      )
								)
				);
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> spawnTail() {
		return argument("pos", Vec3Argument.vec3())
				.executes(SpawnHandler::handle)
				.then(argument("age", StringArgumentType.word())
						      .suggests((ctx, builder) -> {
							      for (var age : DragonAge.values()) {
								      builder.suggest(age.name().toLowerCase());
							      }
							      builder.suggest("-24000");
							      builder.suggest("0");
							      return builder.buildFuture();
						      })
						      .executes(SpawnHandler::handle)
						      .then(argument("args", StringArgumentType.greedyString())
								            .suggests(SpawnExtrasParser::suggest)
								            .executes(SpawnHandler::handle)
						      )
				);
	}
	
	private static CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestBreeds(
			com.mojang.brigadier.context.CommandContext<CommandSourceStack> ctx,
			com.mojang.brigadier.suggestion.SuggestionsBuilder builder
	) {
		DragonBreedsRegistry.getDragonBreeds().stream()
		                    .filter(b -> !b.isHybrid())
		                    .forEach(b -> builder.suggest(b.getId(), b.getName()));
		
		return builder.buildFuture();
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> recall() {
		return literal("recall")
				.then(argument("id", UuidArgument.uuid())
						      .executes(RecallHandler::handle)
						      .then(argument("pos", Vec3Argument.vec3())
								            .executes(RecallHandler::handle)
						      )
				);
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> clearWhistle() {
		return literal("clear_whistle")
				.then(argument("color", StringArgumentType.string())
						      .suggests((ctx, builder) -> {
							      for (DyeColor c : DyeColor.values()) {
								      builder.suggest(c.getName());
							      }
							      return builder.buildFuture();
						      })
						      .executes(ClearWhistleHandler::handle)
				);
	}
}