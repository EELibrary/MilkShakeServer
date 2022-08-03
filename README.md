## MilkShakeServer
一个简单地基于Catserver的半多线程服务端，兼容性不太好，主要本人也没开发经验而且就刚学java(
## 构建
打开你的终端然后到这个目录，运行./graldew setup然后./gradlew build(如果你修改了MC部分的源码，在build之前请先运行一下genPatches)
运行完后可以在build/distributions中看到构建好的jar
## 警告
目前这个服务端就像隔壁优化mod(MCMT)一样，存在一些未经发现的问题，所以如果考虑稳定性(其实也没多不稳定，实体更新部分偶尔会报一下错)请先不要使用这个服务端
## 多线程魔改过的部分
 1.实体和方块实体多线程更新\
 2.异步时间同步(说实话这个跟没有一样)\
 3.多线程tracker\
 4.同步的PathHeap\
 5.部分多线程的ChunkProvider