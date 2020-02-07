package com.otus.homework.chesstask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.core.app.AppWithFacade
import com.example.core.model.task.ChessTaskShortInfo
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.ChessTaskActivity
import com.otus.homework.chesstask.R
import com.otus.homework.chesstask.di.ChessTaskComponent
import com.otus.homework.chesstask.factory.ChessViewFactory
import com.otus.homework.chesstask.model.ChessFigureOnBoard
import com.otus.homework.chesstask.model.ChessFigureView
import com.otus.homework.chesstask.presenters.ChessBoardPresenter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_chess_task.*
import javax.inject.Inject

class ChessTaskFragment : Fragment(), ChessTaskView {

    @Inject
    lateinit var presenter: ChessBoardPresenter

    @Inject
    lateinit var factory: ChessViewFactory

    private var figuresOnView:MutableList<ChessFigureView> = mutableListOf()
    private val _selectedFigureId: PublishSubject<String> = PublishSubject.create()
    override val selectedFigureId: Observable<String>
        get() = _selectedFigureId

    override fun updateChessBoardSelection(selectedCells: List<FigurePosition>) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ChessTaskComponent.init((activity!!.application as AppWithFacade).getFacade()).inject(this)
        return inflater.inflate(R.layout.fragment_chess_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        backButton.setOnClickListener {
            activity?.finish()
        }

        parentLayout.viewTreeObserver.addOnGlobalLayoutListener( object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                parentLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                presenter.setBoardTask(ChessTaskActivity.selectedTask!!)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun updateChessBoardPosition(position: List<ChessFigureOnBoard>) {
        clearBoard()
        for (currentFigure in position) {
            val figureView = factory.buildFigure(currentFigure.figureType, currentFigure.color)

            val pieceCell: ImageView = view!!.findViewById(resources.getIdentifier(
                "cell${currentFigure.position.row}${currentFigure.position.column}",
                "id", context!!.packageName
            ))
            val pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
                "row${currentFigure.position.row + 1}",
                "id", context!!.packageName
            ))

            pieceRow.addView(figureView)
            figureView.x = pieceCell.x
            figureView.y = pieceCell.y
            figureView.layoutParams.height = pieceCell.height
            figureView.layoutParams.width = pieceCell.width
            figureView.requestLayout()
            figureView.setOnClickListener {
                _selectedFigureId.onNext(currentFigure.id)
            }
        }
    }

    private fun clearBoard() {
        for (figureView in figuresOnView) {
            val pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
                "row${figureView.position.row + 1}",
                "id", context!!.packageName
            ))
            pieceRow.removeView(figureView.imageView)
        }
        figuresOnView.clear()
    }
}