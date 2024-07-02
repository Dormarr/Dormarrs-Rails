package dormarr.rails.mixin;

import dormarr.rails.utils.ModTags;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractRailBlock.class)
public class AbstractRailBlockMixin {
    @Inject(at = @At("HEAD"), method = "isRail(Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    private static void isRail(BlockState state, CallbackInfoReturnable<Boolean> info){

        if(state.isIn(ModTags.Blocks.CUSTOM_RAILS)){
            info.setReturnValue(true);
        }
    }
}
