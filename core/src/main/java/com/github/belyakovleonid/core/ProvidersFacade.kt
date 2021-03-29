package com.github.belyakovleonid.core

import com.github.belyakovleonid.core.navigation.CiceroneProvider
import com.github.belyakovleonid.core.starters.StartersProvider
import com.github.belyakovleonid.core_network_api.CoreNetworkProvider

interface ProvidersFacade : CoreNetworkProvider, StartersProvider, CiceroneProvider