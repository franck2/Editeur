package action

import editeur._

class ActionCopier extends ActionTrait {

	var str = ""

  override def toString(): String = {return "ActionCopier {}"};

  override def exec(e:Editeur, skipHisto:Boolean){
  	e.clipboard = e.buffer.getSubstring(e.cursorSelectionBegin,
      e.cursorSelectionEnd)
  	str = e.clipboard
  	e.updateObs()
  }

  override def redo(e:Editeur){
  	e.clipboard = str
  	e.updateObs()
  }

  override def undo(e:Editeur){
    e.clipboard = ""
    e.updateObs()
  }
}