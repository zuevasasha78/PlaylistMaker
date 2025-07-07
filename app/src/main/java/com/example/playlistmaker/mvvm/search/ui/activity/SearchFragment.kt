package com.example.playlistmaker.mvvm.search.ui.activity

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.ui.view_model.SearchViewModel
import com.example.playlistmaker.utils.debounce
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private val viewBinding: FragmentSearchBinding get() = _viewBinding!!
    private var _viewBinding: FragmentSearchBinding? = null

    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackAdapter

    private var savedText: String? = null

    private val gson: Gson by inject()

    private lateinit var onTrackClickDebounce: (Track) -> Unit

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTrackListView()
        observeViewModel()
        setupListeners()
        onTrackClickDebounce = debounce<Track>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { track ->
            startAudioPlayer(track)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText?.let { outState.putString(SEARCH_TEXT, savedText) }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun showLoading() {
        viewBinding.apply {
            progressBar.isVisible = true
            trackList.isVisible = false
            updateButton.isVisible = false
            searchHistory.isVisible = false
        }
        hideError()
    }

    private fun startAudioPlayer(track: Track) {
        onTrackClickDebounce(track)
        val trackJson = gson.toJson(track)
        val action =
            SearchFragmentDirections.actionSearchFragmentToAudioPlayerFragment(trackJson)
        findNavController().navigate(action)
    }

    private fun initTrackListView() {
        trackAdapter = TrackAdapter { track ->
            viewModel.updateTrackListHistory(track)
            startAudioPlayer(track)
        }
        trackHistoryAdapter = TrackAdapter { track ->
            startAudioPlayer(track)
        }
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        viewBinding.apply {
            trackList.layoutManager = layoutManager
            trackList.adapter = trackAdapter
            trackListHistory.adapter = trackHistoryAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.searchResultsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SearchState.Success -> {
                    hideLoading()
                    trackAdapter.setItems(result.data)
                    viewBinding.trackList.isVisible = true
                }

                is SearchState.DataError -> {
                    showPlaceholder(R.drawable.empty_list_tracks, result.message)
                }

                is SearchState.NetworkError -> {
                    showError(result.message)
                }

                is SearchState.History -> {
                    if (result.history.isNotEmpty()) {
                        showTrackHistory(result.history)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        viewBinding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    val text = s.toString()
                    viewBinding.clearIcon.isVisible = true
                    showLoading()
                    viewModel.onSearchTextChanged(text)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                savedText = s.toString()
            }
        })

        viewBinding.clearIcon.setOnClickListener { v ->
            viewBinding.inputEditText.text.clear()
            viewBinding.trackList.isVisible = false
            v.isVisible = false
            hideKeyboard()
            hideError()
        }
        viewBinding.updateButton.setOnClickListener {
            showLoading()
            savedText?.let { viewModel.onSearchTextChanged(it) }
        }
        viewBinding.clearHistory.setOnClickListener {
            viewModel.clearHistory()
            viewBinding.searchHistory.isVisible = false
        }
    }

    private fun hideLoading() {
        viewBinding.apply {
            progressBar.isVisible = false
            searchHistory.isVisible = false
        }
    }

    private fun showTrackHistory(trackHistory: List<Track>) {
        trackHistoryAdapter.setItems(trackHistory)
        viewBinding.searchHistory.isVisible = true
    }

    private fun hideError() {
        viewBinding.apply {
            updateButton.isVisible = false
            emptyImageView.isVisible = false
            errorText.isVisible = false
        }
    }

    private fun showError(textError: String) {
        showPlaceholder(R.drawable.track_internet_error, textError)
        makeButtonRound()
        viewBinding.updateButton.isVisible = true
    }

    private fun makeButtonRound() {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE

        val cornerRadiusInPx =
            resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, resources.displayMetrics
        )
        viewBinding.updateButton.background = drawable
    }

    private fun showPlaceholder(imageRes: Int, errorText: String) {
        viewBinding.apply {
            trackList.isVisible = false

            emptyImageView.isVisible = true
            emptyImageView.setImageResource(imageRes)
        }
        viewBinding.errorText.isVisible = true
        viewBinding.errorText.text = errorText
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewBinding.clearIcon.windowToken, 0)

    }
}
