package com.otus.homework.taskslist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.core.app.AppWithFacade
import com.example.core.model.task.ChessTaskShortInfo
import com.otus.homework.taskslist.R
import com.otus.homework.taskslist.di.TasksComponent
import com.otus.homework.taskslist.model.TasksListNews
import com.otus.homework.taskslist.model.enums.NewsMessageId
import com.otus.homework.taskslist.model.enums.TasksListScreens
import com.otus.homework.taskslist.presenters.TasksPresenter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_all_tasks.*
import javax.inject.Inject

class TasksListFragment : Fragment(), TasksView {

    @Inject
    lateinit var presenter:TasksPresenter

    private var listAdapter:TasksListAdapter? = null
    private val _selectedTask:PublishSubject<ChessTaskShortInfo> = PublishSubject.create()
    override val selectedTask:Observable<ChessTaskShortInfo>
        get() = _selectedTask

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        TasksComponent.init((activity!!.application as AppWithFacade).getFacade()).inject(this)
        return inflater.inflate(R.layout.fragment_all_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        listAdapter = TasksListAdapter()
        listAdapter?.rowClickCallback = {
            _selectedTask.onNext(it)
        }

        tasksList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        tasksList.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun displayMessage(newsMessage: TasksListNews) {
        activity?.runOnUiThread {
            Toast.makeText(context!!, convertNewsToString(newsMessage), Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigateTo(destination: TasksListScreens) {
        when(destination) {
            else -> displayMessage(TasksListNews(NewsMessageId.UNKNOWN_DESTINATION))
        }
    }

    override fun setLoadingVisibility(visible: Boolean) {
        if (visible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }

    override fun updateTasksList(list: List<ChessTaskShortInfo>) {
        listAdapter?.tasks = list
    }

    private fun convertNewsToString(news:TasksListNews):String = when(news.id) {
        NewsMessageId.UNKNOWN_DESTINATION -> resources.getString(R.string.unknown_screen_error)
        NewsMessageId.REQUEST_STATUS_ERROR -> resources.getString(R.string.request_status_error, news.message)
        NewsMessageId.EXCEPTION_TASKS_LIST_REQUEST -> resources.getString(R.string.exception_registration_request_error, news.message)
    }
}