package com.kurostream.tv.di

import com.kurostream.tv.data.adapter.cloudstream.CloudStreamPluginLoader
import com.kurostream.tv.data.adapter.stremio.StremioAdapter
import com.kurostream.tv.domain.provider.AnimeProvider
import com.kurostream.tv.domain.provider.ProviderAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideProviderAggregator(
        stremioAdapter: StremioAdapter,
        cloudStreamPluginLoader: CloudStreamPluginLoader
    ): ProviderAggregator {
        val providers = mutableListOf<AnimeProvider>(stremioAdapter)
        providers.addAll(cloudStreamPluginLoader.loadPlugins())
        return ProviderAggregator(providers)
    }
}
