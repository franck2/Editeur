import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionCouper{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0
		var acut = new ActionCouper()
		acut.exec(edi, false)

		assert(edi.buffer.text == "" && edi.cursor==0 && edi.clipboard=="test",
			"Simple paste failure => text: "+edi.buffer.text + ", cursor: " + edi.cursor+", clipboard: "+ edi.clipboard)
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0		
		var acut = new ActionCouper()
		acut.exec(edi, false)
		acut.undo(edi)

		assert(edi.buffer.text == "test" && edi.cursor==4 && edi.clipboard=="", "Simple undo failure")		
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)

		edi.cursorSelectionBegin=0
		var acut = new ActionCouper()
		acut.exec(edi, false)
		acut.undo(edi)
		acut.redo(edi)

		assert(edi.buffer.text == "" && edi.cursor==0 && edi.clipboard=="test",
			"Simple redo failure=> text: "+edi.buffer.text + ", cursor: " + edi.cursor+", clipboard: "+ edi.clipboard)
	}

}