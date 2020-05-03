package by.maxnevans.gamelist.model

abstract class UpdatableController<T> {
    private var isUpdating = false
    private var onChangeCallbacks: ArrayList<((T) -> Unit)> = arrayListOf()
    protected abstract var obj: T
    var raw: T
        get() {
            return obj
        }
        set(value) {
            obj = value
            notifyChange(true)
        }

    fun beginUpdateTransaction() {
        isUpdating = true
    }

    fun endUpdateTransaction() {
        notifyChange(true)
        isUpdating = false
    }

    fun onChange(cb: (obj: T) -> Unit) {
        onChangeCallbacks.add(cb)
    }

    protected fun notifyChange(singleTransaction: Boolean = false) {
        if (!singleTransaction || isUpdating)
            return

        for (cb in onChangeCallbacks)
            cb(raw)
    }
}