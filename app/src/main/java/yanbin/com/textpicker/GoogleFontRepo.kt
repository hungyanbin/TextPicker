package yanbin.com.textpicker

import kotlinx.coroutines.experimental.delay


class GoogleFontRepo: FontRepo{

    override suspend fun getAllFonts(): List<String> {
//        delay(1000)
        Thread.sleep(1000)
        return listOf("asb", "ee", "cee", "caef", "cec", "ccc", "cef")
    }
}