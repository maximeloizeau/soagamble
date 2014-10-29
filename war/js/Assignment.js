function Assignment(s, name, x, y) {
    Element.call(this, s);
    
    this.WIDTH = 90;
    this.HEIGHT = 35;
    this.ATTR = {
        fill: editor.COLORS.INSTR
    };
    
    this.name = name;
    this.x = x;
    this.y = y;
}

Assignment.prototype = Object.create(Element.prototype);
Assignment.prototype.constructor = Assignment;

Assignment.prototype.draw = function() {
   this.rect = this.snap.rect(
       this.x - this.WIDTH/2,
       this.y - this.HEIGHT/2,
       this.WIDTH,
       this.HEIGHT
   );
   
    if(!editor.readonly)
      this.rect.click(this.onElementClick.bind(this, this));
   
    this.rect.attr(this.ATTR);
    
    this.text = this.snap.text(
        this.rect.getBBox().x + 5,
        this.rect.getBBox().y2 - 12,
        this.name
    );
    this.text.attr({
      fontFamily: 'Arial',
      fontSize: 12,
      fill: editor.COLORS.TEXT,
      textAnchor: 'left'
    });

    this.composition.push(this.rect);
};

Assignment.prototype.getObject = function() {
    return this.rect;
};

Assignment.prototype.getWorkflowCode = function() {
    return "";
};

Assignment.prototype.toColor = function() {
    this.rect.attr(this.ATTR);
};
