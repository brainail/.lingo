package org.brainail.EverboxingLingo.data.di.module

import dagger.Module

@Module(includes = arrayOf(
        PrefModule::class,
        UserModule::class
))
class DataModule