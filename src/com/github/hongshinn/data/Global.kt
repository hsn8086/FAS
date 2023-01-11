package com.github.hongshinn.data

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @author hsn
 */
object Global {

    var ipConnectCount = ConcurrentHashMap<String, Int>()
    var tempPlayerList = ConcurrentHashMap<String, Boolean>()

    @JvmField
    var pingCount = ConcurrentHashMap<String, Int>()

    @JvmField
    var playerKickCount = ConcurrentHashMap<String, Int>()

    @JvmField
    var spamCount = ConcurrentHashMap<String, Int>()

    @JvmField
    var loginTimeOutCount = ConcurrentHashMap<String, Int>()

    @JvmField
    var captcha = ConcurrentHashMap<String, Int>()

    @JvmField
    var captchaString = ConcurrentHashMap<String, String>()

    @JvmField
    var canLogin = ConcurrentHashMap<String, Boolean>()

    @JvmField
    var canLoginPlayer = ConcurrentHashMap<String, Boolean>()

    @JvmField
    var maximumConnectionsPerIp: Int? = null

    @JvmField
    var kickedForTooManyConnections: String? = null

    @JvmField
    var playerCapPerIp: Int? = null

    @JvmField
    var valueValidationChallengeText: String? = null

    @JvmField
    var talkingTooMuchReminder: String? = null

    @JvmField
    var unauthenticatedActionWarning: String? = null

    @JvmField
    var verificationSuccessPrompt: String? = null

    @JvmField
    var underAttack: String? = null

    @JvmField
    var prefix: String? = null

    @JvmField
    var chatEnabled = false

    @JvmField
    var speakingInterval = 0

    @JvmField
    var maxMessageLength = 0

    @JvmField
    var canStoreTheNumberOfSpeeches = 0

    @JvmField
    var computationalVerificationEnabled = false

    @JvmField
    var chatDisabledWarning: String? = null

    @JvmField
    var messageTooLongWarning: String? = null
    var dotSayVerificationEnabled = false

    @JvmField
    var bannedString: List<String>? = null

    @JvmField
    var bannedStringWarning: String? = null

    @JvmField
    var maximumPingsPerSecond = 0

    @JvmField
    var computationalVerificationTimeout = 0

    @JvmField
    var computeVerificationFailed: String? = null

    @JvmField
    var computeVerificationTimeout: String? = null

    @JvmField
    var kickedForCheck: String? = null

    @JvmField
    var kickedForUnderAttack: String? = null

    @JvmField
    var connectCount = 0

    @JvmField
    var connectCountLock: Lock = ReentrantLock()



    @JvmField
    var activity = ConcurrentHashMap<String, Int>()

    @JvmField
    var whitelistActivityThreshold = 0

    @JvmField
    var whileListEnabled = false

    @JvmField
    var nameRegex: String? = null

    @JvmField
    var whitelist = ConcurrentHashMap<String, Int>()
}