package yanbin.com.textpicker

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val fontModule = applicationContext {

    viewModel { FontPickerViewModel(get()) }

    factory { GoogleFontRepo() as FontRepo }
}