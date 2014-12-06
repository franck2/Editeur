package editeur

import action._

class Buffer extends Observed{
  var text = ""

  var actionCourante : ActionComposite = new ActionComposite
  var histo = new Histo

  //var macroCourante: String=""
  //val macroManager = new MacroManager


  def add(s : String, pos: Integer){
  	if (isValidPosition(pos)){
  		
  		text = text.substring(0, pos) + s + text.substring(pos, text.length)

  		var ac = new ActionInsert(){
  			str= s
  			startPos= pos
  		}

      /*
  		if (!skipHisto){
	  		if (actionCourante.actions.length==0 ||
	  			( actionCourante.actions.last.isInstanceOf[ActionInsert] &&
	  				!separators.contains(s))){
	  			actionCourante.addAction(ac)
	  		}
	  		else {
	  			histo.empileActionDo(actionCourante)
	  			actionCourante = new ActionComposite()
	  			actionCourante.addAction(ac)
	  		}

	  		if (separators.contains(s)){
	  			histo.empileActionDo(actionCourante)
	  			actionCourante = new ActionComposite()
	  		}  			
  		}

      if (macroCourante!=""){
        var m= macroManager.getMacro(macroCourante)
        m.addAction(ac)
        macroManager.setMacro(macroCourante, m)
      }
      */

  		updateObs()
  	}
  }
  
  def remove(deb: Integer, fin: Integer){
  	if (isValidPosition(deb) && isValidPosition(fin)){
  		var ac = new ActionRemove()
  		if (deb < fin) {
  			ac.str = text.substring(deb,fin)
  			ac.startPos = fin
			text = text.substring(0, deb) + text.substring(fin, text.length)
  		}
  		else{
  			ac.str = text.substring(fin,deb)
  			ac.startPos = deb
  			text = text.substring(0, fin) + text.substring(deb, text.length)
  		}

      /*
  		if (!skipHisto){
	  		if (actionCourante.isEmpty() ||actionCourante.actions.last.isInstanceOf[ActionRemove]){
	  			actionCourante.addAction(ac)
	  		}
	  		else {
	  			histo.empileActionDo(actionCourante)
	  			actionCourante = new ActionComposite()
	  			actionCourante.addAction(ac)
	  		}		
  		}

      if (macroCourante!=""){
        var m= macroManager.getMacro(macroCourante)
        m.addAction(ac)
        macroManager.setMacro(macroCourante, m)
      }
      */

  		updateObs()
  	}
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

  def redo(e:Editeur){
    var last = histo.getLastUndo()
  	if (last.isDefined){
  	  	last.get.redo(e)
  	  	histo.empileActionDo(last.get, false)
  	  	histo.depileActionUndo()		
  	}
  }

  def undo(e:Editeur){
    println("before:" )
    println(histo)
    var last = histo.getLastDo()
    if (!actionCourante.isEmpty()){
      actionCourante.undo(e)
      histo.empileActionUndo(actionCourante)
      actionCourante=new ActionComposite()
    }
    else if (last.isDefined){
      last.get.undo(e)
      histo.empileActionUndo(last.get)
      histo.depileActionDo()      
    }
    println("after:" )
    println(histo)
  }

/*
  def doMacro(key:String,e:Editeur){
    var ac: ActionComposite = macroManager.getMacro(key)
    ac.exec(e, true)
    histo.empileActionDo(ac)
  }

  def startRecordMacro(key:String){
    if (key!=""){
      macroCourante=key
      macroManager.setMacro(key, new ActionComposite)
    }
  }
  def stopRecordMacro(){
    macroCourante = ""
  }
*/
  private def isValidPosition(pos : Integer) = pos>=0 && pos<=text.length
  
}