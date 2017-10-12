package com.co.giftlist.dataprovider.network.di

import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory
import dagger.Component

/**
 * Created by oscargallon on 6/2/17.
 */
@NetworkScope
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
    fun inject(repositoryFactory: RepositoryFactory)
}