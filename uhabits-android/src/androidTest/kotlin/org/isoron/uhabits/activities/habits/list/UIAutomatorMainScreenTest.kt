package org.isoron.uhabits.activities.habits.list

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Добрый день!
 * Решил прокомметировать свою работу прямо здесь.
 * Надеюсь, что логика моего тест кейса вполне понятна по коду.
 * Мы просто проверяем, что при запуске приложения, все экраны ознакомления работают как надо и их можно пройти.
 * Однако я немного не понял почему у меня приложение удаляется с устройства каждый раз после завершения теста,
 * хотя перед выполнением теста происходит проверка, что оно установлено, и она действительно находит нужный пэкэдж,
 * хотя я и не вижу его иконки на самом устройстве.
 */
class UIAutomatorMainScreenTest {

    private lateinit var device: UiDevice
    private val targetPackage = "org.isoron.uhabits"

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        device.wakeUp()
        device.pressHome()
    }

    @Test
    fun testSplashScreens() {
        checkAppInstallation()
        launchApp()
        checkFirstSplashScreen()
        goToNextSplashScreen()
        checkSecondSplashScreen()
        goToNextSplashScreen()
//        printScreenHierarchy()
        checkThirdSplashScreen()
        checkAndPressDone()
        printScreenHierarchy()
    }

    private fun checkAppInstallation() {
        val packages = device.executeShellCommand("pm list packages")
        assertTrue("Package $targetPackage is not installed!!!", packages.lines().any { it.contains(targetPackage) })
    }

    private fun launchApp() {
        val intent = InstrumentationRegistry.getInstrumentation()
            .context
            .packageManager
            .getLaunchIntentForPackage(targetPackage)
        assertNotNull("Launch intent for package $targetPackage is null", intent)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        InstrumentationRegistry.getInstrumentation().context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(targetPackage).depth(0)), 15000L)
    }

    private fun checkFirstSplashScreen() {
        val descText = device.findObject(By.text("Loop Habit Tracker помогает вам заводить и поддерживать полезные привычки."))
        assertNotNull("Description text on first screen not found", descText)
    }

    private fun goToNextSplashScreen() {
        val nextButton = device.findObject(By.res("$targetPackage:id/next"))
        assertNotNull("Next button not found", nextButton)
        nextButton?.click()
    }

    private fun checkSecondSplashScreen() {
        assertNotNull("Description text on second screen not found", device.findObject(By.text("Добавьте несколько привычек")))
        assertNotNull("Description text on second screen not found", device.findObject(By.text("Каждый день, после выполнения вашей привычки, поставьте галочку в приложении.")))
    }

    private fun checkThirdSplashScreen() {
        assertNotNull("Description text on second screen not found", device.findObject(By.text("Отслеживайте свои успехи")))
        assertNotNull("Description text on second screen not found", device.findObject(By.text("Детализированные диаграммы демонстрируют, как ваши привычки улучшились со временем.")))
    }

    private fun checkAndPressDone() {
        val doneButton = device.findObject(By.res("$targetPackage:id/done"))
        assertNotNull("Done button not found", doneButton)
        doneButton?.click()
    }

    private fun printScreenHierarchy() {
        println("@@@ on this screen:\n")
        device.dumpWindowHierarchy(System.out)
    }
}