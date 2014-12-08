package editeur;

/** An [[editeur.Observed]] is observed by a [[editeur.Observator]] */
trait Observed{

	/** Array of his Observators */
	var obs : Array[Observator] = Array()
	
	/** Add an Observator
	* @param o observator to add
	*/
	def addObservator(o: Observator){
  		obs :+= o
  	}

  	/** Call update an each Observator */
	def updateObs() { 
		for (o <- obs) o.update() 
	}
}