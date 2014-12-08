package action

import editeur._

/** Copy the text selected in editeur.clipboard */
class ActionCopier extends ActionTrait {

	/** The selected text*/
	var str = ""

  override def toString(): String = {return "ActionCopier {}"};

  /** Copy the text selected in editeur.clipboard
  * @param e editeur the editor used
  * @param skipHisto if it must skip the history
  */
  override def exec(e:Editeur, skipHisto:Boolean){
  	e.clipboard = e.buffer.getSubstring(e.cursorSelectionBegin,
      e.cursorSelectionEnd)
  	str = e.clipboard
  	e.updateObs()
  }

  /** Redo the copy (place the backup in the clipboard), use a backup because selection may have been lost*/ 
  override def redo(e:Editeur){
  	e.clipboard = str
  	e.updateObs()
  }

  /** Undo the copy (clear the clipboard */
  override def undo(e:Editeur){
    e.clipboard = ""
    e.updateObs()
  }
}