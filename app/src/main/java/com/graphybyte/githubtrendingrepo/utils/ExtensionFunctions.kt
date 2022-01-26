package com.graphybyte.githubtrendingrepo.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ShareCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.graphybyte.githubtrendingrepo.R
import com.graphybyte.githubtrendingrepo.utils.AppConstants.C
import com.graphybyte.githubtrendingrepo.utils.AppConstants.DART
import com.graphybyte.githubtrendingrepo.utils.AppConstants.DATE_TIME_FORMAT
import com.graphybyte.githubtrendingrepo.utils.AppConstants.HTML
import com.graphybyte.githubtrendingrepo.utils.AppConstants.JAVA
import com.graphybyte.githubtrendingrepo.utils.AppConstants.KOTLIN
import com.graphybyte.githubtrendingrepo.utils.AppConstants.RUBY
import com.graphybyte.githubtrendingrepo.utils.AppConstants.TYPESCRIPT
import com.tapadoo.alerter.Alerter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.removeObservers(this)
    liveData.observe(this, observer)
}

inline fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            observer.invoke(t)
            removeObserver(this)
        }
    })
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Activity.openBrowser(
    url: String?
) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this.startActivity(i)
}

fun <T> Context.clearTaskAndOpenActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}


fun Activity.errorSnackBar(message: String, callback: ((Boolean) -> Unit)? = null) {
    Alerter.create(this)
        .setText(message)
        .setBackgroundColorInt(Color.parseColor("#87cc6c"))
        .setDuration(1500)
        .setOnHideListener {
            callback?.invoke(true)
        }
        .show()
}

fun Activity.successSnackBar(message: String, callback: ((Boolean) -> Unit)?) {
    Alerter.create(this)
        .setText(message)
        .setBackgroundColorInt(Color.parseColor("#87cc6c"))
        .setDuration(1500)
        .setOnHideListener {
            callback?.invoke(true)
        }
        .show()
}

fun Context.getDate(dateString: String?): String? {
    return try {
        val format1 = SimpleDateFormat(DATE_TIME_FORMAT)
        val date = format1.parse(dateString)
        val sdf: DateFormat = SimpleDateFormat("MMM d, yyyy")
        sdf.format(date)
    } catch (ex: Exception) {
        ex.printStackTrace()
        "xx"
    }
}

fun Context.getTime(dateString: String?): String? {
    return try {
        val format1 = SimpleDateFormat(DATE_TIME_FORMAT)
        val date = format1.parse(dateString)
        val sdf: DateFormat = SimpleDateFormat("h:mm a")
        sdf.format(date)
    } catch (ex: Exception) {
        ex.printStackTrace()
        "xx"
    }
}

fun Context.getColor(language: String): Int {
    return when (language.lowercase(Locale.getDefault())) {
        DART.lowercase() -> R.color.color_blue
        C.lowercase() -> R.color.colorAccent
        TYPESCRIPT.lowercase() -> R.color.color_orange
        KOTLIN.lowercase() -> R.color.color_yellow
        JAVA.lowercase() -> R.color.item_color_forks
        RUBY.lowercase() -> R.color.item_color_title
        HTML.lowercase() -> R.color.bg_ripple
        else -> R.color.colorAccent
    }
}

fun Activity.hideStatusBar() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val attrib = this.window.attributes
        attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}


fun Activity.shareUrl(url: String?) {
    ShareCompat.IntentBuilder
        .from(this)
        .setType("text/plain")
        .setChooserTitle("Share URL")
        .setText(url)
        .startChooser()
}