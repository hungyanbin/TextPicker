package yanbin.com.textpicker

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import yanbin.com.textpicker.network.ApiService
import yanbin.com.textpicker.network.OkhttpApiService
import yanbin.com.textpicker.presentation.FontPickerViewModel

const val INJECT_KEY_CONTEXT = "context"

val fontModule = applicationContext {

    viewModel { FontPickerViewModel(get()) }

    factory { GoogleFontRepo(getProperty(INJECT_KEY_CONTEXT), get()) as FontRepo }

    bean { OkhttpApiService() as ApiService }

    bean { TypeFaceHelper(getProperty(INJECT_KEY_CONTEXT)) }

}