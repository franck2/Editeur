package action

import editeur._

/** Remove one char (after the cursor) or the selection (like Del), use [[action.ActionRemove]] */
class ActionErase extends ActionTrait {
  
  /** The remove action */
  var remove = new ActionRemove
  
  override def toString(): String = {return "ActionErase{}"};

  /** Build the remove action, then execute it
  * @param e editeur the editor used
  * @param skipHisto if it must skip the history
  */
  override def exec(e:Editeur,skipHisto:Boolean){
    if (e.hasSelection){
      remove.startPos= e.cursorSelectionBegin
      remove.endPos= e.cursorSelectionEnd
    }
    else{
      remove.startPos= e.cursor
      remove.endPos= e.cursor+1
    }
    remove.exec(e, skipHisto)
  }

  /** Redo the action */
  override def redo(e:Editeur){
    remove.redo(e)
  }

  /** Undo the action */
  override def undo(e:Editeur){
  	remove.undo(e)
  }
}