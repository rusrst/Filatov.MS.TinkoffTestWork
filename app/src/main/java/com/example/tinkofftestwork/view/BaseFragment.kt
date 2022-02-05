package com.example.tinkofftestwork.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tinkofftestwork.R
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
    protected lateinit var simpleClass: SimpleClass

    override fun onCreate(savedInstanceState: Bundle?) {
        if (viewModel.workerThread == null){
            viewModel.workerThread = WorkerThread(viewModel.latestDataLiveData, viewModel.latestDataLiveData, viewModel.topDataLiveData)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (simpleClass.currentPageCounter == null){
            simpleClass.currentPageCounter = -1
            viewModel.workerThread!!.returnData(startUrl + category + 0 + endUrl, messageDownload)
            simpleClass.startDownload = true
            setGlide(null)
        }
        if (simpleClass.currentCounter == null || simpleClass.currentCounter == 0){
            btDown.visibility = View.INVISIBLE
        }
        dataLiveData.observe(viewLifecycleOwner){
            observe(it)
        }
        btUp.setOnClickListener{
            listenerUp()
        }
        btDown.setOnClickListener{
            listenerDown()
        }
    }






    private val listener = object :RequestListener<GifDrawable>{
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<GifDrawable>?,
            isFirstResource: Boolean
        ): Boolean {
            descriptor.text = getString(R.string.glideError)
            return false        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false        }


    }

    private fun setGlide(url: String?){
        Glide
            .with(requireContext())
            .asGif()
            .placeholder(circularProgressDrawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .load(url ?: "Error")
            .transform(RoundedCorners(15))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(listener)
            .error(R.drawable.errot_img)
            .into(imageView)
    }

    private fun listenerUp() {
        if (simpleClass.currentCounter != null) {
            if (simpleClass.currentCounter!! + 1 < data.size) {
                simpleClass.currentCounter = simpleClass.currentCounter!! + 1
                btDown.visibility = View.VISIBLE
                descriptor.text = data[simpleClass.currentCounter!!].descriptor
                setGlide(data[simpleClass.currentCounter!!].url)
            }
            else {
                if (!simpleClass.startDownload) {
                    simpleClass.startDownload = true
                    viewModel.workerThread!!.returnData(
                        startUrl + category + (simpleClass.currentPageCounter!! + 1) + endUrl,
                        messageDownload
                    )
                }
            }
        }
        else{
            if (!simpleClass.startDownload) {
                simpleClass.startDownload = true
                viewModel.workerThread!!.returnData(
                    startUrl + category + 0 + endUrl,
                    messageDownload
                )
                dataLiveData.value = null
            }
        }
    }
    private fun listenerDown() {
        simpleClass.currentCounter = simpleClass.currentCounter!! - 1
        if (simpleClass.currentCounter == 0){
            btDown.visibility = View.INVISIBLE
        }
        descriptor.text = data[simpleClass.currentCounter!!].descriptor
        setGlide(data[simpleClass.currentCounter!!].url)
    }
    private fun observe (list: List<DataClass>?){
        simpleClass.startDownload = false
        if (list != null && list.isNotEmpty()){
            simpleClass.startDownload = false
            if (simpleClass.currentCounter == null) simpleClass.currentCounter = -1
            simpleClass.currentCounter =
                simpleClass.currentCounter!! + 1
            simpleClass.currentPageCounter = simpleClass.currentPageCounter!! + 1
            list.forEach { item->
                data.add(item)
            }
            if(simpleClass.currentCounter != null){
                descriptor.text = data[simpleClass.currentCounter!!].descriptor
            }
            if (simpleClass.currentCounter == null)
                simpleClass.currentCounter = 0
            descriptor.text = data[simpleClass.currentCounter!!].descriptor
            setGlide(data[simpleClass.currentCounter!!].url)
        }
    }
}