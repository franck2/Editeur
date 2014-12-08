package action

import editeur._

/** Paste the text contained in the clipboard of an [[editeur.Editeur]] at the cursor's position
* Use a [[action.ActionInsert]] to be executed. 
*/ 
class ActionColler extends ActionTrait {

  /** The insert action*/
	var add = new ActionInsert()

  override def toString(): String = {return "ActionColler {}"};

  /** Modify add attribute to match the given [[editeur.Editeur]]
  * @param e editeur where the paste will be done
  * @param skipHisto if the action must skip the e.buffer's history
  */
  override def exec(e:Editeur, skipHisto:Boolean){
  	add.str = e.clipboard
    add.exec(e, skipHisto)
  }

  /** Redo the insertion*/
  override def redo(e:Editeur){
    add.redo(e)
  }

  /** Undo the insertion*/
  override def undo(e:Editeur){
    add.undo(e)    
  }
}