package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData

interface FontRepo{

    fun getAllFonts(): MutableLiveData<List<String>>
}