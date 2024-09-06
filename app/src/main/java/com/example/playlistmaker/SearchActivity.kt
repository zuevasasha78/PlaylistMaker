package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
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

        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener { v ->
            inputEditText.setText("")
            setViewVisible(v, false)
            hideKeyboard(v)
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

        val recyclerView = findViewById<RecyclerView>(R.id.trackList)

        val adapter = TrackAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        inputEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iTunesService.search(textView.text.toString()).enqueue(object : Callback<TrackList> {
                    override fun onResponse(call: Call<TrackList>, response: Response<TrackList>) {
                        if (response.isSuccessful) {
                            val trackList = response.body() ?: return
                            val tracks = trackList.results ?: return
                            adapter.tracks = tracks
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<TrackList>, t: Throwable) {
                        println()
                        TODO("Not yet implemented")
                    }
                })
                true
            }
            false
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

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}
