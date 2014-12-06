package action

import editeur._

class ActionInsert(var str : String) extends ActionTrait {

  val separators : Array[String] = Array(" ", "\n", "\t")
  
  def this() = this("")
  
  var startPos = -1
  
  override def toString(): String = {return "ActionInsert {str : " + str + ", startPos : " + startPos + "}"};

  override def exec(e:Editeur, skipHisto:Boolean){
    startPos = e.cursor
  	e.buffer.add(str, startPos)
    e.cursors(e.cursor + str.length)
    if (!skipHisto)
      addToHisto(e)
    e.updateObs()
  }

  override def redo(e:Editeur){
    e.buffer.add(str, startPos)
    e.cursors(e.cursor + str.length)
    e.updateObs()
  }

  override def undo(e:Editeur){
    var bk= e.cursor
  	e.buffer.remove(startPos, startPos + str.length())
    e.cursors(bk - str.length)
    e.updateObs()
  }

  private def addToHisto(e:Editeur){
    var last = e.buffer.histo.getLastDo()
    if (!separators.contains(str) && last.isDefined ){        
      if (last.get.isInstanceOf[ActionInsert]) { 
        if (separators.contains(last.get.asInstanceOf[ActionInsert].str)){
          e.buffer.histo.empileActionDo(this)
        }
        else{
          var comp = new ActionComposite
          comp.addAction(last.get)
          comp.addAction(this)
          e.buffer.histo.depileActionDo()
          e.buffer.histo.empileActionDo(comp)          
        }
      }
      else if (last.get.isInstanceOf[ActionComposite] && 
        last.get.asInstanceOf[ActionComposite].actions.forall(x => x.isInstanceOf[ActionInsert])){
        last.get.asInstanceOf[ActionComposite].addAction(this)
      }
    }
    else
      e.buffer.histo.empileActionDo(this)
  }
}