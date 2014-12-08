import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import editeur.MacroManager
import action._

class TestMacroManager{
	
	var mm : MacroManager = _

	@Before def initialize() {
		mm = new MacroManager
	}

	// setMacro(key:String, ac: ActionComposite)
	
	@Test def verifySetMacro() {
		mm.setMacro("test", new ActionComposite)
		assert(mm.map.contains("test"))
	}

	@Test def verifySetMacroUpdate() {
		mm.setMacro("test", new ActionComposite)
		var ac = new ActionComposite
		mm.setMacro("test", ac)
		
		assert(mm.map.contains("test") && mm.map("test") == ac)
	}


	// getMacro(key:String): ActionComposite
	@Test def verifyGetMacro() {
		var ac = new ActionComposite
		mm.setMacro("test", ac)
		
		assert(mm.getMacro("test") == ac)
	}
}