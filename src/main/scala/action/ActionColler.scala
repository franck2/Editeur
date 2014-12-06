package action

import editeur._

class ActionColler extends ActionTrait {

	var add = new ActionInsert()

  override def toString(): String = {return "ActionColler {}"};

  override def exec(e:Editeur, skipHisto:Boolean){
  	add.str = e.clipboard
    add.exec(e, skipHisto)
  }

  override def redo(e:Editeur){
    add.redo(e)
  }

  override def undo(e:Editeur){
    add.undo(e)    
  }
}