package com.github.hsn8086.data

/**
 * @author hsn
 * @since 1/12/2023 1:43 下午
 * @version 1.0
 */
object Config {
    var maximumConnectionsPerIp: Int = 0
    var kickedForTooManyConnections: String = ""


    var playerCapPerIp: Int = 0
    var banMCStormFreePlanBot: Boolean = false

    var whitelistActivityThreshold: Int = 0
    var valueValidationChallengeText: String = ""


    var talkingTooMuchReminder: String = ""


    var unauthenticatedActionWarning: String = ""


    var verificationSuccessPrompt: String = ""


    var underAttack: String = ""


    var prefix: String = ""


    var chatEnabled: Boolean = false


    var speakingInterval: Int = 0


    var maxMessageLength: Int = 0


    var canStoreTheNumberOfSpeeches: Int = 0


    var computationalVerificationEnabled: Boolean = false


    var chatDisabledWarning: String = ""


    var messageTooLongWarning: String = ""
    var dotSayVerificationEnabled: Boolean = false


    var bannedString: List<String>? = null


    var bannedStringWarning: String = ""


    var maximumPingsPerSecond = 0


    var computationalVerificationTimeout = 0


    var computeVerificationFailed: String = ""


    var computeVerificationTimeout: String = ""


    var kickedForCheck: String = ""


    var kickedForUnderAttack: String = ""


    var whileListEnabled = false


    var nameRegex: String = ""
    var kickedForIllegalName: String = ""
}