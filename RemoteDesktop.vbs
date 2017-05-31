Set ws = CreateObject("WScript.Shell")
ws.run("taskkill /f /im RemoteDesktop.exe"),0,true
ws.run("cmd /c start RemoteDesktop.exe"),0,true
