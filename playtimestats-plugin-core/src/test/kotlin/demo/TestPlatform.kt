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

package demo

import demo.TestDataManager.Companion.ABSOLUTE_PATH
import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import io.github.arcaneplugins.playtimestats.plugin.core.data.PlaytimeData
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.io.path.exists
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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

class TestPlatform : Platform {

    private val dataMgr = TestDataManager(this)

    @OptIn(ExperimentalPathApi::class)
    override fun initialize() {
        // delete database file if it exists
        val databaseFileDirectory = ABSOLUTE_PATH.parent
        if (databaseFileDirectory.exists()) {
            println("Deleting existing test database directory: ${databaseFileDirectory}")
            databaseFileDirectory.deleteRecursively()
        }
    }

    override fun startup() {
        dataMgr.startup()
    }

    override fun shutdown() {
        dataMgr.shutdown()
    }

    override fun reload() {
        shutdown()
        initialize()
        startup()
    }

    @Test
    fun test() {
        initialize()
        startup()

        val notchUuid: UUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5")
        val notchUsername: String = "Notch"
        val minutesPlayed: Float = 5.678912345.toFloat()
        val sessionsPlayed: Int = 15
        val ptData: PlaytimeData = PlaytimeData(
            uuid = notchUuid,
            lastUsername = notchUsername,
            minutesPlayed = minutesPlayed,
            sessionsPlayed = sessionsPlayed,
        )

        assertNull(dataMgr.getPlaytimeData(notchUuid), "playtime data should be null since it was never set")
        dataMgr.setPlaytimeData(ptData)
        val gotPtData: PlaytimeData? = dataMgr.getPlaytimeData(notchUuid)
        assertNotNull(gotPtData, "playtime data should not be null since it was set")
        assertEquals(gotPtData.uuid, notchUuid, "expected match")
        assertEquals(gotPtData.lastUsername, notchUsername, "expected match")
        assertEquals(gotPtData.minutesPlayed, minutesPlayed, "expected match")
        assertEquals(gotPtData.sessionsPlayed, sessionsPlayed, "expected match")
        val newMinutesPlayed: Float = 25.4567.toFloat()
        dataMgr.setPlaytimeData(ptData.copy(minutesPlayed = newMinutesPlayed))
        val newGotPtData: PlaytimeData? = dataMgr.getPlaytimeData(notchUuid)
        assertNotNull(newGotPtData, "playtime data should not be null since it was set")
        assertEquals(newGotPtData.minutesPlayed, newMinutesPlayed, "expected match")
        val differentPtData = PlaytimeData(
            uuid = UUID.fromString("5f9f71d7-bcef-4f2c-affb-f7bf96682b52"),
            lastUsername = "lokka30",
            minutesPlayed = 15.toFloat(),
            sessionsPlayed = 4,
        )
        dataMgr.setPlaytimeData(differentPtData)
        assertEquals(differentPtData, dataMgr.getPlaytimeData(differentPtData.uuid), "expected match")
        val page2 = dataMgr.getTopPlaytimesData(2)
        assertTrue(page2.isEmpty(), "expected page 2 to be empty")
        val page1 = dataMgr.getTopPlaytimesData(1)
        assertEquals(page1.first().uuid, notchUuid, "expected Notch playtime data to be first place")

        shutdown()
    }
}