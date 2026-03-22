package dmr.DragonMounts.server.commands.dragon.models;

import dmr.DragonMounts.server.entity.TameableDragonEntity;
import lombok.Getter;

@Getter
public class DragonSpawnExtras {
	
	private Integer maxAge;
	
	public DragonSpawnExtras() {
	}
	
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}
