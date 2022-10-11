package com.example.testapplication

import android.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.testapplication.databinding.FragmentPhrasesBinding
import java.io.BufferedReader
import kotlin.random.Random
import kotlin.streams.toList


class PhrasesFragment: Fragment() {
    private lateinit var binding: FragmentPhrasesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhrasesBinding.inflate(inflater, container, false)
        val uri: Uri = Uri.parse("https://rr2---sn-045oxu-045s.googlevideo.com/videoplayback?expire=1665510276&ei=JFdFY6b7A9ivyQWWmr6gBw&ip=176.67.86.137&id=o-AAVzZHN41ah6zpK1IIXb1sswsqCKRlhgTHQN6mN0rLwm&itag=247&aitags=133%2C134%2C135%2C136%2C160%2C242%2C243%2C244%2C247%2C278%2C394%2C395%2C396%2C397%2C398&source=youtube&requiressl=yes&spc=yR2vp_xSQXZZJrvxbUalhXZcUWJiu_E&vprv=1&mime=video%2Fwebm&ns=hh8wTXgxi7ScDaEHkaDXTQsI&gir=yes&clen=18156591&dur=170.749&lmt=1542361994590904&keepalive=yes&fexp=24001373,24007246&c=WEB&txp=5432432&n=g1drHuUEiX_u3A&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRQIgXXeaHMwr8BlqllXN0Z-WuU7HoB3kwrBVz8WFb5v4saoCIQC8cmtwD3xATGG9RHhZy_-ejr9VBZbnk4lI_DVjN9eT4A%3D%3D&redirect_counter=1&rm=sn-f5fy7s&req_id=5109738392a9a3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=Vo&mip=145.255.11.39&mm=31&mn=sn-045oxu-045s&ms=au&mt=1665488502&mv=m&mvi=2&pl=25&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgWXBDKdFSBtq7PBqHXZxr66kcJPAC4YRo9KSIXqoe--wCIQCLyrjTWX6Jzlw7vYm6RTnbYNRr9zIRhkbqEhs1iJCyvg%3D%3D")
        //val uri: Uri = Uri.parse("https://rr3---sn-045oxu-045d.googlevideo.com/videoplayback?expire=1665496723&ei=MyJFY_bDCLuClu8PtO6NsAs&ip=216.131.77.135&id=o-AJW-VtmxmwLgu9Ms0TNnV_AwwWbSF2D-JfwNfPohbFjC&itag=247&aitags=133%2C134%2C135%2C136%2C160%2C242%2C243%2C244%2C247%2C278&source=youtube&requiressl=yes&spc=yR2vp8Tiy2JuqmU6beHEDWUHzLX83sA&vprv=1&mime=video%2Fwebm&ns=6Z1kgruEi2k42kMJ93CaCtUI&gir=yes&clen=22304682&dur=131.757&lmt=1495467305922028&keepalive=yes&fexp=24001373,24007246&c=WEB&n=y57GZi4Fd2fxfQ&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAKFb7gDgGmNZsQp60l561DztLDw9BOgyRx3GrxH0-OkQAiEA2QF9eosNWBP6zzBR0HERZSy9MDTl8OsKPgfYobi5SbM%3D&redirect_counter=1&rm=sn-vgqez676&req_id=6987fccb6f1fa3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=sC&mip=145.255.11.39&mm=31&mn=sn-045oxu-045d&ms=au&mt=1665483699&mv=m&mvi=3&pl=25&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgA1GKPQixxCTFDzRrhbwhs6RNDyinSqazGFG11c2tYDsCIQDBqctSWnAXWyHfSEzv7--LaqByWhkzU9deTOYwQDgGdg%3D%3D")
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
                val n = Random.nextInt(0, lines.size)
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
        fun newInstance() = PhrasesFragment()
    }
}