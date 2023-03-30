# 大数据第一次作业Scala练习

沈霁昀201250048

## 1.源程序&运行截图

```scala
import java.io.{File, PrintWriter}
import scala.io.Source

object WordCount {
  def main(args: Array[String]): Unit = {
    var lines: List[String] = readFile("test.txt")
    var wc = wordCnt(lines)
    writeFile("result.txt", wc)
  }

  def readFile(path: String): List[String] = {
    var file = Source.fromFile(path, "UTF-8")
    var lines = file.getLines().toList;
    return lines
  }

  def wordCnt(data: List[String]): List[(String, Int)] = {
    val words = data.map(_.split(" ")).flatten //将行拆分为单词，并展平
    val lowerWord = words.map(_.toLowerCase().replaceAll("[\\pP‘'“”]", "")) //全部小写，去除标点
    val word_Cnt = lowerWord.map((_, 1)).groupBy(_._1) //每个单词计数1，归并单词
    var ret = word_Cnt.map(t => (t._1, t._2.size)).toList //对每组求和
    ret = ret.sortBy(_._2).reverse //排序，按出现频率从高到低
    return ret
  }

  def writeFile(path: String, data: List[(String, Int)]): Unit = {
    var fw = new PrintWriter(new File(path));
    for (x <- data) {
      fw.write(x._1 + "\t" + x._2)
      fw.write("\n")
    }
    fw.close()
  }
}
```

运行截图：

## 2.实现思路

首先，按行读取文件的单词，再将每行的单词都按空格分开并去除标点，并“flatten”，即获取整个文件有重复单词集合。

然后，将每个单词变为（word，1）的K-V对，并通过按单词group进行归并，并对每组求和，即得到单词在全文出现次数。

最后，为了便于观察，按出现次数从高到低排序，并写入result.txt文件。

## 3.实验心得

体会到MapReduce的思想。将文章映射（map）为（word，1）的一个个单位键值对，再进行需要的归并（reduce），这里即按单词归并，最后写入文件。

关于scala及RDD的学习心得笔记，已放入[个人博客](https://stantonjoy.github.io/2022/09/25/BigData1-Scala/)，这里略去。

### Hadoop和Spark的 区别和联系

应用场景不同：

Hadoop是一个分布式数据存储架构，它将巨大的数据集分派到一个由普通计算机组成的集群中的多个节点进行存储，降低了硬件的成本。Spark是专为大规模数据处理而设计的快速通用的计算引擎，它要借助HDFS的数据存储。

处理速度不同：

Hadoop的MapReduce是分步对数据进行处理的，从磁盘中读取数据，进行一次处理，将结果写到磁盘，然后在从磁盘中读取更新后的数据，再次进行的处理，最后再将结果存入磁盘，这存取磁盘的过程会影响处理速度。Spark从磁盘中读取数据，把中间数据放到**内存**中，完成所有必须的分析处理，将结果写回集群，所以Spark更快。

容错性不同：

Hadoop将每次处理后的数据都写入到磁盘上，基本谈不上断电或者出错数据丢失的情况。Spark的数据对象存储在弹性分布式数据集 RDD，RDD是分布在一组节点中的只读对象集合，如果数据集一部分丢失，则可以根据于数据衍生过程对它们进行重建。而且RDD 计算时可以通过 CheckPoint 来实现容错。

联系：

Hadoop提供分布式数据存储功能HDFS，还提供了用于数据处理的MapReduce。 MapReduce是可以不依靠Spark数据的处理的。当然Spark也可以不依靠HDFS进行运作，它可以依靠其它的分布式文件系统。但是两者完全可以结合在一起，Hadoop提供分布式 集群和分布式 文件系统，Spark可以依附在Hadoop的HDFS代替MapReduce弥补MapReduce计算能力不足的问题。
