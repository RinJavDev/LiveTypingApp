package com.rinat.livetypingapp.router


import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.data.BookPreview
import com.rinat.livetypingapp.ui.MainActivity
import com.rinat.livetypingapp.utils.Constants
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

    fun navigateToDetailBookInfo(bookPreview: BookPreview, extras: FragmentNavigator.Extras) {
        val bundle = Bundle()
        bundle.putString(Constants.BOOK_TITLE_ARG, bookPreview.name)
        bundle.putString(Constants.BOOK_AUTHOR_ARG, bookPreview.author)
        bundle.putString(Constants.IMAGE_URL_ARG, bookPreview.imageUrl)
        bundle.putString(Constants.INFO_ARG, bookPreview.info)
        controller?.navigate(R.id.action_fragmentBooks_to_fragmentDetailBook, bundle, null, extras)
    }


}