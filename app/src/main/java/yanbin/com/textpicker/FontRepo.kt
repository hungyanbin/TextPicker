package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import yanbin.com.textpicker.domain.FontFamily

interface FontRepo{

    fun getErrorMessage(): MutableLiveData<String>
    fun getAllFonts(): MutableLiveData<List<FontFamily>>
}