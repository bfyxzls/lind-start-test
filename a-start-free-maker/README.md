* 普通取值：
```
${var}
```
* 对null或者不存在的对象取值，为空时设置默认值，
```
${var!“默认值”}
```
* boolean类型：
```
${booleanVar?String(“true”,“false”)}
```
* 封装对象：
````
${对象.属性}
````
* 时间类型：
```
${date?String(“yyyy-MM-dd”)}
```
* HTML转译：
```
$(var?html)-------携带html标签转译
```
* 定义变量：
```
<#assign num = 10/>
$(num*10) = 100

```
* list集合取值：
```
<#list userList as item>
　${item}
</#list>
```
* 对Map集合取值
```
<#list map?keys as key>
　${key}: ${map[key]}
</#list>
```
* if-else判断
```
<#if 条件>
　　输出
　<#else>
　　输出
</#if>
```
* if-else-if级联判断
```
<#if 条件1>
　　输出
　<#elseif 条件2>
　　输出
　<#elseif 条件3>
　　输出
　<#else>
　　输出
</#if>

```

* switch语句
```
<#switch var>
　<#case 条件1>
　　输出
　<#break>
　<#case 条件2>
　　输出
　<#break>
　<#default>
　　输出
</#switch>
```
# 常用内建函数、macro(宏指令)、function(函数指令)：
## (1) 常用内建函数
* 处理字符串：
```
substring 截取字符串，包头不包尾（下标）
cap_first 第一个字母大写
end_with 以什么字母结尾
contains 是否包含目标字符串
date datetime time 转换成日期格式
starts_with 以什么字母开头
index_of 返回某个指定的字符串值在字符串中首次出现的位置（下标）
last_index_of 获取指定字符出现的最后位置（下标）
split 分隔
trim 去两端空格
```
* 处理数字：
```
string
x?string(“0.##”) 变成小数点后几位
round 四舍五入
floor 去掉小数点
ceiling 近1 变成整数
处理list:
first: 取List值第一个值
last: 取List值最后一个值
seq_contains: 是否包含指定字符
seq_index_of: 指定字符所在位置
size: 集合大小
reverse: 集合倒序排列
sort: 对集合进行排序
sort_by: 根据某一个属性排序
chunk: 分块处理
```
* 其他:
```
is_string: 是否为字符类型
is_number: 是否为整数类型
is_method: 是否为方法
(): 判断整个变量
has_content: 判断对象是否为空或不存在
eval： 求值
(2) macro(宏指令)
调用：<@macro_name param />
语法：<#macro 变量名 参数>
<#nested/>
</#macro>
(3) function(函数指令)
调用：${function_name(param)}
语法：<#function 变量名 参数>
<#return>
</#function>
```