package dev.gallon.motorassistance.common.services;

import dev.gallon.motorassistance.common.domain.Position;
import dev.gallon.motorassistance.common.domain.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Optional;

public class MinecraftService {
    public static Optional<PlayerService> getPlayer() {
        if (Minecraft.getInstance().player == null) {
            return Optional.empty();
        } else {
            return Optional.of(new PlayerService(Minecraft.getInstance().player));
        }
    }

    public static boolean attackKeyPressed() {
        return Minecraft.getInstance().options.keyAttack.isDown();
        // TODO: when mod laoded { exec }
    }

    public static boolean playerAimingMob() {
        return Minecraft.getInstance().crosshairPickEntity instanceof Mob;
    }

    public static Optional<BlockService> getPointedBlock(double maxRange) {
        if (Minecraft.getInstance().hitResult == null) {
            return Optional.empty();
        } else {
            if (Minecraft.getInstance().hitResult instanceof EntityHitResult e) {
                if (getPlayer().isPresent()) {
                    return Optional.of(
                            getPlayer().get().rayTrace(
                                    maxRange,
                                    Position.fromVec3(e.getEntity().getEyePosition()),
                                    Rotation.fromVec3(e.getEntity().getLookAngle())
                            )
                    );
                } else {
                    return Optional.empty();
                }
            } else if (Minecraft.getInstance().hitResult instanceof BlockHitResult b) {
                return Optional.of(new BlockService(b));
            } else {
                return Optional.empty();
            }
        }
    }
}
