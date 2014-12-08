
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import editeur.Editeur

class TestEditeur{

	var edi : Editeur= _


	@Before def initialize() {
		edi = new Editeur
	}

	@Test def verifyCursors(){
		edi.cursors(-1)
		assert(edi.cursor == 0 && edi.cursorSelectionBegin==0 && edi.cursorSelectionEnd==0)
		edi.buffer.text = "aaaaaaaaaaaa"
		edi.cursors(5)
		assert(edi.cursor == 5 && edi.cursorSelectionBegin==5 && edi.cursorSelectionEnd==5)
	}

	@Test def verifyUpdate(){
		edi.buffer.text = "aaaaa"
		edi.cursor = 5
		edi.buffer.text = "a"
		edi.update()
		assert(edi.cursor==1)
	}

	@Test def verifyMoveSelectionLeft(){
		edi.buffer.text = "aaaaa"
		edi.cursors(5)
		edi.moveSelectionLeft()
		assert(edi.cursor==4&&edi.cursorSelectionBegin==4&&edi.cursorSelectionEnd==5)
	}

	@Test def verifyMoveSelectionRight(){
		edi.buffer.text = "aaaaa"
		edi.cursors(4)
		edi.moveSelectionRight()
		assert(edi.cursor==5&&edi.cursorSelectionBegin==4&&edi.cursorSelectionEnd==5)
	}


}
