package editeur

import action._

/** Stock [[action.ActionTrait]] applied a [[editeur.Buffer]]
* Composed by two stacks: one for Do, one for Undo
*/
class Histo {

  /** Stack for Do */ 
  var pile_do :  Array[ActionTrait]= Array()
  
  /** Stack for Undo */ 
  var pile_undo :  Array[ActionTrait]= Array()
  
  override def toString(): String = {
    var str = "do {\n" 
    pile_do.foreach { str += _.toString() + "\n"} 
    str += "\n}, undo {\n"
    pile_undo.foreach { str += _.toString() + "\n"} 
    str += "}"
    return str
  }
  
  /** Add an [[action.ActionTrait]] to the Do stack
  * @param ac [[action.ActionTrait]] to add
  * @param eraseUndo if true, will erase the Undo stack
  */
  def empileActionDo(ac : ActionTrait, eraseUndo:Boolean = true){
    pile_do :+= ac
    if (eraseUndo)
      erasePileUndo
  }

   /** Add an [[action.ActionTrait]] to the Undo stack
  * @param ac [[action.ActionTrait]] to add
  */
  def empileActionUndo(ac : ActionTrait){
    pile_undo :+= ac
  }
  
  /** Remove the [[action.ActionTrait]] on top of the Do stack*/
  def depileActionDo(){
    pile_do = pile_do.dropRight(1)
  }

  /** Remove the [[action.ActionTrait]] on top of the Undo stack*/
  def depileActionUndo(){
    pile_undo = pile_undo.dropRight(1)
  }

  /** Erase the Undo stack*/ 
  def erasePileUndo(){
    pile_undo = Array()
  }

  /** Returns the top of the Do stack*/ 
  def getLastDo() : Option[ActionTrait] = {
    if( pile_do.length>0)
      return Option(pile_do.last)
    return Option(null)
  }
  
  /** Returns the top of the Undo stack*/ 
  def getLastUndo() : Option[ActionTrait] = {
    if( pile_undo.length>0)
      return Option(pile_undo.last)
    return Option(null)
  }

}