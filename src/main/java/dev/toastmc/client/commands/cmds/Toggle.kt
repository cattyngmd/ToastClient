package dev.toastmc.client.commands.cmds

import net.minecraft.util.Formatting
import dev.toastmc.client.ToastClient
import dev.toastmc.client.commands.Command
import dev.toastmc.client.commands.CommandManifest
import dev.toastmc.client.utils.MessageUtil




/**
 * Command to toggle modules
 */
@CommandManifest(label = "Toggle", usage = "toggle [module]", description = "Toggle Module", aliases = ["t"])
class Toggle : Command() {
    override fun run(args: Array<String>) {
        if (args.isEmpty()) {
            MessageUtil.sendMessage("Please provide a module name", MessageUtil.Color.RED)
        } else {
            for (module in ToastClient.MODULE_MANAGER.modules) {
                if (module.name.replace(" ".toRegex(), "").toLowerCase() == args[0].toLowerCase()) {
                    module.toggle()
                    ToastClient.CONFIG_MANAGER.writeModules()
                    MessageUtil.sendMessage("Toggled ${module.name} ${if (module.enabled) "${Formatting.RED} OFF" else "${Formatting.GREEN} ON"}", MessageUtil.Color.GRAY)
                    return
                }
            }
            MessageUtil.sendMessage("""Could not find module ${args[0]}""", MessageUtil.Color.RED)
        }
    }
}