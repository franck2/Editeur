package editeur

import scala.swing.event._
import java.awt.event._
import scala.math._

class Editeur(var buffer: Buffer) extends Observator with Observed{

  def this() = this(new Buffer)
  buffer.addObservator(this)

  private var _cursor : Integer= 0
  def cursor = _cursor
  def cursor_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursor = i }
  
  private var _cursorSelectionBegin : Integer= 0
  def cursorSelectionBegin = _cursorSelectionBegin
  def cursorSelectionBegin_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursorSelectionBegin = i }

  private var _cursorSelectionEnd : Integer= 0
  def cursorSelectionEnd = _cursorSelectionEnd
  def cursorSelectionEnd_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursorSelectionEnd = i }

  def cursors(i:Integer){
    cursor = i
    cursorSelectionBegin = i
    cursorSelectionEnd = i
  }

  var clipboard = ""
  
  //var maj = false

  /*
  def addString(s: String){
    buffer.add(s, cursor)
    cursor+=s.length
    cursorSelectionBegin=cursor
    cursorSelectionEnd=cursor
    updateObs()
  }

  def efface(){
    if (cursorSelectionEnd!=cursorSelectionBegin){
      buffer.remove(cursorSelectionBegin, cursorSelectionEnd)
      cursor=cursorSelectionBegin
    }
    else {
      var bk_cursor = cursor
      buffer.remove(cursor-1, cursor)
      if (bk_cursor==cursor)
        cursor-=1
      cursorSelectionBegin=cursor
      cursorSelectionEnd=cursor
    }
    
    updateObs()
  }

  def erase(){
    buffer.remove(cursor, cursor+1)
    //updateObs()
  }

  def copy(){
    clipboard = buffer.getSubstring(cursorSelectionBegin, cursorSelectionEnd)
  }

  def paste(){
    addString(clipboard)
  }

  def cut(){
    clipboard = buffer.getSubstring(cursorSelectionBegin, cursorSelectionEnd)
    buffer.remove(cursorSelectionBegin, cursorSelectionEnd)
    cursorSelectionBegin=cursor
    cursorSelectionEnd=cursor
  }

  /*
  def toogleMaj(){
    maj = !maj
  }
  */
  */

  def update(){
    //println(cursor+" - " +buffer.text.length)
    cursor = math.min(cursor, buffer.text.length)
    cursorSelectionBegin=cursor
    cursorSelectionEnd=cursor
    updateObs()
  }

  def moveSelectionLeft(){
    if (cursorSelectionEnd==cursor &&
      cursorSelectionEnd!=cursorSelectionBegin)
      cursorSelectionEnd-=1
    else
      cursorSelectionBegin-=1
  }
  def moveSelectionRight(){
    if (cursorSelectionBegin==cursor &&
      cursorSelectionEnd!=cursorSelectionBegin)
      cursorSelectionBegin+=1
    else
      cursorSelectionEnd+=1
  }

  def redo(){buffer.redo(this)}
  def undo(){buffer.undo(this)}

/*
  def doMacro(key:String){
    buffer.doMacro(key, this)
  }*/

  def hasSelection():Boolean = {
    return cursorSelectionEnd!=cursorSelectionBegin
  }

}