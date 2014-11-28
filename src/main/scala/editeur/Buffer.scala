package editeur

class Buffer {
  var text = ""
  
  def openFile(fileName : String){}

  def add(str : String, pos: Integer){
    text = text.substring(0, pos) + str + text.substring(pos, text.length)
  }
  
  def getSubstring(deb : Integer , fin : Integer ) = text.substring(deb, fin) 
  
  def efface() {
  	text = text.substring(0, text.length()-2)
  }
  
}