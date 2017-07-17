package com.github.kamimoo.kotlinarchtecture.ui

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthorizationCallbackActivityTest {

    @get:Rule
    val activityRule: ActivityTestRule<AuthorizationCallbackActivity> =
        ActivityTestRule(AuthorizationCallbackActivity::class.java, false, false)

    @Test
    fun launchActivityByCustomUri() {
        init()
        activityRule.launchActivity(Intent(Intent.ACTION_VIEW, Uri.parse("com.github.kamimoo.kotlinarchitecture://foo?code=bar")))
        intended(hasComponent(AuthorizationCallbackActivity::class.java.name))
        intended(hasComponent(AuthorizationActivity::class.java.name))
        release()
        assertTrue(activityRule.activity.isFinishing)
    }
}
