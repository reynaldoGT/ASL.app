package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.MenuTitle
import com.neo.signLanguage.databinding.FragmentGamesBinding

import com.neo.signLanguage.ui.view.activities.composables.CardWithImage
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.games.*
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.AdUtils
import com.neo.signLanguage.utils.GamesUtils
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.getMenuGamesActivities


class GamesMenuFragment : Fragment() {

  private var _binding: FragmentGamesBinding? = null
  private val binding get() = _binding!!

  private val gameViewModel: GameViewModel by viewModels()
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentGamesBinding.inflate(inflater, container, false)
    gameViewModel.getRandomToFindLetter(3)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    //! toolbar important to show menu
    (activity as AppCompatActivity?)!!.setSupportActionBar(binding.gamesToolbar)

    MobileAds.initialize(requireContext())


    binding.fragmentGamesComposeView.setContent {
      MyMaterialTheme(
        content = {
          GamesMenuContent()
        }
      )
    }
  }

  @Composable
  fun GamesMenuContent() {
    val getMenuTitles = getMenuGamesActivities(requireContext())

    Box(
      modifier = Modifier
        .fillMaxSize()
        .fillMaxHeight()
        .padding(bottom = 64.dp)
    ) {
      Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxSize()
          .fillMaxHeight()
      ) {
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          contentPadding = PaddingValues(16.dp),

          ) {

          items(getMenuTitles.size) { index ->
            CardWithImage(
              title = getMenuTitles[index].title,
              subtitle = getMenuTitles[index].description,
              image = getMenuTitles[index].img,
              onClick = {
                val intent = Intent(requireContext(), getMenuTitles[index].activity!!::class.java)
                if (getMenuTitles[index].afterActivity != null) {
                  intent.putExtra("activityClass", getMenuTitles[index].afterActivity!!::class.java)
                  intent.putExtra("gameName", getMenuTitles[index].title)
                }
                startActivity(intent)
              }
            )
          }
        }
        BannerAdView()
      }
    }

  }

  @Composable
  fun BannerAdView() {
    val unitId = stringResource(id = R.string.test_banner_id)

    AndroidView(
      factory = { context ->
        AdView(context).apply {
          setAdSize(AdSize.BANNER)
          adUnitId = unitId
          loadAd(AdRequest.Builder().build())
        }
      }
    )
  }
}