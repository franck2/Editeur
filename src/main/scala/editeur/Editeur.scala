package editeur

import scala.swing.event._
import java.awt.event._

object Editeur {

  var cursor = 0
  
  var cursorSelectionBegin = 0
  var cursorSelectionEnd = 0
  var clipboard = "" 
  
  var actionCourante : ActionTrait = null
  
  var buffer = new Buffer
  
  var histo = new Histo
  
  var maj = false

  var obs : Array[UIEditeur] = Array()
  
  def pressLetter(letter : Char){

    /*
    // if actionCourante isn't a ActionInsert, save actionCourante
    if (!actionCourante.isInstanceOf[ActionInsert] ){
      histo.doAction(actionCourante)
      actionCourante=null
    } 

    // ac take a new ActionInsert or casted actionCourante to use ActionInsert's methods
    var ac = new ActionInsert
    if (actionCourante != null)
      ac = actionCourante.asInstanceOf[ActionInsert]     
    
    if (actionCourante == null)
      ac = new ActionInsert
    else if (actionCourante.isInstanceOf[ActionInsert])
      ac = actionCourante.asInstanceOf[ActionInsert]
    else
    */

    var ac = getActionInType(new ActionInsert)
    
    buffer.add(letter.toString(), cursor)
    
    ac.str += letter
    if (ac.startPos == -1)
      ac.startPos = cursor
    
    actionCourante = ac

    cursor+=1

    update()
  }
  
  def pressSeparator(sep : String){
    
    buffer.add(sep.toString(), cursor)
    
    histo.doAction(actionCourante)
    
    var ac = new ActionInsert
    ac.str += sep
    ac.startPos = cursor
    histo.doAction(ac)
    
    //?? 
    actionCourante = null
    
    cursor+=1

    update()
  }
  
  def coller(){

    histo.doAction(actionCourante)
    actionCourante=null

    histo.doAction(new ActionInsert{
      str = clipboard
      startPos = cursor
    })

    buffer.add(clipboard, cursor)
    cursor+= clipboard.length()

    update()
  }
  
  def copier(){
    clipboard = buffer.getSubstring(cursorSelectionBegin, cursorSelectionEnd)

    update()
  }
  
  def efface(){
    if (cursor>0){
      buffer.efface()
      cursor-=1
      update()
    }
  }

  def toogleMaj(){
    maj = !maj
    update()
  }
  

  def doAction(a : ActionTrait){
    histo.doAction(actionCourante)
    actionCourante = null
    a.exec()
    histo.doAction(a)
  }

  def undoAction(){
    histo.getLastDo().undo()
    histo.undoAction()
  }

  
  def addObserver(o : UIEditeur) { 
    obs :+= o
  }
  def update() { 
    for (o <- obs) o.update() 
  }


  def getActionInType[T](a : T) : T = {
    if (actionCourante == null || !actionCourante.isInstanceOf[T]){
      histo.doAction(actionCourante)
      a
    }else 
      actionCourante.asInstanceOf[T]
  } 


  /*
  def toMajIfNeeded(c :Char) : Char = { 
    var x = 0
    if (!maj)
      x = 32
    return ( c + x).toChar 
  }

  def reaction(key : Event) : Unit = key match {
    case KeyPressed(_, Key.Space, _, _) => pressSeparator(" ")
    case KeyPressed(_, Key.Enter, _, _) => pressSeparator("\n")
    case KeyPressed(_, Key.A, _, _) => pressLetter( toMajIfNeeded('A'))
    case KeyPressed(_, Key.B, _, _) => pressLetter( toMajIfNeeded('B'))
    case KeyPressed(_, Key.C, _, _) => pressLetter( toMajIfNeeded('C'))
    case KeyPressed(_, Key.D, _, _) => pressLetter( toMajIfNeeded('D'))
    case KeyPressed(_, Key.E, _, _) => pressLetter( toMajIfNeeded('E'))
    case KeyPressed(_, Key.F, _, _) => pressLetter( toMajIfNeeded('F'))
    case KeyPressed(_, Key.G, _, _) => pressLetter( toMajIfNeeded('G'))
    case KeyPressed(_, Key.H, _, _) => pressLetter( toMajIfNeeded('H'))
    case KeyPressed(_, Key.I, _, _) => pressLetter( toMajIfNeeded('I'))
    case KeyPressed(_, Key.J, _, _) => pressLetter( toMajIfNeeded('J'))
    case KeyPressed(_, Key.K, _, _) => pressLetter( toMajIfNeeded('K'))
    case KeyPressed(_, Key.L, _, _) => pressLetter( toMajIfNeeded('L'))
    case KeyPressed(_, Key.M, _, _) => pressLetter( toMajIfNeeded('M'))
    case KeyPressed(_, Key.N, _, _) => pressLetter( toMajIfNeeded('N'))
    case KeyPressed(_, Key.O, _, _) => pressLetter( toMajIfNeeded('O'))
    case KeyPressed(_, Key.P, _, _) => pressLetter( toMajIfNeeded('P'))
    case KeyPressed(_, Key.Q, _, _) => pressLetter( toMajIfNeeded('Q'))
    case KeyPressed(_, Key.R, _, _) => pressLetter( toMajIfNeeded('R'))
    case KeyPressed(_, Key.S, _, _) => pressLetter( toMajIfNeeded('S'))
    case KeyPressed(_, Key.T, _, _) => pressLetter( toMajIfNeeded('T'))
    case KeyPressed(_, Key.U, _, _) => pressLetter( toMajIfNeeded('U'))
    case KeyPressed(_, Key.V, _, _) => pressLetter( toMajIfNeeded('V'))
    case KeyPressed(_, Key.W, _, _) => pressLetter( toMajIfNeeded('W'))
    case KeyPressed(_, Key.X, _, _) => pressLetter( toMajIfNeeded('X'))
    case KeyPressed(_, Key.Y, _, _) => pressLetter( toMajIfNeeded('Y'))
    case KeyPressed(_, Key.Z, _, _) => pressLetter( toMajIfNeeded('Z'))
  }
  */
}