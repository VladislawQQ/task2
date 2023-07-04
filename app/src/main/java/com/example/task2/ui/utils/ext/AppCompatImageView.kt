package com.example.task2.ui.utils.ext

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.task2.R

val GLIDE_OPTIONS = RequestOptions()
    .circleCrop()
    .placeholder(R.drawable.ic_user_avatar)
    .error(R.drawable.ic_user_avatar)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

fun AppCompatImageView.setContactPhoto(
    contactPhotoUri: String = "android.resource://com.example.task2/drawable/profile_photo"
) {
    Glide.with(context)
        .load(contactPhotoUri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

//fun AppCompatImageView.setDefaultPhoto() {
//    val contactPhotoUri = resources.getIdentifier("profile_photo", "drawable", context.packageName)
//
//    Glide.with(context)
//        //.load(R.drawable.profile_photo) //todo just R.
//        .load(contactPhotoUri) //todo just R.
//        .apply(GLIDE_OPTIONS)
//        .into(this)
//}