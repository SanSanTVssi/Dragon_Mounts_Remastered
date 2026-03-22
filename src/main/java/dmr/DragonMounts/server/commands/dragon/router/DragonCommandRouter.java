package dmr.DragonMounts.server.commands.dragon.router;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dmr.DragonMounts.server.commands.dragon.handlers.ClearWhistleHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.CommandHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.RecallCommandHandler;
import dmr.DragonMounts.server.commands.dragon.handlers.SpawnCommandHandler;
import dmr.DragonMounts.server.commands.dragon.parsers.ClearWhistleParser;
import dmr.DragonMounts.server.commands.dragon.parsers.CommandParser;
import dmr.DragonMounts.server.commands.dragon.parsers.RecallCommandParser;
import dmr.DragonMounts.server.commands.dragon.parsers.SpawnCommandParser;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.builder.ArgumentBuilder;

public class DragonCommandRouter {
	public static void register(CommandDispatcher<CommandSourceStack> d) {
		d.register(
				Commands.literal("dragon")
				        .requires(s -> s.hasPermission(2))
				        .then(route("spawn", new SpawnCommandParser(), new SpawnCommandHandler()))
				        .then(route("recall", new RecallCommandParser(), new RecallCommandHandler()))
				        .then(route("clear_whistle", new ClearWhistleParser(), new ClearWhistleHandler()))
		);
	}
	
	private static <T> ArgumentBuilder<CommandSourceStack, ?> route(
			String name,
			CommandParser<T> parser,
			CommandHandler<T> handler
	) {
		return Commands.literal(name)
		               .then(Commands.argument("input", StringArgumentType.greedyString())
		                             .suggests((ctx, builder) -> {
			                             var input = builder.getRemaining();
			                             return parser.suggest(input, builder);
		                             })
		                             .executes(ctx -> {
			                             var raw = StringArgumentType.getString(ctx, "input");
			                             var model = parser.parse(raw, ctx.getSource());
			                             return handler.handle(ctx.getSource(), model);
		                             })
		               );
	}
}
