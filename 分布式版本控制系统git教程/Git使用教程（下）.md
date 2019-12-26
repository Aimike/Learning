# Git使用教程（下）

<!-- TOC -->

- [Git使用教程（下）](#git使用教程下)
    - [标签管理](#标签管理)
    - [创建标签](#创建标签)
    - [操作标签](#操作标签)
    - [删除关联的远程仓库](#删除关联的远程仓库)
    - [自定义Git](#自定义git)
    - [配置别名](#配置别名)
    - [配置文件](#配置文件)

<!-- /TOC -->

## 标签管理

    发布一个版本时，我们通常先在版本库中打一个标签（tag），这样，就唯一确定了打标签时刻的版本。将来无论什么时候，取某个标签的版本，就是把那个打标签的时刻的历史版本取出来。所以，标签也是版本库的一个快照。

    Git的标签虽然是版本库的快照，但其实它就是指向某个commit的指针，所以，创建和删除标签都是瞬间完成的。

## 创建标签

**打标签步骤：**

1. 首先，切换到需要打标签的分支上：

    ```ruby
    $ git branch
    * dev
    master
    $ git checkout master
    Switched to branch 'master'

    ```

2. 敲命令`git tag <name>`就可以打一个新标签：

    ```ruby
    $ git tag v1.0
    ```

3. 可以用命令`git tag`查看所有标签：

    ```ruby
    $ git tag
    v1.0
    ```

默认标签是打在最新提交的commit上的。如果需要在历史的某次提交打上标签，则需要找到历史提交的commit id，然后打上就可以了：

```ruby
$ git log --pretty=oneline --abbrev-commit
12a631b (HEAD -> master, tag: v1.0, origin/master) merged bug fix 101
4c805e2 fix bug 101
e1e9c68 merge with no-ff
f52c633 add merge
cf810e4 conflict fixed
5dc6824 & simple
14096d0 AND simple
b17d20e branch test
d46f35e remove test.txt
b84166e add test.txt
519219b git tracks changes
e43a48b understand how stage works
1094adb append GPL
e475afc add distributed
eaadf4e wrote a readme file
```

比方说要对`add merge`这次提交打标签，它对应的`commit id`是`f52c633`，敲入命令：

```ruby
$ git tag v0.9 f52c633
```

使用`git show <tagname>`:查看标签信息：

```ruby
$ git show v0.9
commit f52c63349bc3c1593499807e5c8e972b82c8f286 (tag: v0.9)
Author: qujialin <1754530626@qq.com>
Date:   Fri May 18 21:56:54 2018 +0800

    add merge

diff --git a/readme.txt b/readme.txt
...
```

创建带有说明的标签，用`-a`指定标签名，`-m`指定说明文字：

```ruby
$ git tag -a v0.1 -m "version 0.1 released" 1094adb
```

**注：** 标签总是和某个commit挂钩。如果这个commit既出现在master分支，又出现在dev分支，那么在这两个分支上都可以看到这个标签。

## 操作标签

- 删除：

    ```ruby
    $ git tag -d v0.1
    Deleted tag 'v0.1' (was f15b0dd)
    ```

    因为创建的标签都只存储在本地，不会自动推送到远程。所以，打错的标签可以在本地安全删除。

- 推送某个标签到远程，使用命令`git push origin <tagname>`：

    ```ruby
    $ git push origin v1.0
    Total 0 (delta 0), reused 0 (delta 0)
    To github.com:qujialin/learn.git
    * [new tag]         v1.0 -> v1.0
    ```

- 一次性推送全部尚未推送到远程的本地标签：

    ```ruby
    $ git push origin --tags
    Total 0 (delta 0), reused 0 (delta 0)
    To github.com:qujialin/learn.git
    * [new tag]         v0.9 -> v0.9
    ```

- 要删除已经推送到的远程标签：

    **步骤**：

    1. 先从本地删除：

        ```ruby
        $ git tag -d v0.9
        Deleted tag 'v0.9' (was f52c633)
        ```

    2. 从远程删除。删除命令也是`push`，但是格式如下：

        ```ruby
        $ git push origin :refs/tags/v0.9
        To github.com:qujialin/learn.git
        - [deleted]         v0.9
        ```

## 删除关联的远程仓库

 `git remote rm origin`:删除关联的远程仓库

## 自定义Git

- 让Git显示颜色，会让命令输出看起来更醒目：`$ git config --global color.ui true`文件名就会标上颜色

- 忽略特殊文件

    **忽略文件的原则：**

    1.  忽略操作系统自动生成的文件，比如缩略图等；

    2.  忽略编译生成的中间文件、可执行文件等，也就是如果一个文件是通过另一个文件自动生成的，那自动生成的文件就没必要放进版本库，比如Java编译产生的.class文件；

    3.  忽略你自己的带有敏感信息的配置文件，比如存放口令的配置文件。

        举例：忽略Python编译产生的.pyc、.pyo、dist等文件或目录：

        ```ruby
        # Python:
        *.py[cod]
        *.so
        *.egg
        *.egg-info
        dist
        build
        ```
    
    **忽略方法：**

    1.  GitHub已经为我们准备了各种配置文件，只需要组合一下就可以使用了。所有配置文件可以直接在线浏览：[https://github.com/github/gitignore](https://github.com/github/gitignore)

    2.  自己添加要忽略的文件： 在仓库下新建`.gitignore`文件,把要忽略的文件名添加进去，支持通配符

    3.  最后一步就是把.gitignore也提交到Git，就完成了！

    **tips：**
    
    `add`被忽略的文件：`$ git add -f <filename>`

    `git check-ignore -v <filename>`命令检查忽略规则：


## 配置别名

告诉Git，以后`st`就表示`status`,即`git st`就相当于`git status`：

```ruby
$ git config --global alias.st status
```

`--global`参数是全局参数，也就是这些命令在这台电脑的所有Git仓库下都有用。

撤销修改一节中，我们知道，命令`git reset HEAD file`可以把暂存区的修改撤销掉（`unstage`），重新放回工作区。既然是一个`unstage`操作，就可以配置一个`unstage`别名：

```ruby
$ git config --global alias.unstage 'reset HEAD'
```

则 `git unstage test.py` == `git reset HEAD test.py`

## 配置文件

配置`Git`的时候，加上`--global`是针对当前用户起作用的，如果不加，那只针对当前的仓库起作用。
每个仓库的Git配置文件都放在`.git/config`文件中

而当前用户的Git配置文件放在用户主目录下的一个隐藏文件`.gitconfig`中：

```ruby
$ cat .gitconfig
[alias]
    co = checkout
    ci = commit
    br = branch
    st = status
[user]
    name = Your Name
    email = your@email.com$ cat .gitconfig
[alias]
    co = checkout
    ci = commit
    br = branch
    st = status
[user]
    name = Your Name
    email = your@email.com
```

配置别名也可以直接修改这个文件，如果改错了，可以删掉文件重新通过命令配置。

**参考链接：** [Git教程-廖雪峰的官方网站]("https://www.liaoxuefeng.com/wiki/896043488029600")