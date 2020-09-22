package com.gino.myredditclient.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gino.myredditclient.BuildConfig
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator
import io.objectbox.Box
import java.lang.reflect.ParameterizedType
import java.util.*
import java.util.regex.Pattern
import java.util.regex.Pattern.DOTALL


private val urlPattern = Pattern.compile(
        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or DOTALL)

@Suppress("UNCHECKED_CAST")
fun <T> Any.getGenericsClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.first() as Class<T>

fun ViewGroup.inflateView(@LayoutRes id: Int) = LayoutInflater
        .from(context)
        .inflate(id, this, false)

fun trace(arg: Any?, tag: String = "---") = arg
        .takeIf { BuildConfig.DEBUG }
        .toString()
    .chunked(1000)
        .forEach { Log.d(tag, it) }

fun Context.openURL(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (couldResolve(intent)) {
        startActivity(intent)
    }
}

fun Context.couldResolve(intent: Intent) = intent.resolveActivity(packageManager) != null

operator fun SharedPreferences.get(key: String, defValue: String): String = getString(key, defValue)!!
operator fun SharedPreferences.get(key: String, defValue: Int): Int = getInt(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Boolean): Boolean = getBoolean(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Float): Float = getFloat(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Long): Long = getLong(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Set<String>): Set<String> = getStringSet(key, defValue)!!

operator fun SharedPreferences.set(key: String, value: String) = edit().putString(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Int) = edit().putInt(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Boolean) = edit().putBoolean(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Float) = edit().putFloat(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Long) = edit().putLong(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Set<String>) = edit().putStringSet(key, value).apply()

fun <T> Box<T>.replace(values: List<T>) = store.runInTx {
    removeAll()
    put(values)
}

val Date.asDateTimeString get() = DateFormatTransformer.getDateTimeFormat().format(this)

inline fun bundle(withBudnle: Bundle.() -> Unit) = Bundle().apply(withBudnle)

inline fun <reified T : Fragment> Context.instantiateFragment(withBudnle: Bundle.() -> Unit) =
        Fragment.instantiate(this, T::class.java.name, bundle(withBudnle)) as T

fun String.fromHtml() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
} else {
    Html.fromHtml(this)
}

fun String.findUrls(): List<Uri> {
    val out = mutableListOf<Uri>()
    val matcher = urlPattern.matcher(this)
    while (matcher.find()) {
        try {
            out.add(Uri.parse(substring(matcher.start(1), matcher.end())))
        } catch (ignored: Throwable) {

        }
    }
    return out
}

inline fun RequestCreator.into(view: ImageView, crossinline cb: (Exception?) -> Unit) = into(view, object : Callback {
    override fun onSuccess() = cb(null)
    override fun onError(e: Exception) = cb(e)
})