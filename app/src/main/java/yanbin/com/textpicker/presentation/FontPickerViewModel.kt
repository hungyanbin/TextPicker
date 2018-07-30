package yanbin.com.textpicker.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import yanbin.com.textpicker.FontRepo

class FontPickerViewModel(fontRepo: FontRepo): ViewModel(){

    var fonts: MutableLiveData<List<String>> = fontRepo.getAllFonts()
    val errorMessage: MutableLiveData<String> = fontRepo.getErrorMessage()
    val selectedFont = MutableLiveData<String>()

    fun onFontSelected(font: String){
        selectedFont.postValue(font)
    }
}