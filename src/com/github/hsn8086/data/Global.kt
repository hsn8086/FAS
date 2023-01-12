package com.github.hsn8086.data

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
    var connectCount = 0

    @JvmField
    var connectCountLock: Lock = ReentrantLock()




    @JvmField
    var activity = ConcurrentHashMap<String, Int>()




    @JvmField
    var whitelist = ConcurrentHashMap<String, Int>()
}