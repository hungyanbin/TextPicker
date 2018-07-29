package yanbin.com.textpicker

interface FontRepo{

    suspend fun getAllFonts(): List<String>
}