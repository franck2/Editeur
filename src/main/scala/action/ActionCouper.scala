package action

import editeur._

class ActionCouper extends ActionTrait {

	var copie : ActionCopier = new ActionCopier()
  var remove : ActionRemove = new ActionRemove()

  override def toString(): String = {return "ActionCouper {}"};

  override def exec(e:Editeur, skipHisto:Boolean){
  	copie.exec(e, skipHisto)
    remove.startPos = e.cursorSelectionBegin
    remove.endPos = e.cursorSelectionEnd
    remove.exec(e, skipHisto)
    e.cursorSelectionBegin=e.cursor
    e.cursorSelectionEnd=e.cursor
    e.updateObs()
  }

  override def redo(e:Editeur){
    copie.redo(e)
    remove.redo(e)
  }

  override def undo(e:Editeur){
    remove.undo(e)
    copie.undo(e)
  }
}