package action

import editeur._

class ActionComposite extends ActionTrait {
  var actions : Array[ActionTrait] = Array()
  
  override def toString(): String = {
    var str = "ActionComposite:  {" 
    actions.foreach { str += _.toString() + "\n"} 
    str += "}"
    return str
  };

  override def exec(e:Editeur,skipHisto:Boolean){
  	for (a <- actions) a.exec(e,skipHisto)
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

  def addAction(a:ActionTrait){
    actions :+= a
  }
  
  def isEmpty():Boolean = actions.length==0
}