<html>
    <head>
        <title>Hi</title>
        <script type="text/javascript">
            function open_window(windowName){
                window.open("newWindow.html",windowName);
            }
        </script>
    </head>
    <body>
        <h2>Hello World!</h2>
        <a href="newWindow.html">Regular Link</a><br />
        <a href="newWindow.html" id="lnkNewWindow" target="_blank">New Window Link</a><br />
        <input type="button" id="btnNewNamelessWindow" onclick="open_window()" value="Open Nameless Window" /><br />
        <input type="button" id="btnSlowNewNamelessWindow"
            onclick="setTimeout(function() {window.open('newWindow.html','slowWindow');},1250);"
            value="Slowly Open Window" /><br />
    </body>
</html>
