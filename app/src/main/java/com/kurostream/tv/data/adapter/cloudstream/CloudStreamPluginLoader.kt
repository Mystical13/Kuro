package com.kurostream.tv.data.adapter.cloudstream

import com.kurostream.tv.domain.provider.AnimeProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudStreamPluginLoader @Inject constructor() {
    
    fun loadPlugins(): List<AnimeProvider> {
        // TODO: PHASE_3 DexClassLoader logic to load CS extensions dynamically from storage
        return emptyList()
    }
    
}
