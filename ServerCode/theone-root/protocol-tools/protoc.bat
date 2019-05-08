cd /d %~dp0
protoc --proto_path=..\..\..\Protocol --java_out=..\theone-common\src\main\java\ ..\..\..\Protocol\*.proto
pause