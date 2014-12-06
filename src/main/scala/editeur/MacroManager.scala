package editeur

import scala.collection.mutable

import action._

class MacroManager{

	var map = mutable.Map[String, ActionComposite]()

	def setMacro(key:String, ac: ActionComposite){
		map(key) = ac
	}
	def getMacro(key:String): ActionComposite={
		return map(key)
	}
}