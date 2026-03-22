package dmr.DragonMounts.server.entity.dragon;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

@Getter
@Setter
public abstract class AgeControlledDragonEntity extends AbstractDragonEntity {
	
	private Integer maxAge;
	
	protected AgeControlledDragonEntity(EntityType<? extends TamableAnimal> type, Level level) {
		super(type, level);
	}
	
	protected int applyAgeRules(int age) {
		if (maxAge != null && age > maxAge) {
			return maxAge;
		}
		
		return age;
	}
	
	@Override
	public void setAge(int age) {
		super.setAge(applyAgeRules(age));
	}
	
	@Override
	public void ageUp(int amount, boolean flag) {
		super.setAge(applyAgeRules(getAge() + amount));
		updateAgeProperties();
	}
}