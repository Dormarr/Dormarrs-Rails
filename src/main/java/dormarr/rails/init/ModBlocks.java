package dormarr.rails.init;

import dormarr.rails.DormarrsRails;
import dormarr.rails.block.AmethystRailBlock;
import dormarr.rails.block.CopperRailBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final AbstractBlock.Settings RAIL_SETTINGS = AbstractBlock.Settings.create().noCollision().nonOpaque().strength(0.7f).sounds(BlockSoundGroup.METAL);
    public static final Block COPPER_RAIL = registerBlock("copper_rail", new CopperRailBlock(RAIL_SETTINGS));
    public static final Block AMETHYST_RAIL = registerBlock("amethyst_rail", new AmethystRailBlock(RAIL_SETTINGS));

    public ModBlocks(){

    }

    public static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);

        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
            RegisterBlockRenderLayerMap(block);
        }

        return Registry.register(Registries.BLOCK, Identifier.of(DormarrsRails.MOD_ID, name), block);
    }


    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, Identifier.of(DormarrsRails.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        DormarrsRails.LOGGER.info("Registering ModBlocks for " + DormarrsRails.MOD_ID);
    }

    @Environment(EnvType.CLIENT)
    private static void RegisterBlockRenderLayerMap(Block block){
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

}
