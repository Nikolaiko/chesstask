package com.otus.homework.chesstask.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.core.app.AppWithFacade
import com.example.core.model.task.FigurePosition
import com.otus.homework.chesstask.ChessTaskActivity
import com.otus.homework.chesstask.MAX_BOARD_INDEX
import com.otus.homework.chesstask.R
import com.otus.homework.chesstask.di.ChessTaskComponent
import com.otus.homework.chesstask.factory.ChessViewFactory
import com.otus.homework.chesstask.getSolutionFromPgn
import com.otus.homework.chesstask.model.board.BoardAction
import com.otus.homework.chesstask.model.figure.ChessFigureOnBoard
import com.otus.homework.chesstask.model.figure.ChessFigureView
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

    private val _exitButton: PublishSubject<Unit> = PublishSubject.create()
    override val exitButton: Observable<Unit>
        get() = _exitButton

    private val _restartButton: PublishSubject<Unit> = PublishSubject.create()
    override val restartButton: Observable<Unit>
        get() = _restartButton

    private val _undoButton: PublishSubject<Unit> = PublishSubject.create()
    override val undoButton: Observable<Unit>
        get() = _undoButton

    private val _selectedCell: PublishSubject<FigurePosition> = PublishSubject.create()
    override val selectedCell: Observable<FigurePosition>
        get() = _selectedCell

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

        answerText.text = getSolutionFromPgn(ChessTaskActivity.selectedTask!!)

        backButton.setOnClickListener {
            activity?.finish()
        }

        showAnswerButton.setOnClickListener {
            presenter.openSolution()
        }

        parentLayout.viewTreeObserver.addOnGlobalLayoutListener( object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                parentLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                for (i in 0..MAX_BOARD_INDEX) {
                    for (j in 0..MAX_BOARD_INDEX) {
                        val pieceCell: ImageView = view.findViewById(resources.getIdentifier(
                            "cell$i$j",
                            "id", context!!.packageName
                        ))
                        pieceCell.setOnClickListener {
                            _selectedCell.onNext(FigurePosition(i, j))
                        }
                    }
                }
                presenter.setBoardTask(ChessTaskActivity.selectedTask!!)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showWrongFigureMessage() {
        Toast.makeText(context!!, getString(R.string.wrong_figure_error_message), Toast.LENGTH_LONG).show()
    }

    override fun showSolutionText() {
        answerText.visibility = View.VISIBLE
    }

    override fun hideOpenSolutionButton() {
        showAnswerButton.visibility = View.INVISIBLE
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
            val newFigure = ChessFigureView(
                currentFigure.id,
                figureView,
                currentFigure.position,
                currentFigure.figureType
            )
            figureView.setOnClickListener {
                _selectedFigureId.onNext(currentFigure.id)
            }
            addFigureToScreen(newFigure)
        }
    }

    override fun applyAction(action: BoardAction) {
        clearSelection()

        if (action.removedFigure != null) {
            val destroyedFigure = figuresOnView.first { it.id == action.removedFigure.id }
            removeFigureFromScreen(destroyedFigure)
        }

        if (action.addedFigure != null) {
            val figureView = factory.buildFigure(action.addedFigure.figureType, action.addedFigure.color)
            val newFigure = ChessFigureView(
                action.addedFigure.id,
                figureView,
                action.addedFigure.position,
                action.addedFigure.figureType
            )
            figureView.setOnClickListener {
                _selectedFigureId.onNext(action.addedFigure.id)
            }
            addFigureToScreen(newFigure)
        }

        var movedFigure = figuresOnView.first { it.id == action.figure.id }
        removeFigureFromScreen(movedFigure)

        movedFigure = movedFigure.copy(position = action.endPosition)
        addFigureToScreen(movedFigure)
    }

    override fun showWrongMoveDialog() {
        val manager: FragmentManager = fragmentManager!!
        val dialog =  WrongMoveDialog()
        dialog.restartCallback = {
            _restartButton.onNext(Unit)
            dialog.dismiss()
        }

        dialog.undoCallback = {
            _undoButton.onNext(Unit)
            dialog.dismiss()
        }

        dialog.exitCallback = {
            _exitButton.onNext(Unit)
            dialog.dismiss()
        }

        dialog.show(manager, resources.getString(R.string.dialog_id))
    }

    override fun showWinDialog() {
        val manager: FragmentManager = fragmentManager!!
        val dialog =  WinDialog()
        dialog.restartCallback = {
            _restartButton.onNext(Unit)
            dialog.dismiss()
        }

        dialog.exitCallback = {
            _exitButton.onNext(Unit)
            dialog.dismiss()
        }

        dialog.show(manager, resources.getString(R.string.win_dialog_id))
    }

    override fun closeView() {
        activity?.finish()
    }

    private fun addFigureToScreen(figure: ChessFigureView) {
        val pieceCell: ImageView = view!!.findViewById(resources.getIdentifier(
            "cell${figure.position.row}${figure.position.column}",
            "id", context!!.packageName
        ))
        val pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
            "row${figure.position.row + 1}",
            "id", context!!.packageName
        ))
        pieceRow.addView(figure.imageView)
        figure.imageView.x = pieceCell.x
        figure.imageView.y = pieceCell.y
        figure.imageView.layoutParams.height = pieceCell.height
        figure.imageView.layoutParams.width = pieceCell.width
        figure.imageView.requestLayout()
        figuresOnView.add(figure)
    }

    private fun removeFigureFromScreen(figure: ChessFigureView) {
        figuresOnView.remove(figure)

        val pieceRow: ConstraintLayout = view!!.findViewById(resources.getIdentifier(
            "row${figure.position.row + 1}",
            "id", context!!.packageName
        ))
        pieceRow.removeView(figure.imageView)
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
