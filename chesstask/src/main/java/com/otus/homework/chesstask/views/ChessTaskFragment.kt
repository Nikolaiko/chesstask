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
import com.otus.homework.chesstask.model.BoardAction
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
    private var selectedBoardCells:MutableList<FigurePosition> = mutableListOf()

    private val _selectedFigureId: PublishSubject<String> = PublishSubject.create()
    override val selectedFigureId: Observable<String>
        get() = _selectedFigureId

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

    override fun updateChessBoardSelection(selectedCells: List<FigurePosition>) {
        clearSelection()
        for (currentCell in selectedCells) {
            val pieceCell: ImageView = view!!.findViewById(resources.getIdentifier(
                "cell${currentCell.row}${currentCell.column}",
                "id", context!!.packageName
            ))
            pieceCell.setColorFilter(R.color.design_default_color_primary)
            selectedBoardCells.add(currentCell)
        }
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
            figuresOnView.add(ChessFigureView(
                currentFigure.id,
                figureView,
                currentFigure.position,
                currentFigure.figureType
            ))
        }
    }

    override fun applyAction(action: BoardAction) {
        clearSelection()

        if (action.removedFigure != null) {
            val displayedFigure = figuresOnView.first { it.id == action.removedFigure.id }
            figuresOnView.remove(displayedFigure)

            val pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
                "row${displayedFigure.position.row + 1}",
                "id", context!!.packageName
            ))
            pieceRow.removeView(displayedFigure.imageView)
        }

        var displayedFigure = figuresOnView.first { it.id == action.figure.id }
        figuresOnView.remove(displayedFigure)

        var pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
            "row${displayedFigure.position.row + 1}",
            "id", context!!.packageName
        ))
        pieceRow.removeView(displayedFigure.imageView)

        displayedFigure = displayedFigure.copy(position = action.endPosition)
        val pieceCell: ImageView = view!!.findViewById(resources.getIdentifier(
            "cell${displayedFigure.position.row}${displayedFigure.position.column}",
            "id", context!!.packageName
        ))
        pieceRow = view!!.findViewById(resources.getIdentifier(
            "row${displayedFigure.position.row + 1}",
            "id", context!!.packageName
        ))

        pieceRow.addView(displayedFigure.imageView)
        displayedFigure.imageView.x = pieceCell.x
        displayedFigure.imageView.y = pieceCell.y
        displayedFigure.imageView.layoutParams.height = pieceCell.height
        displayedFigure.imageView.layoutParams.width = pieceCell.width
        displayedFigure.imageView.requestLayout()
        figuresOnView.add(displayedFigure)
    }

    private fun clearSelection() {
        for (currentPosition in selectedBoardCells) {
            val pieceCell: ImageView = view!!.findViewById(resources.getIdentifier(
                "cell${currentPosition.row}${currentPosition.column}",
                "id", context!!.packageName
            ))
            pieceCell.clearColorFilter()
        }
        selectedBoardCells.clear()
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