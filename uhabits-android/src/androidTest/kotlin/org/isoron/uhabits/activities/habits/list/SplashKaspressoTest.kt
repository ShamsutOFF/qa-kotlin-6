package org.isoron.uhabits.activities.habits.list

import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView
import org.isoron.uhabits.R
import org.junit.Rule
import org.junit.Test

object KaspSplashScreen : KScreen<KaspSplashScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val firstSplashScreenText =
        KTextView { withText(R.string.intro_description_1) }
    val secondSplashScreenText =
        KTextView { withText(R.string.intro_description_2) }
    val thirdSplashScreenText =
        KTextView { (withText(R.string.intro_description_4)) }
    val nextButton = KImageView { withId(R.id.next) }
    val doneButton = KImageView { withId(R.id.done) }
}

class SimpleKaspressoTest : TestCase() {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListHabitsActivity::class.java)

    @Test
    fun testSplashScreens() = run {
        step("Проверяем текст первого сплэша, Находим и кликаем кнопку 'Далее'") {
            KaspSplashScreen {
                isDisplayed()
                firstSplashScreenText { isDisplayed() }
                nextButton { isDisplayed() }
                nextButton.click()
            }
        }
        step("Проверяем текст второго сплэша") {
            KaspSplashScreen {
                isDisplayed()
                secondSplashScreenText { isDisplayed() }
                nextButton { isDisplayed() }
                nextButton.click()
            }
        }
        step("Проверяем текст третьего сплэша") {
            KaspSplashScreen {
                isDisplayed()
                thirdSplashScreenText { isDisplayed() }
                nextButton { isInvisible() }
                doneButton { isDisplayed() }
                doneButton.click()
            }
        }
    }
}