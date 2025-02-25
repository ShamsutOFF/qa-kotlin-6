package org.isoron.uhabits.activities.habits.list

//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
//import org.junit.Rule
//import org.junit.Test

//class HabitUITest : TestCase() {
//
//    @get:Rule
//    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)
//
//    @Test
//    fun testPrintAllElements() = run {
//        // Запускаем нужный экран
//        step("Запускаем экран") {
//            activityRule.scenario.onActivity { activity ->
//                // Выводим список всех элементов на экране
//                step("Выводим список элементов") {
//                    val rootView = activity.window.decorView.rootView
//                    printViewHierarchy(rootView)
//                }
//            }
//        }
//    }
//
//    // Рекурсивная функция для вывода иерархии View
//    private fun printViewHierarchy(view: View, level: Int = 0) {
//        val indent = "  ".repeat(level)
//        println("$indent${view::class.simpleName} (id=${view.id}, visible=${view.isVisible})")
//
//        if (view is ViewGroup) {
//            for (i in 0 until view.childCount) {
//                printViewHierarchy(view.getChildAt(i), level + 1)
//            }
//        }
//    }
//}