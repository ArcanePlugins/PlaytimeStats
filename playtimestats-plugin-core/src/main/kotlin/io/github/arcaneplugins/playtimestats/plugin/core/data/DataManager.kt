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

import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
abstract class DataManager(
    @Suppress("unused") private val platform: Platform,
    absolutePath: String,
) {

    companion object {
        private const val URI_PREFIX = "jdbc:h2:file:"
        private const val PAGE_SIZE = 10
    }

    private val url: String = URI_PREFIX + absolutePath
    private var connected = false
    private lateinit var connection: Connection

    fun startup() {
        if (connected) {
            return
        }

        Class.forName("org.h2.Driver")
        connection = DriverManager.getConnection(url)
        connected = true
        createTables()
        startTasks()
    }

    fun shutdown() {
        if (!connected) {
            return
        }

        stopTasks()
        connection.close()
        connected = false
    }

    protected abstract fun startTasks()

    protected abstract fun stopTasks()

    private fun createTables() {
        connection.prepareStatement(H2Statements.CREATE_TABLES.str).use { statement ->
            statement.executeUpdate()
        }
    }

    fun getPlaytimeData(uuid: UUID): PlaytimeData? {
        return connection.prepareStatement(H2Statements.GET_PLAYTIME.str).use { statement ->
            statement.setBytes(1, uuid.toKotlinUuid().toByteArray())
            val rs = statement.executeQuery()

            if (!rs.next()) {
                return@use null
            }

            return@use PlaytimeData(
                uuid = uuid,
                lastUsername = rs.getString("last_username"),
                minutesPlayed = rs.getFloat("minutes_played"),
                sessionsPlayed = rs.getInt("sessions_played"),
            )
        }
    }

    fun setPlaytimeData(data: PlaytimeData) {
        connection.prepareStatement(H2Statements.SET_PLAYTIME.str).use { statement ->
            statement.setBytes(1, data.uuid.toKotlinUuid().toByteArray())
            statement.setString(2, data.lastUsername)
            statement.setFloat(3, data.minutesPlayed)
            statement.setInt(4, data.sessionsPlayed)
            statement.executeUpdate()
        }
    }


    fun getTopPlaytimesData(page: Int): List<PlaytimeData> {
        return connection.prepareStatement(H2Statements.GET_TOP_PLAYTIMES.str).use { statement ->
            statement.setInt(1, PAGE_SIZE)
            statement.setInt(2, (page - 1) * PAGE_SIZE)
            val rs = statement.executeQuery()
            if (!rs.next()) {
                return@use emptyList()
            }

            val top = mutableListOf<PlaytimeData>()

            do {
                top.add(
                    PlaytimeData(
                        uuid = Uuid.fromByteArray(rs.getBytes("player_uuid")).toJavaUuid(),
                        lastUsername = rs.getString("last_username"),
                        minutesPlayed = rs.getFloat("minutes_played"),
                        sessionsPlayed = rs.getInt("sessions_played"),
                    )
                )
            } while (rs.next())

            return@use top
        }
    }

}