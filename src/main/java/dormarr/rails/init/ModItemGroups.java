package dormarr.rails.init;

import dormarr.rails.DormarrsRails;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups
{
    public static final ItemGroup CUSTOM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of((DormarrsRails.MOD_ID), "copper_rail"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.copper_rail")).icon(() -> new ItemStack(ModItems.EXAMPLE_ITEM)).entries((displayContext, entries) -> {
                entries.add(ModBlocks.COPPER_RAIL);
                entries.add(ModBlocks.AMETHYST_RAIL);
            }).build());

    public static void registerItemGroups(){
        DormarrsRails.LOGGER.info("Registering Item Groups for " + DormarrsRails.MOD_ID);
    }
}
