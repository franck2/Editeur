package editeur

import scala.swing._
import java.awt.Color
import scala.swing.event._

import action._

/** User Interface showing the content of a [[editeur.Editeur]]
* @param editeur the editeur to interact with
*/
class UIEditeur(editeur : Editeur) extends MainFrame with Observator {
  
  /** Constructor with default parameter */
  def this() = this(new Editeur)

  /** Add itself as Observator of its Editeur*/
  editeur.addObservator(this)

  preferredSize = new Dimension(500, 500)
  title = "Editeur de texte"
  
  /** Display the content of editeur.beffer in a read-only textarea*/
  val bufferContent = new EditorPane(){
    preferredSize = new Dimension(320, 320)
    editable = false
    caret.position = 0
    caret.visible = true
  }

  val cursorLabel = new Label("")
  val clipLabel = new Label("")
  val selBeginLabel = new Label("")
  val selEndLabel = new Label("")
  
  /** Informations about cursors and clipboard */
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

  /** Key to the recording macro*/
  var macroCouranteKey: String=""
  /** The manager of macro, see [[editeur.MacroManager]]*/
  val macroManager = new MacroManager
  
  // Input form for macro (start record, stop record, execute) 

  var keyMacro = new TextField
  var buttonStartRecordMacro = new Button("Start recording macro")
  var buttonStopRecordMacro = new Button("Stop recording macro")
  var buttonDoMacro = new Button("Exec macro")
  val macroPanel = new GridPanel(1,4){
    contents += keyMacro
    contents += buttonStartRecordMacro
    contents += buttonStopRecordMacro
    contents += buttonDoMacro
  }

  /** Save the macro in the MacroManager, the key is the macroCouranteKey attribute
  * @param a macro to save 
  */
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

    // listen user keyboard input and 4 buttons
    listenTo(this.keys, buttonStartRecordMacro, buttonStopRecordMacro,buttonDoMacro, buttonFocus)
    reactions += { 
      case ButtonClicked(b) => 
        b.text match {
          case "Start recording macro" => 
            if (keyMacro.text != ""){
              macroCouranteKey = keyMacro.text
              macroManager.setMacro(macroCouranteKey, new ActionComposite)
            }
          case "Stop recording macro" => macroCouranteKey = ""
          case "Exec macro" => macroManager.getMacro(keyMacro.text).exec(editeur, true)
          case "Remettre le focus" => 
            this.requestFocusInWindow()
            bufferContent.caret.visible =true
        }

      case x: KeyEvent =>
        // bool to break the switch when a pattern match
        var bool=false
        (bool, x) match {
          case (false, KeyTyped(_, c, m, _) ) => 
            (bool,c.toInt, m) match {
              // backspace or Shift backspace
              case (false ,8, 0) | (false ,8, 64) => 
                var a = new ActionEfface()
                a.exec(editeur, false)
                saveMacro(a)
                bool = true
              // Del or Shift Del
              case (false, 127, 0) | (false, 127,64) => 
                var a = new ActionErase()
                a.exec(editeur, false)
                saveMacro(a) 
                bool = true
              // All others key
              case (false, _, 0) | (false,_, 64)=> 
                var a = new ActionInsert(c.toString)
                a.exec(editeur, false) 
                saveMacro(a)
                bool=true
              // Default
              case (false, _, _)=>
            }
          case (false, KeyPressed(_, c, m, _)) =>
            (bool, c, m) match {
              //Key.left + ctrl + shift
              case (false, Key.Left, 192) =>
                editeur.moveSelectionLeft()
                bool=true
                update()
              //Key.Right + ctrl + shift
              case (false, Key.Right, 192) => 
                editeur.moveSelectionRight()
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
          case (false, _) =>
        }
      
    }
  }
  

  /** Updates display to correspond to editeur */
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