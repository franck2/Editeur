package editeur

object Main {
  def main(args: Array[String]) : Unit = {
  	//println("Hello")
    
    var ui = new UIEditeur
    ui.visible = true
  	
    /*
    Editeur.buffer.text = "aaaaa bbkfs fdnskl"
    Editeur.cursor = 5
    println(Editeur.buffer.text)

    Editeur.cursorSelectionBegin = 3
    Editeur.cursorSelectionEnd = 17
    Editeur.copier()
    println("Copie entre 5 et 10 : " + Editeur.clipboard)
    
    Editeur.coller()

    println("Coller : ")
    println(Editeur.buffer.text)
    */
    /*
    println(Editeur.buffer.text)
    Editeur.pressLetter('a')
    //Thread.sleep(1000);
    println(Editeur.buffer.text)
    Editeur.pressLetter('b')
    //Thread.sleep(1000);
    println(Editeur.buffer.text)
    Editeur.pressSeparator("\n")
    //Thread.sleep(1000);
    println(Editeur.buffer.text)
    Editeur.pressLetter('d')
    //Thread.sleep(1000);
    println(Editeur.buffer.text)
    */



    
    //println("\n\nhisto : " +Editeur.histo)
  }
}