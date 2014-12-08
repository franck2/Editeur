import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionCopie{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		

		var ac = new ActionCopier()
		ac.exec(edi, false)
		assert(edi.buffer.text == "test" && edi.cursor==0 && edi.clipboard=="test", "Simple copy failure")
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		

		var ac = new ActionCopier()
		ac.exec(edi, false)
		ac.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==0 && edi.clipboard=="", "Simple undo failure")		
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		

		var ac = new ActionCopier()
		ac.exec(edi, false)
		ac.undo(edi)
		ac.redo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==0 && edi.clipboard=="test", "Simple redo failure")
	}

}