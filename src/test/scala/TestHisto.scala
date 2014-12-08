
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import editeur.Histo
import action._

class TestHisto{

	var histo : Histo = _

  @Before def initialize() {
    histo = new Histo
  }
  
  // Histo.empileActionUndo(ac : ActionTrait)

  @Test def verifyEmpileActionUndo(){
    histo.empileActionUndo(new ActionComposite())
    assert(histo.pile_undo.length==1)
  }

  // Histo.empileActionDo(ac : ActionTrait, eraseUndo:Boolean = true)

  @Test def verifyEmpileActionDo(){
    histo.empileActionDo(new ActionComposite(), false)
    assert(histo.pile_do.length==1)
  }

  @Test def verifyEmpileActionDoAndErase(){
    histo.empileActionUndo(new ActionComposite())
    histo.empileActionDo(new ActionComposite(), true)
    assert(histo.pile_do.length==1 && histo.pile_undo.length==0)
  }
  
  // Histo.depileActionDo()

  @Test def verifyDepileActionDo(){
    histo.empileActionDo(new ActionComposite(), false)
    histo.depileActionDo()
    assert(histo.pile_do.length==0)
  }

  // Histo.depileActionUndo()

  @Test def verifyDepileActionUndo(){
    histo.empileActionUndo(new ActionComposite())
    histo.depileActionUndo()
    assert(histo.pile_undo.length==0)
  }

  // Histo.erasePileUndo()
  @Test def verifyErasePileUndo(){
    histo.empileActionUndo(new ActionComposite())
    histo.erasePileUndo()
    assert(histo.pile_undo.length==0)
  }

  // Histo.getLastDo() : Option[ActionTrait]

  @Test def verifyGetLastDo(){
    var ac = new ActionComposite()
    histo.empileActionDo(ac, false)
    assert(histo.getLastDo().get == ac )
  }
  
  // Histo.getLastUndo() : Option[ActionTrait]

  @Test def verifyGetLastUndo(){
    var ac = new ActionComposite()
    histo.empileActionUndo(ac)
    assert(histo.getLastUndo().get == ac )
  }
}
