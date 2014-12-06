package action

import editeur._

class ActionErase extends ActionTrait {
  
  var remove = new ActionRemove
  
  override def toString(): String = {return "ActionErase{}"};

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

  override def redo(e:Editeur){
    remove.redo(e)
  }

  override def undo(e:Editeur){
  	remove.undo(e)
  }
}