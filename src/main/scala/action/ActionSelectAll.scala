package action

import editeur._

/** Set selection to all the buffer content */ 
class ActionSelectAll extends ActionTrait {

  override def toString(): String = {return "ActionColler {}"};

  /** Set selection to all the buffer content 
  * @param e editeur where the paste will be done
  * @param skipHisto if the action must skip the e.buffer's history
  */
  override def exec(e:Editeur, skipHisto:Boolean){
  	e.cursors(0)
    e.cursorSelectionEnd=e.buffer.text.length
  }

  /** Redo the selection*/
  override def redo(e:Editeur){
    exec(e, true)
  }

  /** Undo the selection */
  override def undo(e:Editeur){
    e.cursors(0)    
  }
}