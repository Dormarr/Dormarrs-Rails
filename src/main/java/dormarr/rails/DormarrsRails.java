package dormarr.rails;

import dormarr.rails.init.ModBlocks;
import dormarr.rails.init.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DormarrsRails implements ModInitializer {
	public static final String MOD_ID = "dormarrs-rails";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialising Dormarrs Rails.");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}