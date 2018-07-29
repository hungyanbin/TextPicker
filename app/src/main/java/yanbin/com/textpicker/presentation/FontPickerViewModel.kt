package yanbin.com.textpicker.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import yanbin.com.textpicker.FontRepo

class FontPickerViewModel(fontRepo: FontRepo): ViewModel(){

    val fonts = MutableLiveData<List<String>>()
    val selectedFont = MutableLiveData<String>()

    init {
        launch(UI) {
            val allFonts = withContext(CommonPool){
                fontRepo.getAllFonts()
            }
            fonts.postValue(allFonts)
        }
    }

    fun onFontSelected(font: String){
        selectedFont.postValue(font)
    }
}