    package com.riveong.storyapp.ui.Fragments

    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.riveong.storyapp.Data.Model.StoriesViewModel
    import com.riveong.storyapp.Data.Model.ViewModelFactory
    import com.riveong.storyapp.Data.Repository.Result
    import com.riveong.storyapp.databinding.FragmentStoriesBinding
    import com.riveong.storyapp.ui.Adapter.StoryAdapter


    class StoriesFragment : Fragment() {

        private lateinit var _binding: FragmentStoriesBinding
        private val binding get() = _binding


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            _binding = FragmentStoriesBinding.inflate(layoutInflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
            binding.Dastories.setHasFixedSize(true)
            val viewModel: StoriesViewModel by viewModels {
                factory
            }


            viewModel.getStory().observe(viewLifecycleOwner) { result ->

                if (result != null) {

                    when (result) {
                        is Result.Loading -> {
                            //loading
                            Log.w("loading","loading")
                        }

                        is Result.Success -> {
                            //success
                            val datas = result.data
                            val Jamaldapter = StoryAdapter()
                            Jamaldapter.submitList(datas)
                            val llm = LinearLayoutManager(context)
                            llm.orientation = LinearLayoutManager.VERTICAL
                            _binding.Dastories.setLayoutManager(llm)
                            _binding.Dastories.setAdapter(Jamaldapter)



                        }

                        is Result.Error -> {
                            //error
                            Log.w("error","error")
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT

                            ).show()
                        }
                    }
                }
            }
        }
    }