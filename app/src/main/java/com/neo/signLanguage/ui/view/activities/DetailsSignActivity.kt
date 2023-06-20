package com.neo.signLanguage.ui.view.activities


import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import java.util.*
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.utils.AdUtils
import com.neo.signLanguage.utils.Utils.Companion.getHandColor


class DetailsSignActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailsBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    AdUtils.initLoad(binding.banner)
    val myIntent = intent
    val letter = myIntent.getStringExtra("letter")
    val imageInt = intent.getIntExtra("image", 0)
    var type =
      myIntent.getStringExtra("type")?.capitalize(Locale.ROOT) // will return "SecondKeyValue"
    type = if (type == "Letter") getStringByIdName(this, "letter_title") else getStringByIdName(
      this,
      "number_title"
    )
    val imageURL = intent.getStringExtra("imageUrl")

    binding.composeViewDetailsActivity.setContent {
      MyMaterialTheme(

        content = {
          ContainerLayout(imageInt, letter!!, imageURL)
        }
      )
    }

    binding.detailToolbar.title = "$type $letter"
    this.setSupportActionBar(binding.detailToolbar)
    val actionbar = supportActionBar
    actionbar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  @Composable
  fun ContainerLayout(image: Int, letter: String, url: String?) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
      .components {
        if (SDK_INT >= 28) {
          add(ImageDecoderDecoder.Factory())
        } else {
          add(GifDecoder.Factory())
        }
      }
      .build()
    val painter = rememberAsyncImagePainter(
      ImageRequest.Builder(context).data(url).apply(block = {
      }).build(), imageLoader = imageLoader
    )
    Box(
      contentAlignment = Alignment.Center
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = if (url != null) painter else painterResource(image),
          contentDescription = null,
          colorFilter = if (url != null) null else getHandColor(context),
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
        )
        if (url === null)
          Text(
            text = letter,
            modifier = Modifier,
            style = MaterialTheme.typography.titleLarge,
          )
      }
    }

  }
}
