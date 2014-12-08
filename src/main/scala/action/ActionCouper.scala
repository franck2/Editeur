package action

import editeur._

/** Cut the selection of a [[editeur.Editeur]]
* Composition of [[action.ActionCopier]] and [[action.ActionRemove]]
* */
class ActionCouper extends ActionTrait {

  /** The copy action */
	var copie : ActionCopier = new ActionCopier()

  /** The remove action*/
  var remove : ActionRemove = new ActionRemove()

  override def toString(): String = {return "ActionCouper {}"};

  /** Build copie and remove attribute, then execute them 
  * @param e editeur the editor used
  * @param skipHisto if it must skip the history
  */ 
  override def exec(e:Editeur, skipHisto:Boolean){
  	copie.exec(e, skipHisto)
    remove.startPos = e.cursorSelectionBegin
    remove.endPos = e.cursorSelectionEnd
    remove.exec(e, skipHisto)
    e.cursorSelectionBegin=e.cursor
    e.cursorSelectionEnd=e.cursor
    e.updateObs()
  }

  /** Redo the action */
  override def redo(e:Editeur){
    copie.redo(e)
    remove.redo(e)
  }

  /** Undo the action */
  override def undo(e:Editeur){
    remove.undo(e)
    copie.undo(e)
  }
}