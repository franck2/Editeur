package editeur

class Histo {

  var actions_do :  Array[Action]= Array()
  
  var actions_undo :  Array[Action]= Array()
  
   override def toString(): String = {
    var str = "do {" 
    actions_do.foreach { str += _.toString() + "\n"} 
    str += "}, undo {"
    actions_undo.foreach { str += _.toString() + "\n"} 
    str += "}"
    return str
   }
  
  def doAction(ac : Action){
    actions_do :+= ac
    actions_undo = Array()
  }
  
  def undoAction(){
    val last = actions_do.last
    actions_do = actions_do.dropRight(1)
    actions_undo :+= last
  }

  def getLastDo() : Action ={
    return actions_do.last
  }
}