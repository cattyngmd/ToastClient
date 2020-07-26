package dev.toastmc.client.modules.misc

import com.google.common.eventbus.Subscribe
import org.apache.commons.lang3.RandomStringUtils
import dev.toastmc.client.ToastClient
import dev.toastmc.client.events.network.EventSyncedUpdate
import dev.toastmc.client.modules.Module
import dev.toastmc.client.utils.MessageUtil
import dev.toastmc.client.utils.TimerUtil
import java.io.File
import java.util.*

/**
 * Module to automatically send lines of a file after every chosen delay
 */
class Spammer : Module("Spammer", "Spams messages in chat from a file.", Category.MISC, -1) {
    private var lines: List<String> = ArrayList()
    private var currentLine = 0
    private val timer = TimerUtil()
    override fun onEnable() {
        timer.reset()
        lines = ToastClient.FILE_MANAGER.readFile(ToastClient.FILE_MANAGER.createFile(File("spammer.txt")))
        if (lines.isEmpty()) {
            MessageUtil.sendMessage("spammer.txt was empty!", MessageUtil.Color.RED)
            this.disable()
        }
    }

    override fun onDisable() {
        timer.reset()
    }

    @Subscribe
    fun onTick(event: EventSyncedUpdate?) {
        if (timer.isDelayComplete((getDouble("Delay") * 1000L))) {
            timer.reset()
            when (getBool("AntiSpam")) {
                false -> (mc.player ?: return).sendChatMessage((lines)[currentLine])
                true -> (mc.player
                        ?: return).sendChatMessage((lines)[currentLine] + " [" + RandomStringUtils.randomAlphanumeric(getDouble("AntiSpam length").toInt()).toLowerCase() + "]")
            }
            if (currentLine >= (lines).size - 1) currentLine = 0
            else currentLine++
        }
    }

    init {
        settings.addSlider("Delay", 0.0, 1.0, 20.0)
        settings.addBoolean("AntiSpam", false)
        settings.addSlider("AntiSpam length", 1.0, 20.0, 25.0)
    }
}