package yanbin.com.textpicker

import android.app.Application
import org.koin.android.ext.android.startKoin

class PickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules = listOf(fontModule))
    }
}