package dmr.DragonMounts.server.commands.dragon.handlers;

import dmr.DragonMounts.network.packets.CompleteDataSync;
import dmr.DragonMounts.server.commands.dragon.models.ClearWhistleModel;
import dmr.DragonMounts.util.PlayerStateUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClearWhistleHandler implements CommandHandler<ClearWhistleModel> {
	
	@Override
	public int handle(CommandSourceStack source, ClearWhistleModel model) {
		
		var player = source.getPlayer();
		var handler = PlayerStateUtils.getHandler(player);
		
		var id = model.color().getId();
		
		handler.dragonNBTs.remove(id);
		handler.dragonInstances.remove(id);
		handler.respawnDelays.remove(id);
		
		PacketDistributor.sendToPlayer(player, new CompleteDataSync(player));
		
		source.sendSuccess(
				() -> Component.translatable(
						"dmr.commands.clear_whistle.success",
						Component.translatable("color.minecraft." + model.color().getName())
				),
				true
		);
		
		return 1;
	}
}
