package com.github.hsn8086


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
            Global.prefix!!
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
                            Bukkit.getPlayer(key).kickPlayer(Global.computeVerificationTimeout)
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L)
    }

    private val configVer1: Unit
        get() {
            loadConfig()
            Global.computationalVerificationEnabled =
                config.getBoolean("Verification.ComputationalVerification.Enabled")
            Global.computationalVerificationTimeout = config.getInt("Verification.ComputationalVerification.Timeout")
            Global.banMCStormFreePlanBot = config.getBoolean("Verification.BanMCStormFreePlanBot.Enabled")
            Global.chatEnabled = config.getBoolean("Chat.Enabled")
            Global.speakingInterval = config.getInt("Chat.SpeakingInterval")
            Global.maxMessageLength = config.getInt("Chat.MaxMessageLength")
            Global.canStoreTheNumberOfSpeeches = config.getInt("Chat.CanStoreTheNumberOfSpeeches")
            Global.bannedString = config.getStringList("Chat.BannedString")
            Global.maximumConnectionsPerIp = config.getInt("Network.MaximumConnectionsPerIp")
            Global.playerCapPerIp = config.getInt("Network.PlayerCapPerIp")
            Global.maximumPingsPerSecond = config.getInt("Network.MaximumPingsPerSecond")
            Global.prefix = config.getString("Message.Prefix").replace('&', ChatColor.COLOR_CHAR).replace("\\n", "\n")
            Global.underAttack = stringProcess(config.getString("Message.UnderAttack"))
            Global.kickedForTooManyConnections = stringProcess(config.getString("Message.KickedForTooManyConnections"))
            Global.kickedForCheck = stringProcess(config.getString("Message.KickedForCheck"))
            Global.kickedForUnderAttack = stringProcess(config.getString("Message.KickedForUnderAttack")).replace(
                "{underAttack}",
                Global.underAttack!!
            )
            Global.kickedForIllegalName = stringProcess(config.getString("Message.KickedForIllegalName"))
            Global.valueValidationChallengeText =
                stringProcess(config.getString("Message.ValueValidationChallengeText"))
            Global.talkingTooMuchReminder = stringProcess(config.getString("Message.TalkingTooMuchReminder"))
            Global.unauthenticatedActionWarning =
                stringProcess(config.getString("Message.UnauthenticatedActionWarning"))
            Global.verificationSuccessPrompt = stringProcess(config.getString("Message.VerificationSuccessPrompt"))
            Global.chatDisabledWarning = stringProcess(config.getString("Message.ChatDisabledWarning"))
            Global.messageTooLongWarning = stringProcess(config.getString("Message.MessageTooLongWarning"))
            Global.bannedStringWarning = stringProcess(config.getString("Message.BannedStringWarning"))
            Global.computeVerificationFailed = stringProcess(config.getString("Message.ComputeVerificationFailed"))
            Global.computeVerificationTimeout = stringProcess(config.getString("Message.ComputeVerificationTimeout"))
            Global.whileListEnabled = config.getBoolean("Player.WhileList.Enabled")
            Global.whitelistActivityThreshold = config.getInt("Player.Whitelist.ActivityThreshold")
            Global.nameRegex = config.getString("Player.Name.Regex")
        }
}