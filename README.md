# NRouter

Android路由框架

| nrouter-annotation | nrouter-api | nrouter-processor | nrouter-plugin |
| :---: | :---: | :---: | :---: |
| [ ![Download](https://api.bintray.com/packages/fangdawei/maven/nrouter-annotation/images/download.svg?version=1.0.5) ](https://bintray.com/fangdawei/maven/nrouter-annotation/1.0.5/link) | [ ![Download](https://api.bintray.com/packages/fangdawei/maven/nrouter-api/images/download.svg?version=1.0.5) ](https://bintray.com/fangdawei/maven/nrouter-api/1.0.5/link) | [ ![Download](https://api.bintray.com/packages/fangdawei/maven/nrouter-processor/images/download.svg?version=1.0.5) ](https://bintray.com/fangdawei/maven/nrouter-processor/1.0.5/link) | [ ![Download](https://api.bintray.com/packages/fangdawei/maven/nrouter-plugin/images/download.svg?version=1.0.5) ](https://bintray.com/fangdawei/maven/nrouter-plugin/1.0.5/link) |

#### 功能介绍
* 路由。支持Activity、Fragment、Service，可自行扩展对其他类型的支持；支持自动或手动注册
* 拦截器。支持自定义拦截顺序，支持自动或手动注册
* 依赖注入。
* Scheme跳转。
* 等


#### 使用方式
* 配置

项目根目录下的build.gradle中添加配置

```
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath "club.fdawei.nrouter:nrouter-plugin:?"
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
```

module目录下的build.gradle中添加配置

```
apply plugin: 'nrouter-plugin'

dependencies {
    implementation "club.fdawei.nrouter:nrouter-annotation:?"
    implementation "club.fdawei.nrouter:nrouter-api:?"
    kapt "club.fdawei.nrouter:nrouter-processor:?"
}
```

添加混淆规则（如果开启了混淆）

```
-keep class nrouter.generated.providers.NRouter_AppProvider
```

* 使用

路由Activity

```
@Route(path = "/suba/page/home", desc = "A页面")
class AActivity : AppCompatActivity() {

}

NRouter.route("/suba/page/home").arg(this).go()
```

路由Fragment

```
@Route(path = "/main/fragment/home")
class MainFragment : Fragment() {

}

val mainFragment = NRouter.route("/main/fragment/home")
    .withString("from", "CActivity")
    .get(Fragment::class)
```

路由Service

```
@Route(path = "/main/service/main")
class MainService : Service() {

}

//start
NRouter.route("/main/service/main").arg(ServiceOption.START).go()

//stop
NRouter.route("/main/service/main").arg(ServiceOption.STOP).go()

//bind
NRouter.route("/main/service/main")
    .arg(ServiceOption.BIND)
    .arg(conn)
    .go()

//unbind
NRouter.route("/main/service/main")
    .arg(ServiceOption.UNBIND)
    .arg(conn)
    .go()
```

拦截器

```
@Interceptor(desc = "路由日志记录")
class RouterLogInterceptor : RouteInterceptor {
    override fun intercept(context: InterceptContext): Boolean {
        return false
    }
}

@Interceptor(path = "/main/page/home")
class JumpMainInterceptor : RouteInterceptor {
    override fun intercept(context: InterceptContext): Boolean {
        return false
    }
}
```

依赖注入

```
interface IService {
    fun printName()
}

@Route(path = "/subc/service/cservice")
class CService : IService, Creatable {
    override fun printName() {
        
    }
}

class MainActivity : AppCompatActivity() {

    @Autowired(path = "/subc/service/cservice")
    var cService: IService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NRouter.injector().inject(this)
    }
}
```

Scheme跳转

app模块下的build.gradle中添加配置，开启对scheme的支持

```
nrouter {
    scheme {
        support true
        scheme 'nrouter'
        host 'sample'
    }
}
```

定义Scheme处理器

```
@SchemeAware
class MainSchemeHandler : SchemeHandler {
    override fun handle(intent: Intent): Boolean {
        //处理scheme
        return true
    }
}
```

