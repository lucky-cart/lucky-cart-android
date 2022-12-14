package com.luckycart.utils

import android.util.Log
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import kotlin.math.pow

fun <T> Observable<T>.observeOnMain(): Observable<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.subscribeIO(): Observable<T> = subscribeOn(Schedulers.io())


fun <T> Observable<T>.retryPolling(
    predicate: (Throwable) -> Boolean,
    delayBeforeRetry: Long,
    maxRetry: Int,
    timeUnit: TimeUnit
): Observable<T> where T : Any =
    retryWhen { observable ->
        Observables.zip(
            observable.map {if (predicate(it)){ it }else throw it },
            Observable.interval(delayBeforeRetry, timeUnit)
        ).map { if (it.second >= maxRetry) throw it.first }
    }


