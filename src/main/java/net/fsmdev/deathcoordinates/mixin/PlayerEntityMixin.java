package net.fsmdev.deathcoordinates.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.GlobalPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;


@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public void sendMessage(Text message) {}

    @Inject(method="onDeath", at=@At("RETURN"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        final var optionalLastDeathPos = ((ServerPlayerEntity) (Object) this).getLastDeathPos();
        if (optionalLastDeathPos.isEmpty()) return;
        final GlobalPos lastDeathPos = optionalLastDeathPos.get();
        this.sendMessage(Text.of("%s, %s".formatted(lastDeathPos.pos().toShortString(), lastDeathPos.dimension().getValue())));
    }
}
