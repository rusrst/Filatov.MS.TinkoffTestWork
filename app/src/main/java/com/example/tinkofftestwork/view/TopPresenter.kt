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
import com.example.tinkofftestwork.databinding.TopPresenterBinding

private const val MESSAGE_DOWNLOAD_TOP = 2
private const val str = "top/"
private const val start = "https://developerslife.ru/"
private const val end = "?json=true"
class TopPresenter: BaseFragment() {
    private lateinit var binding: TopPresenterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TopPresenterBinding.inflate(inflater, container, false)
        initializeValue(binding.imageViewTop, binding.textViewTop, binding.imageButtonTop, binding.imageButton2Top)
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
        data = viewModel.topData

        messageDownload = MESSAGE_DOWNLOAD_TOP
        dataLiveData = viewModel.topDataLiveData
        category = str
        startUrl = start
        endUrl = end
    }

}