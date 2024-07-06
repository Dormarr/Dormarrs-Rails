package dormarr.rails.mixin;

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
        //this is overriding the return value, but doesn't dictate whether or not the game acknowledges the custom rails as rails.
        info.setReturnValue(state.getBlock() instanceof AbstractRailBlock);
    }
}
