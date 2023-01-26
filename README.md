# 使用聊天栏指令进行翻译

## 说明

这是一个使用聊天栏指令进行翻译的Minecraft mod。**不是**对聊天内容的翻译，**也不是**对指令的翻译。
此mod基于Forge[^1]，适用于mc1.12.2。使用有道翻译。

[^1]:详细Forge版本待补充

写这个东西的原因是在游玩加了神秘时代及其多个扩展之后在进行研究和合成时会大量出现拉丁文要素名称，甚是影响效率

## Mod的使用

> /trsl [翻译方式] [待翻译文本]

翻译方式：
cte或c2e(汉译英)
etc或e2c(英译汉)
ltc或l2c(拉丁文译中)

## Mod的配置

安装Mod后，在第一次使用之前需要申请有道翻译的API Key
前往有道智云AI开放平台进行注册。注册完成之后进入控制台创建应用。
应用类型选择自然语言翻译服务→文本翻译
创建完成后获取应用ID及密钥。
打开MC，在Mods列表中找到本mod，选择config页面填入应用id与密钥并保存。