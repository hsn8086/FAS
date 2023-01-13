# FAS

一个反攻击和反刷屏的插件

## Introduce

这个项目是以前写的,是用于低版本的bot攻击防御,但由于我们的1.5.2 Minecraft服务器关闭,后来只有一些小的更新.

## 特点

可用于防御:

- 快速/慢速/绕过bot攻击(单IP)
- 分布式bot攻击
- Motd攻击
- 被攻击时正常游玩
- 刷屏

## 性能指标(0.4.1)

### 5000bot/s的攻击

- 1Core 2G 5m 卡顿但不崩溃!!(AMD EPYC 7K62 2.6GHz)

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

- 5mbps 流畅1

注:这只是我记忆中的数据.

## Acknowledgements:

- 感谢[JetBrains](https://www.jetbrains.com/)提供的[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  社区版和kotlin语言支持.
- 感谢[DeepL](https://www.deepl.com/)的翻译支持.
- 感谢[MCStorm](https://mcstorm.ru/)提供的免费压力测试.(XD)