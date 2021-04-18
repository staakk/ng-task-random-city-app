package io.github.staakk.randomcity.di

import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class SchedulerQualifier(val type: Type) {

    enum class Type { MAIN, IO }
}