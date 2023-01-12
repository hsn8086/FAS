package com.github.hsn8086


import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import com.github.hsn8086.event.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author hsn
 */
class Main : JavaPlugin(), Listener {

    private fun loadConfig() {
        saveDefaultConfig()
    }

    private fun stringProcess(str: String?): String {
        return str?.replace('&', ChatColor.COLOR_CHAR)?.replace("\\n", "\n")?.replace(
            "{prefix}",
            Config.prefix
        )
            ?: "null"
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(ChatEvent(), this)
        Bukkit.getPluginManager().registerEvents(MoveEvent(), this)
        Bukkit.getPluginManager().registerEvents(JoinEvent(), this)
        Bukkit.getPluginManager().registerEvents(QuitEvent(), this)
        Bukkit.getPluginManager().registerEvents(PingEvent(), this)
        Bukkit.getPluginManager().registerEvents(LoginEvent(), this)
        configVer1
        object : BukkitRunnable() {
            override fun run() {
                Global.pingCount.clear()
                try {
                    Global.connectCountLock.lock()
                    if (Global.connectCount > -10) {
                        Global.connectCount -= (server.onlinePlayers.size + 8) / 4
                    }
                } finally {
                    Global.connectCountLock.unlock()
                }
                for ((key, value) in Global.playerKickCount) {
                    if (value > 1) {
                        try {
                            Global.connectCountLock.lock()
                            Global.playerKickCount[key] = value - 1
                        } finally {
                            Global.connectCountLock.unlock()
                        }
                        if (value == 2) {
                            Bukkit.getPlayer(key).kickPlayer(Config.computeVerificationTimeout)
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L)
    }

    private val configVer1: Unit
        get() {
            loadConfig()
            Config.computationalVerificationEnabled =
                config.getBoolean("Verification.ComputationalVerification.Enabled")
            Config.computationalVerificationTimeout = config.getInt("Verification.ComputationalVerification.Timeout")
            Config.banMCStormFreePlanBot = config.getBoolean("Verification.BanMCStormFreePlanBot.Enabled")
            Config.chatEnabled = config.getBoolean("Chat.Enabled")
            Config.speakingInterval = config.getInt("Chat.SpeakingInterval")
            Config.maxMessageLength = config.getInt("Chat.MaxMessageLength")
            Config.canStoreTheNumberOfSpeeches = config.getInt("Chat.CanStoreTheNumberOfSpeeches")
            Config.bannedString = config.getStringList("Chat.BannedString")
            Config.maximumConnectionsPerIp = config.getInt("Network.MaximumConnectionsPerIp")
            Config.playerCapPerIp = config.getInt("Network.PlayerCapPerIp")
            Config.maximumPingsPerSecond = config.getInt("Network.MaximumPingsPerSecond")
            Config.prefix = config.getString("Message.Prefix").replace('&', ChatColor.COLOR_CHAR).replace("\\n", "\n")
            Config.underAttack = stringProcess(config.getString("Message.UnderAttack"))
            Config.kickedForTooManyConnections = stringProcess(config.getString("Message.KickedForTooManyConnections"))
            Config.kickedForCheck = stringProcess(config.getString("Message.KickedForCheck"))
            Config.kickedForUnderAttack = stringProcess(config.getString("Message.KickedForUnderAttack")).replace(
                "{underAttack}",
                Config.underAttack
            )
            Config.kickedForIllegalName = stringProcess(config.getString("Message.KickedForIllegalName"))
            Config.valueValidationChallengeText =
                stringProcess(config.getString("Message.ValueValidationChallengeText"))
            Config.talkingTooMuchReminder = stringProcess(config.getString("Message.TalkingTooMuchReminder"))
            Config.unauthenticatedActionWarning =
                stringProcess(config.getString("Message.UnauthenticatedActionWarning"))
            Config.verificationSuccessPrompt = stringProcess(config.getString("Message.VerificationSuccessPrompt"))
            Config.chatDisabledWarning = stringProcess(config.getString("Message.ChatDisabledWarning"))
            Config.messageTooLongWarning = stringProcess(config.getString("Message.MessageTooLongWarning"))
            Config.bannedStringWarning = stringProcess(config.getString("Message.BannedStringWarning"))
            Config.computeVerificationFailed = stringProcess(config.getString("Message.ComputeVerificationFailed"))
            Config.computeVerificationTimeout = stringProcess(config.getString("Message.ComputeVerificationTimeout"))
            Config.whileListEnabled = config.getBoolean("Player.WhileList.Enabled")
            Config.whitelistActivityThreshold = config.getInt("Player.Whitelist.ActivityThreshold")
            Config.nameRegex = config.getString("Player.Name.Regex")
        }
}