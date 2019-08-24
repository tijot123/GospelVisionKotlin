package com.smvis123.schedule

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.model.Schedule
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")
class ScheduleAdapter(
    private var scheduleList: List<Schedule>,
    var mActivity: Activity,
    var newDate: String
) :
    RecyclerView.Adapter<ScheduleAdapter.MyViewHolder>() {
    private lateinit var dh: DatabaseHandler

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var switchState = itemView.findViewById<View>(R.id.switch_state) as ToggleButton
        var scheduledItem = itemView.findViewById<View>(R.id.scheduled_item) as TextView
        var scheduledItemTime = itemView.findViewById<View>(R.id.scheduled_item_time) as TextView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_schedule, viewGroup, false)
        dh = DatabaseHandler(viewGroup.context)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    private lateinit var date: String
    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.scheduledItem.text = scheduleList[position].title
        /*dummy testing*/
        /* if (position == scheduleList.count() - 1)
             scheduleList[position].time = "04:50 PM"*/

        myViewHolder.scheduledItemTime.text = scheduleList[position].time
        /**checking current time with the given time*/
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val currentDate = sdf.format(Date())
        val sdp = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val time = sdf.format(sdp.parse(scheduleList[position].time))
        println(" C DATE is  $currentDate  $time")
        /**compare > 0, if date1 is greater than date2
        compare = 0, if date1 is equal to date2
        compare < 0, if date1 is smaller than date2*/
        /*getting today date*/
        val dateCal = Calendar.getInstance()
        val df =
            SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH) // getting date changed to date formate
        date = df.format(dateCal.time)
        if (date == newDate) {
            val compare = currentDate.compareTo(time)
            println(" C DATE is  $compare")
            when {
                compare == 0 -> myViewHolder.switchState.visibility = View.VISIBLE
                compare > 0 -> myViewHolder.switchState.visibility = View.INVISIBLE
                else -> myViewHolder.switchState.visibility = View.VISIBLE
            }
        }

        //getting data from dashboard
        val dataDate: List<String> =
            dh.getData(
                2,
                "Tv_schedule${scheduleList[myViewHolder.adapterPosition].day.toLowerCase(Locale.ENGLISH)}"
            )
        val dataTime: List<String> =
            dh.getData(
                3,
                "Tv_schedule${scheduleList[myViewHolder.adapterPosition].day.toLowerCase(Locale.ENGLISH)}"
            )

        for (q in dataDate.indices) {
            if (newDate == dataDate[q]) {
                for (w in dataTime.indices) {
                    var time = scheduleList[myViewHolder.adapterPosition].time
                    if (time.contains("PM")) {
                        val displayFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                        val parseFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                        val date = parseFormat.parse(time)
                        println(parseFormat.format(date) + " = " + displayFormat.format(date))
                        time = displayFormat.format(date)
                    }
                    if (time == dataTime[w])
                        myViewHolder.switchState.isChecked = true
                }
            }

        }
        myViewHolder.switchState.setOnCheckedChangeListener { _, isChecked ->
            var day = 1
            val weekArray = arrayOf(
                "sun",
                "mon",
                "tue",
                "wed",
                "thu",
                "fri",
                "sat"
            )
            val weekIntArray = arrayOf(
                Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY
            )
            for (r in weekArray.indices) {
                if (scheduleList[myViewHolder.adapterPosition].day.toLowerCase(Locale.ENGLISH) == weekArray[r]) {
                    day = weekIntArray[r]
                }
            }

            val time = scheduleList[myViewHolder.adapterPosition].time
            val hour = TextUtils.split(time, ":")[0].toInt()
            val min = TextUtils.split(time, ":")[1].replace("PM", "")
                .replace("AM", "").trim().toInt()
            val cal = Calendar.getInstance()
            /**
             * Sets the values for the calendar fields <code>YEAR</code>,
             * <code>MONTH</code>, <code>DAY_OF_MONTH</code>,
             * <code>HOUR_OF_DAY</code>, and <code>MINUTE</code>.
             * Previous values of other fields are retained.  If this is not desired,
             * call {@link #clear()} first.
             *
             * @param year the value used to set the <code>YEAR</code> calendar field.
             * @param month the value used to set the <code>MONTH</code> calendar field.
             * Month value is 0-based. e.g., 0 for January.
             * @param date the value used to set the <code>DAY_OF_MONTH</code> calendar field.
             * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field.
             * @param minute the value used to set the <code>MINUTE</code> calendar field.
             * @see #set(int,int)
             * @see #set(int,int,int)
             * @see #set(int,int,int,int,int,int)
             */

            /*Month not needed because api has incorrect month*/
            val year = newDate.split("-")[2].toInt()
            val dayOfMonth = newDate.split("-")[0].toInt()
            val month = newDate.split("-")[1].toInt() - 1
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MINUTE, min)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.DAY_OF_WEEK, day)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            cal.set(Calendar.MONTH, month)

            if (isChecked) {
                setAlarm(cal, myViewHolder.adapterPosition, min)
                dh.addfav(
                    1,
                    newDate,
                    time,
                    null,
                    "Tv_schedule${scheduleList[myViewHolder.adapterPosition].day.toLowerCase(Locale.ENGLISH)}"
                )
                val alertDialogBuilder = AlertDialog.Builder(mActivity, R.style.CustomDialogTheme)
                alertDialogBuilder.setMessage("Program Reminder set at ${scheduleList[myViewHolder.adapterPosition].time} on $newDate")
                    .setPositiveButton("OK") { _, _ ->
                    }
                    .show()
            } else {
                cancelAlarm(myViewHolder.adapterPosition, min)
                dh.delete(
                    time,
                    "Tv_schedule${scheduleList[myViewHolder.adapterPosition].day.toLowerCase(Locale.ENGLISH)}"
                )
                val alertDialogBuilder = AlertDialog.Builder(mActivity, R.style.CustomDialogTheme)
                alertDialogBuilder.setMessage("Program Reminder removed")
                    .setPositiveButton("OK") { _, _ ->
                    }
                    .show()
            }

        }

    }

    private fun setAlarm(cal: Calendar, adapterPosition: Int, min: Int) {
        val textDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.time)
        println("Alarm set at $textDate")
        val alarmMgr = mActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(mActivity, AlarmBroadcastReceiver::class.java)
        broadcastIntent.action = "com.smvis123.ACTION"
        broadcastIntent.putExtra(
            "data",
            scheduleList[adapterPosition].title + " at " + scheduleList[adapterPosition].time
        )
        // The Pending Intent to pass in AlarmManager
        val pIntent = PendingIntent.getBroadcast(
            mActivity,
            min,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val timeInMillis = cal.timeInMillis

        // val currentTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm", Locale.ENGLISH)
        val resultDate = Date(timeInMillis)
        println(sdf.format(resultDate))
        alarmMgr.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pIntent
        )

    }

    private fun cancelAlarm(adapterPosition: Int, min: Int) {
        val alarmMgr = mActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(mActivity, AlarmBroadcastReceiver::class.java)
        broadcastIntent.putExtra("data", scheduleList[adapterPosition].title)
        // The Pending Intent to pass in AlarmManager
        val pIntent = PendingIntent.getBroadcast(mActivity, min, broadcastIntent, 0)
        /* alarmMgr.set(
             AlarmManager.RTC_WAKEUP,
             cal.timeInMillis,
             pIntent
         )*/
        // Cancel the alarm associated with that PendingIntent
        alarmMgr.cancel(pIntent)
    }

}