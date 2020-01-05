## 发送文本邮件


- 方法：EmailUtils.sendEmail()
- 功能：发送指定文本邮件
- 参数：

    String from:发件人邮箱

    String fromAuthorizationCode:授权码

    String to:收件人邮箱

    String subject:主题

    String content:内容

    EmailUtils.Type ptype:类型（目前只支持网易邮箱或者QQ邮箱）

- 备注：EmailUtils.Type是一个内部枚举类，含qq(代表qq邮箱服务器)和wy(代表网易邮箱服务器)两个常量，