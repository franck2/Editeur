package editeur

class Histo {

  var actions_do :  Array[ActionTrait]= Array()
  var actions_undo :  Array[ActionTrait]= Array()
  
  override def toString(): String = {
    var str = "do {" 
    actions_do.foreach { str += _.toString() + "\n"} 
    str += "}, undo {"
    actions_undo.foreach { str += _.toString() + "\n"} 
    str += "}"
    return str
  }
  
  // => empileActionDo
  def doAction(ac : ActionTrait){
    actions_do :+= ac
    actions_undo = Array()
  }
  
  // => depileActionUndo
  def undoAction(){
    val last = actions_do.last
    actions_do = actions_do.dropRight(1)
    actions_undo :+= last
  }

  //verif
  def getLastDo() : ActionTrait = actions_do.last

}