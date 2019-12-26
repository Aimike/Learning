# Git使用教程（中）

<!-- TOC -->

- [Git使用教程（下）](#git使用教程下)
    - [创建与合并分支](#创建与合并分支)
    - [命令](#命令)
    - [合并冲突及解决](#合并冲突及解决)
    - [分支管理策略](#分支管理策略)
    - [Bug分支](#bug分支)
    - [feature分支](#feature分支)
    - [多人协作](#多人协作)
    - [Rebase](#rebase)

<!-- /TOC -->

## 创建与合并分支

  每次提交，Git都把它们串成一条时间线，这条时间线就是一个分支。在Git里，这个分支叫主分支，即`master`分支。`HEAD`严格来说不是指向提交，而是指向`master`，`master`才是指向提交的，所以，`HEAD`指向的就是当前分支。
  每次提交，`master`分支都会向前移动一步，这样，随着你不断提交，`master`分支的线也越来越长。

  ![图片.png](https://i.loli.net/2019/12/25/sc94h3xb86TiLFg.png)

  当我们创建新的分支，例如dev时，Git新建了一个指针叫dev，指向master相同的提交，再把HEAD指向dev，就表示当前分支在dev上：

  ![图片.png](https://i.loli.net/2019/12/25/QxDk8ivVNZo3jFp.png)

  **举例**

  首先，我们创建`dev`分支，然后切换到`dev`分支：

  ```ruby
  $ git checkout -b dev
  Switched to a new branch 'dev'
  ```

  `git checkout`命令加上`-b`参数表示创建并切换，相当于以下两条命令:

  ```ruby
  $ git branch dev
  $ git checkout dev
  Switched to branch 'dev'
  ```

  然后，用git branch命令查看当前分支(当前分支前有个*号)：

  ```ruby
  $ git branch
  * dev
    master
  ```

  然后，我们就可以在dev分支上正常提交，比如对`readme.txt`做个修改，加上一行,然后提交.

  切换回`master`分支,发现刚才修改的内容“没有生效”，这是因为那个提交是在`dev`分支上，而`master`分支此刻的提交点并没有变：

  ```ruby
  $ git checkout master
  Switched to branch 'master'
  ```

  ![图片.png](https://i.loli.net/2019/12/25/8k3LTtsAHPnaSrR.png)

  现在，我们把`dev`分支的工作成果合并到`master`分支上：

  ```ruby
  $ git merge dev
  Updating d46f35e..b17d20e
  Fast-forward
  readme.txt | 1 +
  1 file changed, 1 insertion(+)
  ```

  **注：**`git merge`命令用于合并指定分支到当前分支。上面的`Fast-forward`信息，Git告诉我们，这次合并是“快进模式”，也就是直接把`master`指向`dev`的当前提交，所以合并速度非常快。

  合并完成后，就可以放心地删除dev分支了：

  ```ruby
  $ git branch -d dev
  Deleted branch dev (was b17d20e).
  ```

  创建并切换到新的`dev`分支，可以使用：`git switch -c dev`
  直接切换到已有的`master`分支，可以使用：`git switch master`

## 命令

  查看分支：`git branch`

  创建分支：`git branch <name>`

  切换分支：`git checkout <name>`或者`git switch <name>`

  创建+切换分支：`git checkout -b <name>`或者`git switch -c <name>`

  合并某分支到当前分支：`git merge <name>`

  删除分支：`git branch -d <name>`

## 合并冲突及解决

  **冲突：** 

  1. 新建分支`dev`，切换到`dev`分支并且修改`a.txt`后`add`并`commit`
  2. 切换到`master`分支，修改`a.txt`后`add`并`commit`
  3. 尝试合并分支出现冲突！

  ```ruby
  $ git merge dev
  Auto-merging a.txt
  CONFLICT (content): Merge conflict in a.txt
  Automatic merge failed; fix conflicts and then commit the result.
  ```

  ![图片.png](https://i.loli.net/2019/12/25/vEzZM8eDHqtFKAc.png)

  **解决方案**

  Git告诉我们，`a.txt`文件存在冲突，必须手动解决冲突后再提交。`git status`也可以告诉我们冲突的文件：

  ```ruby
  $ git status
  On branch master
  Your branch is ahead of 'origin/master' by 2 commits.
    (use "git push" to publish your local commits)

  You have unmerged paths.
    (fix conflicts and run "git commit")
    (use "git merge --abort" to abort the merge)

  Unmerged paths:
    (use "git add <file>..." to mark resolution)

    both modified:   a.txt

  no changes added to commit (use "git add" and/or "git commit -a")
  ```

  再提交：

  ```ruby
  $ git add a.txt 
  $ git commit -m "conflict fixed"
  [master cf810e4] conflict fixed
  ```

  合并过程如下图：

  ![图片.png](https://i.loli.net/2019/12/25/my28wz5ZDQl1BaE.png)

  用带参数的`git log`也可以看到分支的合并情况：

  ```ruby
  $ git log --graph --pretty=oneline --abbrev-commit
  *   cf810e4 (HEAD -> master) conflict fixed
  |\  
  | * 14096d0 (dev) AND simple
  * | 5dc6824 & simple
  |/  
  * b17d20e branch test
  * d46f35e (origin/master) remove test.txt
  * b84166e add test.txt
  * 519219b git tracks changes
  * e43a48b understand how stage works
  * 1094adb append GPL
  * e475afc add distributed
  * eaadf4e wrote a readme file
  ```

  最后，删除`dev`分支：

  ```ruby
  $ git branch -d dev
  Deleted branch dev (was 14096d0).
  ```

## 分支管理策略

  通常，合并分支时，如果可能，Git会用`Fast forward`模式，但这种模式下，删除分支后，会丢掉分支信息。

  如果要强制禁用`Fast forward`模式，Git就会在`merge`时生成一个新的   `commit`，这样，从分支历史上就可以看出分支信息

  **实战：** 在`master`分支上合并`dev`分支:

  ```ruby
  $ git merge --no-ff -m "merge with no-ff" dev
  Merge made by the 'recursive' strategy.
  readme.txt | 1 +
  1 file changed, 1 insertion(+)
  ```

  合并后，我们用`git log`看看分支历史：

  ```ruby
  $ git log --graph --pretty=oneline --abbrev-commit
  *   e1e9c68 (HEAD -> master) merge with no-ff
  |\  
  | * f52c633 (dev) add merge
  |/  
  *   cf810e4 conflict fixed
  ...
  ```

  不使用`Fast forward`模式，`merge`后就像这样：

  ![图片.png](https://i.loli.net/2019/12/25/oe91AjiYupILNKf.png)

  **分支策略**
  实际开发中，首先，`master`分支应该是非常稳定的，也就是仅用来发布新版本，平时不能在上面干活；那在哪干活呢？干活都在`dev`分支上，也就是说，`dev`分支是不稳定的，到某个时候，比如1.0版本发布时，再把`dev`分支合并到`master`上，在`master`分支发布1.0版本；

  你和你的小伙伴们每个人都在`dev`分支上干活，每个人都有自己的分支，时不时地往`dev`分支上合并就可以了。所以，团队合作的分支看起来就像这样：

  ![图片.png](https://i.loli.net/2019/12/25/p5lmo2BnHqPLaOV.png)

## Bug分支

  修复`bug`时，我们会通过创建新的`bug`分支进行修复，然后合并，最后删除；

  当手头工作没有完成时，先把工作现场`git stash`一下，然后去修复`bug`，修复后，再`git stash pop`，回到工作现场；

  在`master`分支上修复的`bug`，想要合并到当前`dev`分支，可以用`git cherry-pick <commit>`命令，把`bug`提交的修改“复制”到当前分支，避免重复劳动。

  **举例：**

  当你在`dev`分支工作时，有一个`bug`需要尽快解决，此时使用`git stash`保存工作现场，切换到`master`分支，创建新分支修复：

  ```ruby
  $ git checkout master
  Switched to branch 'master'
  Your branch is ahead of 'origin/master' by 6 commits.
    (use "git push" to publish your local commits)

  $ git checkout -b issue-101
  Switched to a new branch 'issue-101'

  $ git add readme.txt 
  $ git commit -m "fix bug 101"
  [issue-101 4c805e2] fix bug 101
  1 file changed, 1 insertion(+), 1 deletion(-)
  ```

  再切换到`dev`分支。此时，用`git stash list`命令列出该分支下的所有工作现场：

  ```ruby
  $ git stash list
  stash@{0}: WIP on dev: f52c633 add merge
  ```

  工作现场还在，`Git`把`stash`内容存在某个地方了，但是需要恢复一下，有两个办法：

  一是用`git stash apply`或者`git stash apply stash@{0}`恢复，但是恢复后，`stash`内容并不删除，你需要用`git stash drop`来删除；

  另一种方式是用`git stash pop`，恢复的同时把`stash`内容也删了.

  修复完成后，因为`dev`分支是来源于`master`分支，所以，使用`git cherry-pick <提交的版本号>`命令，此处版本号为`4c805e2`，来使`dev`分支进行同样的提交，避免重复劳动。

  ```ruby
  $ git cherry-pick 4c805e2
  [master 1d4b803] fix bug 101
  1 file changed, 1 insertion(+), 1 deletion(-)
  ```

## feature分支

  - 开发一个新feature，最好新建一个分支；

  - 如果要丢弃一个没有被合并过的分支，可以通过git branch -D <name>强行删除。

  添加一个新功能时，你肯定不希望因为一些实验性质的代码，把主分支搞乱了，所以，每添加一个新功能，最好新建一个`feature`分支，在上面开发，完成后，合并，最后，删除该`feature`分支。

  实例：
  1. 你接到了一个新任务：开发代号为Vulcan的新功能，该功能计划用于下一代星际飞船。

  ```ruby
  $ git switch -c feature-vulcan
  Switched to a new branch 'feature-vulcan'
  ```

  2. 5分钟后，开发完毕：

  ```ruby
  $ git add vulcan.py

  $ git status
  On branch feature-vulcan
  Changes to be committed:
    (use "git reset HEAD <file>..." to unstage)

    new file:   vulcan.py

  $ git commit -m "add feature vulcan"
  [feature-vulcan 287773e] add feature vulcan
  1 file changed, 2 insertions(+)
  create mode 100644 vulcan.py
  ```

  3. 切回dev，准备合并,但是此时接到上级命令，因经费不足，新功能必须取消！虽然白干了，但是这个包含机密资料的分支还是必须就地销毁：

  ```ruby
  $ git switch dev

  $ git branch -d feature-vulcan
  error: The branch 'feature-vulcan' is not fully merged.
  If you are sure you want to delete it, run 'git branch -D feature-vulcan'.
  ```

  4. 销毁失败。Git友情提醒，`feature-vulcan`分支还没有被合并，如果删除，将丢失掉修改，如果要强行删除，需要使用大写的`-D`参数。

  ```ruby
  $ git branch -D feature-vulcan
  Deleted branch feature-vulcan (was 287773e).
  ```

## 多人协作

  当你从远程仓库克隆时，实际上Git自动把本地的`master`分支和远程的`master`分支对应起来了，并且，远程仓库的默认名称是`origin`。

  要查看远程库的信息，用`git remote`：

  ```ruby
  $ git remote
  origin
  ```
  `git remote -v`查看更详细的信息：

  ```ruby
  $ git remote -v
  origin  git@github.com:qujialin/learn.git (fetch)
  origin  git@github.com:qujialin/learn.git (push)
  ```

  上面显示了可以抓取和推送的origin的地址。如果没有推送权限，就看不到push的地址。

  **推送分支**

  推送分支，就是把该分支上的所有本地提交推送到远程库。推送时，要指定本地分支，这样，Git就会把该分支推送到远程库对应的远程分支上：

  ```ruby
  $ git push origin master
  ```

  如果要推送其他分支，比如`dev`，就改成：

  ```ruby
  $ git push origin dev
  ```

  **抓取分支**

  多人协作时，大家都会往`master`和`dev`分支上推送各自的修改。

  创建远程`origin`的`dev`分支到本地：

  ```ruby
  $ git checkout -b dev origin/dev
  ```

  你的小伙伴已经向`origin/dev`分支推送了他的提交，而碰巧你也对同样的文件作了修改，并试图推送：

  ```ruby
  $ cat env.txt
  env

  $ git add env.txt

  $ git commit -m "add new env"
  [dev 7bd91f1] add new env
  1 file changed, 1 insertion(+)
  create mode 100644 env.txt

  $ git push origin dev
  To github.com:michaelliao/learngit.git
  ! [rejected]        dev -> dev (non-fast-forward)
  error: failed to push some refs to 'git@github.com:michaelliao/learngit.git'
  hint: Updates were rejected because the tip of your current branch is behind
  hint: its remote counterpart. Integrate the remote changes (e.g.
  hint: 'git pull ...') before pushing again.
  hint: See the 'Note about fast-forwards' in 'git push --help' for details.
  ```

  推送失败，因为你的小伙伴的最新提交和你试图推送的提交有冲突，解决办法也很简单，Git已经提示我们，先用`git pull`把最新的提交从`origin/dev`抓下来，然后，在本地合并，解决冲突，再推送：

  ```ruby
  $ git pull
  There is no tracking information for the current branch.
  Please specify which branch you want to merge with.
  See git-pull(1) for details.

      git pull <remote> <branch>

  If you wish to set tracking information for this branch you can do so with:

      git branch --set-upstream-to=origin/<branch> dev
  ```

  `git pull`也失败了，原因是没有指定本地`dev`分支与远程`origin/dev`分支的链接，根据提示，设置`dev`和`origin/dev`的链接：

  ```ruby
  $ git branch --set-upstream-to=origin/dev dev
  Branch 'dev' set up to track remote branch 'dev' from 'origin'.
  ```

  再`pull`：

  ```ruby
  $ git pull
  Auto-merging env.txt
  CONFLICT (add/add): Merge conflict in env.txt
  Automatic merge failed; fix conflicts and then commit the result.
  ```

  这回`git pull`成功，但是合并有冲突，需要手动解决，解决的方法和分支管理中的解决冲突完全一样。解决后，提交，再`push`：

  ```ruby
  $ git commit -m "fix env conflict"
  [dev 57c53ab] fix env conflict

  $ git push origin dev
  Counting objects: 6, done.
  Delta compression using up to 4 threads.
  Compressing objects: 100% (4/4), done.
  Writing objects: 100% (6/6), 621 bytes | 621.00 KiB/s, done.
  Total 6 (delta 0), reused 0 (delta 0)
  To github.com:michaelliao/learngit.git
    7a5e5dd..57c53ab  dev -> dev
  ```

  因此，多人协作的工作模式通常是这样：

      首先，可以试图用`git push origin <branch-name>`推送自己的修改；

      如果推送失败，则因为远程分支比你的本地更新，需要先用`git pull`试图合并；

      如果合并有冲突，则解决冲突，并在本地提交；

      没有冲突或者解决掉冲突后，再用`git push origin <branch-name>`推送就能成功！

  如果`git pull`提示`no tracking information`，则说明本地分支和远程分支的链接关系没有创建，用命令`git branch --set-upstream-to <branch-name> origin/<branch-name>`。

  这就是多人协作的工作模式，一旦熟悉了，就非常简单。

## Rebase

  在上一节我们看到了，多人在同一个分支上协作时，很容易出现冲突。即使没有冲突，后push的童鞋不得不先pull，在本地合并，然后才能push成功。

  每次合并再push后，分支变成了这样：

  ```ruby
  $ git log --graph --pretty=oneline --abbrev-commit
  * d1be385 (HEAD -> master, origin/master) init hello
  *   e5e69f1 Merge branch 'dev'
  |\  
  | *   57c53ab (origin/dev, dev) fix env conflict
  | |\  
  | | * 7a5e5dd add env
  | * | 7bd91f1 add new env
  | |/  
  * |   12a631b merged bug fix 101
  |\ \  
  | * | 4c805e2 fix bug 101
  |/ /  
  * |   e1e9c68 merge with no-ff
  |\ \  
  | |/  
  | * f52c633 add merge
  |/  
  *   cf810e4 conflict fixed
  ```

  总之看上去很乱，有强迫症的童鞋会问：为什么Git的提交历史不能是一条干净的直线？

  其实是可以做到的！

  Git有一种称为`rebase`的操作，有人把它翻译成“变基”。输入`git rebase`

  ```ruby
  $ git rebase
  First, rewinding head to replay your work on top of it...
  Applying: add comment
  Using index info to reconstruct a base tree...
  M	hello.py
  Falling back to patching base and 3-way merge...
  Auto-merging hello.py
  Applying: add author
  Using index info to reconstruct a base tree...
  M	hello.py
  Falling back to patching base and 3-way merge...
  Auto-merging hello.py
  ```

  此时用`git log`再看：

  ```ruby
  $ git log --graph --pretty=oneline --abbrev-commit
  * 7e61ed4 (HEAD -> master) add author
  * 3611cfe add comment
  * f005ed4 (origin/master) set exit=1
  * d1be385 init hello
  ...
  ```

  原本分叉的提交现在变成一条直线了！这种神奇的操作是怎么实现的？其实原理非常简单。我们注意观察，发现Git把我们本地的提交“挪动”了位置，放到了`f005ed4 (origin/master) set exit=1`之后，这样，整个提交历史就成了一条直线。rebase操作前后，最终的提交内容是一致的，但是，我们本地的`commit`修改内容已经变化了，它们的修改不再基于`d1be385 init hello`，而是基于`f005ed4 (origin/master) set exit=1`，但最后的提交`7e61ed4`内容是一致的。

  这就是`rebase`操作的特点：把分叉的提交历史“整理”成一条直线，看上去更直观。缺点是本地的分叉提交已经被修改过了。

  最后，通过`push`操作把本地分支推送到远程：

  ```ruby
  $ git push origin master
  Counting objects: 6, done.
  Delta compression using up to 4 threads.
  Compressing objects: 100% (5/5), done.
  Writing objects: 100% (6/6), 576 bytes | 576.00 KiB/s, done.
  Total 6 (delta 2), reused 0 (delta 0)
  remote: Resolving deltas: 100% (2/2), completed with 1 local object.
  To github.com:qujialin/Learn.git
    f005ed4..7e61ed4  master -> master
  ```

  远程分支的提交历史也是一条直线。如下：

  ```ruby
  $ git log --graph --pretty=oneline --abbrev-commit
  * 7e61ed4 (HEAD -> master, origin/master) add author
  * 3611cfe add comment
  * f005ed4 set exit=1
  * d1be385 init hello
  ...
  ```

  参考链接：
  
  <a href="https://www.liaoxuefeng.com/wiki/896043488029600/900003767775424">https://www.liaoxuefeng.com/wiki/896043488029600/900003767775424</a>

  