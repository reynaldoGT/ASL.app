package com.neo.signLanguage.data.models

import android.app.Activity

class MenuTitle(
  title: String, description: String, img: Int, activity: Activity, afterActivity: Activity? = null
) {
  var title: String = ""
  var description: String = ""
  var img: Int = 0
  var activity: Activity? = null
  var afterActivity: Activity? = null

  init {
    this.title = title
    this.description = description
    this.img = img
    this.activity = activity
    this.afterActivity = afterActivity
  }
}