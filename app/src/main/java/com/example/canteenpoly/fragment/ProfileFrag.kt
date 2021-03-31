package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.model.Canteen
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var canteen: Canteen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view  = inflater.inflate(R.layout.fragment_profile, container, false)
        view.textView15.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        intnitView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController();
        // We use a String here, but any type that can be put in a Bundle is supported
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner) { result ->
            canteen.avatar = result
            Glide.with(view).load(result).into(view.img_avatar)
        }
    }

    private fun intnitView(view: View) {
        view.textView16.text = "Profile"
        view.textView17.text = "Lưu"

        var name = view.textView6.text.toString()
        var phone = view.edt_phone.text.toString()
        var nameCan = view.edt_CanName.text.toString()
        var address = view.edt_address.text.toString()



                canteen = Canteen("",name,phone,nameCan,address,"sfsdfsdfsdfsdfsdfsdf")
        //getall image in device
        view.img_avatar.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFrag_to_listImgFrag) }
        view.textView17.setOnClickListener { updateFireBase() }
    }

    private fun updateFireBase() {
        Log.i("TAG", "updateFireBase: "+canteen.avatar)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}