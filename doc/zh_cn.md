# FAS

一个轻量级的mc反压测/反刷屏插件

注:由于只使用了BukkitAPI,所以无法达到完美防御的效果,但服务器的版本可以是1.5也可以是最新版.

## 前言

这个项目是以前写的,是用于低版本的bot攻击防御,但由于我们的1.5.2 Minecraft服务器关闭,后来只有一些小的更新.

## 依赖

- [KotlinMC](https://www.spigotmc.org/resources/kotlinmc.82237/)

## 特点

可用于防御:

- 快速/慢速/绕过bot攻击(单IP)
- 分布式bot攻击
- Motd攻击
- 被攻击时正常游玩
- 刷屏

## 性能指标(0.4.2)

### 5000bot/s的攻击

- 1Core 2G 5m 卡顿但不崩溃!!(AMD EPYC 7K62 2.6GHz)
- 2Core 4G 20m 几近流畅(Intel Platinum 8374C IceLake 2.7/3.3GHz)

## 性能指标(低于0.4.0的版本)

以下测试结果是在1.8.8和E5(具体型号我忘了,可能是E5-2470,频率大概是
3.1-3.3ghz)

### 1000bot/s的攻击

- 1Core 2G 卡顿
- 3Core 6G 流畅
- 4Core 8G 流畅

### 5000bot/s的攻击

- 1Core 2G 灾难(服务器崩溃)
- 4Core 8G 攻击的最后十秒略微卡顿(总共30秒)

### 2000connect/s motd攻击

- 5mbps 流畅

注:这只是我记忆中的数据.

## Acknowledgements:

- 感谢[JetBrains](https://www.jetbrains.com/)提供的[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  社区版和kotlin语言支持.
- 感谢[DeepL](https://www.deepl.com/)的翻译支持.
- 感谢[MCStorm](https://mcstorm.ru/)提供的免费压力测试.(XD)