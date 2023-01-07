# FAS

## Introduce

This project was previously written, was used for low version of the bot attack defense, but because our 1.5.2 Minecraft
server is not open, and then only a few small updates.

Note: This is the previous project, the code level is not high.

## Feature

Can be used for defense:

- Fast/slow/bypass bot attacks (single ip)
- Distributed bot attacks
- motd attack
- Play normally while being attacked

## Performance indicators:

The following test results are derived under 1.8.8 and E5 (the specific generation I forget, probably E5-2470, probably
3.1-3.3ghz)

### 1000bot/s attack

- 1Core 2G lagging
- 3Core 6G smooth
- 4Core 8G Smooth

### 5000bot/s attack

- 1Core 2G disaster(server crash)
- 4Core 8G Last ten seconds of the attack lagged (total 30s)

### 2000connect/s motd attack

- 5mbps smooth

Note:This is only my impression of the data

## Acknowledgements:

- Thanks to [JetBrains](https://www.jetbrains.com/) for the [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  Community Edition and kotlin language support.
- Thanks to [DeepL](https://www.deepl.com/) for translation support.
- Thanks to [MCStorm](https://mcstorm.io/) for providing free stress tests.(XD)