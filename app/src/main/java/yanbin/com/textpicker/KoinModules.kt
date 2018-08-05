package yanbin.com.textpicker

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import yanbin.com.textpicker.network.ApiService
import yanbin.com.textpicker.network.OkhttpApiService
import yanbin.com.textpicker.presentation.FontPickerViewModel

const val INJECT_KEY_CONTEXT = "context"

val fontModule = module {

    viewModel { FontPickerViewModel(get()) }

    factory { FontPickerUseCase(get()) }

    factory { GoogleFontRepo(getProperty(INJECT_KEY_CONTEXT), get()) as FontRepo }

    single { OkhttpApiService() as ApiService }

    single { TypeFaceHelper(getProperty(INJECT_KEY_CONTEXT)) }

}