package com.pahnupan.poc.agora

import androidx.lifecycle.ViewModel
import com.pahnupan.poc.agora.core.SharePref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val sharePref: SharePref
) : ViewModel() {

}