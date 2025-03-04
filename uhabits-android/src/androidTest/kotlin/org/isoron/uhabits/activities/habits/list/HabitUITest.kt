package org.isoron.uhabits.activities.habits.list

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

/**
 * Допустим пользователь находится на странице логина
 * Когда пользователь вводит логин 'user_login'
 *    И пользователь вводит пароль 'user_password'
 * Тогда появляется сообщение ‘Welcome to our site’
 */

class HabitUITest : BehaviorSpec({

    Context("Scenario name") {
        2 + 2 shouldBe 4
        Given("(allowable) [ first initial condition ]") {
            2 + 2 shouldBe 4
            When("(When) [ event initiator ]") {
                2 + 2 shouldBe 4
                When("(When) [ second event initiator ]") {
                    2 + 2 shouldBe 4
                }
                And("(And) [ second initial condition ]") {
                    2 + 2 shouldBe 4
                }
                Then("result") {
                    2 + 2 shouldBe 4
                }
            }
            And("second initial condition") {
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