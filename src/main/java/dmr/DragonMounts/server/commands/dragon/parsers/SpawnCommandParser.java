package dmr.DragonMounts.server.commands.dragon.parsers;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dmr.DragonMounts.registry.DragonBreedsRegistry;
import dmr.DragonMounts.server.commands.dragon.models.DragonSpawnExtras;
import dmr.DragonMounts.server.commands.dragon.models.SpawnDragonModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.CompletableFuture;

public class SpawnCommandParser implements CommandParser<SpawnDragonModel> {
	
	@Override
	public SpawnDragonModel parse(String input, CommandSourceStack source) {
		
		String breed = null;
		Vec3 pos = null;
		Integer age = null;
		DragonSpawnExtras extras = new DragonSpawnExtras();
		CompoundTag nbt = null;
		
		var tokens = input.split(" ");
		
		for (int i = 0; i < tokens.length; i++) {
			
			switch (tokens[i]) {
				
				case "pos" -> {
					pos = PosArgParser.parse(tokens, i + 1, source);
					i += 3;
				}
				
				case "age" -> {
					if (i + 1 >= tokens.length) {
						throw new IllegalArgumentException("Invalid age");
					}
					
					age = AgeArgParser.parse(tokens[++i]);
				}
				
				case "max_age" -> {
					if (i + 1 >= tokens.length) {
						throw new IllegalArgumentException("Invalid max_age");
					}
					
					extras.setMaxAge(Integer.parseInt(tokens[++i]));
				}
				
				case "nbt" -> {
					var raw = String.join(" ", java.util.Arrays.copyOfRange(tokens, i + 1, tokens.length));
					
					try {
						nbt = net.minecraft.nbt.TagParser.parseTag(raw);
					} catch (Exception e) {
						throw new IllegalArgumentException("Invalid NBT");
					}
					
					return SpawnDragonModel.create(breed, pos, age, extras, nbt, source);
				}
				
				default -> {
					if (breed == null) breed = tokens[i];
				}
			}
		}
		
		return SpawnDragonModel.create(breed, pos, age, extras, nbt, source);
	}
	
	@Override
	public CompletableFuture<Suggestions> suggest(String input, SuggestionsBuilder builder) {
		
		var tokens = input.split(" ");
		
		if (tokens.length == 0 || tokens.length == 1) {
			DragonBreedsRegistry.getDragonBreeds()
			                    .forEach(b -> builder.suggest(
					                    b.getId(),
					                    Component.literal("dragon breed")
			                    ));
			return builder.buildFuture();
		}
		
		if (!input.contains("pos")) {
			builder.suggest("pos", Component.literal("position: x y z"));
		}
		
		if (!input.contains("nbt")) {
			builder.suggest("nbt", Component.literal("nbt: { ... }"));
		}
		
		if (tokens.length >= 2 && tokens[tokens.length - 2].equals("pos")) {
			PosArgParser.suggest(builder);
		}
		
		return builder.buildFuture();
	}
}
