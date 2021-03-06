/*
 * Copyright 2019 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.util

import androidx.recyclerview.widget.RecyclerView

open class SimpleAdapterDataObserver : RecyclerView.AdapterDataObserver() {
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = onChanged()
    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = onChanged()
    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = onChanged()
    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = onChanged()
}
