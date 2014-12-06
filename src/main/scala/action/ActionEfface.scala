package action

import editeur._

class ActionEfface extends ActionTrait {
  
  var remove = new ActionRemove
  
  override def toString(): String = {return "ActionEfface{}"};

  override def exec(e:Editeur,skipHisto:Boolean){
    if (e.hasSelection){
      remove.startPos= e.cursorSelectionBegin
      remove.endPos= e.cursorSelectionEnd
    }
    else{
      remove.startPos= e.cursor-1
      remove.endPos= e.cursor
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