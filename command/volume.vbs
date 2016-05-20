Set ws = CreateObject("WScript.Shell")
Set args = WScript.Arguments
    For Each argc In args 
        ws.run("ClickMonitorDDC.exe volume "+argc)
    Next
Set args = Nothing