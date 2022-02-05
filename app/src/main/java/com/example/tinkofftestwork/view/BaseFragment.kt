package com.example.tinkofftestwork.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tinkofftestwork.WorkerThread
import com.example.tinkofftestwork.data.DataClass


abstract class BaseFragment: Fragment() {
    abstract fun initializeValue(imageView: ImageView, descriptor: TextView, btUp: ImageButton, btDown: ImageButton)

    protected val viewModel: SharedViewModel by activityViewModels()
    protected lateinit var circularProgressDrawable: CircularProgressDrawable
    protected lateinit var imageView: ImageView
    protected lateinit var descriptor: TextView
    protected lateinit var btUp: ImageButton
    protected lateinit var btDown: ImageButton
    protected var messageDownload: Int = 0
    protected lateinit var dataLiveData: MutableLiveData<List<DataClass>?>


    protected lateinit var  category: String
    protected lateinit var  startUrl: String
    protected lateinit var  endUrl: String
    protected lateinit var  data: MutableList<DataClass>


    private var startDownload = false
    private var currentPageCounter: Int? = null
    private var currentCounter: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (viewModel.workerThread == null){
            viewModel.workerThread = WorkerThread(viewModel.latestDataLiveData, viewModel.latestDataLiveData, viewModel.topDataLiveData)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (currentPageCounter == null){
            currentPageCounter = -1
            viewModel.workerThread!!.returnData(startUrl + category + 0 + endUrl, messageDownload)
            startDownload = true
            setGlide(null)
        }
        if (currentCounter == null || currentCounter == 0){
            btDown.visibility = View.INVISIBLE
        }
        dataLiveData.observe(viewLifecycleOwner){
            observe(it)
        }
        btUp.setOnClickListener{
            listenerUp(it)
        }
        btDown.setOnClickListener{
            listenerDown(it)
        }
    }









    private fun setGlide(url: String?){
        Glide
            .with(requireContext())
            .asGif()
            .placeholder(circularProgressDrawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(15))
            .load(url ?: "Error")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .error(ColorDrawable(Color.RED))
            .fallback(ColorDrawable(Color.BLUE))
            .into(imageView)
    }

    private fun listenerUp (view: View){
        if (currentCounter != null) {
            if (currentCounter!! + 1 < data.size) {
                currentCounter = currentCounter!! + 1
                btDown.visibility = View.VISIBLE
                descriptor.text = data[currentCounter!!].descriptor
                setGlide(data[currentCounter!!].url)
            }
            else {
                if (!startDownload) {
                    startDownload = true
                    viewModel.workerThread!!.returnData(
                        startUrl + category + (currentPageCounter!! + 1) + endUrl,
                        messageDownload
                    )
                }
            }
        }
        else{
            if (!startDownload) {
                startDownload = true
                viewModel.workerThread!!.returnData(
                    startUrl + category + 0 + endUrl,
                    messageDownload
                )
                dataLiveData.value = null
            }
        }
    }
    private fun listenerDown(view: View){
        currentCounter = currentCounter!! - 1
        if (currentCounter == 0){
            btDown.visibility = View.INVISIBLE
        }
        descriptor.text = data[currentCounter!!].descriptor
        setGlide(data[currentCounter!!].url)
    }
    private fun observe (list: List<DataClass>?){
        startDownload = false
        if (list != null && list.isNotEmpty()){
            startDownload = false
            if (currentCounter == null) currentCounter = -1
            currentCounter =
                currentCounter!! + 1
            currentPageCounter = currentPageCounter!! + 1
            list.forEach { item->
                data.add(item)
            }
            if(currentCounter != null){
                descriptor.text = data[currentCounter!!].descriptor
            }
            if (currentCounter == null)
                currentCounter = 0
            descriptor.text = data[currentCounter!!].descriptor
            setGlide(data[currentCounter!!].url)
        }
    }
}