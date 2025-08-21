# Core
A repository that mirrors all our core libraries!

## Notice
This is a **MIRROR** of the libraries from our private repository.<br>
We will only update this repo if there are major changes, the builds on our Repository are based on our private repo.<br>
Code based on the release 0.1.59.
<hr>

## CloudHelper
Make working with different clouds easier!
<details>
  <summary>Supported Clouds</summary>

### What is meant with Default and Bukkit?
#### Default
This implementation will be used when we couldn't determine the used Cloud BUT detected that current software is **NOT** based on Bukkit.
#### Bukkit
This implementation will be used when we couldn't determine the used Cloud BUT detected that current software is based on Bukkit.
  
| Name | setLobby | setIngame | setMOTD | getMOTD | getServerName | sendToGroup | sendToServer | sendToLobby | setMaxPlayers | getMaxPlayers | Properties | kickPlayer
| ---------- | - | - | - | - | - | - | - | - | - | - | - | - |
| Default | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| Bukkit | ❌ | ❌ | ❌ | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ |
| Simple Cloud 2 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Simple Cloud 3 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| CloudNET 3 | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ |
| TimoCloud | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ❌ | ✅ |
</details>

## GameCore
Give your gamemodes a stable code base to make the experience even better!

## PluginCore
Make working on plugins easier by using a code base with custom Inventory Managers and more!

## OpenBoatPacketUtils
A plugin that implements all packets from the "OpenBoatUtils" mod!
<hr>

# Maven Repo
```xml
<repository>
  <id>badgames-releases</id>
  <name>BadGames Repository</name>
  <url>https://repo.badgames.de/releases</url>
</repository>

<dependency>
  <groupId>de.badgames</groupId>
  <artifactId>[NAME OF LIBRARY]</artifactId>
  <version>0.1.59</version>
</dependency>
```
