import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionRemove{

	var edi : Editeur = _ 

	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyExec(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		var ar = new ActionRemove(0,4)
		ar.exec(edi, false)
		assert(edi.buffer.text == "" && edi.cursor==0, "Simple remove failure")
		assert(edi.buffer.histo.getLastDo.get == ar, "Simple add to Histo failure")

		ai.exec(edi, false)
		ar = new ActionRemove(0,2)
		ar.exec(edi, false)
		ar.exec(edi, false)
		assert(edi.buffer.text == "" && edi.cursor==0, "Double remove failure")
		assert(edi.buffer.histo.getLastDo.get.isInstanceOf[ActionComposite], "Action merge in histo failure")
	}

	@Test def verifyUndo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		var ar = new ActionRemove(0,4)
		ar.exec(edi, false)
		ar.undo(edi)
		assert(edi.buffer.text == "test" && edi.cursor==4, "Simple undo failure => text: "+edi.buffer.text+", cursor: "+edi.cursor)
	}

	@Test def verifyRedo(){
		var ai = new ActionInsert("test")
		ai.exec(edi, false)
		var ar = new ActionRemove(0,4)
		ar.exec(edi, false)
		ar.undo(edi)
		ar.redo(edi)
		assert(edi.buffer.text == "" && edi.cursor==0, "Simple redo failure")
	}

}