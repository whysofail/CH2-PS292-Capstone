package com.dicoding.lawmate.ui.lawbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.FragmentResponseLawBotBinding
import com.dicoding.lawmate.ui.lawyer.LawyerFragment

class ResponseLawBotFragment : Fragment() {
    private var _binding: FragmentResponseLawBotBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val konsulViewModel =
            ViewModelProvider(this).get(LawBotViewModel::class.java)
        val responseChat = arguments?.getString("responseChat")
        val responseTag = arguments?.getString("responseTag")


        _binding = FragmentResponseLawBotBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.tvResponse.text = responseChat
        binding.tvRekomendasiTag.text = "kami merekomendasikan advokat dengan spesialisasi '$responseTag'"

        binding.btnDescBack.setOnClickListener {
            val fragmentTujuan = LawBotFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragmentTujuan)
            transaction.commit()
        }
        binding.btnRekomendasi.setOnClickListener {
            val fragmentTujuan = LawyerFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragmentTujuan)
            transaction.commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}