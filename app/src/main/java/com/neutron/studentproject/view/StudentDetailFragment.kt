package com.neutron.studentproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neutron.studentproject.databinding.FragmentStudentDetailBinding
import com.neutron.studentproject.model.Student
import com.neutron.studentproject.viewmodel.DetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var student: Student
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            student = it

            binding.txtID.setText(student.id)
            binding.txtName.setText(student.name)
            binding.txtDob.setText(student.dob)
            binding.txtPhone.setText(student.phone)
        })
    }
}