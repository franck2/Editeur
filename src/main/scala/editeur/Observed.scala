package editeur;

trait Observed{

	var obs : Array[Observator] = Array()
	
	def addObservator(bo: Observator){
  		obs :+= bo
  	}

	def updateObs() { 
		for (o <- obs) o.update() 
	}
}