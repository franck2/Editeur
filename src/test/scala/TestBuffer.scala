
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

import editeur.Buffer

class TestBuffer{

	var buf : Buffer = _

  @Before def initialize() {
    buf = new Buffer
  }

  //Buffer.Add

  @Test def verifyAddOnEmptyBuffer() { 
  	buf.add("test", 0)
    assert(buf.text == "test")
  }

  @Test def verifyAddOnNonEmptyBuffer() {
  	buf.add("test", 0)
  	buf.add("test", 0)
    assert(buf.text=="testtest")
  }

  @Test def verifyAddInvalidPositionNeg() {
  	buf.add("test", 0)
  	buf.add("test", 0)
  	buf.add("test", -1)
    assert(buf.text=="testtest")
  }

  @Test def verifyAddInvalidPositionOver() {
  	buf.add("test", 4)
    assert(buf.text=="")
  }


  //Buffer.getSubstring

  @Test def verifyGetSub{
  	buf.add("abcd",0)
  	println("get sub" + buf.getSubstring(1,2))
  	assert(buf.getSubstring(1,2)=="b")
  }

  @Test def verifyGetSubReverseOrder{
  	buf.add("abcd",0)
  	assert(buf.getSubstring(2,1)=="b")
  }

  @Test def verifyGetSubAll{
  	buf.add("abcdef",0)
  	assert(buf.getSubstring(0,6)=="abcdef")
  }

  @Test def verifyGetSubInvalidInput{
  	buf.add("abcdef",0)
  	assert(buf.getSubstring(-1,5)=="")
  }

  //Buffer.efface
  @Test def verifyEfface{
  	buf.add("aaaab",0)
  	buf.efface()
  	assert(buf.text=="aaaa")
  }

  //Beffer.remove
  @Test def verifyRemove{
  	buf.add("aabbaa",0)
  	buf.remove(2,4)
  	assert(buf.text=="aaaa")
  }
}
