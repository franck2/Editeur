package editeur

/** An [[editeur.Observer]] observs a [[editeur.Observed]] */
trait Observer{
	def update():Unit
}