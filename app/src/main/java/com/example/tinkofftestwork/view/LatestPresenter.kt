package com.example.tinkofftestwork.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tinkofftestwork.WorkerThread
import com.example.tinkofftestwork.data.DataClass
import com.example.tinkofftestwork.databinding.PresenterBinding


private const val MESSAGE_DOWNLOAD_LATEST = 0
private const val str = "latest/"
private const val startUrl = "https://developerslife.ru/"
private const val endUrl = "?json=true"
class LatestPresenter: Fragment() {
    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private lateinit var binding: PresenterBinding
    private val viewModel: SharedViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        Glide.get(context).clearMemory()
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (viewModel.workerThread == null){
            viewModel.workerThread = WorkerThread(viewModel.latestDataLiveData)
        }
        circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.setColorSchemeColors(Color.GRAY)
        circularProgressDrawable.start()

        binding = PresenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (viewModel.currentPageCounterLast == null){
            viewModel.currentPageCounterLast = 0
            viewModel.workerThread!!.returnData(startUrl + str + viewModel.currentPageCounterLast + endUrl, MESSAGE_DOWNLOAD_LATEST)
            viewModel.latestStartDownload = true
            setGlide(null)
        }
        if (viewModel.currentCounterLast == null || viewModel.currentCounterLast == 0){
            binding.imageButton2.visibility = View.INVISIBLE
        }
        viewModel.latestDataLiveData.observe(viewLifecycleOwner){
            observe(it)
        }
        binding.imageButton.setOnClickListener{
            listenerUp(it)
        }
        binding.imageButton2.setOnClickListener{
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
            .into(binding.imageView)
    }

    private fun listenerUp (view: View){
        if (viewModel.currentCounterLast != null) {
            if (viewModel.currentCounterLast!! + 1 < viewModel.latestData.size) {
                viewModel.currentCounterLast = viewModel.currentCounterLast!! + 1
                binding.imageButton2.visibility = View.VISIBLE
                binding.textView.text = viewModel.latestData[viewModel.currentCounterLast!!].descriptor
                setGlide(viewModel.latestData[viewModel.currentCounterLast!!].url)
            }
            else {
                if (!viewModel.latestStartDownload) {
                    viewModel.latestStartDownload = true
                    viewModel.workerThread!!.returnData(
                        startUrl + str + viewModel.currentPageCounterLast!! + 1 + endUrl,
                        MESSAGE_DOWNLOAD_LATEST
                    )
                }
            }
        }
        else{
            if (!viewModel.latestStartDownload) {
                viewModel.latestStartDownload = true
                viewModel.workerThread!!.returnData(
                    startUrl + str + 0 + endUrl,
                    MESSAGE_DOWNLOAD_LATEST
                )
                viewModel.latestDataLiveData.value = null
            }
        }
    }
    private fun listenerDown(view: View){
        viewModel.currentCounterLast = viewModel.currentCounterLast!! - 1
        if (viewModel.currentCounterLast == 0){
            binding.imageButton2.visibility = View.INVISIBLE
        }
        binding.textView.text = viewModel.latestData[viewModel.currentCounterLast!!].descriptor
        setGlide(viewModel.latestData[viewModel.currentCounterLast!!].url)
    }
    private fun observe (list: List<DataClass>?){
        viewModel.latestStartDownload = false
        if (list != null){
            viewModel.latestStartDownload = false
            if (viewModel.currentCounterLast == null) viewModel.currentCounterLast = -1
            viewModel.currentCounterLast = viewModel.currentCounterLast!! + 1
            viewModel.currentPageCounterLast = viewModel.currentPageCounterLast!! + 1
            list.forEach { item->
                viewModel.latestData.add(item)
            }
            if(viewModel.currentCounterLast != null){
                binding.textView.text = viewModel.latestData[viewModel.currentCounterLast!!].descriptor
            }
            if (viewModel.currentCounterLast == null)
                viewModel.currentCounterLast = 0
            binding.textView.text = viewModel.latestData[viewModel.currentCounterLast!!].descriptor
            setGlide(viewModel.latestData[viewModel.currentCounterLast!!].url)
        }
    }
}