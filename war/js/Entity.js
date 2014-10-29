/* global Element: false, services: true */

function Entity(s, name, nextTo) {
    Element.call(this, s);
    
    this.SERVICE_WIDTH = 125;
    this.SERVICE_HEIGHT = 40;
    this.SERVICE_LIFELINE = 1200;
    this.SERVICE_ATTR = {
        fill: editor.COLORS.ENT
    };
    this.LIFE_ATTR = {
        stroke: editor.COLORS.ENT_BG,
        strokeWidth: 6
    };
    
    this.snap = s;
    this.name = name;
    
    this.textW = editor.measureText(this.name, "20px Arial") + 15;    
    if(!nextTo) {
        this.x = 30;
        this.y = 0;
    } else {
        this.x = nextTo.x + nextTo.textW + 50;
        this.y = nextTo.y;
    }

    this.neighbor = nextTo;
    
    this.callsFromEntity = [];
}

Entity.prototype = Object.create(Element.prototype);
Entity.prototype.constructor = Entity;

Entity.prototype.draw = function() {	
    var lineX = this.x + (this.textW/2);
    var lineY = this.y + this.SERVICE_HEIGHT;
    this.lifeLine = this.snap.line(
        lineX, lineY,
        lineX, lineY + this.SERVICE_LIFELINE
    );
    this.lifeLine.attr(this.LIFE_ATTR);
    
    if(!editor.readonly) {
        this.lifeLine.addClass("clickable");
        this.lifeLine.click(this.onElementClick.bind(this, this));
    }
    

    this.rect = this.snap.rect(
        this.x, this.y,
        this.textW, this.SERVICE_HEIGHT
    );
    this.rect.attr(this.SERVICE_ATTR);
    

    // TODO : fix the centering
    var size = this.name.length * 5;
    this.text = this.snap.text(
        this.rect.getBBox().cx - size,
        this.rect.getBBox().cy + size / 10,
        this.name
    );
    this.text.attr({
      fontFamily: 'Arial',
      fontSize: 20,
      fill: editor.COLORS.TEXT,
      textAnchor: 'left'
    });

    editor.lowerLayer.add(this.lifeLine);
    editor.lowerLayer.add(this.rect);
    editor.lowerLayer.add(this.text);
};

Entity.prototype.getObject = function() {
    return this.lifeLine;
};

Entity.prototype.getWorkflowCode = function() {
    return "";
};

Entity.prototype.getServiceName = function() {
    return this.name;
};

Entity.prototype.push = function(el) {
    var compare = function(p1, p2) {
        var x1 = p1.getObject().getBBox().x,
            y1 = p1.getObject().getBBox().y,
            x2 = p2.getObject().getBBox().x,
            y2 = p2.getObject().getBBox().y;

        if(x1 <= x2 && y1 <= y2 ||
           y1 <= y2 && x1 >= x2) {
            return 1;
        } else {
            return -1;
        }
    };

    var locationOf = function (element, array, start, end) {
        start = start || 0;
        end = end || array.length;
        var pivot = parseInt(start + (end - start) / 2, 10);

        //if (array[pivot] === element) return pivot;

        if (end - start <= 1) {
            return compare(array[pivot], element) == -1 ? pivot - 1 : pivot;
        } else if (compare(array[pivot], element) == 1) {
            return locationOf(element, array, pivot, end);
        } else {
            return locationOf(element, array, start, pivot);
        }
    };

    if(this.callsFromEntity.length === 0) {
        this.callsFromEntity.push(el);
    } else {
        this.callsFromEntity.splice(locationOf(el, this.callsFromEntity) + 1, 0, el);
    }
    
    console.log(el.y, this.lifeLine.getBBox().y2);
    if(el.y > this.lifeLine.getBBox().y2) {
    	console.log("Extending", el)
        this.extendLifeLine(el.y - this.lifeLine.getBBox().y2 + 200);
    }
};

Entity.prototype.extendLifeLine = function(length) {
	for(var i = 0; i < editor.services.length; i++) {
		var service = editor.services[i];
		
		service.lifeLine = this.snap.line(
			service.lifeLine.getBBox().x2,
			service.lifeLine.getBBox().y2,
			service.lifeLine.getBBox().x2,
			service.lifeLine.getBBox().y2 + length
	    );
		service.lifeLine.attr(this.LIFE_ATTR);

	    if(!editor.readonly) {
	    	service.lifeLine.addClass("clickable");
	    	service.lifeLine.click(this.onElementClick.bind(this, this));
	    }

	    editor.lowerLayer.add(service.lifeLine);
	}
};

Entity.prototype.expandDrawing = function(me) {
    var MINIMUM_FREE_SPACE = 100;

    if(this.lifeLine.getBBox().y2 < me.clientY + MINIMUM_FREE_SPACE) {
        editor.services.forEach(function(service) {
            service.extendLifeLine(MINIMUM_FREE_SPACE);
        });
    }

    this.snap.node.style.height = this.snap.node.style.clientHeight + 100 + "px";
};

Entity.prototype.toColor = function(stayInColor) {
    this.rect.attr(this.SERVICE_ATTR);

    this.composition.forEach(function(el) {
        el.toColor(stayInColor);
    });
};
