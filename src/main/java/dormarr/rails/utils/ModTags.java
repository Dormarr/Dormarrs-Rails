package dormarr.rails.utils;

import dormarr.rails.DormarrsRails;
import net.fabricmc.fabric.mixin.datagen.TagProviderMixin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks{

        public static final TagKey<Block> RAILS = createTag("rails");
        public static final TagKey<Block> CUSTOM_RAILS = createTag("custom_rails");


        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(DormarrsRails.MOD_ID, name));
        }
    }

    public static class Items{




        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(DormarrsRails.MOD_ID, name));
        }
    }
}