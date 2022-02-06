package my.projects.githubclient.utils

import java.io.IOException

sealed interface SelectableObject<T> {
    operator fun <T> invoke(obj: T?): SelectableObject<T>?
    val obj: T
}

class Selected<T>(override val obj: T): SelectableObject<T> {
    override fun <T> invoke(obj: T?): SelectableObject<T>?
    =   if (obj == null) null
        else Selected(obj)
}
class Unselected<T>(override val obj: T): SelectableObject<T> {
    override fun <T> invoke(obj: T?): SelectableObject<T>?
    =   if (obj == null) null
        else Unselected(obj)
}


operator fun <T> SelectableObject<T>.not(): SelectableObject<T> = when(this) {
    is Selected<T> -> Unselected(this.obj)
    is Unselected<T> -> Selected(this.obj)
}


fun <T> Iterable<SelectableObject<T>>.removeUnselected(): List<SelectableObject<T>>
= this.filter { _obj ->
    when (_obj) {
        is Unselected<T> -> false
        else -> true
    }
}


fun <T> Iterable<SelectableObject<T>>.removeSelected(): List<SelectableObject<T>>
= this.filter { _obj ->
    when (_obj) {
        is Selected<T> -> false
        else -> true
    }
}


fun <T> SelectableObject<T>.toBoolean()
= when (this) {
    is Selected<T> -> true
    is Unselected<T> -> false
}

fun <T> SelectableObject<T>.save(): String
= when (this) {
    is Selected<T> -> "1${this.obj}"
    is Unselected<T> -> "0${this.obj}"
}

fun <T: FromString<T>> SelectableObject<T>.read(str: String): SelectableObject<T?>
= when (str[0]) {
    '1' -> Selected(this.obj.fromString(str))
    '0' -> Unselected(this.obj.fromString(str))
    else -> throw IOException()
}

fun <T> SelectableObject<T?>.checkNull(): SelectableObject<T>?
= when (this.obj) {
    null -> null
    else -> this(this.obj!!)
}