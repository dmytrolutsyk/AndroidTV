package fr.supinternet.androidtv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import fr.supinternet.androidtv.R
import fr.supinternet.androidtv.data.network.NetworkManager
import fr.supinternet.androidtv.data.network.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class MainFragment : BrowseSupportFragment() {

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = fragmentAdapter

        val boxOfficeAdapter = ArrayObjectAdapter(PresenterHolder())
        val anticipateOfficeAdapter = ArrayObjectAdapter(PresenterHolder())
        fragmentAdapter.add(ListRow(HeaderItem(0 , "Les sorties"), boxOfficeAdapter))
        fragmentAdapter.add(ListRow(HeaderItem(1 , "Les plus attendus"), anticipateOfficeAdapter))

        lifecycleScope.launch(Dispatchers.IO) {
            val boxOffices = NetworkManager.getBoxOffice()
            val anticipateBoxOffice =NetworkManager.getAnticipatedMovies()
            launch(Dispatchers.Main) {

                val boxOffice = async(Dispatchers.IO) {
                    NetworkManager.getBoxOffice()
                }
                val anticipatedMovies = async(Dispatchers.IO) {
                    NetworkManager.getAnticipatedMovies()
                }

                boxOffice.await().forEach { movie ->
                    boxOfficeAdapter.add(movie)
                }

                anticipatedMovies.await().apply {
                    forEach { movie ->
                        anticipateOfficeAdapter.add(movie)
                    }
                }
            }



            startEntranceTransition()
        }

    }

    override fun startEntranceTransition() {
        super.startEntranceTransition()
    }

}

