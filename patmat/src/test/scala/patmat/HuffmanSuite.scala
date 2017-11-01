package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)

	}

  trait TestTable {
    val table1 = List(('a', List(0, 1, 1)), ('b', List(0, 2, 2)))
  }


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton for some lists") {

  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("decode of some lists") {
    new TestTrees {
      assert(decode(t1, List(0)) == "a".toList)
      assert(decode(t1, List(1)) == "b".toList)
      assert(decode(t2, List(0, 0)) == "a".toList)
      assert(decode(t2, List(0, 1)) == "b".toList)
      assert(decode(t2, List(1)) == "d".toList)
      assert(decode(t2, List(0, 0, 0, 1)) == "ab".toList)
      assert(decode(t2, List(0, 0, 1)) == "ad".toList)
      assert(decode(t2, List(0, 1, 1)) == "bd".toList)
    }
  }

  test("encode of some lists") {
    new TestTrees {
      assert(encode(t1)(List('a')) == List(0))
      assert(encode(t1)(List('b')) == List(1))
      assert(encode(t1)(List('a', 'b')) == List(0, 1))
      assert(encode(t1)(List('b', 'a')) == List(1, 0))
      assert(encode(t2)(List('a')) == List(0, 0))
      assert(encode(t2)(List('b')) == List(0, 1))
      assert(encode(t2)(List('d')) == List(1))
      assert(encode(t2)(List('a', 'b')) == List(0, 0, 0, 1))
      assert(encode(t2)(List('a', 'd')) == List(0, 0, 1))
      assert(encode(t2)(List('b', 'd')) == List(0, 1, 1))
      assert(encode(t2)(List('a', 'b', 'd')) == List(0, 0, 0, 1, 1))

    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("test table codeBits with some cases") {
    new TestTable {
      assert(codeBits(table1)('a') === List(0, 1, 1))
      assert(codeBits(table1)('b') === List(0, 2, 2))
      assert(codeBits(table1)('c') === List())
    }
  }

  test("convert on some trees") {
    new TestTrees {
      assert(convert(t1) === List(('a', List(0)), ('b', List(1))))
    }
  }


}
