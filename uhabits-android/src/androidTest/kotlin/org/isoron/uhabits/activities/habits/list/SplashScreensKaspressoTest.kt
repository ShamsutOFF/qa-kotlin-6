package org.isoron.uhabits.activities.habits.list

import android.Manifest
import android.content.Context
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.kaspresso.files.resources.ResourceFilesProvider
import com.kaspersky.kaspresso.internal.extensions.other.createFileIfNeeded
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView
import org.isoron.uhabits.R
import org.junit.Rule
import org.junit.Test
import timber.log.Timber
import java.io.File

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

class LoggingResourceFilesProvider(
    private val context: Context
) : ResourceFilesProvider {
    override fun provideLogcatFile(tag: String, subDir: String?): File {
        val file = File(context.getExternalFilesDir(null), "logcat.txt")
        Timber.d("@@@ Logcat file path: ${file.absolutePath}")
        return file.apply {
            createFileIfNeeded()
        }
    }

    override fun provideScreenshotFile(tag: String, subDir: String?): File {
        val file = File(context.getExternalFilesDir(null), "screenshot.png")
        Timber.d("@@@ Screenshot file path: ${file.absolutePath}")
        return file.apply {
            createFileIfNeeded()
        }
    }

    override fun provideVideoFile(tag: String, subDir: String?): File {
        val file = File(context.getExternalFilesDir(null), "video.mp4")
        Timber.d("@@@ Video file path: ${file.absolutePath}")
        return file.apply {
            createFileIfNeeded()
        }
    }

    override fun provideViewHierarchyFile(tag: String, subDir: String?): File {
        TODO("Not yet implemented")
    }
}

class SplashScreensKaspressoTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport()
) {
    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

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