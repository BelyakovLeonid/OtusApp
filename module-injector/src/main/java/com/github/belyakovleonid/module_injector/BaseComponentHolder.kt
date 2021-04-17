package com.github.belyakovleonid.module_injector

abstract class BaseComponentHolder<A : BaseApiProvider, D : BaseDependencies> {

    private var component: BaseApiProvider? = null

    fun getInstance(dependencies: D): A {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = initializeComponent(dependencies)
                }
            }
        }
        return checkNotNull(component as? A)
    }

    fun release() {
        component = null
    }

    protected abstract fun initializeComponent(dependencies: D): A
}