<div align="center">

# PlaytimeStats

A peformant playtime monitoring solution for MC with leaderboards and more.

</div>

# Features

* **Per-user playtime and sessions count monitoring**.
* **View top playtimes on the server** via `/pttop` / `/playtimetop`.
  * Paginated and delivered via a SQL database.
* **Uses Minecraft statistics**.
  * This means that Playtimestats will display playtime that happened *before* the plugin was installed as well.
* **Ignores AFK playtime**.
  * When players are AFK in EssentialsX, PlaytimeStats will track this time separately and negate it from the player's net playtime.
* **Works out of the box**. Zero configuration necessary.
* **Free and open source software** under GPL v3.
* Built with Kotlin & Gradle, with (mostly) unit-tested database logic.
* **[More ideas in the pipeline - check the To-Do List](https://github.com/orgs/ArcanePlugins/projects/11/views/1)**!

# Support

* The [ArcanePlugins Discord](https://discord.gg/HqZwdcJ) is the best place for support! Join in and select the **"Other lokka30 plugins"** role and channel on join. Feel free to chat or query about the plugin.

# Commands
* `/playtime`
  * **Aliases:** `/pt`
  * **Usage:** `/pt [player]`
  * **Description:** View your or another players' playtime stats.

* `/playtimestats`
  * **Aliases:** `/ptstats`
  * **Description:** General plugin information and management.
  * **Subcommands:**
    * `reload`
      * **Aliases:** `rl`
      * **Usage:** `/ptstats rl`
      * **Description:** Reload the configuration.
    * `version`
      * **Aliases:** `ver`, `about`, `info`
      * **Usage:** `/ptstats ver`
      * **Description:** View plugin version.

* `/playtimetop`
  * **Aliases:** `/pttop`
  * **Usage:** `/pttop [page]`
  * **Description:** View top playtimes on the server.

# Permissions
* `playtimestats.command.playtime` (default: yes)
  * allows you to run `/playtime`
* `playtimestats.command.playtimestats` (default: yes)
  * allows you to run `/playtimestats` (base command)
* `playtimestats.command.playtimestats.reload` (default: operators)
  * allows you to run `/playtimestats reload`
* `playtimestats.command.playtimestats.version` (default: yes)
  * allows you to run `/playtimestats version`

# License

Copyright (C) 2025 lokka30 and contributors

<details>
<summary>Toggle license notice</summary>

> This program is free software: you can redistribute it and/or modify
> it under the terms of the GNU General Public License as published by
> the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
>
> This program is distributed in the hope that it will be useful,
> but WITHOUT ANY WARRANTY; without even the implied warranty of
> MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
> GNU General Public License for more details.
>
> You should have received a copy of the GNU General Public License
> along with this program. If not, see <https://www.gnu.org/licenses/>.

[TODOLIST]: https://github.com/orgs/ArcanePlugins/projects/11/views/1

</details>
