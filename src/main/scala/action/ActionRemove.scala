package action

import editeur._

/** Remove text of a buffer between 2 positions 
* @param startPos position to start the remove
* @param endPos position to end the remove
*/
class ActionRemove(var startPos:Integer, var endPos:Integer) extends ActionTrait {
  
  /** Constructor with default parameters*/
  def this() = this(-1,-1)

  /** Text removed */
  var str = ""
  
  override def toString(): String = {return "{str : " + str + ", startPos : " + startPos + "}"};

  /** Remove the text, add the action to history if needed
  * @param e editeur the editor used
  * @param skipHisto if it must skip the history
  */
  override def exec(e:Editeur,skipHisto:Boolean){
  	str = e.buffer.getSubstring(startPos,endPos)
  	e.buffer.remove(startPos, endPos)
  	e.cursors(startPos)
    if (!skipHisto)
      addToHisto(e)
    e.updateObs()
  }

  /** Redo the action */
  override def redo(e:Editeur){
  	exec(e, true)
  }

  /** Undo the action */
  override def undo(e:Editeur){
  	e.buffer.add(str, startPos)
    e.cursors(endPos)
    e.updateObs()
  }

  /** Add itself to an editeur's history*/
  private def addToHisto(e:Editeur){
    var last = e.buffer.histo.getLastDo()
    if (last.isDefined){        
      if (last.get.isInstanceOf[ActionRemove]) { 
        var comp = new ActionComposite
        comp.addAction(last.get)
        comp.addAction(this)
        e.buffer.histo.depileActionDo()
        e.buffer.histo.empileActionDo(comp)       
      }
      else if (last.get.isInstanceOf[ActionComposite] && 
        last.get.asInstanceOf[ActionComposite].actions.forall(x => x.isInstanceOf[ActionRemove])){
        last.get.asInstanceOf[ActionComposite].addAction(this)
      }
      else
        e.buffer.histo.empileActionDo(this)
    }
    else
      e.buffer.histo.empileActionDo(this)
  }
}