package editeur

import scala.collection.mutable

import action._

/** Conservs [[action.ActionComposite]] with a associated names */
class MacroManager{

	/** Map of (name, ActionComposite) */
	var map = mutable.Map[String, ActionComposite]()

	/** Set an [[action.ActionComposite]] to the given key
	* @param key name of the macro
	* @param ac [[action.ActionComposite]] to map with the key
	*
	* If the key already contains a value, it will by overrided
	*/
	def setMacro(key:String, ac: ActionComposite){
		map(key) = ac
	}

	/** Return the [[action.ActionComposite]] associated to the key*/
	def getMacro(key:String): ActionComposite={
		return map(key)
	}
}