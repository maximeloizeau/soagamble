function Editor() {
    // Constants
    this.CLICK_STATE = {
        NO_CLICK: 1,
        FIRST_CLICK: 2
    };

    this.OBJECT_TYPE = {
        SERVICE_CALL: "serviceCall",
        COMPOSITE_CODE: "compositeCode",
        IF: "if",
        ELSE: "else",
        ENDIF: "endIf",
        FORLOOP: "forLoop",
        ENDFOR: "endFor"
    };
    
    this.COLORS = {
    	ENT: "#0066FF",
    	CODE: "#E88000",
    	INSTR: "#ffa500",
    	BLOCK: "#58E875",
    	ENT_BG: "#5BB9FF",
    	TEXT: "#FFFFFF"
    };
    
    this.readonly = false;
    this.edited = false;
}

Editor.prototype.start = function(readonly) {
	this.snap = Snap("#svg");
	this.snap.clear();
    this.services = [];
	
    var editorNodes = document.querySelectorAll(".editormode"),
    	parserNodes = document.querySelectorAll(".parsermode");

    // Parser mode
	if(readonly) {
		this.readonly = readonly;
		
		for(var i = 0; i < editorNodes.length; i++) {
			editorNodes[i].style.display = "none";
		}
		for(i = 0; i < parserNodes.length; i++) {
			parserNodes[i].style.display = "block";
		}
	} else {
		for(var i = 0; i < editorNodes.length; i++) {
			editorNodes[i].style.display = "block";
		}
		for(i = 0; i < parserNodes.length; i++) {
			parserNodes[i].style.display = "none";
		}
	}

    this.clickState = {
        state: this.CLICK_STATE.NO_CLICK
    };
    this.objectType = this.OBJECT_TYPE.SERVICE_CALL;

    this.lowerLayer = this.snap.g();
    this.higherLayer = this.snap.g();

    var composite = new Entity(this.snap, "Composite");
    composite.draw();

    this.services.push(composite);

    // ###
    // Place listeners in user interface
    // ###

    document.getElementById("generate").addEventListener('click', function() {
        var workflowText = editor.generateWorkflow(composite);
        document.getElementById("generated-workflow").value = workflowText;
    });

    document.removeEventListener("keypress", editor.shorcutListener);
    document.addEventListener("keypress", editor.shorcutListener);

    document.getElementById("addEntity").removeEventListener("click", this.addEntityListener);
    document.getElementById("entityName").removeEventListener("keydown", this.addEntityListener);
    document.getElementById("addEntity").addEventListener("click", this.addEntityListener);
    document.getElementById("entityName").addEventListener("keydown", this.addEntityListener);
    
    document.getElementById("execute").removeEventListener("click", app.initiateSequence.bind(app));
    document.getElementById("execute").addEventListener("click", app.initiateSequence.bind(app));
};

Editor.prototype.shorcutListener = function(key) {
    if(key.which == 178) { // key "²"
        if(editor.objectType == editor.OBJECT_TYPE.SERVICE_CALL) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.COMPOSITE_CODE + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.COMPOSITE_CODE;
        } else if(editor.objectType == editor.OBJECT_TYPE.COMPOSITE_CODE) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.IF + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.IF;
        } else if(editor.objectType == editor.OBJECT_TYPE.IF) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.ELSE + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.ELSE;
        }  else if(editor.objectType == editor.OBJECT_TYPE.ELSE) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.ENDIF + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.ENDIF;
        } else if(editor.objectType == editor.OBJECT_TYPE.ENDIF) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.FORLOOP + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.FORLOOP;
        } else if(editor.objectType == editor.OBJECT_TYPE.FORLOOP) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.ENDFOR + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.ENDFOR;
        } else if(editor.objectType == editor.OBJECT_TYPE.ENDFOR) {
            document.querySelector('input[value="' + editor.OBJECT_TYPE.SERVICE_CALL + '"]').checked = "checked";
            editor.objectType = editor.OBJECT_TYPE.SERVICE_CALL;
        }
    }
};

Editor.prototype.addEntityListener = function(event) {
    var send = false;
    if(event instanceof KeyboardEvent) {
        if(event.keyCode == 13) {
            send = true;
        }
    } else {
        send = true;
    }

    var text = document.getElementById("entityName").value;
    if(send && text != "") {
        editor.addEntity(text);
        document.getElementById("entityName").value = "";
    }
};

Editor.prototype.stop = function() {
	this.edited = false;
	this.services = [];
};

Editor.prototype.generateWorkflow = function(composite) {
    var endValue = "";

    endValue = endValue + "START " + document.getElementById("inputs").value;

    for(var i = 0; i < composite.callsFromEntity.length; i++) {
        var node = composite.callsFromEntity[i];

        while(node) {
            var code = node.getWorkflowCode();

            if(code.length > 0) {
                endValue = endValue + "\n" + node.getWorkflowCode();
            }

            node = node.next;
        }
    }

    endValue = endValue + "\nRETURN " + document.getElementById("output").value;

    return endValue;
};

Editor.prototype.addEntity = function(name) {
	this.edited = true;
	
    var entity = new Entity(this.snap, name, this.services[this.services.length-1]);
    entity.draw();

    this.services.push(entity);
    
    console.log("ADDED ENTITY " + name, this.services);
};

Editor.prototype.addCompositeCode = function(value, y) {
    var cc = new CompositeCode(this.snap, value, this.services[0].getObject().getBBox().cx, y);
    cc.draw();

    this.services[0].push(cc);
};

Editor.prototype.addFor = function(value, y) {
  var ff = new ForLoop(this.snap, value, this.services[0].getObject().getBBox().cx, y);
    ff.draw();

    this.services[0].push(ff);
};

Editor.prototype.addEndFor = function(y) {
    var ff = new EndFor(this.snap, this.services[0].getObject().getBBox().cx, y);
    ff.draw();

    this.services[0].push(ff);
};

Editor.prototype.addIf = function(value, y) {
  var ff = new ConditionnalIf(this.snap, value, this.services[0].getObject().getBBox().cx, y);
    ff.draw();

    this.services[0].push(ff);
};

Editor.prototype.addElse = function(y) {
    var ff = new ConditionnalEkse(this.snap, this.services[0].getObject().getBBox().cx, y);
    ff.draw();

    this.services[0].push(ff);
};

Editor.prototype.addEndIf = function(y) {
    var ff = new ConditionnalEndIf(this.snap, this.services[0].getObject().getBBox().cx, y);
    ff.draw();

    this.services[0].push(ff);
};


/**
 * Uses canvas.measureText to compute and return the width of the given text of given font in pixels.
 * 
 * @param {String} text The text to be rendered.
 * @param {String} font The css font descriptor that text is to be rendered with (e.g. "bold 14px verdana").
 * 
 * @see http://stackoverflow.com/questions/118241/calculate-text-width-with-javascript/21015393#21015393
 */
Editor.prototype.measureText = function (text, font) {
	    // re-use canvas object for better performance
	    var canvas = this.canvas || (this.canvas = document.createElement("canvas"));
	    var context = canvas.getContext("2d");
	    context.font = font;
	    var metrics = context.measureText(text);
	    return metrics.width;
};
