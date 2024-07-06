package dormarr.rails.mixin;

import dormarr.rails.block.AmethystRailBlock;
import dormarr.rails.block.CopperRailBlock;
import dormarr.rails.init.ModBlocks;
import dormarr.rails.utils.MinecartUtil;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin {

    private static final double VANILLA_MAX_SPEED = 12.0 / 20.0;

    private Vec3d lastSpeedPos = null;
    private BlockPos lastPos = null;
    private double adjustedGoldSpeed = 20.0;
    private double copperSpeed = 16.0;
    private double amethystSpeed = 12.0;//haven't decided what to do with this yet.
    private double maxSpeed = VANILLA_MAX_SPEED;

    private final AbstractMinecartEntity minecart = (AbstractMinecartEntity) (Object) this;

    @Inject(at = @At("HEAD"), method = "getMaxSpeed", cancellable = true)
    protected void getMaxSpeed(CallbackInfoReturnable<Double> cir){
        final double maxSpeed = getModMaxSpeed();
        if(maxSpeed != VANILLA_MAX_SPEED){
            cir.setReturnValue(maxSpeed);
        }
        cir.setReturnValue(maxSpeed);
    }

    //add in move on rail injection to top.
    @Inject(at = @At("HEAD"), method = "moveOnRail", cancellable = true)
    protected void moveOnRail(BlockPos pos, BlockState state, CallbackInfo ci){
        boolean bl = false;
        boolean bl2 = false;
        if(state.isOf(ModBlocks.COPPER_RAIL)){
            bl = (Boolean) state.get(CopperRailBlock.POWERED);
            bl2 = !bl;
        }
        if(state.isOf(ModBlocks.AMETHYST_RAIL)){

            bl = (Boolean) state.get(AmethystRailBlock.POWERED);
            bl2 = !bl;
        }

        if (bl) {
            Vec3d vec3d5 = minecart.getVelocity();
            double w = vec3d5.horizontalLength();
            if (w > 0.01) {
                double z = 0.06;
                minecart.setVelocity(vec3d5.add(vec3d5.x / w * 0.06, 0.0, vec3d5.z / w * 0.06));
            } else {
                Vec3d vec3d6 = minecart.getVelocity();
                double aa = vec3d6.x;
                double ab = vec3d6.z;

                RailShape railShape = state.get(((AbstractRailBlock)state.getBlock()).getShapeProperty());
//                minecart.setVelocity(aa, vec3d6.y, ab);

                Vec3d velocity = minecart.getVelocity();
                double speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);

                if (speed > 0.01D) {
                    minecart.setVelocity(velocity.normalize().multiply(((MinecartEntityAccessor) minecart).invokeGetMaxSpeed()));
                } else {
                    MinecartUtil.HandleBlockHit(pos, minecart, railShape);
                }
            }
        }
    }

    private double getModMaxSpeed(){

        //I want to set the speed depending on the rail, but I don't know how to access the world context in 1.21.
        final BlockPos currentPos = minecart.getBlockPos();
        if(currentPos.equals(lastPos)) return maxSpeed;
        lastPos = currentPos;

        final Vec3d v = minecart.getMovement();
        final BlockPos nextPos = new BlockPos(
                currentPos.getX() + (int)Math.sin(v.getX()),
                currentPos.getY(),
                currentPos.getZ() + (int)Math.sin(v.getZ())
        );

        final BlockState nextState = minecart.getBlockStateAtPos();
        if(nextState.getBlock() instanceof PoweredRailBlock rail){
            final RailShape shape = nextState.get(rail.getShapeProperty());
            if(shape == RailShape.NORTH_EAST || shape == RailShape.NORTH_WEST || shape == RailShape.SOUTH_EAST || shape == RailShape.SOUTH_WEST){
                return maxSpeed = VANILLA_MAX_SPEED;
            }else{
                return (minecart.isTouchingWater() ? adjustedGoldSpeed * 0.75 : adjustedGoldSpeed) / 20;
            }
        }else if(nextState.getBlock() instanceof CopperRailBlock rail){
            final RailShape shape = nextState.get(rail.getShapeProperty());
            if(shape == RailShape.NORTH_EAST || shape == RailShape.NORTH_WEST || shape == RailShape.SOUTH_EAST || shape == RailShape.SOUTH_WEST){
                //Vanilla on slopes, deal with slopes later.
                return maxSpeed = VANILLA_MAX_SPEED;
            }else{
                return (minecart.isTouchingWater() ? copperSpeed * 0.75 : copperSpeed) / 20;
            }
        }else if(nextState.getBlock() instanceof AmethystRailBlock rail){
            final RailShape shape = nextState.get(rail.getShapeProperty());
            if(shape == RailShape.NORTH_EAST || shape == RailShape.NORTH_WEST || shape == RailShape.SOUTH_EAST || shape == RailShape.SOUTH_WEST){
                //Vanilla on slopes, deal with slopes later.
                return maxSpeed = VANILLA_MAX_SPEED;
            }else{

                return (minecart.isTouchingWater() ? amethystSpeed * 0.75 : amethystSpeed) / 20;
            }
        }
        else{
            return VANILLA_MAX_SPEED;
        }
    }
}
