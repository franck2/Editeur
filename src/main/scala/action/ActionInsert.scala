package action

import editeur._

/** Insert text at a given position to a [[editeur.Editeur]] */
class ActionInsert(var str : String) extends ActionTrait {

  /** Constructor, default parameter*/
  def this() = this("")
  
  /** Array of separators, use to split insertion in history*/
  val separators : Array[String] = Array(" ", "\n", "\t")
  
  /** Position of the insertion*/
  var startPos = -1
  
  override def toString(): String = {return "ActionInsert {str : " + str + ", startPos : " + startPos + "}"};

  /** Build itself, then execute and add itself to the buffer history if needed
  * @param e editeur the editor used
  * @param skipHisto if it must skip the history
  */
  override def exec(e:Editeur, skipHisto:Boolean){
    startPos = e.cursor
  	e.buffer.add(str, startPos)
    e.cursors(e.cursor + str.length)
    if (!skipHisto)
      addToHisto(e)
    e.updateObs()
  }

  /** Redo the action */
  override def redo(e:Editeur){
    e.buffer.add(str, startPos)
    e.cursors(e.cursor + str.length)
    e.updateObs()
  }

  /** Undo the action */
  override def undo(e:Editeur){
    var bk= e.cursor
  	e.buffer.remove(startPos, startPos + str.length())
    e.cursors(bk - str.length)
    e.updateObs()
  }

  /** Add itself to an editeur's history*/
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
      else 
        e.buffer.histo.empileActionDo(this)
    }
    else
      e.buffer.histo.empileActionDo(this)
  }
}