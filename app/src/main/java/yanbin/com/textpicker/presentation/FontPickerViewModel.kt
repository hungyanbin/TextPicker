package yanbin.com.textpicker.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import yanbin.com.textpicker.FontPickerUseCase
import yanbin.com.textpicker.domain.FontFamily

class FontPickerViewModel(private val fontPickerUseCase: FontPickerUseCase): ViewModel(){

    var fonts: MutableLiveData<List<FontFamily>> = fontPickerUseCase.getLiveFonts()
    val errorMessage: MutableLiveData<String> = fontPickerUseCase.getLiveError()
    val selectedFont = MutableLiveData<String>()

    fun onFontSelected(font: String){
        selectedFont.postValue(font)
    }

    fun sortFontByFamily(){
        fontPickerUseCase.sortFontsByFamily()
    }

    fun sortFontByLastModified(){
        fontPickerUseCase.sortFontsByLastModified()
    }
}