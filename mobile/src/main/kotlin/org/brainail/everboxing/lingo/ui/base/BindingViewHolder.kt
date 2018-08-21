package org.brainail.everboxing.lingo.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BindingViewHolder<VB : ViewDataBinding>(
        protected val binding: VB) : RecyclerView.ViewHolder(binding.root)
