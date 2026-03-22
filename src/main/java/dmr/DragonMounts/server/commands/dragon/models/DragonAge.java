package dmr.DragonMounts.server.commands.dragon.models;

public enum DragonAge {
	HATCHLING(-24000),
	YOUNG(-12000),
	JUVENILE(-6000),
	ADULT(0);
	
	private final int ticks;
	
	DragonAge(int ticks) {
		this.ticks = ticks;
	}
	
	public int ticks() {
		return ticks;
	}
}
