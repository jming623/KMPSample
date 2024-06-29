package compose.module

import compose.data.repos.UserReposImpl
import compose.domain.repos.UserRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReposModule {
    @Singleton
    @Provides
    fun provideUserRepos(httpClient: HttpClient): UserRepos = UserReposImpl(httpClient)
}