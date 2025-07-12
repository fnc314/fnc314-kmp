package com.fnc314.kmp.features.posts.list

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val postsListModule = module {
    singleOf<PostsListRepository>(::PostsListRepositoryImpl)
    viewModel<PostsListViewModel> {
        PostsListViewModel(postsListRepository = get())
    }
}