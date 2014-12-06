package editeur

import scala.swing._
import java.awt.Color
import scala.swing.event._

import action._


class UIEditeur(editeur : Editeur) extends MainFrame with Observator {
  
  def this() = this(new Editeur)
  editeur.addObservator(this)

  preferredSize = new Dimension(500, 500)
  title = "Editeur de texte"
  
  val bufferContent = new EditorPane(){
    preferredSize = new Dimension(320, 320)
    editable = false
    //deafTo(keys)
    caret.position = 0
    caret.visible = true
  }

  val cursorLabel = new Label("")
  val clipLabel = new Label("")
  val selBeginLabel = new Label("")
  val selEndLabel = new Label("")
  
  val infoEditeur = new GridPanel(2,4){
    contents += new Label("Curseur :")
    contents += cursorLabel
    contents += new Label("Clipboard :")
    contents += clipLabel
    contents += new Label("Cursor debut sél :")
    contents += selBeginLabel
    contents += new Label("Cursor fin sél :")
    contents += selEndLabel
  }


  var macroCouranteKey: String=""
  val macroManager = new MacroManager
  
  var keyMacro = new TextField
  var buttonStartRecordMacro = new Button("Start macro")
  var buttonStopRecordMacro = new Button("Stop macro")
  var buttonDoMacro = new Button("Do macro")
  val macroPanel = new GridPanel(1,4){
    contents += keyMacro
    contents += buttonStartRecordMacro
    contents += buttonStopRecordMacro
    contents += buttonDoMacro
  }

  def saveMacro(a: ActionTrait){
    if (macroCouranteKey!=""){
      var ac = macroManager.getMacro(macroCouranteKey)
      ac.addAction(a)
      macroManager.setMacro(macroCouranteKey, ac)
    }
  }

  var buttonFocus = new Button("Remettre le focus")
  contents = new BoxPanel(Orientation.Vertical){
    contents += bufferContent
    contents += infoEditeur
    contents += macroPanel
    contents += buttonFocus

    focusable = true
    requestFocusInWindow()
    listenTo(this.keys, buttonStartRecordMacro, buttonStopRecordMacro,buttonDoMacro, buttonFocus)
    reactions += { 
      case ButtonClicked(b) => 
        b.text match {
          case "Start macro" => 
            if (keyMacro.text != ""){
              macroCouranteKey = keyMacro.text
              macroManager.setMacro(macroCouranteKey, new ActionComposite)
            }
          case "Stop macro" => macroCouranteKey = ""
          case "Do macro" => macroManager.getMacro(keyMacro.text).exec(editeur, true)
          case "Remettre le focus" => 
            this.requestFocusInWindow()
            bufferContent.caret.visible =true
        }

      case x: KeyEvent =>
        var bool=false
        (bool, x) match {
          case (false, KeyTyped(_, c, m, _) ) => 
            //println("Typed :" + c + ","+m)
            (bool,c.toInt, m) match {
              case (false ,8, 0) | (false ,8, 64) => 
                var a = new ActionEfface()
                a.exec(editeur, false)
                saveMacro(a)
                bool = true
              case (false, 127, 0) | (false, 127,64) => 
                var a = new ActionErase()
                a.exec(editeur, false)
                saveMacro(a) 
                bool = true
              case (false, _, 0) | (false,_, 64)=> 
                var a = new ActionInsert(c.toString)
                a.exec(editeur, false) 
                saveMacro(a)
                bool=true
              case (false, _, _)=>
            }
          case (false, KeyPressed(_, c, m, _)) =>
            (bool, c, m) match {
              //Key.left + ctrl + shift
              case (false, Key.Left, 192) =>
                editeur.moveSelectionLeft()
                editeur.cursor-=1
                bool=true
                update() // editeur.upObs ???
              //Key.Right + ctrl + shift
              case (false, Key.Right, 192) => 
                editeur.moveSelectionRight()
                editeur.cursor+=1
                bool=true
                update()
              //Key.left
              case (false, Key.Left, 0) => 
                editeur.cursors(editeur.cursor-1)
                bool=true
                update()
              //Key.Right
              case (false, Key.Right, 0) => 
                editeur.cursors(editeur.cursor+1)
                bool=true
                update()
              //Ctrl+A
              case (false, Key.A, 128) => 
                // TODO => def Action
                editeur.cursorSelectionBegin=0
                editeur.cursorSelectionEnd=editeur.buffer.text.length()
                bool=true
                update()
              //Ctrl+C
              case (false, Key.C, 128) => 
                var a = new ActionCopier()
                a.exec(editeur, false) 
                saveMacro(a)
                bool=true
                update()
              //Ctrl+V
              case (false, Key.V, 128) => 
                var a = new ActionColler()
                a.exec(editeur, false) 
                saveMacro(a)
                bool=true
                update()
              //Ctrl+X
              case (false, Key.X, 128) => 
                var a = new ActionCouper()
                a.exec(editeur, false)
                saveMacro(a)
                bool=true
                update()
              //Ctrl+Z
              case (false, Key.Z, 128) =>
                editeur.undo()
                bool=true
                update()
              //Ctrl+Shift+Z
              case (false, Key.Z, 192) => 
                editeur.redo()
                bool=true
                update()
              case (false, _, _) =>
    
            }
            //println("Pressed :" + c + " " + m)
          case (false, _) =>
        }
      
    }
  }
  
  def update(){
    bufferContent.text = editeur.buffer.text
    bufferContent.caret.position = editeur.cursor
    cursorLabel.text = editeur.cursor.toString
    clipLabel.text = editeur.clipboard
    selBeginLabel.text = editeur.cursorSelectionBegin.toString
    selEndLabel.text = editeur.cursorSelectionEnd.toString
  }

  update


}