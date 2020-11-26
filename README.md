# VersionUpdater
APP版本更新器，将app版本升级中的通用逻辑与具体的UI展示剥离,只需实现自身APP的UI代码，提高开发效率。</br>
为了让VersionUpdater库更加健壮，如果您在使用过程中发现任何问题，请联系我，我会立即跟进修复和维护。<br/>
感谢您的支持！<br/>
微信：w361281607<br/>
邮箱：coder_zzq@aliyun.com<br/>
## 添加依赖
1.在Project的gradle文件中<br/>
<pre><code>
allprojects {

    repositories {

        ...

        maven { url 'https://jitpack.io' }

    }

}
</code></pre>
2.在Module的gradle文件中<br/>
<pre><code>

    dependencies {

      implementation 'com.github.opensource-zhuzhiqiang:version-updater:1.1.3'

    }

</code></pre>

