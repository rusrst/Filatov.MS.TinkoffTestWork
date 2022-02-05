package com.example.tinkofftestwork.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.tinkofftestwork.data.DataClass
import com.example.tinkofftestwork.databinding.LatestPresenterBinding


private const val MESSAGE_DOWNLOAD_LATEST = 0
private const val str = "latest/"
private const val start = "https://developerslife.ru/"
private const val end = "?json=true"
class LatestPresenter: BaseFragment(){
    private lateinit var binding: LatestPresenterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LatestPresenterBinding.inflate(inflater, container, false)
        initializeValue(binding.imageViewLatest, binding.textViewLatest, binding.imageButtonLatest, binding.imageButton2Latest)
        return binding.root
    }

    override fun initializeValue(
        imageView: ImageView,
        descriptor: TextView,
        btUp: ImageButton,
        btDown: ImageButton,
    ) {
        circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.setColorSchemeColors(Color.GRAY)
        circularProgressDrawable.start()

        this.imageView = imageView
        this.descriptor = descriptor
        this.btUp = btUp
        this.btDown = btDown
        data = viewModel.latestData
        simpleClass = viewModel.latestSimpleClass

        messageDownload = MESSAGE_DOWNLOAD_LATEST
        dataLiveData = viewModel.latestDataLiveData
        category = str
        startUrl = start
        endUrl = end
    }


}