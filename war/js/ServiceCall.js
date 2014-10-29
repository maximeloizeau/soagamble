function ServiceCall(s, name, entity, x, y) {
	Element.call(this, s);

	this.CALL_WIDTH = 90;
	this.CALL_HEIGHT = 35;
	this.ATTR = {
			fill: editor.COLORS.INSTR
	};

	this.name = name;
	this.serviceParent = entity;
	this.x = x;
	this.y = y;
}

ServiceCall.prototype = Object.create(Element.prototype);
ServiceCall.prototype.constructor = ServiceCall;

ServiceCall.prototype.draw = function() {
	this.rect = this.snap.rect(
			this.x - this.CALL_WIDTH/2,
			this.y - this.CALL_HEIGHT/2,
			this.CALL_WIDTH,
			this.CALL_HEIGHT
	);

	if(!editor.readonly)
		this.rect.click(this.onElementClick.bind(this, this));

	this.rect.attr(this.ATTR);

	this.rect.mouseover(this.mouseover.bind(this));
	this.rect.mouseout(this.mouseout.bind(this));    

	editor.higherLayer.add(this.rect);

	this.composition.push(this.rect);

	this.mouseout();
};

ServiceCall.prototype.mouseover = function() {
	if(this.text)
		this.text.remove();

	this.rectOver = this.snap.rect(
		this.x - this.CALL_WIDTH/2,
		this.y - this.CALL_HEIGHT/2,
		editor.measureText(this.name, "16px Arial") + 10,
		this.CALL_HEIGHT
	);
	this.rectOver.attr(this.ATTR);

	this.text = this.snap.text(
		this.rect.getBBox().x + 5,
		this.rect.getBBox().y2 - 12,
		this.name
	);
	this.text.attr({
		fontFamily: 'Arial',
		fontSize: 16,
		fill: editor.COLORS.TEXT,
		textAnchor: 'left'
	});

	this.text.mouseover(this.mouseover.bind(this));
	this.text.mouseout(this.mouseout.bind(this));
};

ServiceCall.prototype.mouseout = function() {
	if(this.text)
		this.text.remove();
	
	if(this.rectOver)
		this.rectOver.remove();

	this.text = this.snap.text(
			this.rect.getBBox().x + 5,
			this.rect.getBBox().y2 - 12,
			this.name.substr(0, 7) + "..."
	);
	this.text.attr({
		fontFamily: 'Arial',
		fontSize: 16,
		fill: editor.COLORS.TEXT,
		textAnchor: 'left'
	});

	this.text.mouseover(this.mouseover.bind(this));
	this.text.mouseout(this.mouseout.bind(this));
};

ServiceCall.prototype.getObject = function() {
	return this.rect;
};

ServiceCall.prototype.getWorkflowCode = function() {
	var text = "";
	if(this.next instanceof Assignment) {
		text = text + this.next.name + " = ";
	}

	text = text + this.serviceParent.getServiceName() + "." + this.name;
	return text;
};

ServiceCall.prototype.toColor = function(stayInColor) {
	this.rect.attr(this.ATTR);

	if(!stayInColor) {
		var backToGrey = function() {
			this.toGrey();
		}

		setTimeout(backToGrey.bind(this), 2*parser.waitTime/3);
	}
};
