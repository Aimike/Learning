# 无法提交到远程仓库

## 问题描述如图

![图片.png](https://i.loli.net/2019/12/25/eMFzXJqu7KY4ArL.png)

## 解决方案

**温柔型：** 

1. 通过`git pull `先将本地库更新到与远程库一致的版本，但要注意本地库后来做的修改可能被覆盖，最好使用`git fetch`(不会自动合并)，查看更新情况再有选择合并，或者先将本地库修改过的文件备份，`git pull`后再重新修改；

2. 再运行`git push`即可成功。

**暴力型：** 

1. git提供了一种强制上传的方式：`git push -f `，它会忽略版本不一致等问题，强制将本地库上传的远程库，但是一定要谨慎使用，因为`-f`会用**本地库覆盖掉远程库**，如果远程库上有重要更新，或者有其他同伴做的修改，也都会被覆盖，所以一定要在确定无严重后果的前提下使用此操作。

ps：远程仓库只有我自己用，所以直接就用第二种方案了。😄

![图片2.png](https://i.loli.net/2019/12/25/t4JGfaysrH2gVcT.png)

参考链接：<a href="https://blog.csdn.net/weixin_44118318/article/details/85030461">https://blog.csdn.net/weixin_44118318/article/details/85030461</a>


