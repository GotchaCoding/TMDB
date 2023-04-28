package org.techtown.diffuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.techtown.diffuser.Repository
import org.techtown.diffuser.RepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds// 라이브러리처럼 외부에서 생서자 받아와서 제어 할 수 없으면 provide .  제어가능하면 bind
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository //  @Inject 로 repositoryImpl 주입받음.
}