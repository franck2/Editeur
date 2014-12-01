package editeur

class Buffer {
  var text = ""

  def add(str : String, pos: Integer){
  	if (isValidPosition(pos))
  		text = text.substring(0, pos) + str + text.substring(pos, text.length)
  }
  
  def getSubstring(deb : Integer , fin : Integer ):String = { 
  	if (isValidPosition(deb) && isValidPosition(fin)){
  		if (deb < fin) {
  			return text.substring(deb, fin)
  		}
  		else{
  			return text.substring(fin, deb)
  		}
  	}
  	return ""
  }
  
  def efface() {
  	text = text.substring(0, text.length()-1)
  }

  def remove(deb: Integer, fin: Integer){
  	if (isValidPosition(deb) && isValidPosition(fin)){
  		if (deb < fin) {
  			text = text.substring(0, deb) + text.substring(fin, text.length)
  		}
  		else{
  			text = text.substring(0, fin) + text.substring(deb, text.length)
  		}
  	}
  }

  private def isValidPosition(pos : Integer) = pos>=0 && pos<=text.length
  
}