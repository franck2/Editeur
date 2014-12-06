package action

import editeur._

class ActionRemove(var startPos:Integer, var endPos:Integer) extends ActionTrait {
  
  def this() = this(-1,-1)

  var str = ""
  
  override def toString(): String = {return "{str : " + str + ", startPos : " + startPos + "}"};

  override def exec(e:Editeur,skipHisto:Boolean){
  	str = e.buffer.getSubstring(startPos,endPos)
  	e.buffer.remove(startPos, endPos)
  	e.cursors(startPos)
    if (!skipHisto)
      addToHisto(e)
    e.updateObs()
  }

  override def redo(e:Editeur){
  	exec(e, false)
  }

  override def undo(e:Editeur){
  	e.buffer.add(str, startPos-str.length)
    e.cursors(endPos)
    e.updateObs()
  }

  private def addToHisto(e:Editeur){
    var last = e.buffer.histo.getLastDo()
    if (last.isDefined ){        
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
    }
    else
      e.buffer.histo.empileActionDo(this)
  }
}