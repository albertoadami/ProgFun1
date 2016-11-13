package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {

    def pascalRecursive(c: Int, r: Int): Int = {
      if (r == 0 && c == 0)
          1
      else if(c < 0 || c > r )
        0
      else
        pascalRecursive(c - 1, r - 1) + pascalRecursive(c, r - 1)

    }

    if(r < 0)
      throw new IllegalArgumentException()
    else
       pascalRecursive(c, r)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def balanceTailRec(chars: List[Char], balance: Int): Boolean = {
      if (chars.isEmpty)
         balance == 0
      else if (chars.head == ')') {
              if (balance > 0)
                balanceTailRec(chars.tail, balance - 1)
              else
                false
            }

      else if (chars.head == '(')
        balanceTailRec(chars.tail, balance + 1)
      else
        balanceTailRec(chars.tail, balance)
    }
    balanceTailRec(chars, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (money < 0 || coins.isEmpty)
      0
    else
      countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}
