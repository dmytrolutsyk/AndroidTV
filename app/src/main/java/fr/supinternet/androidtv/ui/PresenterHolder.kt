package fr.supinternet.androidtv.ui

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import fr.supinternet.androidtv.data.network.model.Movie

class PresenterHolder: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(ImageCardView(parent?.context))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        // On récupère l'objet de la requête
        val movie = item as Movie

        // On récupère le ViewHolder et l'ImageCardView
        val holder = viewHolder as CardViewHolder
        val img = holder.card

        // TODO Remplir le contenu de la carte à partir de l'objet Movie
        var name = holder.card.titleText
        var description = holder.card.contentText

        var image_url = movie.posterURL

        name = movie.name
        description = movie.overview
        if (image_url != null)
            holder.loadImage(image_url)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        TODO("Not yet implemented")
    }
}