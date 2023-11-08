package com.testbird.pressureblack

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.testbird.pressureblack.ui.activity.LeadActivity
import com.testbird.pressureblack.util.SharedHelper

class LifecycleHelper : Application.ActivityLifecycleCallbacks {
    private var runningActivityNumber = 0
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (runningActivityNumber == 0
            && System.currentTimeMillis() - SharedHelper.activityInBackTime > 5000
            && (activity is LeadActivity).not()
        ) {
            appContext.startActivity(Intent(appContext, LeadActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
        runningActivityNumber++

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        runningActivityNumber--
        if (runningActivityNumber == 0) {
            SharedHelper.activityInBackTime = System.currentTimeMillis()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}