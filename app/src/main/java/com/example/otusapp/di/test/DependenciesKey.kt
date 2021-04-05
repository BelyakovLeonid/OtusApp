package com.example.otusapp.di.test

import com.github.belyakovleonid.module_injector.BaseDependencies
import dagger.MapKey
import kotlin.reflect.KClass


@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class DependenciesKey(val value: KClass<out BaseDependencies>)
