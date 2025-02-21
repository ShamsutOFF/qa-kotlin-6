import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.isoron.uhabits.core.database.Repository
import org.isoron.uhabits.core.models.Habit
import org.isoron.uhabits.core.models.ModelFactory
import org.isoron.uhabits.core.models.ModelObservable
import org.isoron.uhabits.core.models.sqlite.SQLiteEntryList
import org.isoron.uhabits.core.models.sqlite.SQLiteHabitList
import org.isoron.uhabits.core.models.sqlite.records.HabitRecord
import java.util.UUID
import kotlin.random.Random

class SQLiteHabitListSpec : StringSpec({

    "should add, get and delete habit" {
        val modelFactory = mockk<ModelFactory>(relaxed = true)
        val repository = mockk<Repository<HabitRecord>>(relaxed = true)
        val mockObservable = mockk<ModelObservable>(relaxed = true)

        every { modelFactory.buildHabitListRepository() } returns repository

        val habitList = spyk(SQLiteHabitList(modelFactory))
        every { habitList.observable } returns mockObservable

        val habit = Habit(
            id = Random.nextLong(),
            uuid = UUID.randomUUID().toString(),
            computedEntries = mockk<SQLiteEntryList>(relaxed = true),
            originalEntries = mockk<SQLiteEntryList>(relaxed = true),
            scores = mockk(relaxed = true),
            streaks = mockk(relaxed = true)
        )

        val habitRecord = mockk<HabitRecord>(relaxed = true).apply {
            every { id } returns habit.id
        }

        habit.id?.let { habitId ->
            every { repository.save(any<HabitRecord>()) } answers {
                val record = firstArg<HabitRecord>()
                record.id = habitId
            }

            every { repository.find(habitId) } returns habitRecord

            habitList.size() shouldBe 0

            habitList.add(habit)
            verify { repository.save(any<HabitRecord>()) }
            verify { mockObservable.notifyListeners() }

            habitList.size() shouldBe 1
            habitList.getById(habitId) shouldBe habit

            habitList.remove(habit)

//            verify { repository.remove(any<HabitRecord>()) }
            verify { mockObservable.notifyListeners() }

            habitList.getById(habitId) shouldBe null
            habitList.size() shouldBe 0
        }
    }
})