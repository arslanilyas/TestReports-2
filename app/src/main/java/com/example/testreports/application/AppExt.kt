package com.smartserve.pos.Utils.Application


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.testreports.R


import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt



fun String?.getIntOrZero(): Int {
    var value = 0;
    if (this == null) {
        return value
    }
    try {
        value = this.replace(",", "").toInt()
    } catch (e: Exception) {
        value = 0
    }
    return value
}


fun EditText.observe(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}



fun String.ellipsize(maxChar:Int):String{
    return if (this == null || this.length < maxChar) {
        this
    } else this.substring(0, maxChar - 3) + "..."
}

fun String?.require():String
{
    var value=""
    this?.let {
        value=it
    }
    return value
}

fun Int?.require():Int
{

    var value=-1
    this?.let {
        value=it
    }
    return value
}

fun Int?.validate():Boolean
{

    var value=0
    this?.let {
        value=it
    }
    return value==1
}


fun Boolean?.require():Boolean
{
    var value=false
    this?.let {
        value=it
    }
    return value
}

fun Double?.require():Double
{
    var value=0.0
    this?.let {
        value=it
    }
    return value
}

fun Double?.require2Decimals():String
{
    var value=0.0
    this?.let {
        value=it
    }
    return String.format(Locale.ENGLISH, "%.2f", value)
}

fun Float?.require():Float
{
    var value=0f
    this?.let {
        value=it
    }
    return value
}

fun Context.newNavigatorIntent(
    latitude: Double,
    longitude: Double,
    name: String
): Intent? {
    val format =
        "geo:0,0?q=" + java.lang.Double.toString(latitude) + "," + java.lang.Double.toString(
            longitude
        ) + "(" + name + ")"
    val uri: Uri = Uri.parse(format)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    return if (intent.resolveActivity(this.packageManager) != null) {
        intent
    } else null
}

fun Context.newDialerIntent(phone: String): Intent? {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    return if (intent.resolveActivity(this.packageManager) != null) {
        intent
    } else null
}

fun Context.newSendEmailsIntent(
    emails: Array<String>?,
    subject: String?,
    text: String?
): Intent? {
    val mailIntent = Intent(Intent.ACTION_SENDTO)
    mailIntent.type = "message/rfc822"
    mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    mailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
    mailIntent.putExtra(Intent.EXTRA_TEXT, text)
    return if (mailIntent.resolveActivity(this.packageManager) != null) {
        mailIntent
    } else null
}

fun Context.newSendEmailIntent(
    email: String,
    subject: String?,
    text: String?
): Intent? {
    return newSendEmailsIntent(arrayOf(email), subject, text)
}

fun Context.newOpenUrlIntent(url: String?): Intent? {
    val urlIntent = Intent(Intent.ACTION_VIEW)
    urlIntent.data = Uri.parse(url)
    return if (urlIntent.resolveActivity(this.packageManager) != null) {
        urlIntent
    } else null
}

fun Context.newShareFileIntent(
    uri: Uri?,
    mimeType: String?
): Intent? {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareIntent.type = mimeType
    return if (shareIntent.resolveActivity(this.packageManager) != null) {
        shareIntent
    } else null
}


fun Context.newShareTextIntent(text: String?): Intent? {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    shareIntent.type = "text/*"
    return if (shareIntent.resolveActivity(this.packageManager) != null) {
        shareIntent
    } else null
}


fun Activity.openActivity(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
}

fun Activity.openActivityWithExit(clazz: Class<out Activity>) {
    this.startActivity(Intent(this, clazz))
    this.finish()
}


fun Activity.openActivityForResult(clazz: Class<out Activity>, requestCode: Int) {
    startActivityForResult(Intent(this, clazz), requestCode)
}




/*fun Fragment.showAlertDialog(msg: String) {
    var newMessage=msg
    if(newMessage.isEmpty())
    {
        newMessage="Unable to process your request \nPlease try again later !!"
    }
    AlertMessageDialog.newInstance(newMessage).show(
        requireActivity().supportFragmentManager,
        AlertMessageDialog.TAG
    )
}

fun FragmentActivity.showAlertDialog(msg: String) {
    var newMessage=msg
    if(newMessage.isEmpty())
    {
        newMessage="Unable to process your request \nPlease try again later !!"
    }
    AlertMessageDialog.newInstance(newMessage).show(supportFragmentManager, AlertMessageDialog.TAG)
}*/

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.getSimpleName(): String {
    return this.javaClass.simpleName
}

fun Fragment.getColorCustom(color: Int): Int {
    return ContextCompat.getColor(requireContext(), color)
}

fun Date.getOnlyCurrentDate():String
{
    val sdf= SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getDefault());
    return sdf.format(this)
}

fun Date.getCurrentDateTime():String
{
    val simpleDateFormat = SimpleDateFormat("EEEE, dd MMM, yyyy HH:mm:ss a", Locale.ENGLISH)
    simpleDateFormat.setTimeZone(TimeZone.getDefault());
    return simpleDateFormat.format(this)
}

fun String.getDateWithCurrentTimeZone():String
{
    val sdf= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
   // sdf.timeZone = TimeZone.getTimeZone("GMT");
    val date= sdf.parse(this)
    return ""+date?.dateWithCurrentTimeZone()
}

fun String.getDateObject():Date
{
    val sdf= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    // sdf.timeZone = TimeZone.getTimeZone("GMT");
   return sdf.parse(this)
}

fun Date.dateWithCurrentTimeZone():String
{
    val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
   // sdf.timeZone = TimeZone.getDefault();
    return sdf.format(this)
}

fun Fragment.getColorToLoad(id: Int):Int
{
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        requireContext().getColor(id)
    }
    else {
        requireContext().resources.getColor(id)
    }
}

/*fun FragmentActivity.confirmationDialog(msg: String, isConfirm: (Boolean) -> Unit){
    ConfirmationDialog(msg,
        object : ConfirmationDialog.ConfirmationListener {
            override fun isConfirmed(isConfirmed: Boolean) {
                isConfirm.invoke(isConfirmed)
            }
        }).show(this.supportFragmentManager, "")
}*/

infix fun TextView.round(value: Double) {
    this.text = "£" + value.toBigDecimal().setScale(2, RoundingMode.UP).toString()
}

infix fun TextView.round(value: Float) {
    var value = value.toDouble()
    this.text = "" + value.toBigDecimal().setScale(2, RoundingMode.UP).toString()
}


fun Context.tabletSize(): Double {

        // Compute screen size
        val dm: DisplayMetrics = this.resources.displayMetrics
        val screenWidth = dm.widthPixels / dm.xdpi
        val screenHeight = dm.heightPixels / dm.ydpi
        return sqrt(
            screenWidth.toDouble().pow(2.0) +
                    screenHeight.toDouble().pow(2.0)
        )
}


fun Context.color(id: Int):Int
{
    return ContextCompat.getColor(this, id)
}

fun Context.color(colorCode: String):Int
{
    return Color.parseColor(colorCode)
}







/*fun Fragment.pickDate(selectedCalenderDate: Calendar, onPicked: (Date) -> Unit)
{
    val calendar: Calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)
    val datePicker = DatePickerDialog(
        requireActivity(), R.style.DateDialogTheme,
        { p0, yearR, monthR, dayR ->
            val calendarR = Calendar.getInstance()
            calendarR.set(yearR, monthR, dayR)
            onPicked.invoke(calendarR.time)
        }, year, month, day
    )
    datePicker.show()
}*/











