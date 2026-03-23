package dmr.DragonMounts.server.commands.dragon.models;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

@Getter
@Setter
public class SpawnExtras {
	
	private Integer maxAge;
	private CompoundTag nbt = new CompoundTag();
}