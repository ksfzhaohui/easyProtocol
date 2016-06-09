@echo off

set config=D:\easyProtocol-1.0.1-beta\config.xml
set progen=D:\easyProtocol-1.0.1-beta\progen.xml

java -jar easyProtocol-1.0.1-beta.jar %config% %progen%
pause