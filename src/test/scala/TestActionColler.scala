import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionColler{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0
		var acopy = new ActionCopier()
		acopy.exec(edi, false)

		var ac = new ActionColler()
		ac.exec(edi, false) 

		assert(edi.buffer.text == "testtest" && edi.cursor==8 && edi.clipboard=="test",
			"Simple paste failure => text: "+edi.buffer.text + ", cursor: " + edi.cursor+", clipboard: "+ edi.clipboard)
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0		
		var acopy = new ActionCopier()
		acopy.exec(edi, false)

		var ac = new ActionColler()
		ac.exec(edi, false) 
		ac.undo(edi)

		assert(edi.buffer.text == "test" && edi.cursor==4 && edi.clipboard=="test", "Simple undo failure")		
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0
		var acopy = new ActionCopier()
		acopy.exec(edi, false)

		var ac = new ActionColler()
		ac.exec(edi, false) 
		ac.undo(edi)
		ac.redo(edi)

		assert(edi.buffer.text == "testtest" && edi.cursor==8 && edi.clipboard=="test", "Simple redo failure")
	}

}