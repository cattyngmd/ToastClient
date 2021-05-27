package dev.toastmc.toastclient.api.config

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.toastmc.toastclient.IToastClient
import dev.toastmc.toastclient.api.module.Module
import dev.toastmc.toastclient.api.module.ModuleManager
import dev.toastmc.toastclient.api.setting.Setting
import dev.toastmc.toastclient.api.setting.Setting.*
import dev.toastmc.toastclient.api.setting.SettingManager
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Hoosiers
 **/
object LoadConfig : IToastClient {

    private val mainDirectory = "${mc.runDirectory.canonicalPath}/toastclient/"
    private const val moduleDirectory = "modules/"

    fun init(){
        try {
            loadModules()
        } catch (e: IOException){

        }
    }

    fun loadModules() {
        val moduleLocation: String = mainDirectory + moduleDirectory
        for (module in ModuleManager.modules) {
            try {
                loadModuleDirect(moduleLocation, module)
            } catch (e: IOException) {
                println(module.getName())
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    fun loadModuleDirect(moduleLocation: String, module: Module) {
        if (!Files.exists(Paths.get(moduleLocation + module.getName() + ".json"))) {
            return
        }
        val inputStream: InputStream = Files.newInputStream(Paths.get(moduleLocation + module.getName() + ".json"))
        val moduleObject: JsonObject = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        if (moduleObject["Module"] == null) {
            return
        }
        val settingObject = moduleObject["Settings"].asJsonObject
        for (setting in SettingManager.getSettingsForMod(module)) {
            val dataObject = settingObject[setting.configName]
            if (dataObject != null && dataObject.isJsonPrimitive) {
                when (setting.type) {
                    Type.BOOLEAN -> (setting as Setting.Boolean).value = dataObject.asBoolean
                    Type.DOUBLE -> (setting as Setting.Double).value = dataObject.asDouble
                    Type.COLOR -> (setting as ColorSetting).fromInteger(dataObject.asInt)
                    Type.MODE -> (setting as Mode).run {
                        if(value.toString() != dataObject.asString) this.increment()
                    }
                }
            }
        }
        if(moduleObject["Enabled"].asBoolean) module.enable()
        module.setDrawn(moduleObject["Drawn"].asBoolean)
//        module.setBind(moduleObject["Bind"].asInt)
        inputStream.close()
    }
}