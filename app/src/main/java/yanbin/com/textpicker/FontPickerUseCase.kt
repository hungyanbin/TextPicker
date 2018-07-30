package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import yanbin.com.textpicker.domain.FontFamily

private const val SORT_BY_FAMILY = 0
private const val SORT_BY_LAST_MODIFIED = 1

class FontPickerUseCase(private val fontRepo: FontRepo){

    private val sortEvents = MutableLiveData<Int>()

    init {
        sortEvents.postValue(SORT_BY_FAMILY)
    }

    private val liveFonts = fontRepo.getAllFonts().zip(sortEvents) {fonts, sortBy->
        if(sortBy == SORT_BY_FAMILY){
            fonts.sortedBy { it.family }
        }else{
            fonts.sortedBy { it.lastModified }
        }
    }

    fun getLiveFonts(): MutableLiveData<List<FontFamily>>{
        return liveFonts
    }

    fun getLiveError(): MutableLiveData<String>{
        return fontRepo.getErrorMessage()
    }

    fun sortFontsByFamily(){
        sortEvents.postValue(SORT_BY_FAMILY)
    }

    fun sortFontsByLastModified(){
        sortEvents.postValue(SORT_BY_LAST_MODIFIED)
    }
}