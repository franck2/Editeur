package editeur

import action._

/** The buffer containing the text and the history [[editeur.histo]]
 *  Extends [[editeur.Observed]]
 *
 *
*/
class Buffer extends Observed{
  
  /** Buffer content **/
  var text = ""
  
  /** History (Actions executed on the Buffer) **/ 
  var histo = new Histo

  /** Add a string to the Buffer at a given position
  * @param s string to add
  * @param pos position where add s
  */
  def add(s : String, pos: Integer){
  	if (isValidPosition(pos)){
  		
  		text = text.substring(0, pos) + s + text.substring(pos, text.length)
  		updateObs()
  	}
  }
  
   /** Remove a string to the Buffer between 2 positions
  * @param deb begin position 
  * @param fin end position
  */
  def remove(deb: Integer, fin: Integer){
  	if (isValidPosition(deb) && isValidPosition(fin)){
  		if (deb < fin)
        text = text.substring(0, deb) + text.substring(fin, text.length)
  		else
  			text = text.substring(0, fin) + text.substring(deb, text.length)

  		updateObs()
  	}
  }

   /** Returns the string contained between 2 positions
  * @param deb begin position 
  * @param fin end position
  */
  def getSubstring(deb : Integer , fin : Integer ):String = { 
  	if (isValidPosition(deb) && isValidPosition(fin)){
  		if (deb < fin) 
  			return text.substring(deb, fin)
  		else
  			return text.substring(fin, deb)
  	}
  	return ""
  }

  def redo(e:Editeur){
    var last = histo.getLastUndo()
  	if (last.isDefined){
  	  	last.get.redo(e)
  	  	histo.empileActionDo(last.get, false)
  	  	histo.depileActionUndo()		
  	}
  }

  def undo(e:Editeur){
    var last = histo.getLastDo()
    if (last.isDefined){
      last.get.undo(e)
      histo.empileActionUndo(last.get)
      histo.depileActionDo()      
    }
  }


  /** Return true if the given position is valid (between 0 and text.length)*/
  private def isValidPosition(pos : Integer) = pos>=0 && pos<=text.length
  
}