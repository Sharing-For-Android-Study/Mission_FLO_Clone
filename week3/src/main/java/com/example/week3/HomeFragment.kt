package com.example.week3

//import androidx.room.jarjarred.org.antlr.v4.codegen.model.Loop
import com.example.week3.databinding.FragmentHomeBinding
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    // 앨범 리스트 생성
    private var albumDatas = ArrayList<Album>() // 공란
    private lateinit var songDB: SongDatabase
//    private val timer = Timer()
//    private val handler = Handler(Loop.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homePannelAlbumImg01Iv.setOnClickListener {
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }

        // AlbumList에 더미 값들 넣어주기
        inputDummyAlbums() // 1. 더미 값들을 생성
        songDB = SongDatabase.getInstance(requireContext())!! // 2. songDB라는 SongDatabase의 객체에 더미 값을 넣어줌
        albumDatas.addAll(songDB.albumDao().getAlbums()) // 3. 실제로 어댑터에서 사용하는 것은 albumDatas이므로, songDB의 앨범 테이블을 ArrayList의 형태로 반환하고, 그것을 albumDatas에 추가

        // albumDatas: AlbumList<Album>에 직접 값 넣어주고
        /**
         * 오늘 발매 음악
         * 앨범을 horizontal하게 리사이클러 뷰로 나타내는 부분
         */
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

/*        albumRVAdapter.setItemClickListener(object: AlbumRVAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                TODO("Not yet implemented")
            }

            override fun onPlayAlbum(album: Album) {
                TODO("Not yet implemented")
            }
        })

*/
        /**
         * Pannel 출력
         */
        val pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addFragment((PannelFragment(R.drawable.img_first_album_default)))
        // home fragment의 adapter에 pannel adapter를 할당
        binding.homePannelBackgroundVp.adapter = pannelAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        return binding.root
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val songs = songDB.albumDao().getAlbums()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert (
            Album(
                1,
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            ))
        songDB.albumDao().insert(
            Album(
                2,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp
            ))
        songDB.albumDao().insert(
            Album(
                3,
                "iScreaM Vol.10: Next Level Remixes",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3
            ))
        songDB.albumDao().insert(
            Album(
                4,
                "Map of the Soul Persona",
                "뮤직 보이 (Music Boy)",
                R.drawable.img_album_exp4,
            ))
        songDB.albumDao().insert(
            Album(
                5,
                "Great!",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5
            ))
    }
}