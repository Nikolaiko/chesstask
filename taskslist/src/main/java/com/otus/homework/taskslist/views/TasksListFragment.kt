package com.otus.homework.taskslist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.core.app.AppWithFacade
import com.example.core.mediator.OnBoardingMediator
import com.example.core.mediator.SingleChessTaskMediator
import com.example.core.model.enums.ChessTaskDifficulty
import com.example.core.model.task.ChessTask
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

    @Inject
    lateinit var mediator:SingleChessTaskMediator

    @Inject
    lateinit var logoutMediator:OnBoardingMediator

    private var listAdapter:TasksListAdapter? = null

    private val _logoutUserButton: PublishSubject<Unit> = PublishSubject.create()
    override val logoutUserButton: Observable<Unit>
        get() = _logoutUserButton

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

        easyButton.setOnClickListener {
            presenter.getTaskByDifficulty(ChessTaskDifficulty.EASY)
        }

        normalButton.setOnClickListener {
            presenter.getTaskByDifficulty(ChessTaskDifficulty.NORMAL)
        }

        hardButton.setOnClickListener {
            presenter.getTaskByDifficulty(ChessTaskDifficulty.HARD)
        }

        logoutButton.setOnClickListener {
            _logoutUserButton.onNext(Unit)
        }

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

    override fun navigateTo(destination: TasksListScreens, task:ChessTask?) {
        when(destination) {
            TasksListScreens.SINGLE_CHESS_TASK -> {
                mediator.startOnChessActivity(context!!, task!!)
            }
            TasksListScreens.LOGIN -> {
                logoutMediator.createOnBoardingActivity(context!!)
                activity?.finish()
            }
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
        NewsMessageId.EXCEPTION_TASKS_LIST_REQUEST -> {
            resources.getString(R.string.exception_all_tasks_request_error, news.message)
        }
        NewsMessageId.EXCEPTION_TASK_BY_ID_REQUEST -> {
            resources.getString(R.string.exception_task_by_id_request_error, news.message)
        }
        NewsMessageId.NULL_TOKEN_ERROR -> resources.getString(R.string.null_token_error)
        else -> resources.getString(R.string.unknown_error_message)
    }
}
