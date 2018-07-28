package yanbin.com.textpicker

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val viewModelModule = applicationContext {

    viewModel { FontPickerViewModel() }
}