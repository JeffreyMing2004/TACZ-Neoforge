<p align="center">
    <img width="300" src="https://s2.loli.net/2024/04/30/NJrstR1QzpoLyIT.png" alt="title">
</p>
<hr>
<p align="center">Timeless and Classics Guns Zero</p>

> [!NOTE]
> **NeoForge 1.21.1 移植版**
> 本仓库是 [Timeless and Classics Zero](https://github.com/MCModderAnchor/TACZ)（Forge 1.20.1）向 NeoForge 1.21.1 的移植。
> - 上游：MCModderAnchor/TACZ `1.20.1` 分支（GPL-3.0 / CC BY-NC-ND 4.0）
> - 平台迁移基于社区开源移植项目 [MUKSC/TACZ-1.21.1](https://github.com/MUKSC/TACZ-1.21.1)（GPL-3.0）的成果，并合并了上游后续修复与 KubeJS 扩展功能的迁移
> - 注意：1.20.1 的存档数据与本移植不兼容；旧枪械包需要使用 TaCZ Pack Upgrader 升级

> [!IMPORTANT]
> **问题反馈请找本仓库作者，不要打扰原作者**
> 使用本移植版遇到的任何 Bug、崩溃或兼容性问题，请提交到本仓库的 [Issues](https://github.com/JeffreyMing2004/TACZ-Neoforge/issues)，**请勿**向上游 [MCModderAnchor/TACZ](https://github.com/MCModderAnchor/TACZ/issues) 报告移植版的问题。

> [!TIP]
> **提交 Issue 自动编译**
> 本仓库配置了 GitHub Actions 流水线：每当有新 Issue 提交时，会自动触发完整编译。
> - 编译产物（mod jar）可在对应 [Actions 运行页](https://github.com/JeffreyMing2004/TACZ-Neoforge/actions) 底部的 Artifacts 区域下载（保留 14 天）
> - 构建结果（成功/失败 + 日志链接）会以评论形式回复到触发它的 Issue 下方
> - **Issue 在编译触发后会自动关闭**：本仓库的 Issue 仅作为构建触发器使用，若问题未解决，请携带构建产物再次提交新 Issue
> - 工作流定义见 `.github/workflows/issue-build.yml`

<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero">
        <img src="http://cf.way2muchnoise.eu/full_timeless-and-classics-zero.svg" alt="CurseForge Download">
    </a>
    <img src="https://img.shields.io/badge/license-GNU GPL 3.0 | CC%20BY--NC--ND%204.0-green" alt="License">
    <br>
    <a href="https://jitpack.io/#MCModderAnchor/TACZ">
        <img src="https://jitpack.io/v/MCModderAnchor/TACZ.svg" alt="jitpack build">
    </a>
    <a href="https://crowdin.com/project/tacz">
        <img src="https://badges.crowdin.net/tacz/localized.svg" alt="crowdin">
    </a>
</p>
<p align="center">
    <a href="https://github.com/MCModderAnchor/TACZ/issues">Report Bug</a>    ·
    <a href="https://github.com/MCModderAnchor/TACZ/releases">View Release</a>    ·
    <a href="https://tacwiki.mcma.club/zh/">Wiki</a>
</p>

Timeless and Classics Guns Zero is a gun mod for Minecraft Forge 1.20.1.

## Notice

- If you have any bugs, you can visit [Issues](https://github.com/MCModderAnchor/TACZ/issues) to
  submit issues.

## Authors

- Programmer: `286799714`, `TartaricAcid`, `F1zeiL`, `xjqsh`, `ClumsyAlien`
- Artist: `NekoCrane`, `Receke`, `Pos_2333`

## Credits

- Other players who have helped me in any ways, and you

## License

- Code: [GNU GPL 3.0](https://www.gnu.org/licenses/gpl-3.0.txt)
- Assets: [CC BY-NC-ND 4.0](https://creativecommons.org/licenses/by-nc-nd/4.0/)

## Maven

```groovy
repositories {
    maven {
        // Add curse maven to repositories
        name = "Curse Maven"
        url = "https://www.cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
    // You can see the https://www.cursemaven.com/
    // Choose one of the following three

    // If you want to use version tacz-1.20.1-1.1.6-release
    implementation fg.deobf("curse.maven:timeless-and-classics-zero-1028108:6632240-sources-6633203")
}
```
