/* global Editor: false, Parser : false, window: false, document: false */

var editor = new Editor(),
	parser = new Parser(),
	app = new App();

window.onload = function() {
    document.getElementById('startEditor').addEventListener('click', function() {
        document.getElementById('editor').style.display = "block";
        document.getElementById('parser').style.display = "none";
        document.getElementById('app').style.display = "none";
        document.getElementById('homepage').style.display = "none";
        
        if(!parser.workflow.parsed) {
        	editor.start();
        }
    });

    document.getElementById('startParser').addEventListener('click', function() {
    	if(parser.workflow.parsed) {
    		document.getElementById('editor').style.display = "block";
            document.getElementById('parser').style.display = "none";
            document.getElementById('app').style.display = "none";
            document.getElementById('homepage').style.display = "none";
            
    	} else {
    		document.getElementById('editor').style.display = "none";
            document.getElementById('parser').style.display = "block";
            document.getElementById('app').style.display = "none";
            document.getElementById('homepage').style.display = "none";

        	parser.start();
    	}
    });
    
    document.getElementById('startApp').addEventListener('click', function() {
    	document.getElementById('editor').style.display = "none";
        document.getElementById('parser').style.display = "none";
        document.getElementById('app').style.display = "block";
        document.getElementById('homepage').style.display = "none";
        app.start();
    });
};
