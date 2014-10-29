/* global Editor: false, Parser : false, window: false, document: false */

var editor = new Editor(),
	parser = new Parser(),
	app = new App();

window.onload = function() {
    document.getElementById('startEditor').addEventListener('click', function() {
    	if(parser.workflow.parsed) {
        	var launchEditor = confirm("This will delete the generated diagram. Continue ?");
        	
        	if(launchEditor) {
        		document.getElementById('editor').style.display = "block";
                document.getElementById('parser').style.display = "none";
                document.getElementById('app').style.display = "none";
                
        		parser.stop();
        		editor.start();
        	}
        } else {
        	document.getElementById('editor').style.display = "block";
            document.getElementById('parser').style.display = "none";
            document.getElementById('app').style.display = "none";
            
        	editor.start();
        }
    });

    document.getElementById('startParser').addEventListener('click', function() {
    	if(parser.workflow.parsed) {
    		document.getElementById('editor').style.display = "block";
            document.getElementById('parser').style.display = "none";
            document.getElementById('app').style.display = "none";
            
    	} else {
    		if(editor.edited) {
            	var launch = confirm("This will delete the created diagram. Continue ?");
            	
            	if(launch) {
            		document.getElementById('editor').style.display = "none";
                    document.getElementById('parser').style.display = "block";
                    document.getElementById('app').style.display = "none";
            		
            		editor.stop();
            		parser.start();
            	}
            } else {
            	document.getElementById('editor').style.display = "none";
                document.getElementById('parser').style.display = "block";
                document.getElementById('app').style.display = "none";
            	
            	parser.start();
            }
    	}
    });
    
    document.getElementById('startApp').addEventListener('click', function() {
    	document.getElementById('editor').style.display = "none";
        document.getElementById('parser').style.display = "none";
        document.getElementById('app').style.display = "block";
        app.start();
    });
    
    var xmlhttp, text;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', '/gamble-workflow.txt', false);
    xmlhttp.send();
    document.getElementById('workflowToParse').innerHTML = xmlhttp.responseText;
    parser.start();    
    parser.clickToStart();
};
