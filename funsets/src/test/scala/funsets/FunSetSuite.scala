package funsets

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestContains {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  trait TestSingleton {
    val s1 = singletonSet(1)
    val s2 = singletonSet(-1)
  }

  trait TestSetsUnion {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val union1 = union(s1, s2)
    val union2 = union(s3, s4)
    val union3 = union(union1, union2)
  }

  trait TestSetsIntersect {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val union12 = union(s1, s2)
    val union23 = union(s2, s3)
    val union34 = union(s3, s4)
    val union41 = union(s4, s1)
    val union1234 = union(s1, union(s2, union(s3, s4)))
    val intersect1 = intersect(union12, union23)
    val intersect2 = intersect(union34, union23)
    val intersect3 = intersect(union34, union41)
    val intersect4 = intersect(union41, union23)
    val intersect5 = intersect(union12, union1234)
  }

  trait TestSetsDiff{
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val union12 = union(s1, s2)
    val union23 = union(s2, s3)
    val union34 = union(s3, s4)
    val diff1 = diff(union12, union23)
    val diff2 = diff(union23, union12)
    val diff3 = diff(union23, union34)
    val diff4 = diff(union34, union12)
  }

  trait TestSetsFilter {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val all = union(s1, union(s2, union(s3, s4)))//1, 2, 3, 4
    val onlyEven = filter(all, x => x%2 == 0)
    val onlyOdd = filter(all, x => x%2 != 0)
    val greaterOrZero = filter(all, x => x >= 0)
    val lowerThanZero = filter(all, x => x < 0)
  }

  trait TestSetsForAll {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val oneTwo = union(s1, s2)
    val threeFour = union(s3, s4)
    val oneTwoThreeFour = union(oneTwo, threeFour)
  }

  trait TestSetsExists {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val oneTwo = union(s1, s2)
    val threeFour = union(s3, s4)
  }

  trait TestSetsMap {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val oneTwo = union(s1, s2)
    val threeFour = union(s3, s4)
    val oneTwoThreeFour = union(oneTwo, threeFour)
  }

  test("singletonSet return a FunSet of a unique int") {
    new TestSingleton {
      assert(s1(1), "s1 contains 1")
      assert(s2(-1), "s2 contains -1")
      assert(!s1(2), "s1 doesn't contains 2")
      assert(!s1(-1), "s1 doens't contains -1")
      assert(!s2(1), "s2 doens't contians 1")
      assert(!s2(1), "s2 dosnt't contains 1")
    }
  }

  test("contains return true if set contains a specified value otherwise false") {
    new TestContains  {
      assert(contains(s1, 1), "s1 contains 1")
      assert(contains(s2, 2), "s2 contains 2")
      assert(contains(s3, 3), "s3 contains 3")
      assert(!contains(s1, 2), "s1 doesn't contains 2")
      assert(!contains(s1, 3), "s1 doesn't contains 3")
      assert(!contains(s2, 1), "s2 doens't contains 1")
      assert(!contains(s2, 3), "s2 doens't contains 3")
      assert(!contains(s3, 1), "s3 deons't contains 1")
      assert(!contains(s3, 2), "s3 doens't contains 2")
    }
  }

  test("union returns a Set with element in A or B") {
    new TestSetsUnion {
      assert(contains(union1, 1), "Union of 1 and 2 contains 1")
      assert(contains(union1, 2), "Union of 1 and 2 contains 2")
      assert(contains(union2, 3), "Union of 3 and 4 contains 3")
      assert(contains(union2, 4), "Union of 3 and 4 contains 4")
      assert(contains(union3, 1), "Union of 1, 2, 3, 4 contains 1")
      assert(contains(union3, 2), "Union of 1, 2, 3, 4 contains 2")
      assert(contains(union3, 3), "Union of 1, 2, 3, 4 contains 3")
      assert(contains(union3, 4), "Union of 1, 2, 3, 4 contains 4")
      assert(!contains(union1, 3), "Union of 1 and 2 doens't contains 3")
      assert(!contains(union1, 4), "Union of 1 and 2 doens't contains 4")
      assert(!contains(union2, 1), "Union of 3 and 4 doens't contains 1")
      assert(!contains(union2, 2), "Union of 3 and 4 doens't contains 2")
      assert(!contains(union3, 5), "Union of 1, 2, 3, 4 doens't contains 5")
      assert(!contains(union3, 6), "Union of 1, 2, 3, 4 doens't contains 6")
    }

  }

  test("intersect returns a Set with element in A and B") {
    new TestSetsIntersect {
      assert(contains(intersect1,2), "Intersect of (1, 2) and (2, 3) contains 2")
      assert(!contains(intersect1, 1), "Intersect of (1, 2) and (2, 3) doens't contains 1")
      assert(!contains(intersect1, 3), "Intersect of (1, 2) and (2, 3) doens't contains 3")
      assert(!contains(intersect1, 4), "Intersect of (1, 2) and (2, 3) doens't contains 4")
      assert(contains(intersect2,3), "Intersect of (3, 4) and (2, 3) contains 3")
      assert(!contains(intersect2, 4), "Intersect of (3, 4) and (2, 3) doens't contains 4")
      assert(!contains(intersect2, 2), "Intersect of (3, 4) and (2, 3) doens't contains 2")
      assert(!contains(intersect2, 1), "Intersect of (3, 4) and (2, 3) doens't contains 1")
      assert(contains(intersect3,4), "Intersect of (3, 4) and (4, 1) contains 4")
      assert(!contains(intersect3, 1), "Intersect of (3, 4) and (4, 1) doens't contains 1")
      assert(!contains(intersect3, 2), "Intersect of (3, 4) and (4, 1) doens't contains 2")
      assert(!contains(intersect3, 3), "Intersect of (3, 4) and (4, 1) doens't contains 3")
      assert(!contains(intersect4,1), "Intersect of (4, 1) and (2, 3) doenst' contains 1")
      assert(!contains(intersect4, 2), "Intersect of (4, 1) and (2, 3) doenst' contains 2")
      assert(!contains(intersect4, 3), "Intersect of (4, 1) and (2, 3) doenst' contains 3")
      assert(!contains(intersect4, 4), "Intersect of (4, 1) and (2, 3) doenst' contains 4")
      assert(contains(intersect5,1), "Intersect of (1, 2) and (1, 2, 3, 4) contains 1")
      assert(contains(intersect5, 2), "Intersect of (1, 2) and (1, 2, 3, 4) contains 2")
      assert(!contains(intersect5, 3), "Intersect of (1, 2) and (1, 2, 3, 4) doesn't contains 3")
      assert(!contains(intersect5, 4), "Intersect of (1, 2) and (1, 2, 3, 4) doesn't contains 4")
    }
  }

  test("diff returns a Set with elements in A that aren't in B") {
    //val diff1 = diff(union12, union23)
    //val diff2 = diff(union23, union12)
    //val diff3 = diff(union23, union34)
    //val diff4 = diff(union34, union12)
    new TestSetsDiff {
      assert(contains(diff1, 1), "Diff of (1, 2) and (2,3) contains 1")
      assert(!contains(diff1, 2), "Diff of (1, 2) and (2,3) doesn't contains 2")
      assert(!contains(diff1, 3), "Diff of (1, 2) and (2,3) doesn't contains 3")
      assert(!contains(diff1, 4), "Diff of (1, 2) and (2,3) doesn't contains 4")
      assert(!contains(diff2, 1), "Diff of (2, 3) and (1,2) doens't contains 1")
      assert(!contains(diff2, 2), "Diff of (2, 3) and (1,2) doesn't contains 2")
      assert(contains(diff2, 3),  "Diff of (2, 3) and (1,2) contains 3")
      assert(!contains(diff2, 4), "Diff of (2, 3) and (1, 2) doesn't contains 4")
      assert(!contains(diff3, 1), "Diff of (2, 3) and (3,4) doens't contains 1")
      assert(contains(diff3, 2), "Diff of (2, 3) and (3,4) contains 2")
      assert(!contains(diff3, 3),  "Diff of (2, 3) and (3,4) doesn't contains 3")
      assert(!contains(diff3, 4), "Diff of (2, 3) and (3, 4) doesn't contains 4")
      assert(!contains(diff4, 1), "Diff of (3, 4) and (1, 2) doens't contains 1")
      assert(!contains(diff4, 2), "Diff of (3, 4) and (1, 2) doensn't contains 2")
      assert(contains(diff4, 3),  "Diff of (3, 4) and (1, 2) contains 3")
      assert(contains(diff4, 4), "Diff of (3, 4) and (1, 2) contains 4")
    }
  }

  test("filter returns the elements of set A that satisfy a function") {
    new TestSetsFilter {
      assert(!contains(onlyEven, 1), "Set onlyEven doesn't contains 1")
      assert(contains(onlyEven, 2), "Set onlyEven doesn't contains 2")
      assert(!contains(onlyEven, 3), "Set onlyEven doesn't contains 3")
      assert(contains(onlyEven, 4), "Set onlyEven doesn't contains 4")
      assert(contains(onlyOdd, 1), "Set onlyOdd contains 1")
      assert(!contains(onlyOdd, 2), "Set onlyOdd doesn't contains 2")
      assert(contains(onlyOdd, 3), "Set onlyOdd contains 3")
      assert(!contains(onlyOdd, 4), "Set onlyOdd doesn't contains 4")
      assert(contains(greaterOrZero, 1), "Set greaterOrZero contains 1")
      assert(contains(greaterOrZero, 2), "Set greaterOrZero contains 2")
      assert(contains(greaterOrZero, 3), "Set greaterOrZero contains 3")
      assert(contains(greaterOrZero, 4), "Set greaterOrZero contains 4")
      assert(!contains(lowerThanZero, 1), "Set lowerThanZero contains 1")
      assert(!contains(lowerThanZero, 2), "Set lowerThanZero contains 2")
      assert(!contains(lowerThanZero, 3), "Set lowerThanZero contains 3")
      assert(!contains(lowerThanZero, 4), "Set lowerThanZero contains 4")
    }
  }

  test("forall returns if all elements of a set satisfy a function") {
    new TestSetsForAll {
      assert(forall(oneTwoThreeFour, _ > 0), "(1, 2, 3, 4) contains only lower than zero elements")
      assert(!forall(oneTwoThreeFour, _ < 0), "(1, 2, 3, 4) doesn't contains any negative number")
      assert(forall(s1, _ == 1), "(1) contains only it self")
      assert(!forall(s2, _ ==1), "(2) doens't contains 1")
      assert(!forall(s3, _ == 1), "(3) doens0t contains 1 ")
      assert(!forall(s4, _ == 1), "(4) doens0t contains 1 ")
      assert(!forall(oneTwoThreeFour, _ % 2 == 0), "(1, 2, 3, 4) doens't contains only odd numbers")
      assert(!forall(oneTwoThreeFour, _ % 2 != 0), "(1, 2, 3, 4) doens't contains only even numbers")
    }
  }

  test("exists returs true if exists an element that satisfy a function") {
    new TestSetsExists {
      assert(exists(oneTwo, _ > 0), "(1, 2) contains a > 0 elements")
      assert(!exists(oneTwo, _ < 0), "(1, 2) doesn't contains a < 0 elements")
      assert(exists(oneTwo, _ == 1), "(1, 2) contains element 1")
      assert(exists(oneTwo, _ == 2), "(1, 2) contains element 2")
      assert(!exists(oneTwo, _ == 3), "(1, 2) contains element 3")
      assert(!exists(oneTwo, _ == 4), "(1, 2) contains element 4")
      assert(exists(threeFour, _ > 0), "(3, 4) contains a > 0 elements")
      assert(!exists(threeFour, _ < 0), "(3, 4) doesn't contains a < 0 elements")
      assert(!exists(threeFour, _ == 1), "(3, 4) contains element 1")
      assert(!exists(threeFour, _ == 2), "(3, 4) contains element 2")
      assert(exists(threeFour, _ == 3), "(3, 4) contains element 3")
      assert(exists(threeFour, _ == 4), "(3, 4) contains element 4")
    }
  }

  test("map returns a Set applying a function to each elements") {
    new TestSetsMap{
      assert(FunSets.toString(map(oneTwo, _ * 2)) == "{2,4}", "Applied function * 2")
      assert(FunSets.toString(map(oneTwo, _ - 1)) == "{1, 3}", "Applied function -1")
    }
  }

}
