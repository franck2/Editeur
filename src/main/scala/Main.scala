
import editeur._

object Main {
  def main(args: Array[String]) : Unit = {

    var b : Buffer = new Buffer()
    var e : Editeur = new Editeur(b)
    var ui1 : UIEditeur = new UIEditeur(e)
    ui1.visible = true
    var ui2 : UIEditeur = new UIEditeur(e)
    ui2.visible = true
  }
}