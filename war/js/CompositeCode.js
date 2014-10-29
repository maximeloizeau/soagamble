function CompositeCode(s, value, x, y) {
    Element.call(this, s);
    
    this.CALL_WIDTH = 90;
    this.CALL_HEIGHT = 35;
    this.ATTR = {
        fill: editor.COLORS.CODE
    };
    
    this.value = value;
    this.x = x;
    this.y = y;
}

CompositeCode.prototype = Object.create(Element.prototype);
CompositeCode.prototype.constructor = CompositeCode;

CompositeCode.prototype.draw = function() {
    this.rect = this.snap.rect(
       this.x - this.CALL_WIDTH/2,
       this.y - this.CALL_HEIGHT/2,
       this.CALL_WIDTH,
       this.CALL_HEIGHT
    );
      
    this.rect.attr(this.ATTR);
    
	this.rect.mouseover(this.mouseover.bind(this));
	this.rect.mouseout(this.mouseout.bind(this));

    this.composition.push(this.rect);
    
    this.mouseout();
};

CompositeCode.prototype.mouseover = function() {
	if(this.text)
		this.text.remove();

	this.rectOver = this.snap.rect(
		this.x - this.CALL_WIDTH/2,
		this.y - this.CALL_HEIGHT/2,
		editor.measureText(this.value, "12px Arial") + 10,
		this.CALL_HEIGHT
	);
	this.rectOver.attr(this.ATTR);

	this.text = this.snap.text(
		this.rect.getBBox().x + 5,
		this.rect.getBBox().y2 - 12,
		this.value
	);
	
	this.text.attr({
      fontFamily: 'Arial',
      fontSize: 12,
      fill: editor.COLORS.TEXT,
      textAnchor: 'left'
    });

	this.text.mouseover(this.mouseover.bind(this));
	this.text.mouseout(this.mouseout.bind(this));
};

CompositeCode.prototype.mouseout = function() {
	if(this.text)
		this.text.remove();
	
	if(this.rectOver)
		this.rectOver.remove();

	
	this.text = this.snap.text(
		this.rect.getBBox().x + 5,
		this.rect.getBBox().y2 - 12,
		this.value.substr(0, 12) + "..."
    );
	
    this.text.attr({
      fontFamily: 'Arial',
      fontSize: 12,
      fill: editor.COLORS.TEXT,
      textAnchor: 'left'
    });

	this.text.mouseover(this.mouseover.bind(this));
	this.text.mouseout(this.mouseout.bind(this));
};

CompositeCode.prototype.getObject = function() {
    return this.rect;
};

CompositeCode.prototype.getWorkflowCode = function() {
    return this.value;
};

CompositeCode.prototype.toColor = function(stayInColor) {
    this.rect.attr(this.ATTR);
    
    if(!stayInColor) {
		var backToGrey = function() {
			this.toGrey();
		}

		setTimeout(backToGrey.bind(this), 2*parser.waitTime/3);
	}
};
