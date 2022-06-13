package com.danielsenik.mytv.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.danielsenik.mytv.R
import com.danielsenik.mytv.databinding.FragmentShowBinding
import com.danielsenik.mytv.util.px


class ShowFragment : DialogFragment() {
    private val args by navArgs<ShowFragmentArgs>()

    private lateinit var binding: FragmentShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.closeBn
        ) { v: View, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val mlp = v.layoutParams as MarginLayoutParams
            mlp.topMargin = insets.top + 16.px
            v.layoutParams = mlp
            WindowInsetsCompat.CONSUMED
        }

        binding.closeBn.setOnClickListener { closeFragment() }

        binding.watchBn.setOnClickListener { openShow() }

        binding.apply {
            Glide.with(context!!)
                .load(args.show.image.original)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageIv)
            nameTv.text = args.show.name
            languageTv.text = args.show.language
            genreTv.text = args.show.genres[0]
            ratingTv.text = args.show.rating.average.toString()
            summaryTv.text = Html.fromHtml(args.show.summary)
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    private fun openShow() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.show.url)))
    }
}