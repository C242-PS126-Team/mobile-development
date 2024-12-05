package com.example.truecolors.ui.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.truecolors.data.test.TestConstant
import com.example.truecolors.databinding.FragmentColorBlindTestBinding
import com.example.truecolors.ui.MainActivity

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

        val actionBar = (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = "Color Blind Test"
            subtitle = null
            setDisplayHomeAsUpEnabled(true)
        }

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