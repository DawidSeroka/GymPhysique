package com.myproject.gymphysique.core.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val gpDispatcher: GPDispatchers)

enum class GPDispatchers {
    IO
}
