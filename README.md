# 解决 `Obsidian+PicGo+Plugin` 上传图片问题

在 `Windows` 下可以通过 `PicGo` 和 `Obsidian` 的插件 `Image auto upload plugin` 实现剪贴板图片直接上传到 `OSS` 拿到链接，但在 `Ubuntu` 下 `PicGo` 剪贴板上传图片有问题，通过查看文档，可以通过 `PicGo server` 与 `Plugin` 对接，根据其参数和返回值，可以自己写一套，目前实现了阿里云 `OSS`，有需要自己改，反正就是这个思路.