package yanbin.com.textpicker

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import yanbin.com.textpicker.presentation.FontPickerViewModel

const val INJECT_KEY_CONTEXT = "context"

val fontModule = applicationContext {

    viewModel { FontPickerViewModel(get()) }

    factory { GoogleFontRepo(getProperty(INJECT_KEY_CONTEXT)) as FontRepo }
}