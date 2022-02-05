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
import com.example.tinkofftestwork.databinding.HotPresenterBinding
private const val MESSAGE_DOWNLOAD_HOT = 1
private const val str = "hot/"
private const val start = "https://developerslife.ru/"
private const val end = "?json=true"
class HotPresenter: BaseFragment() {
    private lateinit var binding: HotPresenterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HotPresenterBinding.inflate(inflater, container, false)
        initializeValue(binding.imageViewHot, binding.textViewHot, binding.imageButtonHot, binding.imageButton2Hot)
        return binding.root
    }

    override fun initializeValue(
        imageView: ImageView,
        descriptor: TextView,
        btUp: ImageButton,
        btDown: ImageButton
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
        data = viewModel.hotData
        simpleClass = viewModel.hotSimpleClass

        messageDownload = MESSAGE_DOWNLOAD_HOT
        dataLiveData = viewModel.hotDataLiveData
        category = str
        startUrl = start
        endUrl = end
    }

}