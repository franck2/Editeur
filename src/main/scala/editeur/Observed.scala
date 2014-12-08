package editeur;

/** An [[editeur.Observed]] is observed by a [[editeur.Observer]] */
trait Observed{

	/** Array of his Observers */
	var obs : Array[Observer] = Array()
	
	/** Add an Observer
	* @param o Observer to add
	*/
	def addObserver(o: Observer){
  		obs :+= o
  	}

  	/** Remove an Observer
	* @param o Observer to remove
	*/
	def removeObserver(o: Observer){
  		obs = (obs.toBuffer - o).toArray
  	}

  	/** Call update an each Observer */
	def updateObs() { 
		for (o <- obs) o.update() 
	}
}