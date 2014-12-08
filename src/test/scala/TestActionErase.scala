import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionErase{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		edi.cursors(3)
		var aer = new ActionErase()
		aer.exec(edi, false)
		assert(edi.buffer.text == "tes" && edi.cursor==3, "Simple erase failure")

		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		aer.exec(edi, false)
		assert(edi.buffer.text == "" && edi.cursor==0, "Erase selection failure")
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		edi.cursors(3)


		var aer = new ActionErase()
		aer.exec(edi, false)
		aer.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Simple undo failure")

		edi.cursorSelectionBegin = 0
		aer.exec(edi, false)
		aer.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Undo selection erase failure")		
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		edi.cursors(3)

		var aer = new ActionErase()
		aer.exec(edi, false)
		aer.undo(edi)
		aer.redo(edi)
		assert(edi.buffer.text == "tes" && edi.cursor==3, "Simple redo failure")

		edi.cursorSelectionBegin = 0
		aer.exec(edi, false)
		aer.undo(edi)
		aer.redo(edi)
		assert(edi.buffer.text == "" && edi.cursor==0, "Redo selection erase failure")
	}

}