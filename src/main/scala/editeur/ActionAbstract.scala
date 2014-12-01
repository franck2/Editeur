package editeur

trait ActionTrait{

	def exec() : Unit
	def undo() : Unit

}