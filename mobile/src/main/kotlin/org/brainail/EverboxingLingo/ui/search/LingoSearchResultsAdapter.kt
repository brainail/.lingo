package org.brainail.EverboxingLingo.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.brainail.EverboxingLingo.R

/**
 * This file is part of Everboxing modules. <br></br><br></br>
 *
 *
 * The MIT License (MIT) <br></br><br></br>
 *
 *
 * Copyright (c) 2017 Malyshev Yegor aka brainail at wsemirz@gmail.com <br></br><br></br>
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br></br>
 * of this software and associated documentation files (the "Software"), to deal <br></br>
 * in the Software without restriction, including without limitation the rights <br></br>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br></br>
 * copies of the Software, and to permit persons to whom the Software is <br></br>
 * furnished to do so, subject to the following conditions: <br></br><br></br>
 *
 *
 * The above copyright notice and this permission notice shall be included in <br></br>
 * all copies or substantial portions of the Software. <br></br><br></br>
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br></br>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br></br>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br></br>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br></br>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br></br>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN <br></br>
 * THE SOFTWARE.
 */
class LingoSearchResultsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))
    }

    // TODO: enter anim
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // ...
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
