package editeur

/** An [[editeur.Observator]] observs a [[editeur.Observed]] */
trait Observator{
	def update():Unit
}