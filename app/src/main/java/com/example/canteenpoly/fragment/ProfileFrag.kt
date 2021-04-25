package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.model.User
import com.example.canteenpoly.repository.CanteenDAO
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var disposable: Disposable

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var user: User
    private lateinit var canteenDAO: CanteenDAO
    lateinit var uid: String

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.textView15.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        intnitView(view)
        Log.i("TAG", "onCreateView: ")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("TAG", "onViewCreated: ")
        val navController = findNavController()
        // We use a String here, but any type that can be put in a Bundle is supported
        val name = view.textView6.text.toString()
        val phone = view.edt_phone.text.toString()
        val nameCan = view.edt_CanName.text.toString()
        val address = view.edt_address.text.toString()
        if(ProductFrag.listProduct == null){
            user = User(HomeFrag.token,uid,"", name, phone, nameCan, address,ArrayList(), ArrayList(),ArrayList())
        }else{
            user = User(HomeFrag.token,uid,"", name, phone, nameCan, address,ArrayList(), ArrayList(),ProductFrag.listProduct)
        }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner
        ) { result ->

            user.avatar = result
            Glide.with(view).load(result).into(view.img_avatar)

        }
    }

    private fun intnitView(view: View) {
        canteenDAO = CanteenDAO()
        view.textView16.text = "Profile"
        view.textView17.text = "UpDate"

        //getUser to update on firebase
        uid = arguments?.getString("uid").toString()

        Log.i("TAG", "intnitView: "+uid)

        //getUser in firebase to View
        getUserRealTime(view)

        //getall image in device
        view.img_avatar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFrag_to_listImgFrag)
        }
        view.textView17.setOnClickListener { updateFireBase() }
        view.button5.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            Navigation.findNavController(view).navigate(R.id.action_profileFrag_to_loginFrag)
        }
    }

    private fun getUserRealTime(view: View) {
        canteenDAO.getUser(uid).observe(viewLifecycleOwner, {
            if (it.avatar == "default" || it.avatar == "") {
            } else Glide.with(view).load(it.avatar).into(view.img_avatar)
            view.textView6.setText(it.nameboss)
            view.edt_phone.setText(it.phone)
            view.edt_CanName.setText(it.nameCanteen)
            view.edt_address.setText(it.address)

        })
//        canteenDAO.getAllProduct(uid).observe(viewLifecycleOwner, {
//            Log.i("TAG", "getUserRealTime: "+it)
//        })

    }

    override fun onResume() {
        super.onResume()
        textView6.addTextChangedListener {
            user.nameboss = it.toString()
        }
        edt_phone.addTextChangedListener {
            user.phone = it.toString()
        }
        edt_CanName.addTextChangedListener {
            user.nameCanteen = it.toString()
        }
        edt_address.addTextChangedListener {
            user.address = it.toString()
        }

    }



    private fun updateFireBase() {

        Log.i("TAG", "updateFireBase: " + user.nameboss)
        canteenDAO.upDateuser(user, uid)
        Toast.makeText(context,"Update Success",Toast.LENGTH_LONG).show()
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