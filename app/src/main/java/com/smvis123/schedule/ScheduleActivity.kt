package com.smvis123.schedule

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityScheduleBinding
import com.smvis123.helper.Utils
import com.smvis123.model.Schedule
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : BaseActivity(), View.OnClickListener {
    private lateinit var scheduleAdapter: ScheduleAdapter
    override fun onClick(view: View?) {
        println("Button tag=" + view?.tag)
        if (Utils.isNetworkAvailable(this)) {
            var i = Integer.parseInt(view?.tag.toString())   //getting next day position
            i -= 1
            for (j in dateArray.indices) {
                // btn[j].setBackgroundResource(R.drawable.bg_cell_alarm)
                btn[j].background =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_cell_alarm

                    )
                btn[j].setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            // make it tomorrow
            val dateCal = Calendar.getInstance()
            dateCal.add(Calendar.DAY_OF_MONTH, i)
            val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val formattedDate = df.format(dateCal.time)
            /*setting data of the day*/
            setSchedule(btn[i].text.toString().toLowerCase(Locale.ENGLISH), formattedDate)
            btn[i].setBackgroundResource(R.drawable.bg_cell_alarm_selected)
            btn[i].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else Utils.showToast(getString(R.string.network_connection), this)
    }

    private fun initFunction() {
        try {
            if (Utils.isNetworkAvailable(this)) {
                binding.h1.visibility = View.VISIBLE
                try {
                    /*getting today date*/
                    val outFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
                    val dayOfTheWeek =
                        outFormat.format(Date())   //getting today day in phone calender
                    val dateCal = Calendar.getInstance()
                    val df = SimpleDateFormat(
                        "dd-MM-yyyy",
                        Locale.ENGLISH
                    ) // getting date changed to date formate
                    val date =
                        df.format(dateCal.time)           //today date saved in to String : date
                    /*setting data of the current day*/
                    setSchedule(dayOfTheWeek.toLowerCase(Locale.ENGLISH), date)

                    /*setting values to buttons*/
                    var i = 0
                    for (str in dateArray) {
                        if (str.equals(dayOfTheWeek, ignoreCase = true)) {
                            break
                        } else i++
                    }
                    for (j in btnID.indices) {
                        btn[j] = findViewById(btnID[j])
                        btn[j].text = dateArray[i]
                        i++
                        if (i == 7)
                            i = 0
                    }
                    btn[0].setBackgroundResource(R.drawable.bg_cell_alarm_selected)
                    btn[0].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else Utils.showToast(getString(R.string.network_connection), this)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private lateinit var btn: Array<Button>
    var dateArray = arrayOf("mon", "tue", "wed", "thu", "fri", "sat", "sun") //days
    var btnID =
        intArrayOf(R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7)
    private var monList: List<Schedule> = ArrayList()
    private var tueList: List<Schedule> = ArrayList()
    private var wedList: List<Schedule> = ArrayList()
    private var thrList: List<Schedule> = ArrayList()
    private var friList: List<Schedule> = ArrayList()
    private var satList: List<Schedule> = ArrayList()
    private var sunList: List<Schedule> = ArrayList()
    private lateinit var binding: ActivityScheduleBinding
    private lateinit var viewModel: ScheduleActivityViewModel
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule)
        viewModel = ViewModelProvider(this).get(ScheduleActivityViewModel::class.java)
        setUpActionBar(binding.toolbar, getString(R.string.tv_schedule), binding.toolbarTitle)
        binding.h1.visibility = View.GONE
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        for (i in btnID.indices) {
            findViewById<Button>(btnID[i]).setOnClickListener(this)
        }
        btn = Array(dateArray.size) { Button(this) }
        if (Utils.isNetworkAvailable(this)) {
            fetchSchedule()
        } else {
            Utils.showToast(getString(R.string.network_connection), this)
            hideProgressView()
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.scheduleList.observe(this, androidx.lifecycle.Observer {
            monList = it.monList
            tueList = it.tueList
            wedList = it.wedList
            thrList = it.thuList
            friList = it.friList
            satList = it.satList
            sunList = it.sunList
            initFunction()
        })
        viewModel.isApiSuccess.observe(this, androidx.lifecycle.Observer {
            if (!it) Utils.showToast(getString(R.string.network_connection), this)
            hideProgressView()
        })
    }

    private fun fetchSchedule() {
        viewModel.getScheduleList(params)
        showProgressView()
    }

    fun dayClickFn(view: View) {
        if (Utils.isNetworkAvailable(this)) {
            var i = Integer.parseInt(view.tag.toString())   //getting next day position
            i -= 1
            for (j in dateArray.indices) {
                // btn[j].setBackgroundResource(R.drawable.bg_cell_alarm)
                btn[j].setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                btn[j].setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            // make it tomorrow
            val dateCal = Calendar.getInstance()
            dateCal.add(Calendar.DAY_OF_MONTH, i)
            val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val formattedDate = df.format(dateCal.time)
            /*setting data of the day*/
            setSchedule(btn[i].text.toString().toLowerCase(Locale.ENGLISH), formattedDate)
            btn[i].setBackgroundResource(R.drawable.bg_cell_alarm_selected)
            btn[i].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else Utils.showSnackView(getString(R.string.network_connection), binding.root)
    }

    private fun setSchedule(dayOfTheWeek: String, date: String) {
        try {
            when (dayOfTheWeek) {
                dateArray[0] -> {
                    scheduleAdapter = ScheduleAdapter(monList, this, date)
                    if (monList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[1] -> {
                    scheduleAdapter = ScheduleAdapter(tueList, this, date)
                    if (tueList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[2] -> {
                    scheduleAdapter = ScheduleAdapter(wedList, this, date)
                    if (wedList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[3] -> {
                    scheduleAdapter = ScheduleAdapter(thrList, this, date)
                    if (thrList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[4] -> {
                    scheduleAdapter = ScheduleAdapter(friList, this, date)
                    if (friList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[5] -> {
                    scheduleAdapter = ScheduleAdapter(satList, this, date)
                    if (satList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
                dateArray[6] -> {
                    scheduleAdapter = ScheduleAdapter(sunList, this, date)
                    if (sunList.isEmpty())
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                }
            }
            binding.recyclerView.adapter = scheduleAdapter
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}
