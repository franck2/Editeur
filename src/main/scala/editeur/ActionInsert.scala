package editeur

class ActionInsert extends ActionTrait {
  var str = ""
  var startPos = -1
  
  override def toString(): String = {return "{str : " + str + ", startPos : " + startPos + "}"};

  override def exec(){
  	startPos = Editeur.cursor
  	Editeur.buffer.add(str, startPos)
  }

  override def undo(){
  	Editeur.buffer.text  = Editeur.buffer.text.substring(0, startPos) + Editeur.buffer.text.substring(startPos + str.length())
  }
}