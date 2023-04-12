package com.myproject.gymphysique.core.common.dispatcher.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val gpDispatcher: GPDispatchers)

enum class GPDispatchers {
    IO
}