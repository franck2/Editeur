package editeur

import scala.swing.event._
import java.awt.event._
import scala.math._

/**  Container of a [[editeur.Buffer]]
* Add cursors, selection and clipboard 
* extends [[editeur.Observer]] to observ its Buffer (an other Editeur can edit the same Buffer)
* extends [[editeur.Observed]] to be observed by an UI
*
* @param buffer The Buffer to interact with
*/
class Editeur(var buffer: Buffer) extends Observer with Observed{

  /** Constructeur with default parameter*/ 
  def this() = this(new Buffer)

  /** Add itself as Observer of its Buffer*/
  buffer.addObserver(this)

  /** Current position in the Buffer */
  private var _cursor : Integer= 0
  def cursor = _cursor
  /** Setter of cursor, to make always valid (unsigned, lower than the buffer size)*/ 
  def cursor_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursor = i }
  
  /** Position of the begining of the selection*/
  private var _cursorSelectionBegin : Integer= 0
  def cursorSelectionBegin = _cursorSelectionBegin
  /** Setter of cursorSelectionBegin, to make always valid (unsigned, lower than the buffer size)*/ 
  def cursorSelectionBegin_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursorSelectionBegin = i }

  /** Position of the end of the selection*/
  private var _cursorSelectionEnd : Integer= 0
  def cursorSelectionEnd = _cursorSelectionEnd
  /** Setter of cursorSelectionEnd, to make always valid (unsigned, lower than the buffer size)*/ 
  def cursorSelectionEnd_=(i: Integer) { if (i>=0 && i<=buffer.text.length) _cursorSelectionEnd = i }

  /** The clipboard, to conserv the copied text*/ 
  var clipboard = ""

  /** Setter of all cursors in the same time (cursors, cursorSelectionBegin,cursorSelectionEnd)
  * @param i the value to affect to all cursors
  **/
  def cursors(i:Integer){
    cursor = i
    cursorSelectionBegin = i
    cursorSelectionEnd = i
  }


  /** Update cursors corresponding to the buffer, then notify its Observers*/ 
  def update(){
    cursor = math.min(cursor, buffer.text.length)
    cursorSelectionBegin = math.min(cursorSelectionBegin, buffer.text.length)
    cursorSelectionEnd = math.min(cursorSelectionEnd, buffer.text.length)
    updateObs()
  }

  /** Move the selection to the left*/
  def moveSelectionLeft(){
    if (cursorSelectionEnd==cursor &&
      cursorSelectionEnd!=cursorSelectionBegin)
      cursorSelectionEnd-=1
    else
      cursorSelectionBegin-=1
    cursor-=1
  }

  /** Move the selection to the right*/
  def moveSelectionRight(){
    if (cursorSelectionBegin==cursor &&
      cursorSelectionEnd!=cursorSelectionBegin)
      cursorSelectionBegin+=1
    else
      cursorSelectionEnd+=1
    cursor+=1
  }

  /** Returns true if there is a selection*/
  def hasSelection():Boolean = {
    return cursorSelectionEnd!=cursorSelectionBegin
  }


  def redo(){buffer.redo(this)}
  def undo(){buffer.undo(this)}

}