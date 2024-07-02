package dormarr.rails.mixin;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin {

    @Inject(at = @At("HEAD"), method = "getMaxSpeed", cancellable = true)
    protected void getMaxSpeed(CallbackInfoReturnable<Double> cir){
        AbstractMinecartEntity minecart = (AbstractMinecartEntity) (Object) this;
        cir.setReturnValue((minecart.isTouchingWater() ? 12.0 : 16.0) /20.0);
    }
}
