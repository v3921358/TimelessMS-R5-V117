@echo off
@title v117 Starter/Console
set CLASSPATH=.;dist\*
java -client -Dnet.sf.odinms.wzpath=wz server.Start
pause