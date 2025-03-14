package org.isoron.uhabits.activities.habits.list

import android.Manifest
import android.content.Context
import androidx.preference.PreferenceManager
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.VideoParams
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.dialog.KAlertDialog
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.isoron.uhabits.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

object KaspMainScreen : KScreen<KaspMainScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
    val createNewHabitButton = KImageView { withId(R.id.actionCreateHabit) }
    val yesNoButton = KImageView { withId(R.id.buttonYesNo) }
    val newHabitTV = KTextView { withText("Новая тестовая привычка") }
}

object KaspCreateNewHabitScreen : KScreen<KaspCreateNewHabitScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
    val nameInputText = KEditText { withId(R.id.nameInput) }
    val questionInputText = KEditText { withId(R.id.questionInput) }
    val saveButton = KImageView { withId(R.id.buttonSave) }
}

object KaspHabitDetailScreen : KScreen<KaspHabitDetailScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
    val moreButton =
        KImageView { withContentDescription(R.string.abc_action_menu_overflow_description) }
    val deleteButton = KTextView { withText(R.string.delete) }
    val yesDialogButton = KButton { withText(R.string.yes) }
    val deleteDialog = KAlertDialog()
}

class CreateNewHabitKaspressoTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport()
) {
    private lateinit var activityScenario: ActivityScenario<ListHabitsActivity>

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @Before
    fun setUp() {
        // Получаем контекст приложения
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Получаем SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        // Устанавливаем isFirstRun в false
        sharedPreferences.edit().putBoolean("pref_first_run", false).apply()
        // Проверяем, что значение изменилось
        val isFirstRun = sharedPreferences.getBoolean("pref_first_run", true)
        Timber.d("@@@ isFirstRun after setup: %b", isFirstRun)
        // Запускаем активность вручную
        activityScenario = ActivityScenario.launch(ListHabitsActivity::class.java)
    }

    @After
    fun tearDown() {
        // Закрываем активность после теста
        activityScenario.close()
    }

    // Данный сценарий считаю за два (Создание и удаление), но так как они тесно взаимосвязаны, я их объеденил.
    @Test
    fun testCreateNewHabitAndDeleteIt() = run {
        KaspMainScreen {
            step("Нажимаем на кнопку 'Создать привычку'") {
                isDisplayed()
                createNewHabitButton {
                    isDisplayed()
                    click()
                }
                yesNoButton {
                    isDisplayed()
                    click()
                }

            }
            step("Заполняем привычку и нажимаем 'Сохранить'") {
                KaspCreateNewHabitScreen {
                    isDisplayed()
                    nameInputText {
                        isDisplayed()
                        replaceText("Новая тестовая привычка")
                    }
                    questionInputText {
                        isDisplayed()
                        replaceText("Тестовый вопрос?")
                    }
                    saveButton {
                        isDisplayed()
                        click()
                    }
                }
            }
            step("Проверяем, что новая привычка создалась и открываем её") {
                newHabitTV {
                    isDisplayed()
                    click()
                }
            }
            step("Удаляем созданную привычкку") {
                KaspHabitDetailScreen {
                    moreButton {
                        isDisplayed()
                        click()
                    }
                    deleteButton {
                        isDisplayed()
                        click()
                    }
                    deleteDialog { isDisplayed() }
                    yesDialogButton {
                        isDisplayed()
                        click()
                    }
                }
            }
            step("Проверяем что привычки больше нет") {
                newHabitTV { doesNotExist() }
            }
        }
    }
}