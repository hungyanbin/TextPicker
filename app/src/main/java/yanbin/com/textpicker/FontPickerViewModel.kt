package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class FontPickerViewModel(fontRepo: FontRepo): ViewModel(){

    val fonts = MutableLiveData<List<String>>()

    init {
        launch(UI) {
            val allFonts = withContext(CommonPool){
                fontRepo.getAllFonts()
            }
            fonts.postValue(allFonts)
        }
    }

}