package com.am.gallery.model

import android.arch.lifecycle.ViewModel
import android.util.Log

class SharedViewModel : ViewModel() {
    private var photos: ArrayList<Photo>

    init {
        photos = arrayListOf(
            Photo(
                url = "https://cdn.theatlantic.com/assets/media/img/photo/2016/10/2016-national-geographic-nature-pho/n02_798135-9007740/main_900.jpg?1475614360",
                description = "A landscape is the visible features of an area of land," +
                        " its landforms, and how they integrate with natural or man-made features."
            ),
            Photo(
                url = "https://cdn.newsapi.com.au/image/v1/a4137cc33d81858e84d57bddf02794f6?width=650",
                description = " A landscape includes the physical elements of geophysically" +
                        " defined landforms such as (ice-capped) mountains, hills, water" +
                        " bodies such as rivers, lakes, ponds and the sea, living elements " +
                        "of land cover including indigenous vegetation, human elements" +
                        " including different forms of land use, buildings, and structures," +
                        " and transitory elements such as lighting and weather conditions."
            ),
            Photo(
                url = "https://images2.alphacoders.com/571/571424.jpg",
                description = "Combining both their physical origins and the cultural overlay " +
                        "of human presence, often created over millennia, landscapes reflect " +
                        "a living synthesis of people and place that is vital to" +
                        " local and national identity."
            ),
            Photo(
                url = "https://i.ytimg.com/vi/_6ebmUiGTso/maxresdefault.jpg",
                description = "The character of a landscape helps define the self-image of" +
                        " the people who inhabit it and a sense of place that differentiates" +
                        " one region from other regions."
            ),
            Photo(url="https://static.wixstatic.com/media/5a7174_b62eb3d1efe54bba97cc7b62cee3ad9b~mv2.jpg/v1/fill/w_630,h_426,al_c,q_80,usm_0.66_1.00_0.01/5a7174_b62eb3d1efe54bba97cc7b62cee3ad9b~mv2.jpg"),
            Photo(url="https://i.wpimg.pl/985x0/m.fotoblogia.pl/best-of-engagement-2018--96b7381,0,0,0,0,3-0,1280-852.jpg"),
            Photo(
                url = "https://goo.gl/32YN2B",
                description = "A landscape is the visible features of an area of land," +
                        " its landforms, and how they integrate with natural or man-made features."
            ),
            Photo(
                url = "https://goo.gl/Wqz4Ev",
                description = " A landscape includes the physical elements of geophysically" +
                        " defined landforms such as (ice-capped) mountains, hills, water" +
                        " bodies such as rivers, lakes, ponds and the sea, living elements " +
                        "of land cover including indigenous vegetation, human elements" +
                        " including different forms of land use, buildings, and structures," +
                        " and transitory elements such as lighting and weather conditions."
            )
        )
    }

    fun sortByRating() {
        photos.sortWith(Comparator { x: Photo, y: Photo ->  (y.rating - x.rating).toInt() })
    }

    fun getPhotos(): ArrayList<Photo> {
        return photos
    }

    fun addPhoto(photo : Photo){
        photos.add(photo)
    }


    fun changeRating(id: Int?, rating: Float) {
        for (p in photos) {
            if (p.getId() == id) {
                p.rating = rating
            }
        }
        sortByRating()
    }

}