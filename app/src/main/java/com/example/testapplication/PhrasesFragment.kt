package com.example.testapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapplication.databinding.FragmentPhrasesBinding
import java.io.BufferedReader
import kotlin.random.Random
import kotlin.streams.toList


class PhrasesFragment: Fragment() {
    private val MY_STR = "my_string"
    private lateinit var binding: FragmentPhrasesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhrasesBinding.inflate(inflater, container, false)
        val path = arguments?.getString(MY_STR, "")
        val uri = Uri.parse(path)
        binding.videoView.setVideoURI(uri)
        binding.videoView.start()
        var reader: BufferedReader
        var lines: MutableList<String> = mutableListOf()
        var linesOutput: MutableList<String> = mutableListOf()
        var position = -1
        binding.imageButton.setOnClickListener{
            if (lines.size == 0)
            {
                position = -1
                reader = context!!.assets.open("Phrases").bufferedReader()
                lines = reader.lines().toList().toMutableList()
                linesOutput = mutableListOf()
            }
            if (position == linesOutput.size - 1) {
                val n = Random(System.nanoTime()).nextInt(0, lines.size)
                linesOutput.add(lines[n])
                lines.removeAt(n)
            }
            position++
            binding.textView.text = linesOutput[position]
        }
        binding.imageButton2.setOnClickListener{
            if (linesOutput.size > 1 && position > 0)
            {
                position--
                binding.textView.text = linesOutput[position]
            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(path: String) = PhrasesFragment().apply {
            arguments = Bundle(1).apply {
                putString(MY_STR, path)
            }
        }
    }
}