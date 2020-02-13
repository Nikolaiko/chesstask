package com.otus.homework.taskslist.di

import com.example.core.app.ProvidersFacade
import com.otus.homework.storage.di.UserDataComponent
import com.otus.homework.taskslist.views.TasksListFragment
import dagger.Component

@Component(
    modules = [TasksBindings::class],
    dependencies = [
        ProvidersFacade::class,
        UserDataComponent::class
    ]
)
interface TasksComponent {
    companion object {
        fun init(facadeComponent:ProvidersFacade):TasksComponent = DaggerTasksComponent
            .builder()
            .providersFacade(facadeComponent)
            .userDataComponent(UserDataComponent.init(facadeComponent))
            .build()
    }

    fun inject(fragment:TasksListFragment)
}
