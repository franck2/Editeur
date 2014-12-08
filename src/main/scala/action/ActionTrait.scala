package action

import editeur._

/** Trait of an action*/
trait ActionTrait{

	//execute l'action en se basant sur le curseur
	def exec(e:Editeur,skipHisto:Boolean) : Unit

	//ré-éxecute l'action en se basant sur la sauvegarde des éléments nécessaires de l'éditeur
	def redo(e:Editeur) : Unit

	//execute l'action inverse
	def undo(e:Editeur) : Unit

	//def addToHisto(e:Editeur) : Unit

}