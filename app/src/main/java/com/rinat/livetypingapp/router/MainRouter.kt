package com.rinat.livetypingapp.router


import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.ui.MainActivity
import com.rinat.livetypingapp.util.Constants
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRouter @Inject constructor() {

    private var activity: MainActivity? = null

    private val controller
        get() = activity?.findNavController(R.id.nav_host_fragment_activity_main)

    fun setActivity(mainActivity: MainActivity) {
        this.activity = mainActivity
    }

    fun onBack() {
        controller?.popBackStack()
    }

    fun closeApp() {
        if (controller?.popBackStack() == false) {
            activity?.finish()
        }
    }

    fun navigateToDetailBookInfo(bookModel: BookPreview, extras: FragmentNavigator.Extras) {
        val bundle = Bundle()
        bundle.putString(Constants.BOOK_TITLE_ARG, bookModel.name)
        bundle.putString(Constants.BOOK_AUTHOR_ARG, bookModel.author)
        bundle.putString(Constants.IMAGE_URL_ARG, bookModel.imageUrl)
        controller?.navigate(R.id.action_fragmentBooks_to_fragmentDetailBook, bundle, null, extras)
    }


}