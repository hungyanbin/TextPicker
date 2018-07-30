package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import yanbin.com.textpicker.domain.FontFamily

class FontPickerUseCase(private val fontRepo: FontRepo){

    fun getLiveFonts(): MutableLiveData<List<FontFamily>>{
        return fontRepo.getAllFonts()
    }

    fun getLiveError(): MutableLiveData<String>{
        return fontRepo.getErrorMessage()
    }

    fun sortFontsByFamily(){
        
    }

    fun sortFontsByLastModified(){

    }
}