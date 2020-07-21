package com.rohitTheBest.owlbotdictionaryretrofit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rohitTheBest.owlbotdictionaryretrofit.adapters.MainActivityRVAdapter
import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Definition
import com.rohitTheBest.owlbotdictionaryretrofit.util.Resources
import com.rohitTheBest.owlbotdictionaryretrofit.viewModels.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: DictionaryViewModel by viewModels()
    private lateinit var mAdapter: MainActivityRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideUI()
        mAdapter = MainActivityRVAdapter()
        searchIB.setOnClickListener {

            if (searchWordET.text.toString().trim().isNotEmpty()) {

                hideUI()

                Glide.with(this)
                    .load(R.drawable.ic_baseline_image_24)
                    .into(searchResultImage)

                mainViewModel.fetchMeaning(searchWordET.text.toString().trim())
            } else {
                showToast("Please enter any word first.")
            }

            hideKeyBoard()
        }
        setUpObserver()
        textWatcher()
    }

    private fun textWatcher() {

        searchWordET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.isEmpty()!!) {
                    hideUI()
                    hideProgressBar()
                }
            }
        })
    }

    private fun hideKeyBoard() {

        val view = this.currentFocus
        if (view != null) {

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setUpObserver() {
        try {
            mainViewModel.meaning.observe(this, Observer {

                when (it) {

                    is Resources.Success -> {

                        hideProgressBar()
                        showUI()

                        val r = Random.nextInt(60, 255)
                        val g = Random.nextInt(60, 255)
                        val b = Random.nextInt(60, 255)

                        cardView2.setCardBackgroundColor(Color.argb(100, r, g, b))

                        it.data?.let { dictionary ->

                            searchResultWordTV.text = dictionary.word
                            searchResultPronounciationTV.text = dictionary.pronunciation

                            if (dictionary.definitions?.get(0)?.image_url != null) {
                                Glide.with(this)
                                    .load(dictionary.definitions[0].image_url)
                                    .into(searchResultImage)
                            }

                            setUpRecyclerView(dictionary.definitions)
                        }
                    }
                    is Resources.Loading -> {

                        showProgressBar()
                    }

                    is Resources.Error -> {

                        showToast("Error : Please check you word.")
                        hideProgressBar()
                        hideUI()
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpRecyclerView(definitions: List<Definition>?) {

        try {
            definitions?.let {

                mAdapter.submitList(it)

                searchResultRV.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = mAdapter
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showProgressBar() {

        try {
            progressBar.visibility = View.VISIBLE
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun hideProgressBar() {

        try {
            progressBar.visibility = View.GONE
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun showToast(message: String) {
        try {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun showUI() {
        try {
            cardView2.visibility = View.VISIBLE
            searchResultImage.visibility = View.VISIBLE
            searchResultRV.visibility = View.VISIBLE
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun hideUI() {
        try {
            cardView2.visibility = View.GONE
            searchResultImage.visibility = View.GONE
            searchResultRV.visibility = View.GONE
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}