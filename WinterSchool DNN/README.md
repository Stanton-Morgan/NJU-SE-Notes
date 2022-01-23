# MindSpore Winter Camp

华为寒假MindSpore实践课，12h速成DNN…

华为讲的还是可以的，老师准备很充分，资源配备也很好，云服务器体验实在太顶了，那模型我本机十几天都不一定训练的出来

一点点文档没有的知识记录：

+ 特征图大小：(n+2p-f)/s +1向下取整

  n：边长

  p：填充（padding）

  f：filter边长

  s：步长（stride）

  因此代码中全连接时数据都是经过上述公式计算的，不是随便填的

+ `net_loss = nn.SoftmaxCrossEntropyWithLogits(sparse=True, reduction='mean')`

  sparse指不对数据进行[One-Hot编码](https://zhuanlan.zhihu.com/p/37471802)

  标签编码的问题是它假定类别值越高，该类别更好，对于编号1、2、3，会有1 + 3 = 4，4 / 2 = 2，将类别之间建立起错误地关联
