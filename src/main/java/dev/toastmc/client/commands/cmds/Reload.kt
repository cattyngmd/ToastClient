package dev.toastmc.client.commands.cmds

import net.minecraft.util.Formatting
import dev.toastmc.client.ToastClient
import dev.toastmc.client.commands.Command
import dev.toastmc.client.commands.CommandManifest
import dev.toastmc.client.utils.MessageUtil

/**
 * Command to reload one or all of the mod's configurations from it's file
 */
@CommandManifest(label = "Reload", usage = "reload [config]", description = "Reloads config", aliases = ["rl"])
class Reload : Command() {
    override fun run(args: Array<String>) {
        if (args.isEmpty()) {
            ToastClient.CONFIG_MANAGER.loadConfig()
            ToastClient.CONFIG_MANAGER.loadKeyBinds()
            ToastClient.CONFIG_MANAGER.loadModules()
            if (ToastClient.clickGuiHasOpened) {
//                ToastClient.clickGui.reloadConfig()
            }
            MessageUtil.sendMessage("Reloaded all configuration files.", MessageUtil.Color.GREEN)
        } else {
            when (args[0]) {
                "config" -> {
                    ToastClient.CONFIG_MANAGER.loadConfig()
                    MessageUtil.sendMessage("Reloaded module options.", MessageUtil.Color.GREEN)
                }
                "modules" -> {
                    ToastClient.CONFIG_MANAGER.loadModules()
                    MessageUtil.sendMessage("Reloaded modules.", MessageUtil.Color.GREEN)
                }
                "keybinds" -> {
                    ToastClient.CONFIG_MANAGER.loadKeyBinds()
                    MessageUtil.sendMessage("Reloaded keybinds.", MessageUtil.Color.GREEN)
                }
                "clickgui" -> if (ToastClient.clickGuiHasOpened) {
//                    ToastClient.clickGui.reloadConfig()
                } else {
                    MessageUtil.sendMessage("ClickGUI hasn't been opened yet", MessageUtil.Color.GREEN)
                }
                else -> {
                    MessageUtil.sendMessage("Invalid argument, valid arguments are:", MessageUtil.Color.RED)
                    MessageUtil.sendMessage("${Formatting.GRAY}  modules ${Formatting.YELLOW}reloads the enabled state of modules", MessageUtil.Color.GRAY)
                    MessageUtil.sendMessage("${Formatting.GRAY}  keybinds ${Formatting.YELLOW}reloads all keybinds", MessageUtil.Color.GRAY)
                    MessageUtil.sendMessage("${Formatting.GRAY}  clickgui ${Formatting.YELLOW}reloads the clickgui", MessageUtil.Color.GRAY)
                    MessageUtil.sendMessage("${Formatting.GRAY}  config ${Formatting.YELLOW}reloads client config (not modules)", MessageUtil.Color.GRAY)
                }
            }
        }
    }
}