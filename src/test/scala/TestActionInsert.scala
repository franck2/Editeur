import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionInsert{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Simple insertion failure")
		assert(edi.buffer.histo.getLastDo.get == ai, "Simple Add to Histo failure")

		ai.exec(edi, false)
		assert(edi.buffer.text == "testtest" && edi.cursor==8, "Double insertion failure")
		assert(edi.buffer.histo.getLastDo.get.isInstanceOf[ActionComposite], "Action merge in histo failure")
		
		var sep = new ActionInsert("\n")
		sep.exec(edi, false)
		assert(edi.buffer.text == "testtest\n" && edi.cursor==9, "separator insertion failure")
		assert(edi.buffer.histo.getLastDo.get == sep, "Separator Add to Histo failure")

		ai.exec(edi, false)
		assert(edi.buffer.text == "testtest\ntest" && edi.cursor==13, "insertion after separator failure")
		assert(edi.buffer.histo.getLastDo.get == ai, "Add to Histo after separator failure")
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		ai.undo(edi)
		assert(edi.buffer.text == "" && edi.cursor==0, "Simple undo failure")
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		ai.undo(edi)
		ai.redo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Simple redo failure")
	}

}