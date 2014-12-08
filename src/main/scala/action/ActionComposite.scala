package action

import editeur._

/** A composition of [[action.ActionTrait]] */
class ActionComposite extends ActionTrait {
  
  /** Components of the composite action */
  var actions : Array[ActionTrait] = Array()
  
  override def toString(): String = {
    var str = "ActionComposite:  {" 
    actions.foreach { str += _.toString() + ", "} 
    str += "}"
    return str
  }


  override def exec(e:Editeur,skipHisto:Boolean){
  	for (a <- actions) a.exec(e,true)
    if (!skipHisto)
      e.buffer.histo.empileActionDo(this)
    e.updateObs()
  }

  override def redo(e:Editeur){
    for (a <- actions) a.redo(e)
    e.updateObs()
  }

  override def undo(e:Editeur){
  	for (a <- actions.reverse) a.undo(e)
    e.updateObs()
  }

  /** Add an action
  * @param a [[action.ActionTrait]] to add
  */
  def addAction(a:ActionTrait){
    actions :+= a
  }

  /** Remove an Observer
  * @param a [[action.ActionTrait]] to add
  */
  def removeAction(a:ActionTrait){
      actions = (actions.toBuffer - a).toArray
    }
  
  /** Returns true if actions is empty */
  def isEmpty():Boolean = actions.length==0
}