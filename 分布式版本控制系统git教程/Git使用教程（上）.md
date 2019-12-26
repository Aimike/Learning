# Git使用教程（上）
<!-- TOC -->

- [Git使用教程（上）](#git使用教程上)
    - [什么是Git？](#什么是git)
    - [为什么用Git？](#为什么用git)
    - [分布式与集中式版本控制系统的区别？](#分布式与集中式版本控制系统的区别)
    - [Windows下安装](#windows下安装)
    - [创建版本库(Repository)](#创建版本库repository)
    - [添加文件到版本库](#添加文件到版本库)
    - [查看当前仓库状态](#查看当前仓库状态)
    - [版本回退](#版本回退)
    - [工作区和暂存区](#工作区和暂存区)
    - [查看工作区和版本库里面最新版本的区别](#查看工作区和版本库里面最新版本的区别)
    - [撤销修改](#撤销修改)
    - [删除文件](#删除文件)
    - [远程仓库](#远程仓库)
    - [添加远程仓库](#添加远程仓库)
    - [从远程库克隆](#从远程库克隆)

<!-- /TOC -->

## 什么是Git？

    Git是一个用C语言编写的分布式版本控制系统。

## 为什么用Git？

    Git可以很方便的进行版本控制，比如：今天你修改了自己版本库中的文件A，过了一个星期，你又想回退到一周前的文件A里的内容，这个时候Git就可以帮你很方便的回退到你想要的版本。

## 分布式与集中式版本控制系统的区别？

集中式：

    最大弊端必须联网才能工作，如果网速不好的时候效率极低。

分布式：

    不联网也可以使用！
    没有“中央服务器”，每个人的电脑上都具有完整的版本库，这也意味着如果你的版本库出了差错，可以从你的同事电脑上拷贝一个版本库就可以了。
    在项目中，如果你在你的电脑上修改了文件A，你的同事也在他的电脑上修改了文件A，那么这时，你们只需要将各自的文件A推送给对方，这样就可以互相看到对方的修改了。但是如果你和你的同事不在同一局域网下，这样就需要一台服务器来为你们提供一个交换修改的功能，与中央服务器不同的是，此处的服务器仅仅提供一个交换修改的功能。

## Windows下安装

下载后按默认安装即可。

注：安装完成后，需要在Git -> GitBash中（自报家门）使用。

```ruby
git config --global user.name "Your Name"
git config --global user.email "email@example.com"
```

--global属性，表示你这台机器上所有的Git仓库都会使用这个配置

## 创建版本库(Repository)

版本库又名仓库，你可以简单理解成一个目录，这个目录里面的所有文件都可以被Git管理起来，每个文件的修改、删除，Git都能跟踪，以便任何时刻都可以追踪历史，或者在将来某个时刻可以“还原”。

**步骤：**
    
1. 创建目录，右键在当前目录下打开GitBash

2. 运行`git init`
创建成功后，目录下会多了一个`.git`文件（默认是隐藏的，执行`ls -ah`即可看到），Git就是根据这个文件进行版本控制的，所以千万不要乱改这个文件。

## 添加文件到版本库

  注意，所有的版本控制系统，只能跟踪文本文件的改动，比如TXT文件，网页，所有的程序代码等等，版本控制系统可以告诉你每次的改动，比如在第5行加了一个单词“Linux”，在第8行删了一个单词“Windows”。而图片、视频这些二进制文件，虽然也能由版本控制系统管理，但没法跟踪文件的变化，只能把二进制文件每次改动串起来，也就是只知道图片从100KB改成了120KB，但到底改了啥，版本控制系统不知道，也没法知道。（Microsoft的Word格式是二进制格式） 

  ps:文本编码建议使用标准的UTF-8编码

**步骤：**

1. 告诉Git，添加文件到仓库`git add filename.xxx`，其中filename为你的文件名，xxx为后缀名。

2. 用`git commit`将文件提交到仓库。

举例：

```ruby
git commit -m "a readme file"
[master (root-commit) eaadf2e] a readme file
 1 file changed, 2 insertions(+)
 create mode 100644 readme.txt
```

-m后面输入的是本次提交的说明

## 查看当前仓库状态

```ruby
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   readme.txt

no changes added to commit (use "git add" and/or "git commit -a")
```

**注：** git status:查看仓库当前的状态，上面的命令输出告诉我们，readme.txt被修改过了，但还没有准备提交的修改。

```ruby
$ git diff readme.txt 
diff --git a/readme.txt b/readme.txt
index 46d49bf..9247db6 100644
--- a/readme.txt
+++ b/readme.txt
@@ -1,2 +1,2 @@
-Git is a version control system.
+Git is a distributed version control system.
 Git is free software.
```
**注：** git diff：查看difference（具体修改了哪些地方），从上面的命令输出看到，我们在第一行添加了一个distributed单词。知道了对readme.txt作了什么修改后，再把它提交到仓库就放心多了

## 版本回退

```ruby
$ git log
commit 1094adb7b9b3807259d8cb349e7df1d4d6477073 (HEAD -> master)
Author: Michael Liao <askxuefeng@gmail.com>
Date:   Fri May 18 21:06:15 2018 +0800

    append GPL

commit e475afc93c209a690c39c13a46716e8fa000c366
Author: Michael Liao <askxuefeng@gmail.com>
Date:   Fri May 18 21:03:36 2018 +0800

    add distributed

commit eaadf4e385e865d25c48e7ca9c8395c3f7dfaef0
Author: Michael Liao <askxuefeng@gmail.com>
Date:   Fri May 18 20:59:18 2018 +0800

    wrote a readme file
```

**注：** git log命令显示从最近到最远的提交日志，我们可以看到3次提交，最近的一次是append GPL，上一次是add distributed，最早的一次是wrote a readme file。

**ps：** 如果嫌输出信息太多，看得眼花缭乱的，可以试试加上--pretty=oneline参数，像下面这样

```ruby
$ git log --pretty=oneline
1094adb7b9b3807259d8cb349e7df1d4d6477073 (HEAD -> master) append GPL
e475afc93c209a690c39c13a46716e8fa000c366 add distributed
eaadf4e385e865d25c48e7ca9c8395c3f7dfaef0 wrote a readme file
```
**注：** 类似1094adb...的是commit id（版本号）

**版本回退步骤：** 准备把readme.txt回退到上一个版本，也就是add distributed的那个版本

1. Git必须知道当前版本是哪个版本，在Git中，用HEAD表示当前版本，上一个版本就是HEAD^，上上一个版本就是HEAD^^，当然往上100个版本写100个^比较容易数不过来，所以写成HEAD~100。

2. 执行`git reset`命令回退到上一个版本

````ruby
$ git reset --hard HEAD^
HEAD is now at e475afc add distributed
````

或者指定版本号回退(版本号没必要写全，前几位就可以了，Git会自动去找。)

```ruby
$ git reset --hard 1094a
HEAD is now at 83b0afe append GPL
```

Git的版本回退速度非常快，因为Git在内部有个指向当前版本的HEAD指针，当你回退版本的时候，Git仅仅是把HEAD从指向append GPL。

当你用`$ git reset --hard HEAD^`回退到add distributed版本时，再想恢复到append GPL，就必须找到append GPL的commit id。Git提供了一个命令`git reflog`用来记录你的每一次命令：

```ruby
$ git reflog
e475afc HEAD@{1}: reset: moving to HEAD^
1094adb (HEAD -> master) HEAD@{2}: commit: append GPL
e475afc HEAD@{3}: commit: add distributed
eaadf4e HEAD@{4}: commit (initial): wrote a readme file
```

从输出可知，append GPL的commit id是1094adb，执行git reset --hard 1094adb即可。

## 工作区和暂存区

Git和其他版本控制系统如SVN的一个不同之处就是有暂存区的概念。

**工作区（Working Directory）：**
就是你在电脑里能看到的目录，比如我的learngit文件夹就是一个工作区

**版本库（Repository）：**
工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。
Git的版本库里存了很多东西，其中最重要的就是称为stage（或者叫index）的暂存区，还有Git为我们自动创建的第一个分支master，以及指向master的一个指针叫HEAD。

![图片.png](https://i.loli.net/2019/12/24/YJcxMK4D5NTOkGj.png)

我们把文件往Git版本库里添加的时候，是分两步执行的：

第一步是用`git add`把文件添加进去，实际上就是把文件修改添加到暂存区；

第二步是用`git commit`提交更改，实际上就是把暂存区的所有内容提交到当前分支。

**实践举例**

1. 先对readme.txt做个修改，比如加上一行内容。

2. 然后，在工作区新增一个LICENSE文本文件（内容随便写）。

3. git status查看一下状态：

```ruby
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   readme.txt

Untracked files:
  (use "git add <file>..." to include in what will be committed)

	LICENSE

no changes added to commit (use "git add" and/or "git commit -a")
```

Git非常清楚地告诉我们，readme.txt被修改了，而LICENSE还从来没有被添加过，所以它的状态是Untracked。

4. 现在，使用两次命令git add，把readme.txt和LICENSE都添加后，用git status再查看一下：

```ruby
$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

	new file:   LICENSE
	modified:   readme.txt
```
现在，暂存区的状态就变成这样了：

![图片.png](https://i.loli.net/2019/12/24/L1vnXlVWC7p8QKB.png)

所以，git add命令实际上就是把要提交的所有修改放到暂存区（Stage），然后，执行git commit就可以一次性把暂存区的所有修改提交到分支。

```ruby
$ git commit -m "understand how stage works"
[master e43a48b] understand how stage works
 2 files changed, 2 insertions(+)
 create mode 100644 LICENSE
```

一旦提交后，如果你又没有对工作区做任何修改，那么工作区就是“干净”的：

```ruby
$ git status
On branch master
nothing to commit, working tree clean
```
现在版本库变成了这样，暂存区就没有任何内容了：

![图片.png](https://i.loli.net/2019/12/24/ImAMLWTh5gQK2Gf.png)

## 查看工作区和版本库里面最新版本的区别

`git diff HEAD -- readme.txt`命令可以查看工作区和版本库里面最新版本的区别：

```ruby
$ git diff HEAD -- readme.txt 
diff --git a/readme.txt b/readme.txt
index 76d770f..a9c5755 100644
--- a/readme.txt
+++ b/readme.txt
@@ -1,4 +1,4 @@
 Git is a distributed version control system.
 Git is free software distributed under the GPL.
 Git has a mutable index called stage.
-Git tracks changes.
+Git tracks changes of files.
```

## 撤销修改

命令`git checkout -- <file>`:丢弃工作区的修改。分这里有两种情况：

一种是**修改后还没有被放到暂存区**，使用`git checkout -- <file>`直接丢弃工作区的修改，撤销修改就回到和**版本库**一模一样的状态；（**修改后没有add**）

一种是**已经添加到暂存区后又作了修改**，现在，撤销修改就回到添加到**暂存区**后的状态。（**add后修改**）

总之，就是让这个文件回到最近一次git commit或git add时的状态。

**注：** `git checkout -- file`命令中的`--`很重要，没有`--`，就变成了“切换到另一个分支”的命令

如果已经做了**修改并且添加到了暂存区**，使用命令`git reset HEAD <file>`可以把暂存区的修改撤销掉（unstage），重新放回工作区，再使用`git checkout -- <file>`丢弃工作区修改。（**修改后**add）

## 删除文件

  删除工作区的文件后：

  1. 从版本库中彻底删除该文件，使用命令`git rm <file>`删掉，并且`git commit -m "remove file"`
  2. 删错了，从版本库中还原文件到工作区，`git checkout -- <file>`,**但是,只能恢复文件到最新版本!**

## 远程仓库

本地Git仓库和GitHub仓库之间的传输是通过SSH加密的，所以需要一些设置如下：

1. 创建SSH Key。在用户主目录下，看看有没有.ssh目录，如果有，再看看这个目录下有没有id_rsa和id_rsa.pub这两个文件，如果已经有了，可直接跳到下一步。如果没有，打开Git Bash，创建SSH Key：`ssh-keygen -t rsa -C "youremail@example.com"` 你需要把邮件地址换成你自己的邮件地址，然后一路回车，使用默认值即可，无需设置密码。

    成功之后，可以在用户主目录里找到`.ssh`目录，里面有`id_rsa`和`id_rsa.pub`两个文件，这两个就是SSH Key的秘钥对，`id_rsa`是私钥，不能泄露出去，`id_rsa.pub`是公钥，可以放心地告诉任何人。

2. 登陆GitHub，打开“Account settings”，“SSH Keys”页面：

3. 然后，点“Add SSH Key”，填上任意Title，在Key文本框里粘贴id_rsa.pub文件的内容

## 添加远程仓库

在本地仓库目录打开GitBash执行`git remote add origin git@github.com:qujialin/Java.git`

添加后，远程库的名字就是`origin`，这是Git默认的叫法，也可以改成别的，但是`origin`这个名字一看就知道是远程库

把本地库的所有内容推送到远程库上：
```ruby
$ git push -u origin master
Counting objects: 20, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (15/15), done.
Writing objects: 100% (20/20), 1.64 KiB | 560.00 KiB/s, done.
Total 20 (delta 5), reused 0 (delta 0)
remote: Resolving deltas: 100% (5/5), done.
To github.com:qujialin/Java.git
 * [new branch]      master -> master
Branch 'master' set up to track remote branch 'master' from 'origin'.
```
把本地库的内容推送到远程，用`git push`命令，实际上是把当前分支`master`推送到远程。

由于远程库是空的，我们第一次推送`master`分支时，加上了`-u`参数，Git不但会把本地的`master`分支内容推送的远程新的`master`分支，还会把本地的`master`分支和远程的`master`分支关联起来，在以后的推送或者拉取时就可以简化命令。

从现在起，只要本地作了提交，就可以通过命令：`git push origin master`把本地master分支的最新修改推送至GitHub.

## 从远程库克隆

用命令`git clone`克隆一个本地库,如：
```ruby
$ git clone git@github.com:qujialin/Java.git
Cloning into 'Java'...
remote: Counting objects: 3, done.
remote: Total 3 (delta 0), reused 0 (delta 0), pack-reused 3
Receiving objects: 100% (3/3), done.
```
Git支持多种协议，包括https，但通过ssh支持的原生git协议速度最快。

参考链接:[Git教程-廖雪峰的官方网站]("https://www.liaoxuefeng.com/wiki/896043488029600")