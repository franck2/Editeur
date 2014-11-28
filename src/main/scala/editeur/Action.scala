package editeur

class Action {
  var str = ""
  var startPos = -1
  
  override def toString(): String = {return "{str : " + str + ", startPos : " + startPos + "}"};

  def exec(){
  	startPos = Editeur.cursor
  	Editeur.buffer.add(str, startPos)
  }

  def undo(){
  	Editeur.buffer.text  = Editeur.buffer.text.substring(0, startPos) + Editeur.buffer.text.substring(startPos + str.length())
  }
}