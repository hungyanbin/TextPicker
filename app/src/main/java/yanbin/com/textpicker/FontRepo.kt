package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData

interface FontRepo{

    fun getErrorMessage(): MutableLiveData<String>
    fun getAllFonts(): MutableLiveData<List<String>>
}