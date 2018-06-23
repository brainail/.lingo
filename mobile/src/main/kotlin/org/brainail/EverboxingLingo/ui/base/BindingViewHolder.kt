package org.brainail.EverboxingLingo.ui.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView


abstract class BindingViewHolder<VB : ViewDataBinding>(
        protected val binding: VB) : RecyclerView.ViewHolder(binding.root)
