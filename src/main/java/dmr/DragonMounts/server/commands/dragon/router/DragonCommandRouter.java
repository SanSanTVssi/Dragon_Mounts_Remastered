package dmr.DragonMounts.server.commands.dragon.router;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dmr.DragonMounts.server.commands.dragon.handlers.ClearWhistleCommandHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.RecallCommandHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.SpawnCommandHandler;
import dmr.DragonMounts.server.commands.dragon.parsers.*;
import net.minecraft.commands.CommandSourceStack;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.item.DyeColor;

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
								.executes(SpawnCommandHandler::handle)
								.then(argument("pos", Vec3Argument.vec3())
										      .executes(SpawnCommandHandler::handle)
										      .then(argument("age", IntegerArgumentType.integer())
												            .executes(SpawnCommandHandler::handle)
										      )
								)
				)
				
				// hybrid
				.then(
						literal("hybrid")
								.then(argument("parent1", StringArgumentType.word())
										      .then(argument("parent2", StringArgumentType.word())
												            .executes(SpawnCommandHandler::handle)
												            .then(argument("pos", Vec3Argument.vec3())
														                  .executes(SpawnCommandHandler::handle)
														                  .then(argument("age", IntegerArgumentType.integer())
																                        .executes(SpawnCommandHandler::handle)
														                  )
												            )
										      )
								)
				);
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> recall() {
		return literal("recall")
				.then(argument("id", UuidArgument.uuid())
						      .executes(RecallCommandHandler::handle)
						      .then(argument("pos", Vec3Argument.vec3())
								            .executes(RecallCommandHandler::handle)
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
						      .executes(ClearWhistleCommandHandler::handle)
				);
	}
}