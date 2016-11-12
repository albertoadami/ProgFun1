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
    if (r == 0) {
      if (c == 0)
        1
      else
        0
    } else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def balanceTailRec(chars: List[Char], balance: Int): Boolean = {
      if (chars.isEmpty)
        return balance == 0

      if (chars.head == ')') {
        if (balance > 0)
          return balanceTailRec(chars.tail, balance - 1)
        else
          return false
      }

      if (chars.head == '(')
        return balanceTailRec(chars.tail, balance + 1)

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
