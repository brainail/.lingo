package org.brainail.EverboxingLingo.ui.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.ui.BaseFragment
import org.brainail.EverboxingLingo.util.NavigableBack
import org.brainail.EverboxingLingo.util.TextWatcherAdapter
import org.jetbrains.anko.toast
import java.util.Locale

class LingoSearchFragment: BaseFragment(), NavigableBack {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lingo_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initFlysearch(savedInstanceState)
        initSearchResults()
    }

    private fun initSearchResults() {
        searchResultsRecyclerView.adapter = LingoSearchResultsAdapter()
    }

    private fun initFlysearch(savedInstanceState: Bundle?) {
        floatingSearchView.showIcon(shouldShowNavigationForSearch())
        floatingSearchView.icon = DrawerArrowDrawable(activity)
        floatingSearchView.showLogo(true)
        floatingSearchView.setOnIconClickListener {
            if (!floatingSearchView.isActivated) {
                activity?.toast("open Drawer please")
            } else {
                floatingSearchView.isActivated = false
            }
        }
        floatingSearchView.setOnSearchListener { query ->
            floatingSearchView.isActivated = false
            searchForResults(query.toString())
        }
        floatingSearchView.setOnSearchFocusChangedListener({ focused ->
            showClearButton(focused && !floatingSearchView.text.isEmpty())
            if (!focused) {
                showProgress(false)
            }
            floatingSearchView.showLogo(!focused && floatingSearchView.text.isEmpty())
            floatingSearchView.showIcon(focused || shouldShowNavigationForSearch())
        })
        floatingSearchView.setOnMenuItemClickListener({ menuItem ->
            when (menuItem.itemId) {
                R.id.menu_clear -> floatingSearchView.clearText()
                R.id.menu_tts -> startTextToSpeech("What are you looking for?", REQ_CODE_SPEECH_INPUT)
            }
            true
        })
        floatingSearchView.addTextChangedListener(object: TextWatcherAdapter() {
            override fun afterTextChanged(query: Editable) {
                showClearButton(query.isNotEmpty() && floatingSearchView.isActivated)
                floatingSearchView.showLogo(!floatingSearchView.isActivated && floatingSearchView.text.isEmpty())
                if (floatingSearchView.isActivated) {
                    searchForSuggestions(query.toString().trim())
                }
            }
        })

        if (null == savedInstanceState) {
            showClearButton(false)
        }

        appBarView.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            floatingSearchView.translationY = verticalOffset.toFloat()
        }
    }

    private fun showProgress(shouldShow: Boolean) {
        // floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = shouldShow
    }

    private fun searchForSuggestions(query: String) {
        showProgress(floatingSearchView.isActivated)
    }

    private fun searchForResults(query: String) {
        activity?.toast("Search for results = $query")
    }

    private fun showClearButton(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = shouldShow
    }

    private fun shouldShowNavigationForSearch(): Boolean = true

    fun startTextToSpeech(prompt: String, requestCode: Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
        try {
            startActivityForResult(intent, requestCode)
        } catch (exception: ActivityNotFoundException) {
            activity?.toast("Text to speech is not supported")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    data?.run {
                        val result = getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        floatingSearchView.text = result[0]
                        floatingSearchView.setSelection(floatingSearchView.text.length)
                        floatingSearchView.isActivated = true
                    }
                }
            }
        }
    }

    override fun goBack(): Boolean = when (floatingSearchView.isActivated) {
        true -> {
            floatingSearchView.isActivated = false
            true
        }
        else -> false
    }

    companion object {
        val FRAGMENT_TAG = "LingoSearchFragmentTag"

        private val REQ_CODE_SPEECH_INPUT = 100

        fun newInstance(): LingoSearchFragment {
            return LingoSearchFragment()
        }
    }

}
