package yanbin.com.textpicker

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData

fun <A, B, R> zipLiveData(a: MutableLiveData<A>, b: MutableLiveData<B>, function: (A, B) -> R): MutableLiveData<R> {
    return MediatorLiveData<R>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = function(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }
}


fun <A, B, R> MutableLiveData<A>.zip(b: MutableLiveData<B>, function: (A, B) -> R): MutableLiveData<R> = zipLiveData(this, b, function)