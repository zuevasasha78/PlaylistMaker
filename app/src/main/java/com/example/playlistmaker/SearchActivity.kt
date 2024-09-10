package com.example.playlistmaker

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.network.RetrofitClient.iTunesService
import com.example.playlistmaker.network.data.TrackList
import com.example.playlistmaker.trackview.TrackAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private var savedText: String? = null
    private var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        savedInstanceState?.let {
            val savedText = savedInstanceState.getString(SEARCH_TEXT)
            inputEditText.setText(savedText)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.trackList)

        val adapter = TrackAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val updateButtonView = findViewById<Button>(R.id.updateButton)

        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener { v ->
            inputEditText.setText("")
            setViewVisible(v, false)
            hideKeyboard(v)
            adapter.tracks = emptyList()
            adapter.notifyDataSetChanged()

            hideError(recyclerView)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setViewVisible(clearButton, !s.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {
                savedText = s.toString()
            }
        }
        inputEditText.addTextChangedListener(textWatcher)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchText = textView.text.toString()
                executeSearchRequest(searchText, adapter, recyclerView, updateButtonView)
                true
            }
            false
        }

        updateButtonView.setOnClickListener { view ->
            executeSearchRequest(searchText, adapter, recyclerView, view)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText?.let { outState.putString(SEARCH_TEXT, savedText) }
    }

    private fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun setViewVisible(v: View, visible: Boolean) {
        v.isVisible = visible
    }

    private fun executeSearchRequest(
        searchText: String?,
        adapter: TrackAdapter,
        recyclerView: RecyclerView,
        updateButtonView: View
    ) {
        if (!searchText.isNullOrEmpty()) {
            iTunesService.search(searchText).enqueue(object : Callback<TrackList> {
                override fun onResponse(call: Call<TrackList>, response: Response<TrackList>) {
                    if (response.isSuccessful) {
                        val trackList = response.body() ?: return
                        val tracks = trackList.results ?: return
                        if (tracks.isNotEmpty()) {
                            adapter.tracks = tracks
                            adapter.notifyDataSetChanged()

                            hideError(recyclerView)
                        } else {
                            val text = "Ничего не нашлось"
                            showPlaceholder(recyclerView, R.drawable.empty_list_tracks, text)
                        }
                    } else {
                        internetError(recyclerView, updateButtonView)
                    }
                }

                override fun onFailure(call: Call<TrackList>, t: Throwable) {
                    internetError(recyclerView, updateButtonView)
                }
            })
        }
    }

    private fun hideError(recyclerView: RecyclerView) {
        val imageErrorView = findViewById<ImageView>(R.id.emptyImageView)
        val errorTextView = findViewById<TextView>(R.id.errorText)
        val updateButtonView = findViewById<Button>(R.id.updateButton)
        updateButtonView.isVisible = false
        imageErrorView.isVisible = false
        errorTextView.isVisible = false

        recyclerView.isVisible = true
    }

    private fun internetError(recyclerView: RecyclerView, button: View) {
        val text = "Проблемы со связью\n" +
            "\n" +
            "Загрузка не удалась. Проверьте подключение к интернету"
        showPlaceholder(recyclerView, R.drawable.track_internet_error, text)
        makeButtonRound(button)
        button.isVisible = true
    }

    private fun makeButtonRound(button: View) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE

        val cornerRadiusInPx = resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, resources.displayMetrics
        )
        button.background = drawable
    }

    private fun showPlaceholder(recyclerView: RecyclerView, imageRes: Int, errorText: String) {
        recyclerView.isVisible = false

        val imageErrorView = findViewById<ImageView>(R.id.emptyImageView)
        val errorTextView = findViewById<TextView>(R.id.errorText)

        imageErrorView.isVisible = true
        imageErrorView.setImageResource(imageRes)

        errorTextView.isVisible = true
        errorTextView.text = errorText
    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}
