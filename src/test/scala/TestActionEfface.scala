import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionEfface{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		var aef = new ActionEfface()
		aef.exec(edi, false)
		assert(edi.buffer.text == "tes" && edi.cursor==3, "Simple efface failure")

		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		edi.moveSelectionLeft()		
		aef.exec(edi, false)
		assert(edi.buffer.text == "" && edi.cursor==0, "Efface selection failure")
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		var aef = new ActionEfface()
		aef.exec(edi, false)
		aef.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Simple undo failure")

		edi.cursorSelectionBegin = 0
		aef.exec(edi, false)
		aef.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Undo selection efface failure")		
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		var aef = new ActionEfface()
		aef.exec(edi, false)
		aef.undo(edi)
		aef.redo(edi)
		assert(edi.buffer.text == "tes" && edi.cursor==3, "Simple redo failure")

		edi.cursorSelectionBegin = 0
		aef.exec(edi, false)
		aef.undo(edi)
		aef.redo(edi)
		assert(edi.buffer.text == "" && edi.cursor==0, "Redo selection erase failure")
	}

}