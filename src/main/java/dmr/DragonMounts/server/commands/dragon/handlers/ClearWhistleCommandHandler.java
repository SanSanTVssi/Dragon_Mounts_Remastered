package dmr.DragonMounts.server.commands.dragon.handlers;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dmr.DragonMounts.network.packets.CompleteDataSync;
import dmr.DragonMounts.server.commands.dragon.models.ClearWhistleModel;
import dmr.DragonMounts.util.PlayerStateUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClearWhistleCommandHandler {
	
	public static int handle(CommandContext<CommandSourceStack> ctx) {
		var source = ctx.getSource();
		
		var colorName = StringArgumentType.getString(ctx, "color");
		var color = DyeColor.byName(colorName, DyeColor.WHITE);
		
		var handler = PlayerStateUtils.getHandler(source.getPlayer());
		
		handler.dragonNBTs.remove(color.getId());
		handler.dragonInstances.remove(color.getId());
		handler.respawnDelays.remove(color.getId());
		
		PacketDistributor.sendToPlayer(
				source.getPlayer(),
				new CompleteDataSync(source.getPlayer())
		);
		
		source.sendSuccess(
				() -> Component.translatable(
						"dmr.commands.clear_whistle.success",
						Component.translatable("color.minecraft." + color.getName())
				),
				true
		);
		
		return 1;
	}
}
