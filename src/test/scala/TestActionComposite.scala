import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import action._
import editeur._

class TestActionComposite{

	var edi : Editeur = _
	var ac : ActionComposite = _

	@Before def initialize() {
		edi = new Editeur
		ac = new ActionComposite 
 
		ac.addAction(new ActionInsert("test"))
		ac.addAction(new ActionInsert("test"))
		ac.addAction(new ActionInsert("test"))
	}

	@Test def verifyExec(){

		ac.exec(edi, false)

		assert(edi.buffer.text == "testtesttest" && edi.cursor==12 && edi.buffer.histo.getLastDo.get== ac,
			"Simple composite failure => text: "+edi.buffer.text + ", cursor: " + edi.cursor+", ac:"+ac)
	}

	@Test def verifyUndo(){
		
		ac.exec(edi, false) 
		ac.undo(edi)

		assert(edi.buffer.text == "" && edi.cursor==0,
			"Simple undo failure => text: "+edi.buffer.text + ", cursor: " + edi.cursor)		
	}

	@Test def verifyRedo(){

		ac.exec(edi, false) 
		ac.undo(edi)
		ac.redo(edi)

		assert(edi.buffer.text == "testtesttest" && edi.cursor==12,
			"Simple redo failure => text: "+edi.buffer.text + ", cursor: " + edi.cursor)
	}

	@Test def verify(){
		assert(ac.actions.length ==3, "failure addAction length")
	}

}