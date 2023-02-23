package com.neo.signLanguage.ui.view.activities


import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable


import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.orhanobut.logger.Logger
import java.util.*
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.request.onAnimationStart
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.utils.AdUtils


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

    binding.composeView.setContent {
      MyMaterialTheme(
        content = {
          ContainerLayout(imageInt, letter!!, imageURL)
        }
      )


    }

    /* val circularProgressDrawable = CircularProgressDrawable(this)
     circularProgressDrawable.strokeWidth = 5f
     circularProgressDrawable.centerRadius = 30f
     circularProgressDrawable.start()*/

    /*binding.imageTitle.text = letter
    try {
      if (imageURL?.contains("http") == true){
        Glide.with(this)
          .asGif()
          .load(imageURL)
          .placeholder(R.drawable.ic_2_number)
          .error(R.drawable.ic_x_letter)
          .fitCenter()
          .into(binding.image)

        binding.detailToolbar.title = letter
        binding.imageTitle.visibility = View.GONE
      } else {
        binding.image.setImageResource(intent.getIntExtra("image", R.drawable.circle_shape))
      }
    } catch (error: Error) {
      Logger.d(error)
    }

    if (sharedPrefs.getColor() != 0)
      binding.image.setColorFilter(
        sharedPrefs.getColorShared(this)
      )*/
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
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
        )
        if (url === null)
          Text(
            text = letter,
            modifier = Modifier,
            /*.fillMaxWidth()
            .fillMaxHeight(),*/
            /*textAlign = TextAlign.Center,*/
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
          )
      }
    }

  }
}
