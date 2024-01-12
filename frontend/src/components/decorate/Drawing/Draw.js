class Draw {
  constructor(canvas, ctx) {
    this.canvas = canvas;
    this.ctx = ctx;
    this.coord = { x: 0, y: 0 };
  }

  reposition(event) {
    this.coord.x = event.clientX - this.canvas.offsetLeft;
    this.coord.y = event.clientY - this.canvas.offsetTop;
  }

  move(event) {
    this.ctx.beginPath();
    this.ctx.lineWidth = 5;
    this.ctx.lineCap = 'round';
    this.ctx.strokeStyle = '#ACD3ED';

    this.ctx.moveTo(this.coord.x, this.coord.y);
    this.reposition(event);
    this.ctx.lineTo(this.coord.x, this.coord.y);
    this.ctx.stroke();
  }

  getInfo() {
    const { colorSpace, height, width, data } = this.ctx.getImageData(
      0,
      0,
      this.canvas.width,
      this.canvas.height,
    );

    const rgbDatas = JSON.stringify(Array.from(data));
    const info = { data: rgbDatas, colorSpace, height, width };

    return info;
  }
}

export default Draw;
