package yanbin.com.textpicker

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class FontPickerViewModel: ViewModel(){

    val fonts = MutableLiveData<List<String>>()

    init {
        fonts.postValue(listOf("Abc", "cde", "def", "ii", "fawjef", "cwe"))
    }

}