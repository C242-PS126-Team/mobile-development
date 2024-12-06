package com.example.truecolors.ui.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.truecolors.R
import com.example.truecolors.data.test.TestConstant
import com.example.truecolors.databinding.FragmentColorBlindTestBinding

class ColorBlindTestFragment : Fragment() {

    private var _binding: FragmentColorBlindTestBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentColorBlindTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customAppBar.appBarTitle.text = getString(R.string.color_blind_test)
        binding.customAppBar.appBarSubtitle.visibility = View.GONE

        binding.btnStart.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter Your Name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), TestQuestionActivity::class.java)
                intent.putExtra(TestConstant.USER_NAME, binding.etName.text.toString())
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}