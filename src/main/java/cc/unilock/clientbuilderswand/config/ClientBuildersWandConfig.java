package cc.unilock.clientbuilderswand.config;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class ClientBuildersWandConfig extends ReflectiveConfig {
	public static final ClientBuildersWandConfig INSTANCE = QuiltConfig.create("clientbuilderswand", "main", ClientBuildersWandConfig.class);

	@Comment("Construction mode?")
	public final TrackedValue<Boolean> construction = this.value(true);

	@Comment("Destruction mode?")
	public final TrackedValue<Boolean> destruction = this.value(false);

	@Comment("Range")
	@IntegerRange(min = 1, max = 3)
	public final TrackedValue<Integer> range = this.value(3);
}
