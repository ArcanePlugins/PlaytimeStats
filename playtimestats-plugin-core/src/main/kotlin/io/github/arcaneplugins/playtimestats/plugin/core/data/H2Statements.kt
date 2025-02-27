/*
 * Copyright (c) 2025 lokka30 and contributors.
 *
 * GPLv3 LICENSE NOTICE:
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.arcaneplugins.playtimestats.plugin.core.data

enum class H2Statements(
    val str: String
) {

    CREATE_TABLES(str = """
        CREATE TABLE IF NOT EXISTS Playtime (
            player_uuid             BINARY(16)          NOT NULL,
            last_username           VARCHAR(16)         NOT NULL,
            gross_minutes_played    FLOAT               NOT NULL,
            afk_minutes_played      FLOAT               NOT NULL,
            sessions_played         INTEGER             NOT NULL,
            PRIMARY KEY (player_uuid)
        );
    """.trimIndent()),

    GET_PLAYTIME(str = """
        SELECT last_username, gross_minutes_played, afk_minutes_played, sessions_played
        FROM Playtime
        WHERE player_uuid = ?;
    """.trimIndent()),

    SET_PLAYTIME(str = """
        MERGE INTO Playtime (player_uuid, last_username, gross_minutes_played, afk_minutes_played, sessions_played)
        VALUES (?, ?, ?, ?, ?);
    """),

    GET_TOP_PLAYTIMES(str = """
        SELECT
            player_uuid,
            last_username,
            gross_minutes_played,
            afk_minutes_played,
            sessions_played,
            (gross_minutes_played - afk_minutes_played) AS net_minutes_played
        FROM Playtime
        ORDER BY net_minutes_played DESC
        LIMIT ?
        OFFSET ?;
    """.trimIndent()),

}