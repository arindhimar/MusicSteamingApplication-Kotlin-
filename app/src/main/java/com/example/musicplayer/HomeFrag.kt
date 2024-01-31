import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import com.example.musicplayer.HistoryQueue
import com.example.musicplayer.MainPlayerActivity
import com.example.musicplayer.MediaPlayerUtils

import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentHomeBinding
import java.lang.Thread.sleep
import kotlin.math.min

class HomeFrag : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var startX = 0f
    private var startY = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var miniPlayerFlag :Int = 0
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        var flag:Int=-1
        // Add the card view dynamically
        val cardLayout1 = inflater.inflate(R.layout.historycard, container, false)
        binding.cardView1.addView(cardLayout1)
        binding.constraintLayout.transitionToState(R.id.bringLeft)
        binding.constraintLayout.transitionToState(R.id.end)
        binding.constraintLayout.transitionToState(R.id.start)
        binding.constraintLayout.transitionToState(R.id.middle)

        var intent:Intent
        val arrSongName = arrayOf("Song1","Song2","Song3","Song4","Song5")
        val arrName = arrayOf("S1","S2","S3","S4","S5")


        val txt=cardLayout1.findViewById<TextView>(R.id.historyCardSongName)
        val txt2=cardLayout1.findViewById<TextView>(R.id.historyCardSongDesc)

        flag=0

        val miniPlayerCard=inflater.inflate(R.layout.playingminimusic,container,false)
        val miniPlayerText = miniPlayerCard.findViewById<TextView>(R.id.miniPlayerTitle)

        val miniPlayerImage = miniPlayerCard.findViewById<ImageView>(R.id.miniPlayerImage)

        val miniPlayerCardPlayPauseBtn=miniPlayerCard.findViewById<Button>(R.id.miniPlayerPlayPauseBtn)
        val miniPlayerCardPrevBtn=miniPlayerCard.findViewById<Button>(R.id.miniPlayerPrevBtn)
        val miniPlayerCardNextBtn=miniPlayerCard.findViewById<Button>(R.id.miniPlayerNextBtn)
        binding.miniPlayer.addView(miniPlayerCard)

//        miniPlayerCardPrevBtn.setOnClickListener {
//            if(binding.miniPlayer.isVisible){
//
//            }
//            else{
//
//            }
//        }
//
        miniPlayerCardPlayPauseBtn.setOnClickListener {



        }

//
//        miniPlayerCardNextBtn.setOnClickListener {
//            if(binding.miniPlayer.isVisible){
//
//            }
//            else{
//
//            }
//        }


        binding.miniPlayer.isVisible = false



        txt.text = arrSongName[flag].toString()
        txt2.text = arrName[flag].toString()

        binding.constraintLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val endY = event.y
                    val dx = endX - startX
                    val dy = endY - startY

                    if (Math.abs(dx) > Math.abs(dy)) {
                        // Horizontal swipe detected
                        if (dx > 0) {
                            flag = (flag - 1 + arrSongName.size) % arrName.size
                            txt.text = arrSongName[flag].toString()
                            txt2.text = arrName[flag].toString()
                            binding.constraintLayout.transitionToState(R.id.bringLeft)
                            binding.constraintLayout.transitionToState(R.id.end)
                            binding.constraintLayout.transitionToState(R.id.start)
                            binding.constraintLayout.transitionToState(R.id.middle)
                            Toast.makeText(requireContext(), "Horizontal swipe right detected", Toast.LENGTH_SHORT).show()
                        } else {
                            flag = (flag + 1) % arrSongName.size
                            txt.text = arrSongName[flag].toString()
                            txt2.text = arrName[flag].toString()
                            binding.constraintLayout.transitionToState(R.id.bringRight)
                            binding.constraintLayout.transitionToState(R.id.start)
                            binding.constraintLayout.transitionToState(R.id.end)
                            binding.constraintLayout.transitionToState(R.id.middle)

                            Toast.makeText(requireContext(), "Horizontal swipe left detected", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Vertical swipe detected
                        if (dy > 0) {
                            intent = Intent(requireContext(), MainPlayerActivity::class.java)
                            intent.putExtra("songname", txt.text)
                            intent.putExtra("songdesc", txt2.text)
                            startActivity(intent)
                            miniPlayerText.text = "${arrSongName[flag]}\n${arrName[flag]}"
                            miniPlayerImage.setImageResource(R.drawable.defaultmusic)

                            if(miniPlayerFlag==0){
                                binding.miniPlayer.isVisible= true
                                miniPlayerFlag=1
                            }

                            Toast.makeText(requireContext(), "Vertical swipe down detected", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Vertical swipe up detected", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            true
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
