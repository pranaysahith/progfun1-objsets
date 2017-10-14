package objsets

import TweetReader._
<<<<<<< HEAD
=======
//import scala.collection.parallel.ParIterableLike.Foreach
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String =
    "User: " + user + "\n" +
      "Text: " + text + " [" + retweets + "]"
}

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet

  /**
   * This is a helper method for `filter` that propagetes the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
<<<<<<< HEAD
  def union(that: TweetSet): TweetSet 
=======
  def union(that: TweetSet): TweetSet
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
<<<<<<< HEAD
  def mostRetweeted: Tweet = ???
=======
  def mostRetweeted: Tweet
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
<<<<<<< HEAD
  def descendingByRetweet: TweetList = ???
=======
  def descendingByRetweet: TweetList
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359

  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit
}

class Empty extends TweetSet {
<<<<<<< HEAD
  def filter(p: Tweet => Boolean) = new Empty
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = new Empty
  def union(that: TweetSet): TweetSet = that
=======
  def filter(p: Tweet => Boolean): TweetSet = this
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = this

  def union(that: TweetSet): TweetSet = that

  def mostRetweeted: Tweet = throw new Exception("java.util.NoSuchElementException")

  def descendingByRetweet: TweetList = Nil

>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359
  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

<<<<<<< HEAD
  def filter(p: Tweet => Boolean): TweetSet = {
    val res: TweetSet = filterAcc(p, new Empty)
    res.union(left.filterAcc(p, res)).union(right.filterAcc(p, res))
  }
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = if (p(elem)) acc.incl(elem) else acc
  def union(that: TweetSet): TweetSet = {
    val res = that.incl(elem)
    res.union(left).union(right)
  }
=======
  def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, this).union(left.filter(p)).union(right.filter(p))

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = {
    if (p(this.elem)) new NonEmpty(this.elem, new Empty, new Empty)
    else new Empty
  }

  def union(that: TweetSet): TweetSet = {
    val that2 = that.incl(this.elem)
    val that3 = left.union(that2)
    right.union(that3)
  }

  def mostRetweeted: Tweet = {
    val res = this.elem
    val lres = this.left match {
      case e: Empty => {
//        println("Ahem left");
        this.elem
      }
      case n: NonEmpty => n.mostRetweeted
    }

    val rres = this.right match {
      case e: Empty => {
//        println("Ahem right");
        this.elem
      }
      case n: NonEmpty => n.mostRetweeted
    }
    if (lres.retweets < rres.retweets)
      if (res.retweets < lres.retweets)
        res
      else lres
    else rres

  }

  def descendingByRetweet: TweetList = {

    def listRec(tl: TweetList, ts: TweetSet): TweetList = {
      ts match {
        case e: Empty => tl
        case n: NonEmpty =>
          val mr = n.mostRetweeted
          println(mr)
          listRec(new Cons(mr, tl), ts.remove(mr))
      }
    }
    listRec(Nil, this)
  }

>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359
  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }
}

trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
}

object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

<<<<<<< HEAD
  lazy val googleTweets: TweetSet = ???
  lazy val appleTweets: TweetSet = ???
=======
  lazy val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => google.exists(w => tweet.text.contains(w)))
  lazy val appleTweets: TweetSet = TweetReader.allTweets.filter(tweet => apple.exists(w => tweet.text.contains(w)))
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
<<<<<<< HEAD
  lazy val trending: TweetList = ???
}

object Main extends App {
  // Print the trending tweets
  GoogleVsApple.trending foreach println
=======
  lazy val trending: TweetList = (googleTweets.union(appleTweets)).descendingByRetweet
}

object Main extends App {

 
  // Print the trending tweets
  GoogleVsApple.trending foreach println
  //  print(gadget.remove(gadget.mostRetweeted).mostRetweeted)
>>>>>>> 760ca6d381608952ad04395bb673d1803dd54359
}
