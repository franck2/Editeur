package editeur

import scala.swing._
import java.awt.Color
import scala.swing.event._
import java.awt.event._

class UIEditeur extends MainFrame {
  
  Editeur.addObserver(this)
  
  preferredSize = new Dimension(500, 500)
  title = "Editeur de texte"

  def toMajIfNeeded(c :Char) : Char = { 
    var x = 0
    if (!Editeur.maj)
      x = 32
    return ( c + x).toChar 
  }
  
  val bufferContent = new EditorPane(){
    preferredSize = new Dimension(320, 320)
    editable = false
    caret.position = 0
    caret.visible = true
    focusable = true
    listenTo(keys)
    reactions += {
      
      case KeyPressed(_, Key.Space, _, _) => Editeur.pressSeparator(" ")
      case KeyPressed(_, Key.Enter, _, _) => Editeur.pressSeparator("\n")
      case KeyPressed(_, Key.A, _, _) => Editeur.pressLetter( toMajIfNeeded('A'))
      case KeyPressed(_, Key.B, _, _) => Editeur.pressLetter( toMajIfNeeded('B'))
      case KeyPressed(_, Key.C, _, _) => Editeur.pressLetter( toMajIfNeeded('C'))
      case KeyPressed(_, Key.D, _, _) => Editeur.pressLetter( toMajIfNeeded('D'))
      case KeyPressed(_, Key.E, _, _) => Editeur.pressLetter( toMajIfNeeded('E'))
      case KeyPressed(_, Key.F, _, _) => Editeur.pressLetter( toMajIfNeeded('F'))
      case KeyPressed(_, Key.G, _, _) => Editeur.pressLetter( toMajIfNeeded('G'))
      case KeyPressed(_, Key.H, _, _) => Editeur.pressLetter( toMajIfNeeded('H'))
      case KeyPressed(_, Key.I, _, _) => Editeur.pressLetter( toMajIfNeeded('I'))
      case KeyPressed(_, Key.J, _, _) => Editeur.pressLetter( toMajIfNeeded('J'))
      case KeyPressed(_, Key.K, _, _) => Editeur.pressLetter( toMajIfNeeded('K'))
      case KeyPressed(_, Key.L, _, _) => Editeur.pressLetter( toMajIfNeeded('L'))
      case KeyPressed(_, Key.M, _, _) => Editeur.pressLetter( toMajIfNeeded('M'))
      case KeyPressed(_, Key.N, _, _) => Editeur.pressLetter( toMajIfNeeded('N'))
      case KeyPressed(_, Key.O, _, _) => Editeur.pressLetter( toMajIfNeeded('O'))
      case KeyPressed(_, Key.P, _, _) => Editeur.pressLetter( toMajIfNeeded('P'))
      case KeyPressed(_, Key.Q, _, _) => Editeur.pressLetter( toMajIfNeeded('Q'))
      case KeyPressed(_, Key.R, _, _) => Editeur.pressLetter( toMajIfNeeded('R'))
      case KeyPressed(_, Key.S, _, _) => Editeur.pressLetter( toMajIfNeeded('S'))
      case KeyPressed(_, Key.T, _, _) => Editeur.pressLetter( toMajIfNeeded('T'))
      case KeyPressed(_, Key.U, _, _) => Editeur.pressLetter( toMajIfNeeded('U'))
      case KeyPressed(_, Key.V, _, _) => Editeur.pressLetter( toMajIfNeeded('V'))
      case KeyPressed(_, Key.W, _, _) => Editeur.pressLetter( toMajIfNeeded('W'))
      case KeyPressed(_, Key.X, _, _) => Editeur.pressLetter( toMajIfNeeded('X'))
      case KeyPressed(_, Key.Y, _, _) => Editeur.pressLetter( toMajIfNeeded('Y'))
      case KeyPressed(_, Key.Z, _, _) => Editeur.pressLetter( toMajIfNeeded('Z'))
      case KeyPressed(_, Key.CapsLock,_,_) => Editeur.toogleMaj()
      case KeyPressed(_, Key.BackSpace,_,_) => Editeur.efface()
      //case KeyPressed(_, Key.Left, )
      /*
      case KeyTyped(_, c, _, _) =>
        Editeur.pressLetter(c)
        */
    }
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
  
  contents = new BoxPanel(Orientation.Vertical){
    contents += bufferContent
    contents += infoEditeur
  }
  
  def update(){

    bufferContent.text = Editeur.buffer.text
    bufferContent.caret.position = Editeur.cursor
    cursorLabel.text = Editeur.cursor.toString
    clipLabel.text = Editeur.clipboard
    selBeginLabel.text = Editeur.cursorSelectionBegin.toString
    selEndLabel.text = Editeur.cursorSelectionEnd.toString
  }

  update


}