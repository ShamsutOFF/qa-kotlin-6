package org.isoron.uhabits.activities.habits.list

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Допустим пользователь находится на странице логина
 * Когда пользователь вводит логин 'user_login'
 *    И пользователь вводит пароль 'user_password'
 * Тогда появляется сообщение ‘Welcome to our site’
 */

@get:Rule
val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

class FooTest : BehaviorSpec({

    lateinit var device: UiDevice

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        device.wakeUp()
        device.pressHome()
    }

    Context("(Сценарий): [ Имя сценария ]") {
        2 + 2 shouldBe 4
        Given("(Допустим) [ первое начальное условие ]") {
            2 + 2 shouldBe 4
            When("(Когда) [ событие-инициатор ]") {
                2 + 2 shouldBe 4
                When("(Когда) [ событие-инициатор ]") {
                    2 + 2 shouldBe 4
                }
                And("(И) [ второе начальное условие ]") {
                    2 + 2 shouldBe 4
                }
                Then("(Тогда) [ результат ]") {
                    2 + 2 shouldBe 4
                }
            }
            And("ghjj") {
                2 + 2 shouldBe 4
                When("123") {
                    2 + 2 shouldBe 4
                }
                Then("a baz") {
                    2 + 2 shouldBe 4
                }
            }

            When("222") {
                2 + 2 shouldBe 4
                Then("a baz") {
                    2 + 2 shouldBe 4
                }
            }
        }
    }
})

class HabitUITest {

    private lateinit var device: UiDevice
    @Before

    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        device.wakeUp()
        device.pressHome()
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

    @Test
    fun testPrintAllElements() = run {
        // Запускаем нужный экран
        step("Запускаем экран") {
            activityRule.scenario.onActivity { activity ->
                // Выводим список всех элементов на экране
                step("Выводим список элементов") {
                    printViewHierarchy()
                }
            }
        }
    }

    private fun printViewHierarchy() {
        println("@@@ on this screen:\n")
        device.dumpWindowHierarchy(System.out)
    }
}