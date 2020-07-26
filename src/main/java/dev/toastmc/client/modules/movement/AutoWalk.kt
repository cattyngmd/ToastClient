package dev.toastmc.client.modules.movement

import com.google.common.eventbus.Subscribe
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket
import dev.toastmc.client.events.network.EventPacketSent
import dev.toastmc.client.modules.Module
import dev.toastmc.client.utils.MessageUtil

class AutoWalk : Module("AutoWalk", "Automatically walk forwards", Category.MOVEMENT, -1) {
    @Subscribe
    fun onEvent(e: EventPacketSent) {
        run {
            if (mc.player != null) {
                when (this.settings.getMode("Mode")) {
                    "Simple" -> {
                        if (e.getPacket() is PlayerMoveC2SPacket || e.getPacket() is InventoryS2CPacket) {
                            mc.options.sprintToggled = true
                            mc.options.keyForward.isPressed = true
                        }
                    }
                    "Baritone" -> {
                        MessageUtil.sendMessage("Ree baritone not integrated yet", MessageUtil.Color.RED)
                        mc.options.keyForward.isPressed = false
                    }
                }
            }
        }
    }

    override fun onDisable() {
        if (mc.player != null) mc.options.keyForward.isPressed = false
    }

    init {
        settings.addMode("Mode", "Simple", "Simple", "Baritone")
    }
}