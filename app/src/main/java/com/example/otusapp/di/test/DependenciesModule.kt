package com.example.otusapp.di.test

import com.github.belyakovleonid.core.DependenciesProvider
import com.github.belyakovleonid.feature_main.di.MainDependencies
import com.github.belyakovleonid.feature_recipe_detail.di.RecipeDetailDependencies
import com.github.belyakovleonid.feature_recipe_list.di.RecipeListDependencies
import com.github.belyakovleonid.module_injector.BaseDependencies
import com.github.belyakovleonid.module_injector.NoDependencies
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [DependenciesBindsModule::class])
object DependenciesModule {

    @JvmStatic
    @Provides
    @IntoMap
    @DependenciesKey(NoDependencies::class)
    fun provideNoDependencies(): BaseDependencies = NoDependencies
}

@Module
interface DependenciesBindsModule {

    @Binds
    @IntoMap
    @DependenciesKey(MainDependencies::class)
    fun provideRootDependencies(
        dependencies: MainDependencies
    ): BaseDependencies

    @Binds
    @IntoMap
    @DependenciesKey(RecipeListDependencies::class)
    fun provideRecipeListDependencies(
        dependencies: RecipeListDependencies
    ): BaseDependencies

    @Binds
    @IntoMap
    @DependenciesKey(RecipeDetailDependencies::class)
    fun provideRecipeDetailsDependencies(
        dependencies: RecipeDetailDependencies
    ): BaseDependencies

    @Binds
    fun bindDependenciesProvider(provider: DependenciesProviderImpl): DependenciesProvider
}