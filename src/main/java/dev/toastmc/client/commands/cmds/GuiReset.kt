package dev.toastmc.client.commands.cmds

import dev.toastmc.client.ToastClient
import dev.toastmc.client.commands.Command
import dev.toastmc.client.commands.CommandManifest
import dev.toastmc.client.utils.MessageUtil

/**
 * Command to regenerate ClickGui category positions according to current window resolution
 */
@CommandManifest(label = "GuiReset", usage = "guireset", description = "Makes ClickGui fit on screen as much as possible", aliases = ["fixgui"])
class GuiReset : Command() {
    override fun run(args: Array<String>) {
        if (ToastClient.clickGuiHasOpened) {
//            ToastClient.clickGui.settings.initCategoryPositions()
//            ToastClient.clickGui.settings.savePositions()
            MessageUtil.sendMessage("Re-arranged ClickGui, if you still have problems, try setting your gui scale to minimum. ;)", MessageUtil.Color.GREEN)
        } else {
            MessageUtil.sendMessage("ClickGui hasn't been opened!", MessageUtil.Color.RED)
        }
    }
}