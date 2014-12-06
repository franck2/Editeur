package editeur

import action._

class Histo {

  var pile_do :  Array[ActionTrait]= Array()
  var pile_undo :  Array[ActionTrait]= Array()
  
  override def toString(): String = {
    var str = "do {\n" 
    pile_do.foreach { str += _.toString() + "\n"} 
    str += "\n}, undo {\n"
    pile_undo.foreach { str += _.toString() + "\n"} 
    str += "}"
    return str
  }
  
  def empileActionDo(ac : ActionTrait, eraseUndo:Boolean = true){
    pile_do :+= ac
    if (eraseUndo)
      erasePileUndo
  }
  def empileActionUndo(ac : ActionTrait){
    pile_undo :+= ac
  }
  
  def depileActionDo(){
    pile_do = pile_do.dropRight(1)
  }
  def depileActionUndo(){
    pile_undo = pile_undo.dropRight(1)
  }
  def erasePileUndo(){
    pile_undo = Array()
  }

  //verif
  def getLastDo() : Option[ActionTrait] = {
    if( pile_do.length>0)
      return Option(pile_do.last)
    return Option(null)
  }
  def getLastUndo() : Option[ActionTrait] = {
    if( pile_undo.length>0)
      return Option(pile_undo.last)
    return Option(null)
  }

}