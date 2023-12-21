package com.dicoding.lawmate.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.lawmate.MainActivity
import com.dicoding.lawmate.R
import com.dicoding.lawmate.databinding.FragmentProfileBinding
import com.dicoding.lawmate.ui.authentication.login.LoginActivity
import com.dicoding.lawmate.ui.home.DetailArticleActivity
import com.dicoding.mystoryapp.preference.UserPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Declare UserPref
    private lateinit var userPref: UserPref

    private lateinit var viewModel:ProfileViewModel

    private lateinit var userFirstName:String
    private lateinit var userLastName:String
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var photo:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.edit.setOnClickListener{
            val intent = Intent(requireContext(), Edit_User_Activity::class.java)
            intent.putExtra("firstName", userFirstName)
            intent.putExtra("lastName", userLastName)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("photo", photo)

            startActivity(intent)
        }

        lifecycleScope.launch {
            // Initialize UserPref
            userPref = UserPref(requireContext())
            if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                val token = userPref.getUser().accessToken
                token?.let { viewModel.getUser(it) }
            }

            viewModel.user.observe(viewLifecycleOwner){
                binding.fullName.text = "${it.firstName} ${it.lastName}"
                binding.email.text = it.email

                userFirstName = it.firstName.toString()
                userLastName = it.lastName.toString()
                email = it.email.toString()
                password = it.password.toString()

                photo = "https://i0.wp.com/digitalhealthskills.com/wp-content/uploads/2022/11/3da39-no-user-image-icon-27.png?fit=500%2C500&ssl=1"
                if (it.profilePicture != null){
                    photo = it.profilePicture.toString()
                }

                Glide.with(binding.root)
                    .load(photo)
                    .into(binding.ivUser)
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            // Initialize UserPref
            userPref = UserPref(requireContext())
            if (userPref.preference.contains(UserPref.ACCESSTOKEN)) {
                val token = userPref.getUser().accessToken
                token?.let { viewModel.getUser(it) }
            }

            viewModel.user.observe(viewLifecycleOwner){
                binding.fullName.text = "${it.firstName} ${it.lastName}"
                binding.email.text = it.email

                userFirstName = it.firstName.toString()
                userLastName = it.lastName.toString()
                email = it.email.toString()
                password = it.password.toString()

                photo = "https://i0.wp.com/digitalhealthskills.com/wp-content/uploads/2022/11/3da39-no-user-image-icon-27.png?fit=500%2C500&ssl=1"
                if (it.profilePicture != null){
                    photo = it.profilePicture.toString()
                }

                Glide.with(binding.root)
                    .load(photo)
                    .into(binding.ivUser)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}